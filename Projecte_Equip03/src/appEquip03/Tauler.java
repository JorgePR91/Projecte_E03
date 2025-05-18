package appEquip03;

import javafx.scene.layout.GridPane;

public class Tauler {
	
	private int l;
	private int a;
	private GridPane gridPane;

	private Casella[][] caselles;

	public Tauler(int l, int a) {
		
		super();
		this.l = l;
		this.a = a;
		caselles = new Casella[this.a][this.l];
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
	//CREAR SEGONS DIFICULTAT
	public Tauler dificultat(String dificultat) {
	int tamany = 0;
	
	switch(dificultat) {
	case "f" -> tamany = 9;
	case "n" -> tamany = 10;
	case "d" -> tamany = 12;
	default -> {dificultat = "n"; tamany = 10;}
	}
	return new Tauler(tamany, tamany);
	}
	public void recorrerTauler(Casella[][] c) {
		int nM = 0;
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
//				if(c[o][m] instanceof Lliure) {
//					nM = recompteMines(c, o, m);
//					Lliure aux = (Lliure) c[o][m];
//					aux.setRecompte(nM);
//					aux.setText(new Text(""+aux.getRecompte()));
//				}
			}
			}		
	}
	

}
