package appEquip03.pescamines;

import java.util.Random;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class TaulerPM {
	
	private String dificultat;
	private int tamany;
	private GridPane gridPane;

	private CasellaPM[][] caselles;

	public TaulerPM(String dificultat, Random alea) {
		
		super();
		this.dificultat = dificultat;
		switch(dificultat) {
		case "f" -> this.tamany = 9;
		case "n" -> this.tamany = 10;
		case "d" -> this.tamany = 12;
		default -> {this.dificultat = "n"; this.tamany = 10;}
		}

		this.caselles = assignarBombes(this.caselles, tamany,  alea);
		
		this.gridPane = nouGP(this.caselles);
		recorrerTauler(this.caselles);
		//this.gridPane.setAlignment(Pos.CENTER);
	}
	
	//GETTERS I SETTERS
	public String getDificultat() {
		return dificultat;
	}
	public void setDificultat(String dificultat) {
		this.dificultat = dificultat;
	}
	public int getTamany() {
		return tamany;
	}
	public void setTamany(int tamany) {
		this.tamany = tamany;
	}
	public CasellaPM[][] getCaselles() {
		return caselles;
	}
	public void setCaselles(CasellaPM[][] caselles) {
		this.caselles = caselles;
	}
	
	public GridPane getGridPane() {
		return gridPane;
	}

	public void setGridPane(GridPane gridPane) {
		this.gridPane = gridPane;
	}

	//MÈTODES
	private CasellaPM[][] assignarBombes(CasellaPM[][] c, int tamany, Random alea){
		c = new CasellaPM[tamany][tamany];

		//nombre de bombes com atribut?
		int nMines = 0;
		//decidir bombes
		if(this.dificultat == "f") {
			nMines=10;
		} else if(this.dificultat == "d") {
			nMines=15;
		} else {
			nMines=12;
		}
		
		//col·locar bombes
		for(int b=0;b<nMines;) {
			
			int x = alea.nextInt(tamany);
			int y = alea.nextInt(tamany);
			
			if(c[x][y]==null) {
				c[x][y] = new MinaPM();
				b++;
			}
		}
		//col·locar caselles
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
				if(c[o][m]==null) {
					c[o][m] = new LliurePM();
				}
			}
		}
		recorrerTauler(c);
		
		return c;
	}
	
	private GridPane nouGP(CasellaPM[][] c) {
		int nM = 0;
		GridPane gp = new GridPane();
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
				if(c[o][m] instanceof LliurePM) {
					nM = recompteMines(c, o, m);
					LliurePM aux = (LliurePM) c[o][m];
					aux.setRecompte(nM);
					if(nM>0) {
						aux.setText(new Text(""+aux.getRecompte()));
						c[o][m].setContingut(aux.getText());	
					}
					else {
						aux.setText(new Text(""));
						c[o][m].setContingut(aux.getText());	
					}
				}
				gp.add(c[o][m].getContainer(), o, m);
			}
			nM = 0;
		}
		return gp;
	}
	
	public void recorrerTauler(CasellaPM[][] c) {
		int nM = 0;
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
				if(c[o][m] instanceof LliurePM) {
					nM = recompteMines(c, o, m);
					LliurePM aux = (LliurePM) c[o][m];
					aux.setRecompte(nM);
					aux.setText(new Text(""+aux.getRecompte()));
				}
			}
			}		
	}
	
	public int recompteMines(CasellaPM[][] c, int a, int b) {
		int comptador = 0;

		// CANTO SUP ESQUERRE
		if (a == 0 && b == 0) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] instanceof MinaPM)
						comptador++;
			}
		}
		// CANTO INF ESQUERRE
		else if (a == c.length - 1 && b == 0) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] instanceof MinaPM)
						comptador++;
			}
		}
		// CANTO SUP DRET
		else if (a == 0 && b == c.length - 1) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] instanceof MinaPM)
						comptador++;
			}
		}
		// CANTO INF DRET
		else if (a == c.length - 1 && b == c.length - 1) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] instanceof MinaPM)
						comptador++;
			}
		}
		// PRIMERA FILA
		else if (a == 0 && (b != 0 && b != c.length - 1)) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] instanceof MinaPM)
						comptador++;
			}
		}
		// ÚLTIMA FILA
		else if (a == c.length - 1 && (b != 0 && b != c.length - 1)) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] instanceof MinaPM)
						comptador++;
			}
		}
		// PRIMERA COLUMNA
		else if (b == 0 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] instanceof MinaPM)
						comptador++;
			}
		}
		// ULTIMA COLUMNA
		else if (b == c.length - 1 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] instanceof MinaPM)
						comptador++;
			}

		} else {
			// RESTA
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] instanceof MinaPM)
						comptador++;
			}
		}

		return comptador;
	}
}
