package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Arrays;

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
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/ProjecteProg";
			String usuari = "Root";
			String contrasenya = "root";
			connexio = DriverManager.getConnection(url, usuari, contrasenya);
			System.out.println("Connexió establerta");
		} catch (ClassNotFoundException e) {
			throw new SQLException("Error al cargar el driver H2: " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// -----------------------------------------------------------------------------------------------
	public static boolean connectarBD(String baseD) {
		String url = "jdbc:mysql://localhost:3306/" + baseD;
		String usuari = "Root";
		String contrasenya = "root";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			connexio = DriverManager.getConnection(url, usuari, contrasenya);

			System.out.println("Connexió establerta amb la base de dades: " + baseD);
			return true;
		} catch (ClassNotFoundException e) {
			e.getException();
			return false;
		} catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
			System.out.println("Obs.: Comprova que el Xampp, Lampp, Wampp o programa servidor de BD estiga funcionant");
			return false;
		}

	}

	public static boolean connectarScriptBD(String direccio) {
		String url = "jdbc:mysql://localhost:3306/";
		String script = llegirFitxerBD(direccio);
		String[] aux = script.split(";");
		String usuari = "Root";
		String contrasenya = "root";

		try (Connection c = DriverManager.getConnection(url, usuari, contrasenya); Statement s = c.createStatement();) {
			ConnexioBD.connexio = c;
			Class.forName("com.mysql.cj.jdbc.Driver");
			for (String line : aux) {
				line = line + ";";
				if (!line.matches("^\\s*#.*$") || !line.matches("^\\s*--.*$")) {
					int execucio = s.executeUpdate(line);
					System.out.println(execucio);

				}
			}
			System.out.println("Base de dades creada amb script.");
			return true;
		} catch (ClassNotFoundException e) {
			e.getException();
			System.err.println("Error en la class de l'script BD: " + e);
			return false;
		} catch (SQLException e) {
			System.err.println("Error en la connexió de l'script BD");
			e.printStackTrace();
			return false;
		}
	}
