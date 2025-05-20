package application;

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
	}
	
	//GETTERS I SETTERS
	public Casella[][] getCaselles() {
		return caselles;
	}
	public void setCaselles(Casella[][] caselles) {
		this.caselles = caselles;
	}

	//MÈTODES
	public void omplirPossicio(int x, int y, Casella c) {
		this.caselles[x][y] = c;
	}
	



}
