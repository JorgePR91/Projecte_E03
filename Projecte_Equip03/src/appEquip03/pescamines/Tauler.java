package appEquip03.pescamines;

import java.util.Random;

import javafx.scene.layout.GridPane;

public class Tauler {
	
	private String dificultat;
	private int tamany;
	private GridPane gridPane;

	private Casella[][] caselles;

	public Tauler(String dificultat, Random alea) {
		
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
	public Casella[][] getCaselles() {
		return caselles;
	}
	public void setCaselles(Casella[][] caselles) {
		this.caselles = caselles;
	}
	
	public GridPane getGridPane() {
		return gridPane;
	}

	public void setGridPane(GridPane gridPane) {
		this.gridPane = gridPane;
	}

	//MÈTODES
	private Casella[][] assignarBombes(Casella[][] c, int tamany, Random alea){
		c = new Casella[tamany][tamany];

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
				c[x][y] = new Mina();
				b++;
			}
		}
		//col·locar caselles
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
				if(c[o][m]==null) {
					c[o][m] = new Lliure();
				}
			}
		}
		
		
		return c;
	}
	
	private GridPane nouGP(Casella[][] c) {
		GridPane gp = new GridPane();
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
				gp.add(c[o][m].getContainer(), o, m);
			}
		}
		return gp;
	}
	
}
