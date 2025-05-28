package application;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class ConnexioBD {
	static Connection connexio;
	
	public static Connection getConnexio() {
		return connexio;
	}

	public static void setConnexio(Connection connexio) {
		ConnexioBD.connexio = connexio;
	}

	public static void connectarBD() throws SQLException {

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://localhost:3306/ProjecteProg";
			String usuari = "root";
			String contrasenya = "";
			connexio = DriverManager.getConnection(url, usuari, contrasenya);
			System.out.println("Connexió establerta");
		} catch (ClassNotFoundException e) {
			throw new SQLException("Error al cargar el driver H2: " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//MÈTODE D'INTRODUIR DADES
	//MÈTODE DE MODIFICAR DADES(USUARI, PARTIDES GUARDADES?)
	//MÈTODE DE PUJAR DADES
	
	
	
	
	
	// https://java.19633.com/es/Java-2/1002019061.html
	
	public static String capçaleres(String taula) {
		String[] aux;
		String camps = "";
		String sentencia = "SELECT * FROM "+ taula+";";

		try (Statement s = connexio.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = s.executeQuery(sentencia);){
			
			ResultSetMetaData rsm = res.getMetaData();
			aux = new String[rsm.getColumnCount()];
			
			int i = 0;
			res.last();
			
			while (i < rsm.getColumnCount()) {
				i++;
				if(i!=rsm.getColumnCount())
				camps += rsm.getColumnName(i)+", ";
				else
					camps += rsm.getColumnName(i);
			}
			
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return camps;
	}
	
	public static int[] tipusCamp(String taula, String[] camps) {
		int tipus[] = null;
		String sentencia = "SELECT * FROM "+ taula+" LIMIT 1;";

		try (Statement s = connexio.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = s.executeQuery(sentencia);){
			
			ResultSetMetaData rsm = res.getMetaData();
			
			int i = 0;
			tipus = new int[rsm.getColumnCount()];
			res.first();
			
			for(int o =0;o<camps.length;o++) {
				while (i < rsm.getColumnCount()) {
					if(camps[o].equals(rsm.getColumnName(o))) {
						tipus[o] = rsm.getColumnType(i);
					}	
					i++;
				}
			}
			
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tipus;
	}
	
//HASMAP PER A CLAU VALOR;
	
	public static int afegirDada(String taula, String[] camps, String[] valors) {
		int resultat = 0;
		int[] tipusCamps = tipusCamp(taula, camps);
		
		if(valors.length == 0 || camps.length == 0) {
			return 0;
		}
		
		String sentencia = "INSERT INTO "+taula+" (" + camps.toString().replaceAll("\\[||\\]", "") + ") VALUES (" + "?,".repeat(camps.length-1) + "?);";

		try (PreparedStatement ps = connexio.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);) {
		
			
			for(int o=0;o<valors.length;o++) {
				conversioDadesBD(ps, tipusCamps[o], o+1, valors[o]);
			}

			ps.executeUpdate();
			
			ResultSet r = ps.getGeneratedKeys();
			    if (r.next()) {
			        resultat = r.getInt(1); 
			    }
			return resultat;
		} catch (SQLException e) {
			e.printStackTrace();
			return resultat;
		}

	}
	
//	public static int afegirDada(String taula, String[] valors) {
//		int resultat = 0;
//		int[] tipusCamps = tipusCamp(taula);
//		
//		String sentencia = "INSERT INTO "+taula+" (" + capçaleres(taula) + ") VALUES (" + "?,".repeat(valors.length-1) + "?);";
//
//		try (PreparedStatement ps = connexio.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);) {
//			
//			for(int o=0;o<valors.length;o++) {
//				conversioDadesBD(ps, tipusCamps[o], o+1, valors[o]);
//			}
//
//			ps.executeUpdate();
//			
//			ResultSet r = ps.getGeneratedKeys();
//			    if (r.next()) {
//			        resultat = r.getInt(1); 
//			    }
//			return resultat;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return resultat;
//		}
//
//	}
	

	public static void tancarBD() throws SQLException {
		if (connexio != null) {
			connexio.close();
		}
	}

	public static void conversioDadesBD(PreparedStatement ps, int tipus, int pos, String valors) throws SQLException {
		//https://stackoverflow.com/questions/12367828/how-can-i-get-different-datatypes-from-resultsetmetadata-in-java
		//Taula feta en base a un excel tret de Gemini.ia : https://docs.google.com/spreadsheets/d/1pl1vdyujL0XSCi0Mn-u80nAfBCb6mboEBOalzrkZibU/edit?usp=sharing
		
		String miss = "";
		
		switch (tipus) {
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.TINYINT:
			ps.setInt(pos, Integer.parseInt(valors.trim()));
			break;
		case Types.BIGINT:
			ps.setLong(pos, Long.parseLong(valors.trim()));
			break;
		case Types.BOOLEAN:
		case Types.BIT:
			ps.setBoolean(pos, Boolean.parseBoolean(valors.trim()));
			break;
		case Types.NUMERIC:
		case Types.DECIMAL:
		case Types.DOUBLE:
			ps.setDouble(pos, Double.parseDouble(valors.trim()));
			break;
		case Types.REAL:
		case Types.FLOAT:
			ps.setFloat(pos, Float.parseFloat(valors.trim()));
			break;
		case Types.NCHAR:
		case Types.NVARCHAR:
		case Types.LONGNVARCHAR:
		case Types.VARCHAR:
		case Types.CHAR:
		case Types.LONGVARCHAR:
			ps.setString(pos, valors.trim());
			break;
		case Types.DATE:
			ps.setDate(pos, java.sql.Date.valueOf(valors.trim()));
			break;
		case Types.TIME:
			if(valors.trim().matches("\\d\\d:\\d\\d")) {
				String tf = "00:"+valors.trim();
				ps.setTime(pos, java.sql.Time.valueOf(tf));
			} else {
				ps.setTime(pos, java.sql.Time.valueOf(valors.trim()));
			}
			break;
//		case Types.TIMESTAMP:
//			ps.setTimestamp(pos, java.sql.Timestamp.valueOf(valors.trim()));
//			break;
		case Types.ARRAY:
			ps.setObject(pos, valors);
			break;		
		case Types.STRUCT:
			ps.setObject(pos, valors.trim());
			break;
		case Types.JAVA_OBJECT:
			ps.setObject(pos, valors.trim());
			break;
		default:
			System.err.println("Error: de tipus de dades.");
		}
		
		//CLAUDE PROPOSA CLAVAR-LO DINS D'UN TRY/CATCH
	}

}
