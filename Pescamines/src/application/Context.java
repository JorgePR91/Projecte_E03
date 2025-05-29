package application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;

public class Context implements Serializable {
	private static final long serialVersionUID = 1L;

	protected static int comptador;
	protected BooleanProperty partida = new SimpleBooleanProperty(true);
	protected static int mines;
	protected static int lliures;
	protected static transient Label caixaMines;
	protected static String dificultat;
	protected static int tamany;
	protected static transient Random alea = new Random();

	// GETTERS I SETTERS
	public static int getComptador() {
		return comptador;
	}

	public static void setComptador(int comptador) {
		Context.comptador = comptador;
	}

	public static String getDificultat() {
		return dificultat;
	}

	public static void setDificultat(String dificultat) {
		Context.dificultat = dificultat;
	}

	public static int getMines() {
		return mines;
	}

	public static void setMines(int mines) {
		Context.mines = mines;
	}

	public static int getTamany() {
		return tamany;
	}

	public static void setTamany(int tamany) {
		Context.tamany = tamany;
	}

	public static Random getAlea() {
		return alea;
	}

	public static void setAlea(Random alea) {
		Context.alea = alea;
	}

	public static Label getCaixaMines() {
		return caixaMines;
	}

	public static void setCaixaMines(Label caixaMines) {
		Context.caixaMines = caixaMines;
	}

	public BooleanProperty getPartida() {
		return partida;
	}

	public void setPartida(boolean estat) {
		partida.set(estat);
	}

	public static int getLliures() {
		return lliures;
	}

	public static void setLliures(int lliures) {
		Context.lliures = lliures;
	}

	// METODES
	public static Tauler crearTauler(String dificultat) {
		setDificultat(dificultat.toLowerCase());

		switch (dificultat) {
		case "fàcil" -> {
			tamany = 9;
			mines = tamany;
		}
		case "normal" -> {
			tamany = 10;
			mines = tamany;
		}
		case "difícil" -> {
			tamany = 15;
			mines = tamany;
		}
		default -> {
			setDificultat("normal");
			tamany = 10;
			mines = tamany;
		}
		}
		comptador = mines;
		caixaMines = new Label();
		caixaMines.setText("Antimines\n" + Context.comptador + "/" + Context.tamany);

		return new Tauler(tamany, tamany);
	}

	// OMPLIR BOMBES i OMPLIR CASELLES LLIURES
	public Casella[][] assignarMines(Casella[][] c, int tamany, String dificultat) {
//		int nMines;
//		//decidir bombes
//		if(dificultat == "f") {
//			nMines=10;
//		} else if(dificultat == "d") {
//			nMines=15;
//		} else {
//			nMines=12;
//		}

		// col·locar bombes
		for (int b = 0; b < comptador;) {

			int x = alea.nextInt(tamany);
			int y = alea.nextInt(tamany);

			if (c[x][y] == null) {
				c[x][y] = new Mina(x, y, c);
				System.out.println("Mina en: " + x + ", " + y);
				b++;
			}

		}
		System.out.println("------------------------");

		// col·locar caselles
		for (int o = 0; o < c.length; o++) {
			for (int m = 0; m < c[o].length; m++) {
				if (c[o][m] == null) {
					int nombre = 0;
					nombre = recompteMines(c, o, m);
					if (nombre > 0)
						c[o][m] = new Lliure(nombre, o, m, c, this);
					else
						c[o][m] = new Lliure(o, m, c, this);
					lliures++;
				}
			}
		}
		return c;
	}

	// RECOMPTE DE MINES
	public static int recompteMines(Casella[][] c, int a, int b) {
		int comptador = 0;

		// CANTO SUP ESQUERRE
		if (a == 0 && b == 0) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// CANTO INF ESQUERRE
		else if (a == c.length - 1 && b == 0) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// CANTO SUP DRET
		else if (a == 0 && b == c.length - 1) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// CANTO INF DRET
		else if (a == c.length - 1 && b == c.length - 1) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// PRIMERA FILA
		else if (a == 0 && (b != 0 && b != c.length - 1)) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// ÚLTIMA FILA
		else if (a == c.length - 1 && (b != 0 && b != c.length - 1)) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// PRIMERA COLUMNA
		else if (b == 0 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// ULTIMA COLUMNA
		else if (b == c.length - 1 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}

		} else {
			// RESTA
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}

		return comptador;
	}

	public static void buidar(Tauler t) {
		Casella[][] c = t.getCaselles();

		for (int fil = 0; fil < c.length; fil++) {
			for (int col = 0; col < c.length; col++) {

				c[fil][col] = null;
			}

		}
	}

	public static boolean disminuirComptador() {
		if (comptador > 0) {
			comptador--;
			caixaMines.setText("Antimines\n" + Context.comptador + "/" + Context.tamany);

//			Platform.runLater() encola la operación en el hilo de JavaFX, donde los listeners y la UI pueden reaccionar correctamente. Sin esto, los cambios desde hilos secundarios pueden causar:
//			Listeners que no se ejecutan.
//			Excepciones como IllegalStateException: Not on FX application thread.
//			Aunque usabas Platform.runLater() para Context.partida.set(), los cambios visuales directos (boto.setVisible(), etc.) se hacían en el hilo secundario, lo que puede causar:
//
//				Comportamiento impredecible.
//
//				Errores silenciosos.
//
//				Actualizaciones UI que no se reflejan.
//			El error ocurre porque estás intentando modificar la variable local descoberta dentro de la expresión lambda de Platform.runLater(), lo cual no está permitido en Java. Las variables locales usadas en lambdas deben ser efectivamente final (no modificables). Aquí te explico cómo solucionarlo:
//			Problema Detectado
//			Variable no final: descoberta se declara como variable local y luego se intenta modificar dentro del lambda.
//
//			Ámbito de variables: Las variables locales usadas en lambdas deben ser final o efectivamente final.
	
			return true;
		} else
			return false;

//Ens estalviaria molta feina fer-ho amb la biblioteca Property, de JavaFX.
	}

	public void comprovarPartida() {
		if (lliures == 0 && comptador == 0) {
			System.out.println("Partida acabada");
			setPartida(false);
		}
	}

	public void disminuirLliures() {
	    lliures--;
	    comprovarPartida();
	}
	
	public static boolean augmentarComptador() {
		if (comptador < mines) {
			comptador++;
			caixaMines.setText("Antimines\n" + Context.comptador + "/" + Context.tamany);
			return true;
		} else
			return false;
	}

	public static boolean serialitzacioTauler(Tauler t, String id) {

		// https://www.discoduroderoer.es/serializacion-de-objetos-en-java/
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./Partides/" + id))) {
			oos.writeObject(t);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
