package appEquip03;

import java.util.Random;

public interface MetodeTauler {
	//OMPLIR BOMBES i OMPLIR CASELLES LLIURES
	public static Casella[][] assignarMines(Casella[][] c, int tamany, Random alea, String dificultat){

		//nombre de bombes com atribut?
		int nMines = 0;
		//decidir bombes
		if(dificultat == "f") {
			nMines=10;
		} else if(dificultat == "d") {
			nMines=15;
		} else {
			nMines=12;
		}
		
		//col·locar bombes
		for(int b=0;b<nMines;) {
			
			int x = alea.nextInt(tamany);
			int y = alea.nextInt(tamany);
			
			if(c[x][y]==null) {
				c[x][y] = new Mina(x, y);
				b++;
			}
		}
		//col·locar caselles
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
				if(c[o][m]==null) {
					int nombre = 0;
					nombre = recompteMines(c,o,m);
					if(nombre > 0)
					c[o][m] = new Lliure(nombre, o, m);
					else
						c[o][m] = new Lliure(o, m);

				}
			}
		}
		return c;
	}

	//RECOMPTE DE MINES
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
	
	
	
	public Lliure descobrir(Lliure l, Casella[][] c, int x, int y) {
		//ES LLIURE
		//NO ES FRONTERA
		//DESTAPA FINS QUE ES FRONTERA EN TOTES DIRECCIONS
		if(l.isFrontera() == true || l.getEstat() == false) {
			l.reaccio();
			return l;
		} else {
			//SI NO ES FRONTERA
			if(!l.isFrontera() && (c[l.x].length != l.y) && l.getEstat() == true) {
				l.reaccio();
				return (Lliure) c[l.x][l.y+1];
			} else if (!l.isFrontera() && (l.y>0) && l.getEstat() == true) {
				l.reaccio();
				return (Lliure) c[l.x][l.y-1];
			} else if (!l.isFrontera() && (c.length != l.x) && l.getEstat() == true) {
				l.reaccio();
				return (Lliure) c[l.x+1][l.y];
			} else if (!l.isFrontera() && (l.x>0) && l.getEstat() == true) {
				l.reaccio();
				return (Lliure) c[l.x-1][l.y];
				} else return l;
				//-----------------------------------------
 
			//SI ESTÀ EN EL FINAL DE LA MATRIU
			//
		}
			}
	
}
