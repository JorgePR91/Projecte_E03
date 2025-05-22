package application;

import java.util.Random;

public class Context {

	protected static int comptador;
	protected static String dificultat;
	protected static int tamany = 0;
	protected static Random alea = new Random();

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

	// METODES
	public static Tauler crearTauler(String dificultat) {

		switch (dificultat) {
		case "f" -> {
			tamany = 9;
			comptador = tamany;
		}
		case "n" -> {
			tamany = 10;
			comptador = tamany;
		}
		case "d" -> {
			tamany = 15;
			comptador = tamany;
		}
		default -> {
			dificultat = "n";
			tamany = 10;
			comptador = tamany;
		}
		}
		System.out.println(comptador);
		return new Tauler(tamany, tamany);

	}

//	//OMPLIR BOMBES i OMPLIR CASELLES LLIURES
//	public static Casella[][] assignarMines(Casella[][] c, int tamany, String dificultat){
////		int nMines;
////		//decidir bombes
////		if(dificultat == "f") {
////			nMines=10;
////		} else if(dificultat == "d") {
////			nMines=15;
////		} else {
////			nMines=12;
////		}
//		
//		//col·locar bombes
//		for(int b=0;b<comptador;) {
//			
//			int x = alea.nextInt(tamany);
//			int y = alea.nextInt(tamany);
//			
//			if(c[x][y]==null) {
//				c[x][y] = new Mina(x, y, c);
//				b++;
//			}
//		}
//		
//		//col·locar caselles
//		for(int o=0;o<c.length;o++) {
//			for(int m=0;m<c[o].length;m++) {
//				if(c[o][m]==null) {
//					int nombre = 0;
//					nombre = recompteMines(c,o,m);
//					if(nombre > 0)
//					c[o][m] = new Lliure(nombre, o, m, c);
//					else
//						c[o][m] = new Lliure(o, m, c);
//
//				}
//			}
//		}
//		return c;
//	}
//
//	//RECOMPTE DE MINES
//	public static int recompteMines(Casella[][] c, int a, int b) {
//		int comptador = 0;
//
//		// CANTO SUP ESQUERRE
//		if (a == 0 && b == 0) {
//			for (int k = a; k <= a + 1; k++) {
//				for (int l = b; l <= b + 1; l++)
//					if (c[k][l] instanceof Mina)
//						comptador++;
//			}
//		}
//		// CANTO INF ESQUERRE
//		else if (a == c.length - 1 && b == 0) {
//			for (int k = a - 1; k <= a; k++) {
//				for (int l = b; l <= b + 1; l++)
//					if (c[k][l] instanceof Mina)
//						comptador++;
//			}
//		}
//		// CANTO SUP DRET
//		else if (a == 0 && b == c.length - 1) {
//			for (int k = a; k <= a + 1; k++) {
//				for (int l = b - 1; l <= b; l++)
//					if (c[k][l] instanceof Mina)
//						comptador++;
//			}
//		}
//		// CANTO INF DRET
//		else if (a == c.length - 1 && b == c.length - 1) {
//			for (int k = a - 1; k <= a; k++) {
//				for (int l = b - 1; l <= b; l++)
//					if (c[k][l] instanceof Mina)
//						comptador++;
//			}
//		}
//		// PRIMERA FILA
//		else if (a == 0 && (b != 0 && b != c.length - 1)) {
//			for (int k = a; k <= a + 1; k++) {
//				for (int l = b - 1; l <= b + 1; l++)
//					if (c[k][l] instanceof Mina)
//						comptador++;
//			}
//		}
//		// ÚLTIMA FILA
//		else if (a == c.length - 1 && (b != 0 && b != c.length - 1)) {
//			for (int k = a - 1; k <= a; k++) {
//				for (int l = b - 1; l <= b + 1; l++)
//					if (c[k][l] instanceof Mina)
//						comptador++;
//			}
//		}
//		// PRIMERA COLUMNA
//		else if (b == 0 && (a != 0 && a != c.length - 1)) {
//			for (int k = a - 1; k <= a + 1; k++) {
//				for (int l = b; l <= b + 1; l++)
//					if (c[k][l] instanceof Mina)
//						comptador++;
//			}
//		}
//		// ULTIMA COLUMNA
//		else if (b == c.length - 1 && (a != 0 && a != c.length - 1)) {
//			for (int k = a - 1; k <= a + 1; k++) {
//				for (int l = b - 1; l <= b; l++)
//					if (c[k][l] instanceof Mina)
//						comptador++;
//			}
//
//		} else {
//			// RESTA
//			for (int k = a - 1; k <= a + 1; k++) {
//				for (int l = b - 1; l <= b + 1; l++)
//					if (c[k][l] instanceof Mina)
//						comptador++;
//			}
//		}
//
//		return comptador;
//	}

	public static void buidar(Tauler t) {
		Casella[][] c = t.getCaselles();

		for (int fil = 0; fil < c.length; fil++) {
			for (int col = 0; col < c.length; col++) {

				c[fil][col] = null;
			}

		}
	}

	public static void disminuirComptador() {
		comptador--;
		// SampleController.CompAntimines.setText("Antimines\n"+comptador+"/"+tamany);
	}

	public static void ompllirLlenç(Casella[][] c) {

		for(int o=0;o<c.length;o++) {
		for(int m=0;m<c[o].length;m++) {
			if(c[o][m]==null) {
			c[o][m] = new Pixel (o, m);

			}
		}
	}

	}
}
