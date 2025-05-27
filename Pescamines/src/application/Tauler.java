package application;

import java.io.Serializable;
import java.util.Random;

import javafx.animation.Timeline;
import javafx.scene.control.Label;

public class Tauler extends Context implements Serializable {
	Random alea = new Random();
	private int l;
	private int a;

	private Timeline temps;

	private Casella[][] caselles;

	public Tauler(int l, int a) {

		super();
		temps = new Timeline();
		this.l = l;
		this.a = a;
		caselles = new Casella[this.a][this.l];
		// caselles = Context.assignarMines(caselles, a, alea, "n");
	}

	// GETTERS I SETTERS
	public Casella[][] getCaselles() {
		return caselles;
	}

	public void setCaselles(Casella[][] caselles) {
		this.caselles = caselles;
	}

	public int getL() {
		return l;
	}

	public void setL(int l) {
		this.l = l;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public Timeline getTemps() {
		return temps;
	}

	public void setTemps(Timeline temps) {
		this.temps = temps;
	}

	// MÈTODES

}
