package application;

import java.util.Random;

import javafx.animation.Timeline;
import javafx.scene.layout.GridPane;

public class Tauler extends Context{
	Random alea = new Random();
	private int l;
	private int a;
	private GridPane gridPane;
	private Timeline temps;

	private Casella[][] caselles;

	public Tauler(int l, int a) {
		
		super();
		temps = new Timeline();
		this.l = l;
		this.a = a;
		caselles = new Casella[this.a][this.l];
		//caselles = Context.assignarMines(caselles, a, alea, "n");
	}
	
	//GETTERS I SETTERS
	public Casella[][] getCaselles() {
		return caselles;
	}
	public void setCaselles(Casella[][] caselles) {
		this.caselles = caselles;
	}

	//MÈTODES

	



}
