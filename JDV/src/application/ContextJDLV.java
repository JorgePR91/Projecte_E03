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
			LIMIT_inici = 35;
			return 20;
		}
		case "Mitjà" -> {
			LIMIT_inici = 45;
			return 30;
		}
		case "Gran" -> {
			LIMIT_inici = 80;
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
		morts = 0;
		naixements = 0;

		int[][] aux = seleccioNatural(cellules);

		Cellula[][] nova = new Cellula[cellules.length][cellules.length];

		for (int i = 0; i < aux.length; i++) {
			for (int j = 0; j < aux[0].length; j++) {
				Cellula actual = cellules[i][j];

				if (actual != null && actual.isMoribunda()) {
					nova[i][j] = null;
					morts++;
				} else if (aux[i][j] == 1) {
					nova[i][j] = new Cellula(i, j);
					if (actual == null) {
						nova[i][j].setNaix(true);
						naixements++;
					} else {
						nova[i][j].setNaix(false);
						nova[i][j].setViva(true);
					}
				} else {
					if (actual != null) {
						nova[i][j] = new Cellula(i, j);
						nova[i][j].setMoribunda(true);
						nova[i][j].setNaix(false);
						nova[i][j].setViva(false);
						morts++;
					} else {
						nova[i][j] = null;
					}
				}

			}
		}

		this.setCellula(nova);
	}

	// creació de plantilla d'integers per a indicar qui mor i qui viu
	public int[][] seleccioNatural(Cellula[][] c) {
		int[][] aux = new int[c.length][c.length];
		int veines = 0;

		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				veines = estatVida(c, i, j);

				if (c[i][j] != null) {
					if (veines < 2) {
						aux[i][j] = 0;
					} else if (veines == 2 || veines == 3) {
						aux[i][j] = 1;
					} else if (veines > 3) {
						aux[i][j] = 0;
					}
				} else {
					if (veines == 3) {
						aux[i][j] = 1;
					} else {
						aux[i][j] = 0;
					}
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

		return comptador - 1;
	}

	public static Cellula[][] copiaMatriu(Cellula[][] original) {
		int n = original.length;
		Cellula[][] copia = new Cellula[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (original[i][j] != null) {
					copia[i][j] = new Cellula(original[i][j].getX(), original[i][j].getY());
					copia[i][j].setViva(original[i][j].isViva());
					copia[i][j].setNaix(original[i][j].isNaix());
					copia[i][j].setMoribunda(original[i][j].isMoribunda());
					copia[i][j].setMorta(original[i][j].isMorta());
				}
			}
		}

		return copia;
	}

	// anàlisi del que passarà als pròxims torns
	public boolean preCicle(Cellula[][] c) {
		// https://www.techiedelight.com/es/copy-object-array-java/
		boolean resultat = false;
		ContextJDLV[] postCicle = new ContextJDLV[4];
		Cellula[][] original = copiaMatriu(c);

		for (int i = 0; i < postCicle.length; i++) {
			postCicle[i] = new ContextJDLV(copiaMatriu(original));
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
					recompte++;
					if (cellules[i][j].moribunda)
						moribundes++;
					else if (cellules[i][j].naix)
						naixements++;
					// PENSAR SI LES MORTES I LES QUE HI HA TAMBÉ
				}
			}
		}
		return recompte;
	}

	// Comparar l'actual matriu amb la que se li envia
	public boolean compararCaselles(Cellula[][] c) {
		boolean f = false;
		// https: //
		// www.lawebdelprogramador.com/foros/Java/674725-Saber-si-un-objeto-existe.html
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {

				// Comparació en profunditat
				Cellula a = this.cellules[i][j];
				Cellula b = c[i][j];

				if ((a == null && b != null) || (a != null && b == null)) {
					return false;
				} else if (a != null && b != null) {
					if (a.isViva() != b.isViva() || a.isNaix() != b.isNaix() || a.isMoribunda() != b.isMoribunda()
							|| a.isMorta() != b.isMorta()) {
						return false;
					} else
						return true;
				}
			}
		}
		return f;
	}

	public void depurar(Cellula[][] c) {
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				if(c[i][j] != null && c[i][j].isMoribunda())
					c[i][j] = null;
			}
		}
		System.out.println("Depuració");
	}

	public String conversioAHex(Color color) {

		String colorCad = String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
		return colorCad;
	}

	public void Resum(int a) {
		System.out.println("Generació: " + a + ", Cèl·lules: " + recompteCel());
		System.out.println("Total creades: " + this.comptador + ", Total mortes: " + this.morts);
	}

}
