package application;

import java.io.Serializable;
import java.util.Random;
import javafx.scene.paint.Color;

public class ContextJDLV implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int comptador;
	protected int tamany;
	protected int morts;
	protected int moribundes;
	protected int naixements;
	protected String mida;
	protected transient Random alea = new Random();
	private Cellula[][] cellules;
	protected int LIMIT_inici;
	boolean estancament = false;

	public ContextJDLV() {
		super();
	}

	public ContextJDLV(Cellula[][] cellules) {
		this.cellules = cellules;
	}

	public ContextJDLV(String t) {
		this.tamany = calcularTamany(t);
		cellules = new Cellula[tamany][tamany];
	}

	// GETTERS I SETTERS
	public int getComptador() {
		return comptador;
	}

	public void setComptador(int comptador) {
		this.comptador = comptador;
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

	public String getMida() {
		return mida;
	}

	public void setMida(String mida) {
		this.mida = mida;
	}

	public int getMorts() {
		return morts;
	}

	public void setMorts(int morts) {
		this.morts = morts;
	}

	public Cellula[][] getCellula() {
		return cellules;
	}

	public void setCellula(Cellula[][] cellula) {
		this.cellules = cellula;
	}

	public int getLIMIT_inici() {
		return LIMIT_inici;
	}

	public void setLIMIT_inici(int lIMIT_inici) {
		LIMIT_inici = lIMIT_inici;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// METODES
	public int calcularTamany(String t) {

		switch (t) {
		case "Xicotet" -> {
			LIMIT_inici = 30;
			return 20;
		}
		case "Mitjà" -> {
			LIMIT_inici = 40;
			return 30;
		}
		case "Gran" -> {
			LIMIT_inici = 50;
			return 40;
		}
		default -> {
			return 30;
		}
		}
	}

	// mètode de creació inicial
	public void creacio(Cellula[][] c, Random alea, int limit) {
		int o2;

		do {
			o2 = alea.nextInt(limit);
		} while (o2 > Math.pow(tamany, 2));

		for (int i = 0; i < 10; i++) {
			if (o2 != limit) {
				for (int a = 0; a < o2; a++) {
					c = crearVida(c, alea);
				}
			}
		}

		this.setCellula(c);
	}

	// mètode de creació de cel·lules
	public Cellula[][] crearVida(Cellula[][] c, Random alea) {

		int a = alea.nextInt(c.length);
		int b = alea.nextInt(c.length);

		if (c[a][b] == null) {
			c[a][b] = new Cellula(a, b);
			comptador++;
			return c;
		} else
			crearVida(c, alea);

		return c;
	}

	public void cicleDeLaVida() {

		int[][] aux = seleccioNatural(cellules);

		Cellula[][] nova = new Cellula[cellules.length][cellules.length];

		for (int i = 0; i < aux.length; i++) {
			for (int j = 0; j < aux[0].length; j++) {
				if ((aux[i][j] == 1) && cellules[i][j] == null) {
					nova[i][j] = new Cellula(i, j);
					nova[i][j].setNaix(true);
					if (nova[i][j].isMorta())
						nova[i][j].setMorta(false);
				} else if ((aux[i][j] == 1) && cellules[i][j] != null) {
					nova[i][j] = new Cellula(i, j);
					nova[i][j].setNaix(false);
					nova[i][j].setViva(true);
				} else if ((aux[i][j] == 0) && cellules[i][j] == null) {
					nova[i][j] = new Cellula(i, j);

					nova[i][j].setViva(false);
					nova[i][j].setMoribunda(true);
				} else if ((aux[i][j] == 0) && cellules[i][j] == null) {
					nova[i][j] = new Cellula(i, j);
					nova[i][j].setMoribunda(false);
					if (!nova[i][j].isMorta())
						nova[i][j].setMorta(true);
					else
						nova[i][j] = null;

				}

			}
		}

		this.setCellula(nova);
	}

	// creació de plantilla d'integers per a indicar qui mor i qui viu
	public int[][] seleccioNatural(Cellula[][] c) {
		int[][] aux = new int[c.length][c.length];
		int r = 0;

		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				r = estatVida(c, i, j);

				if (c[i][j] != null) {
					if (r < 3) {
						aux[i][j] = 0;
						morts++;
					} else if (r > 4) {
						aux[i][j] = 0;
						morts++;
					} else
						aux[i][j] = 1;
				} else {
					if (r == 3) {
						aux[i][j] = 1;
						comptador++;
					} else
						aux[i][j] = 0;
				}
			}
		}

		return aux;
	}

	// COMPROVAR SI LA CASELLA HA DE MORIR O VIURE
	public int estatVida(Cellula[][] c, int a, int b) {
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

	// anàlisi del que passarà als pròxims torns
	public boolean preCicle(Cellula[][] c) {
		// https://www.techiedelight.com/es/copy-object-array-java/
		boolean resultat = false;
		ContextJDLV[] postCicle = new ContextJDLV[4];

		for (int i = 0; i < postCicle.length; i++) {
			postCicle[i] = new ContextJDLV(getCellula());
			for (int j = 0; j < i; j++) {
				postCicle[i].cicleDeLaVida();
			}

		}

		if (postCicle[0].compararCaselles(postCicle[1].getCellula())
				|| postCicle[0].compararCaselles(postCicle[2].getCellula())
				|| postCicle[0].compararCaselles(postCicle[3].getCellula()))
			return true;
		else
			return false;

	}

	// Recompte de dades
	public int recompteCel() {

		int recompte = 0;

		for (int i = 0; i < cellules.length; i++) {
			for (int j = 0; j < cellules[0].length; j++) {
				if (cellules[i][j] != null) {
					if (cellules[i][j].moribunda)
						moribundes++;
					else if (cellules[i][j].naix)
						naixements++;
					// PENSAR SI LES MORTES I LES QUE HI HA TAMBÉ
				}
			}
		}
		System.out.println(recompte);
		return recompte;
	}

	// Comparar l'actual matriu amb la que se li envia
	public boolean compararCaselles(Cellula[][] c) {
		boolean f = false;
		// https: //
		// www.lawebdelprogramador.com/foros/Java/674725-Saber-si-un-objeto-existe.html
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				if (cellules[i][j] == null) {
					if (c[i][j] == null) {
						f = true;
					} else {
						f = false;
						return f;
					}
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

	public String conversioAHex(Color color) {

		// Sí IA, és lo que hi ha

		String colorCad = String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
		return colorCad;
	}

	public void Resum(int a) {
		System.out.println("Generació: " + a + ", Cèl·lules: " + recompteCel());
		System.out.println("Total creades: " + this.comptador + ", Total mortes: " + this.morts);
	}

}
