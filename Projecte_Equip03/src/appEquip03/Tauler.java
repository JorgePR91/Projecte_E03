package appEquip03;

import java.util.Random;

import javafx.scene.layout.GridPane;

public class Tauler implements MetodeTauler{
	Random alea = new Random();
	private int l;
	private int a;
	private GridPane gridPane;

	private Casella[][] caselles;

	public Tauler(int l, int a) {
		
		super();
		this.l = l;
		this.a = a;
		caselles = new Casella[this.a][this.l];
		caselles = MetodeTauler.assignarMines(caselles, a, alea, "n");
		this.gridPane = nouGP(caselles);
	}
	
	//GETTERS I SETTERS
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
	public void omplirPossicio(int x, int y, Casella c) {
		this.caselles[x][y] = c;
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
