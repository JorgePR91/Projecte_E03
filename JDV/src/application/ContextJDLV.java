package application;

import java.util.Random;

import javafx.scene.paint.Color;

public class ContextJDLV {

	protected int comptCel;
	protected int comptMor;
	public int getComptMor() {
		return comptMor;
	}

	public void setComptMor(int comptMor) {
		this.comptMor = comptMor;
	}

	protected int tamany;
	protected int morts;
	protected int vives;
	protected int moribundes;
	protected int naixements;
	protected String mida;
	protected transient Random alea = new Random();
	private Cellula[][] cellules;
	protected int LIMIT_inici;
	boolean estancament = false;

	public ContextJDLV() {
		super();
		morts = 0;
		naixements = 0;
	}

	public ContextJDLV(Cellula[][] cellules) {
		this.cellules = cellules;
		morts = 0;
		naixements = 0;
	}

	public ContextJDLV(String t) {
		this.tamany = calcularTamany(t);
		cellules = new Cellula[tamany][tamany];
		morts = 0;
		naixements = 0;
	}

	// GETTERS I SETTERS
	public int getComptador() {
		return comptCel;
	}

	public void setComptador(int comptador) {
		this.comptCel = comptador;
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

	public int getComptCel() {
		return comptCel;
	}

	public void setComptCel(int comptCel) {
		this.comptCel = comptCel;
	}

	public int getVives() {
		return vives;
	}

	public void setVives(int vives) {
		this.vives = vives;
	}

	public int getMoribundes() {
		return moribundes;
	}

	public void setMoribundes(int moribundes) {
		this.moribundes = moribundes;
	}

	public int getNaixements() {
		return naixements;
	}

	public void setNaixements(int naixements) {
		this.naixements = naixements;
	}

	public Cellula[][] getCellules() {
		return cellules;
	}

	public void setCellules(Cellula[][] cellules) {
		this.cellules = cellules;
	}

	public boolean isEstancament() {
		return estancament;
	}

	public void setEstancament(boolean estancament) {
		this.estancament = estancament;
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
	public void creacio(Random alea, int limit) {
		int o2;

		for (int i = 0; i < cellules.length; i++) {
			for (int j = 0; j < cellules[0].length; j++) {
				if (cellules[i][j] == null) {
					cellules[i][j] = new Cellula();
					cellules[i][j].setEstat("morta");
				}
			}
		}

		do {
			o2 = alea.nextInt(limit);
		} while (o2 > Math.pow(tamany, 2));

		for (int i = 0; i < 10; i++) {
			if (o2 != limit) {
				for (int a = 0; a < o2; a++) {
					cellules = crearVida(cellules, alea);
				}
			}
		}

	}

	// mètode de creació de cel·lules
	public Cellula[][] crearVida(Cellula[][] c, Random alea) {

		int a = alea.nextInt(c.length);
		int b = alea.nextInt(c.length);

		if (c[a][b].getEstat().equals("morta")) {
			c[a][b].setEstat("viva");
			comptCel++;
		} else
			crearVida(c, alea);

		return c;
	}

	public void cicleDeLaVida() {

		int[][] aux = seleccioNatural(cellules);

		Cellula[][] nova = new Cellula[cellules.length][cellules.length];

		for (int i = 0; i < aux.length; i++) {
			for (int j = 0; j < aux[0].length; j++) {
				
				nova[i][j] = new Cellula();
				//nova[i][j].setEstat("morta");

				if (cellules[i][j].getEstat().equals("moribunda")) {
					nova[i][j].setEstat("morta");
				}else if (cellules[i][j].getEstat().equals("naixement")) {
					nova[i][j].setEstat("viva");
					comptCel++;
				} else 
					if (aux[i][j] == 1) {

					if (cellules[i][j].getEstat().equals("morta") || cellules[i][j].getEstat().equals("moribunda")) {
						nova[i][j].setEstat("naixement");
						naixements++;
						comptCel++;
					} else {
						nova[i][j].setEstat("viva");
					}
				} else {
					if (cellules[i][j].getEstat().equals("viva")) {
						nova[i][j].setEstat("moribunda");
						comptMor++;

					} else {
						comptMor++;
						nova[i][j].setEstat("morta");

					}
				}
			}
		}
		this.setCellula(nova);
	}

	// creació de plantilla d'integers per a indicar qui mor i qui viu
	public int[][] seleccioNatural(Cellula[][] c) {

		int[][] aux = new int[c.length][c.length];
		int veines;

		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				veines = 0;
				veines = estatVida(c, i, j);

				if (c[i][j].getEstat().equals("viva")) {
					if (veines == 2 || veines == 3)
						aux[i][j] = 1;
				} else if (c[i][j].getEstat().equals("morta")) {
					if (veines == 3)
						aux[i][j] = 1;
				} else
					aux[i][j] = 0;
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
					if (c[k][l] != null && c[k][l].getEstat().equals("viva"))
						comptador++;
			}
		}
		// CANTO INF ESQUERRE
		else if (a == c.length - 1 && b == 0) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] != null && c[k][l].getEstat().equals("viva"))
						comptador++;
			}
		}
		// CANTO SUP DRET
		else if (a == 0 && b == c.length - 1) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] != null && c[k][l].getEstat().equals("viva"))
						comptador++;
			}
		}
		// CANTO INF DRET
		else if (a == c.length - 1 && b == c.length - 1) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] != null && c[k][l].getEstat().equals("viva"))
						comptador++;
			}
		}
		// PRIMERA FILA
		else if (a == 0 && (b != 0 && b != c.length - 1)) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] != null && c[k][l].getEstat().equals("viva"))
						comptador++;
			}
		}
		// ÚLTIMA FILA
		else if (a == c.length - 1 && (b != 0 && b != c.length - 1)) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] != null && c[k][l].getEstat().equals("viva"))
						comptador++;
			}
		}
		// PRIMERA COLUMNA
		else if (b == 0 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] != null && c[k][l].getEstat().equals("viva"))
						comptador++;
			}
		}
		// ULTIMA COLUMNA
		else if (b == c.length - 1 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] != null && c[k][l].getEstat().equals("viva"))
						comptador++;
			}

		} else {
			// RESTA
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] != null && c[k][l].getEstat().equals("viva"))
						comptador++;
			}
		}

		return (comptador - 1);
	}

	public static Cellula[][] copiaMatriu(Cellula[][] original) {
		int n = original.length;
		Cellula[][] copia = new Cellula[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (original[i][j] != null) {
					copia[i][j] = new Cellula();
					copia[i][j].setEstat(original[i][j].getEstat());
				}
			}
		}

		return copia;
	}

	// anàlisi del que passarà als pròxims torns
	public boolean preCicle() {
		// https://www.techiedelight.com/es/copy-object-array-java/

		ContextJDLV[] postCicle = new ContextJDLV[5];
		Cellula[][] original = copiaMatriu(cellules);

		for (int i = 0; i < postCicle.length; i++) {
			postCicle[i] = new ContextJDLV(copiaMatriu(original));
			for (int j = 0; j < i; j++) {
				postCicle[i].cicleDeLaVida();
			}
		}

		if (postCicle[0].compararCaselles(postCicle[1].getCellula())
				|| postCicle[0].compararCaselles(postCicle[2].getCellula())
				|| postCicle[0].compararCaselles(postCicle[3].getCellula())
				|| postCicle[0].compararCaselles(postCicle[4].getCellula()))
			return true;
		else
			return false;
	}

	// Recompte de dades
	public void recompteCel() {
		int vives = 0;
		int mortes = 0;
		int moribundes = 0;
		int naixements = 0;

		for (int i = 0; i < cellules.length; i++) {
			for (int j = 0; j < cellules[0].length; j++) {
				String estat = cellules[i][j].getEstat();
				switch (estat) {
				case "viva" -> vives++;
				case "morta" -> mortes++;
				case "moribunda" -> moribundes++;
				case "naixement" -> naixements++;
				}
			}
		}

		morts = mortes;
		this.moribundes = moribundes;
		this.vives = vives;
		this.naixements = naixements;

		System.out.println("Vives: " + vives + ", Mortes: " + mortes + ", Moribundes: " + moribundes + ", Naixements: "
				+ naixements);
	}

	// Comparar l'actual matriu amb la que se li envia
	public boolean compararCaselles(Cellula[][] c, int ñ) {
		boolean result = true;
		// https: //
		// www.lawebdelprogramador.com/foros/Java/674725-Saber-si-un-objeto-existe.html
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {

				// Comparació en profunditat
				Cellula a = this.cellules[i][j];
				Cellula b = c[i][j];

				if (!a.getEstat().equals(b.getEstat()))
					return false;
			}
		}
		return result;

	}

	public boolean compararCaselles(Cellula[][] c) {
		boolean result = true;
		// https: //
		// www.lawebdelprogramador.com/foros/Java/674725-Saber-si-un-objeto-existe.html
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[0].length; j++) {
				Cellula a = this.cellules[i][j];
				Cellula b = c[i][j];
				if (!a.getEstat().equals(b.getEstat()))
					return false;
			}
		}
		return result;
	}

	public void depurar() {
		for (int i = 0; i < cellules.length; i++) {
			for (int j = 0; j < cellules[0].length; j++) {
				if (cellules[i][j].getEstat().equals("moribunda")) {
					cellules[i][j].setEstat("morta");
				}
			}
		}
		System.out.println("Depuració");
	}

	public String conversioAHex(Color color) {

		String colorCad = String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
		return colorCad;
	}

	public void buidar(Cellula[][] c) {

		for (int fil = 0; fil < c.length; fil++) {
			for (int col = 0; col < c.length; col++) {

				c[fil][col] = null;
			}

		}
	}

	public void Resum(int a) {
		System.out.println("Generació: " + a + ", Cèl·lules: " + getComptCel());
		System.out.println("Total creades: " + this.comptCel + ", Total mortes: " + this.morts);
	}

}
