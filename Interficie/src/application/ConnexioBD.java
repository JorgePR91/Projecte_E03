package application;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnexioBD {
	static Connection connexio;
	
	public Connection getConnexio() {
		return connexio;
	}

	public static void setConnexio(Connection connexio) {
		ConnexioBD.connexio = connexio;
	}

	public void connectarBD(String baseD) throws SQLException {
		File BD = new File("BD");
		if (!BD.exists()) {
			BD.mkdir();
		} else if (BD.exists() && !BD.isDirectory()) {
			BD.mkdir();
		}

		try {
			Class.forName("org.h2.Driver");
			String url = "jdbc:h2:file:./BD/" + baseD;
			String usuari = "root";
			String contrasenya = "";
			connexio = DriverManager.getConnection(url, usuari, contrasenya);
			System.out.println("Connexió establerta amb la base de dades: " + baseD);

		} catch (ClassNotFoundException e) {
			throw new SQLException("Error al cargar el driver H2: " + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// https://java.19633.com/es/Java-2/1002019061.html
	
	public void mostrarFilaBD(Statement s, String taula) {

		String sentencia = "SELECT * FROM " + taula+" GROUP BY id, MAX(temps) ORDER BY temps;";

		try {
			ResultSet res = s.executeQuery(sentencia);
			ResultSetMetaData rsm = res.getMetaData();
			int i = 0;
			res.last();
			
			while (i < rsm.getColumnCount()) {
				i++;
				if(i!=rsm.getColumnCount())

				System.out.print(rsm.getColumnName(i) + ": "+conversioDadesBD(res, rsm.getColumnTypeName(i), rsm.getColumnName(i))+", ");
				else
					System.out.println(rsm.getColumnName(i) + ": "+conversioDadesBD(res, rsm.getColumnTypeName(i), rsm.getColumnName(i))+".");
			}
			
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