//--------------------------------------------------------------------------------------------------	

	// MÈTODE DE MODIFICAR DADES(USUARI, PARTIDES GUARDADES?)
	// MÈTODE DE PUJAR DADES

	// https://java.19633.com/es/Java-2/1002019061.html

	public static String capçaleres(String taula) {
		String camps = "";
		String sentencia = "SELECT * FROM " + taula + ";";

		try (Statement s = connexio.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = s.executeQuery(sentencia);) {

			ResultSetMetaData rsm = res.getMetaData();

			int i = 0;
			res.last();

			while (i < rsm.getColumnCount()) {
				i++;
				if (i != rsm.getColumnCount())
					camps += rsm.getColumnName(i) + ", ";
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
		String sentencia = "SELECT * FROM " + taula + " LIMIT 1;";

		try (Statement s = connexio.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet res = s.executeQuery(sentencia);) {

			ResultSetMetaData rsm = res.getMetaData();

			tipus = new int[camps.length];

			for (int o = 0; o < camps.length; o++) {
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					if (camps[o].equals(rsm.getColumnName(i))) {
						tipus[o] = rsm.getColumnType(i);
						break;
					}
				}
			}

			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return tipus;
	}

	public static int insertarDades(String taula, String[] camps, String[] valors) {
		int resultat = 0;
		int[] tipusCamps = tipusCamp(taula, camps);

		if (valors.length == 0 || camps.length == 0) {
			return 0;
		}

		String sentencia = "INSERT INTO " + taula + " (" + Arrays.toString(camps).replaceAll("\\[|\\]", "")
				+ ") VALUES (" + "?,".repeat(camps.length - 1) + "?);";

		try (PreparedStatement ps = connexio.prepareStatement(sentencia, Statement.RETURN_GENERATED_KEYS);) {

			for (int o = 0; o < valors.length; o++) {
				conversioDadesBD(ps, tipusCamps[o], o + 1, valors[o]);
			}

			ps.executeUpdate();

			ResultSet r = ps.getGeneratedKeys();
			if (r.next()) {
				resultat = r.getInt(1);
			}
			System.out.println("S'han inserit " + resultat + " files.");
			return resultat;
		} catch (SQLException e) {
			e.printStackTrace();
			return resultat;
		}

	}

	public static String ultimaID(String taula, String campId) {
		String resultat;
		String sentencia = "SELECT * FROM " + taula + " ORDER BY dataInsercio DESC LIMIT 1;";

		try (Statement s = connexio.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				ResultSet r = s.executeQuery(sentencia);) {

			if (r.next()) {
				int id = r.getInt(campId);
				String usuari = r.getString("usuari");
				resultat = "" + id + "_" + usuari;
			} else
				resultat = null;

			return resultat;
		} catch (SQLException e) {
			e.printStackTrace();
			return resultat = null;
		}

	}

	public static String[] ranquing(String taula, String[] camps, String criteri) {
		String[] resultat = null;

		if (camps.length == 0) {
			return new String[0];
		}

		String sentencia = "SELECT " + Arrays.toString(camps).replaceAll("\\[|\\]", "") + " FROM " + taula
				+ " ORDER BY " + criteri + ";";
		System.out.println(sentencia);
		try (Statement s = connexio.createStatement(ResultSet.CONCUR_READ_ONLY, ResultSet.TYPE_FORWARD_ONLY);) {
			
			ResultSet res = s.executeQuery(sentencia);
			int pos = 0;
			System.out.println("entra en ranquing, "+res.getFetchSize());
			if (res.next()) {
				
				int[] tipusCamp = tipusCamp(taula, camps);

				resultat = new String[camps.length];

				for (int i = 0; i < tipusCamp.length; i++) {
					
					resultat[i] = obtencioDadesBD(res, tipusCamp[i], camps[i]);
					System.out.println(resultat[i]+", "+tipusCamp[i]+", "+camps[i]);

				}
	
			} else
				return new String[0];


			return resultat;
		} catch (SQLException e) {
			e.printStackTrace();
			return new String[0];
		}

	}

	public static String obtencioDadesBD(ResultSet rs, int tipus, String col) throws SQLException {
		// https://stackoverflow.com/questions/12367828/how-can-i-get-different-datatypes-from-resultsetmetadata-in-java
		// Taula feta en base a un excel tret de Gemini.ia :
		// https://docs.google.com/spreadsheets/d/1pl1vdyujL0XSCi0Mn-u80nAfBCb6mboEBOalzrkZibU/edit?usp=sharing
		String valor = null;

		switch (tipus) {
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.TINYINT:
			return "" + rs.getInt(col);
		case Types.BIGINT:
			return "" + rs.getLong(col);
		case Types.BOOLEAN:
		case Types.BIT:
			return "" + rs.getBoolean(col);
		case Types.NUMERIC:
		case Types.DECIMAL:
		case Types.DOUBLE:
			return "" + rs.getDouble(col);
		case Types.REAL:
		case Types.FLOAT:
			return "" + rs.getFloat(col);
		case Types.NCHAR:
		case Types.NVARCHAR:
		case Types.LONGNVARCHAR:
		case Types.VARCHAR:
		case Types.CHAR:
		case Types.LONGVARCHAR:
			return rs.getString(col);
		case Types.DATE:
			return "" + rs.getDate(col);
		case Types.TIME:

			return "" + rs.getTime(col);
//		case Types.TIMESTAMP:
//			rs.getTimestamp(pos, java.sql.Timestamp.valueOf(valors));
//			break;
//		case Types.ARRAY:
//			rs.getObject(pos, valors);
//			break;		
//		case Types.STRUCT:
//			rs.getObject(pos, valors);
//			break;
//		case Types.JAVA_OBJECT:
//			rs.getObject(pos, valors);
//			break;
		default:
			System.err.println("Error: de tipus de daders.");
			return "No existent";
		}

		// CLAUDE PROPOSA CLAVAR-LO DINS D'UN TRY/CATCH
	}

	public static void conversioDadesBD(PreparedStatement ps, int tipus, int pos, String valors) throws SQLException {
		// https://stackoverflow.com/questions/12367828/how-can-i-get-different-datatypes-from-resultsetmetadata-in-java
		// Taula feta en base a un excel tret de Gemini.ia :
		// https://docs.google.com/spreadsheets/d/1pl1vdyujL0XSCi0Mn-u80nAfBCb6mboEBOalzrkZibU/edit?usp=sharing

		if (!valors.isEmpty() || valors != null)
			valors.trim();

		switch (tipus) {
		case Types.INTEGER:
		case Types.SMALLINT:
		case Types.TINYINT:
			ps.setInt(pos, Integer.parseInt(valors));
			break;
		case Types.BIGINT:
			ps.setLong(pos, Long.parseLong(valors));
			break;
		case Types.BOOLEAN:
		case Types.BIT:
			ps.setBoolean(pos, Boolean.parseBoolean(valors));
			break;
		case Types.NUMERIC:
		case Types.DECIMAL:
		case Types.DOUBLE:
			ps.setDouble(pos, Double.parseDouble(valors));
			break;
		case Types.REAL:
		case Types.FLOAT:
			ps.setFloat(pos, Float.parseFloat(valors));
			break;
		case Types.NCHAR:
		case Types.NVARCHAR:
		case Types.LONGNVARCHAR:
		case Types.VARCHAR:
		case Types.CHAR:
		case Types.LONGVARCHAR:
			ps.setString(pos, valors);
			break;
		case Types.DATE:
			ps.setDate(pos, java.sql.Date.valueOf(valors));
			break;
		case Types.TIME:
			if (valors.matches("\\d\\d:\\d\\d")) {
				String tf = "00:" + valors;
				ps.setTime(pos, java.sql.Time.valueOf(tf));
			} else {
				ps.setTime(pos, java.sql.Time.valueOf(valors));
			}
			break;
//		case Types.TIMESTAMP:
//			ps.setTimestamp(pos, java.sql.Timestamp.valueOf(valors));
//			break;
//		case Types.ARRAY:
//			ps.setObject(pos, valors);
//			break;		
//		case Types.STRUCT:
//			ps.setObject(pos, valors);
//			break;
//		case Types.JAVA_OBJECT:
//			ps.setObject(pos, valors);
//			break;
		default:
			System.err.println("Error: de tipus de dades.");
		}

		// CLAUDE PROPOSA CLAVAR-LO DINS D'UN TRY/CATCH
	}

	public static String llegirFitxerBD(String direc) {
		String script = "";
		String aux = "";
		File f = new File(direc);

		if (f.exists() && f.isFile()) {

			try (FileReader fr = new FileReader(f);) {
				BufferedReader br = new BufferedReader(fr);

				while (br.ready()) {
					aux = br.readLine();
					if (!aux.matches("^--.*$"))
						script += aux;
				}
				fr.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else
			System.out.println("Error: la ruta indicada no és un arxiu o no existeix.");
		return script;
	}

	public static void tancarBD() throws SQLException {
		if (connexio != null) {
			connexio.close();
		}
	}
}
