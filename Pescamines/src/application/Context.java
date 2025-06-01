package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;

public class Context implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int comptador;
	protected transient BooleanProperty partida = new SimpleBooleanProperty(true);
	protected int mines;
	protected int lliures;
	protected transient Label caixaMines;
	protected String dificultat;
	protected int tamany;
	protected int temps;
	protected transient Random alea = new Random();
	protected Casella[][] caselles;

	private void readObject(ObjectInputStream ois) {
		try {
			ois.defaultReadObject();
			this.alea = new Random();
			this.partida = new SimpleBooleanProperty(true);
			this.caselles = (Casella[][]) ois.readObject();
		} catch (IOException e) {
			// TODO: handle exception
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
		}
	}

	public Context() {
		super();
	}

	// GETTERS I SETTERS
	public int getComptador() {
		return comptador;
	}

	public void setComptador(int comptador) {
		this.comptador = comptador;
	}

	public String getDificultat() {
		return dificultat;
	}

	public void setDificultat(String dificultat) {
		this.dificultat = dificultat;
	}

	public int getMines() {
		return mines;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}

	public int getTamany() {
		return tamany;
	}

	public void setTamany(int tamany) {
		this.tamany = tamany;
	}

	public Random getAlea() {
		return alea;
	}

	public void setAlea(Random alea) {
		this.alea = alea;
	}

	public Label getCaixaMines() {
		return caixaMines;
	}

	public void setCaixaMines(Label caixaMines) {
		this.caixaMines = caixaMines;
	}

	public BooleanProperty getPartida() {
		return partida;
	}

	public void setPartida(boolean estat) {
		this.partida.set(estat);
	}

	public int getLliures() {
		return lliures;
	}

	public void setLliures(int lliures) {
		this.lliures = lliures;
	}

	public int getTemps() {
		return temps;
	}

	public void setTemps(int temps) {
		this.temps = temps;
	}

	public void setPartida(BooleanProperty partida) {
		this.partida = partida;
	}

	public Casella[][] getCaselles() {
		return caselles;
	}

	public void setCaselles(Casella[][] caselles) {
		this.caselles = caselles;
	}

	// METODES
	public Tauler crearTauler(String dificultat, Context contxt) {
		setDificultat(dificultat);

		switch (dificultat) {
		case "Fàcil" -> {
			tamany = 9;
			mines = tamany;
		}
		case "Normal" -> {
			tamany = 15;
			mines = tamany;
		}
		case "Difícil" -> {
			tamany = 20;
			mines = tamany;
		}
		default -> {
			setDificultat("Normal");
			tamany = 15;
			mines = tamany;
		}
		}
		comptador = mines;
		if (caixaMines == null) {
			caixaMines = new Label();
		}
		caixaMines.setText("Antimines\n" + comptador + "/" + tamany);

		return new Tauler(tamany, tamany);
	}

	// OMPLIR BOMBES i OMPLIR CASELLES LLIURES
	public Casella[][] assignarMines(Casella[][] c, int tamany, String dificultat, Context contxt) {
//		int nMines;
//		//decidir bombes
//		if(dificultat == "f") {
//			nMines=10;
//		} else if(dificultat == "d") {
//			nMines=15;
//		} else {
//			nMines=12;
//		}

		if (c != null) {
			System.out.println("No hi ha matriu");
		}

		// col·locar bombes
		for (int b = 0; b < comptador;) {

			int x = alea.nextInt(tamany);
			int y = alea.nextInt(tamany);

			if (c[x][y] == null) {
				c[x][y] = new Mina(x, y, c, contxt);
				System.out.println("Mina en: " + x + ", " + y);
				b++;
			}

		}
		System.out.println("------------------------");
		lliures = 0;
		// col·locar caselles
		for (int o = 0; o < c.length; o++) {
			for (int m = 0; m < c[o].length; m++) {
				if (c[o][m] == null) {
					int nombre = 0;
					nombre = recompteMines(c, o, m);
					if (nombre > 0)
						c[o][m] = new Lliure(nombre, o, m, c, contxt);
					else
						c[o][m] = new Lliure(o, m, c, contxt);
					lliures++;
				}
			}
		}
		return c;
	}

	// RECOMPTE DE MINES
	public int recompteMines(Casella[][] c, int a, int b) {
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

	public void buidar(Tauler t) {
		Casella[][] c = t.getCaselles();

		for (int fil = 0; fil < c.length; fil++) {
			for (int col = 0; col < c.length; col++) {

				c[fil][col] = null;
			}

		}
	}

	public boolean disminuirComptador() {
		if (comptador > 0) {
			comptador--;
			caixaMines.setText("Antimines\n" + comptador + "/" + tamany);

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
			setPartida(false);
		}
	}

	public void disminuirLliures() {
		lliures--;
	}

	public boolean augmentarComptador() {
		if (comptador < mines) {
			comptador++;
			caixaMines.setText("Antimines\n" + comptador + "/" + tamany);
			return true;
		} else
			return false;
	}

	public boolean serialitzacioPartida(Context cntxt, String id) {
		// https://infogonzalez.com/2024/10/titulo-serializacion-de-objetos-en-java.html

		// ¿Crear /Partides si no existeix com fem amb les BD?
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./Partides/" + id + ".dat"))) {
			oos.writeObject(cntxt);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Context desserialitzacioTauler(File f) {

		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
			Context c = (Context) ois.readObject();
			return c;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return new Context();
		}

	}
}
