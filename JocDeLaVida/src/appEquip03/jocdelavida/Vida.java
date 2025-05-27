package appEquip03.jocdelavida;

import java.util.Arrays;
import java.util.Random;

public class Vida {
	Random alea = new Random();

	private static int LIMIT_vida = 15;
	private static int LIMIT_inici = 30;

	private int CREACIO = 0;
	private int MORTS = 0;
	private Casella[][] vida = new Casella[LIMIT_vida][LIMIT_vida];

	public Vida() {
	}

	public Vida(Casella[][] v) {
		this.vida = v;
	}

	public Random getAlea() {
		return alea;
	}

	public int getCREACIO() {
		return CREACIO;
	}

	public int getLIMIT_vida() {
		return LIMIT_vida;
	}

	public Casella[][] getvida() {
		return vida;
	}

	public int getLIMIT_inici() {
		return LIMIT_inici;
	}

	public void setvida(Casella[][] vida) {
		this.vida = vida;
	}

	public void creacio(Casella[][] c, Random alea, int limit) {
		int o2;
		do {
			o2 = alea.nextInt(limit);
		} while (o2 > Math.pow(LIMIT_vida, 2));

		if (o2 != limit)
			for (int a = 0; a < o2; a++) {
				c = crearVida(c, alea);
			}

		setvida(c);
	}

	public Casella[][] crearVida(Casella[][] c, Random alea) {
		int a = alea.nextInt(c.length);
		int b = alea.nextInt(c.length);

		if (c[a][b] == null) {
			c[a][b] = new Casella();
			CREACIO++;
		} else
			crearVida(c, alea);

		return c;
	}

	public void cicleDeLaVida() {

		int[][] aux = seleccioNatural(vida);

		Casella[][] nova = new Casella[vida.length][vida.length];

		for (int i = 0; i < aux.length; i++) {
			for (int j = 0; j < aux[0].length; j++) {
				if (aux[i][j] != 0) {
					nova[i][j] = new Casella();
				}
			}
		}

		setvida(nova);
	}

	public int[][] seleccioNatural(Casella[][] c) {
		int[][] aux = new int[c.length][c.length];
		int r = 0;

		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				r = estatVida(c, i, j);

				if (c[i][j] != null) {
					if (r < 3) {
						aux[i][j] = 0;
						MORTS++;
					} else if (r > 4) {
						aux[i][j] = 0;
						MORTS++;
					} else
						aux[i][j] = 1;
				} else {
					if (r == 3) {
						aux[i][j] = 1;
						CREACIO++;
					} else
						aux[i][j] = 0;
				}
			}
		}

		return aux;
	}

//COMPROVAR SI LA CASELLA HA DE MORIR O VIURE 
	public int estatVida(Casella[][] c, int a, int b) {
		int comptador = 0;

		// CANTO SUP ESQUERRE
		if (a == 0 && b == 0) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] != null)
						comptador++;
			}
		}
		// CANTO INF ESQUERRE
		else if (a == c.length - 1 && b == 0) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] != null)
						comptador++;
			}
		}
		// CANTO SUP DRET
		else if (a == 0 && b == c.length - 1) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] != null)
						comptador++;
			}
		}
		// CANTO INF DRET
		else if (a == c.length - 1 && b == c.length - 1) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] != null)
						comptador++;
			}
		}
		// PRIMERA FILA
		else if (a == 0 && (b != 0 && b != c.length - 1)) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] != null)
						comptador++;
			}
		}
		// ÚLTIMA FILA
		else if (a == c.length - 1 && (b != 0 && b != c.length - 1)) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] != null)
						comptador++;
			}
		}
		// PRIMERA COLUMNA
		else if (b == 0 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] != null)
						comptador++;
			}
		}
		// ULTIMA COLUMNA
		else if (b == c.length - 1 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] != null)
						comptador++;
			}

		} else {
			// RESTA
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] != null)
						comptador++;
			}
		}

		return comptador;
	}

	public boolean preCicle(Casella[][] c) {
		// https://www.techiedelight.com/es/copy-object-array-java/
		boolean resultat = false;
		Vida[] postCicle = new Vida[4];

		for (int i = 0; i < postCicle.length; i++) {
			postCicle[i] = new Vida(getvida());
			for (int j = 0; j < i; j++) {
				postCicle[i].cicleDeLaVida();
			}

		}
		//postCicle[0].mostrarVida();
		//postCicle[1].mostrarVida();
		//postCicle[2].mostrarVida();

		if (postCicle[0].compararCaselles(postCicle[1].getvida())
				|| postCicle[0].compararCaselles(postCicle[2].getvida())
				|| postCicle[0].compararCaselles(postCicle[3].getvida()))
			return true;
		else
			return false;

	}

	public int recompteCel() {

		int recompte = 0;

		for (int i = 0; i < this.vida.length; i++) {
			for (int j = 0; j < this.vida[0].length; j++) {
				if (this.vida[i][j] != null)
					recompte++;
			}
		}

		return recompte;
	}
	
	public boolean compararCaselles(Casella[][] c) {
		boolean f = false;
		//https: // www.lawebdelprogramador.com/foros/Java/674725-Saber-si-un-objeto-existe.html
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				if (this.vida[i][j] == null) {
					if (c[i][j] == null) {
						f = true;
					} else {
						f = false;
					return f;}
				} else {
					if (c[i][j] != null) {
						f = true;
					} else {
						f = false;
						return f;
					}
				}
			}
		}
		return f;
	}


	public void mostrarVida() {
		String marc = "\u2550".repeat(getvida()[0].length);
		System.out.println("\u2554" + marc + "\u2557");
		for (int i = 0; i < LIMIT_vida; i++) {
			System.out.print("\u2551");
			for (int j = 0; j < LIMIT_vida; j++) {
				if (getvida()[i][j] != null)
					System.out.print("\u001B[40m" + " " + "\u001B[0m");
				else
					System.out.print(" ");
			}
			System.out.println("\u2551");
		}
		System.out.println("\u255A" + marc + "\u255D");

	}

	public void Resum(int a) {
		System.out.println("Generació: " + a + ", Cèl·lules: " + recompteCel());
		System.out.println("Total creades: " + this.CREACIO + ", Total mortes: " + this.MORTS);
	}

}
