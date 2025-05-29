package application;

import java.io.Serializable;
import java.util.Random;

import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class Tauler extends Context implements Serializable {
    private static final long serialVersionUID = 1L;

	
	Random alea = new Random();
	private int l;
	private int a;
	private transient BooleanProperty partida;

	private transient Timeline temps;

	private Casella[][] caselles;

	public Tauler(int l, int a) {

		super();
		temps = new Timeline();
		partida = new SimpleBooleanProperty(true);
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

	public BooleanProperty getPartida() {
		return partida;
	}

	public void setPartida(BooleanProperty partida) {
		this.partida = partida;
	}
	
	
	// MÈTODES

}
