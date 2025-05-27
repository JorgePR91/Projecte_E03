package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public class ConnexioBD {
	static Connection connexio;
	
	public Connection getConnexio() {
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
			String contrasenya = "root";
			connexio = DriverManager.getConnection(url, usuari, contrasenya);
			
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
	
	public String capçaleres(String taula) {
		String[] aux;
		String camps;
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
	
	public int afegirDada(String taula, String[] valors) {
		int resultat = 0;
		int ncamps = 0;
		
		//Si el camp està en hasmap
		
		String sentencia = "INSERT INTO "+taula+" (" + capçaleres(taula) + ") VALUES (" + "?,".repeat(ncamps) + "?);";



		try (PreparedStatement ps = connexio.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);) {
			String[] aux = linia.split(",");
			
			String[] camps = capçaleres(ps, taula)

			
			
			for (int p = 0; p <= ncamps; p++) {

				if (aux[p].matches("^\\d+[^,]$")) {
					ps.setInt(p + 1, Integer.parseInt(aux[p]));
				} else if (aux[p].matches("^\\d+,\\d$")) {
					ps.setDouble(p + 1, Double.parseDouble(aux[p]));
				} else
					ps.setString(p + 1, aux[p]);
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
	

	public void tancarBD() throws SQLException {
		if (connexio != null) {
			connexio.close();
		}
	}

	public String conversioDadesBD(ResultSet res, String tipo, String columna) throws SQLException {
		String miss = "";
		switch (tipo) {
		case "INTEGER":
		case "INT":
			miss += "" + res.getInt(columna);
			break;
		case "NUMERIC":
		case "DOUBLE":
		case "DECIMAL":
			miss += "" + res.getDouble(columna);
			break;
		case "REAL":
		case "FLOAT":
			miss += "" + res.getFloat(columna);
			break;
		case "CHARACTER VARYING":
		case "DATE":
		case "VARCHAR2":
		case "VARCHAR":
			miss += res.getString(columna);
			break;
		case "CHAR":
			miss += "" + res.getCharacterStream(columna);
			break;
		default:
			System.err.println("Error: dada de la columna '" + columna + "' no identificada.");
		}

		return miss;
	}

}
