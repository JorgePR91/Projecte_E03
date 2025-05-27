
package application;

import java.io.File;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Fitxers {

public File demanarRuta(Scanner entrada) {
		String miss = "Escriu el nom de la ruta on guardar:";
		String ruta = entrada.nextLine().trim();

//COMPROVAR QUE LA RUTA ÉS ACCEPTABLE
//Font: https://studyeasy.org/es/course-articles/java-articles-es/s14l04-verificar-el-sistema-operativo-en-java/
		String w = "[<>:\\\"|?*]";
		String m = "[:]";
		String l = "[/\\0]";

		String os = System.getProperty("os.name").toLowerCase();
		boolean rutaCorrecta = false;

		if (os.contains("win")) {
			rutaCorrecta = comprovacioRuta(w, ruta, os);
		} else if (os.contains("mac")) {
			rutaCorrecta = comprovacioRuta(m, ruta, os);
		} else if (os.contains("nix") || os.contains("nux") || os.contains("untu")) {
			rutaCorrecta = comprovacioRuta(l, ruta, os);
		} else {
			System.err.println("Sistema Operatiu desconegut.");
		}

		File f;
		if (!rutaCorrecta)
			f = demanarRuta(entrada);
		else
			f = new File(ruta);

		return f;
	}

	public boolean comprovacioRuta(String p, String ruta, String os) {
		boolean resultat = false;
		String separator = "" + File.separator;
		Pattern patro = Pattern.compile(p);
//PRIMER ERROR: File.separator no es pot clavar dins de l'split perquè no és exàctament una cadena

		if (os.contains("win")) {
			int index = ruta.indexOf(':');
			if (index >= 0)
				ruta = ruta.substring(index + 1);
			separator = "\\" + separator;
		}

		String[] noms = ruta.split(separator);

		if (noms.length == 1) {
			Matcher buscador = patro.matcher(noms[0]);

			if (buscador.find()) {
				System.err.println("El nom de la carpeta o fitxer " + noms[0] + " no és correcte.");
			} else
				resultat = true;

		} else {
			for (int i = 0; i < noms.length; i++) {
				Matcher buscador = patro.matcher(noms[i]);
				resultat = !buscador.find();

				if (!resultat) {
					System.err.println("El nom de la carpeta o fitxer " + noms[i] + " no és correcte.");
					break;
				}
			}
		}

		return resultat;
	}

	public boolean comprovarArxiu(File a) {
		boolean resultat = false;

		if (a.exists()) {
			if (a.isFile()) {
				System.err.println(a + " és el nom d'un fitxer.");
			} else {
				resultat = true;
			}
		} else {
			System.err.println("La carpeta " + a.getName() + " no existeix.");
		}

		return resultat;
	}

	public void eliminar(File arxiu) {
		File[] arbre = arxiu.listFiles();
		System.out.println("La carpeta " + arxiu.getName() + " té " + arbre.length + " elements.");

		for (File arx : arbre) {

			if (arx.isDirectory()) {
				eliminar(arx);
			}
			else {
				try {
					arx.delete();
					System.out.println("Eliminat el fitxer "+arx.getAbsolutePath());
				} catch (Exception e) {
					System.err.println(e);
				}
			}
		}

		try {
			arxiu.delete();
			System.out.println("Eliminada la carpeta "+arxiu.getAbsolutePath());
		} catch (Exception e) {
			System.err.println(e);
		}

	}
}