package application;

import java.io.Serializable;
import java.util.Random;

public class Tauler extends ContextJDLV implements Serializable {
    private static final long serialVersionUID = 1L;

	
	Random alea = new Random();
// Fer Random un atribut però transient
	private int l;
	private int a;
	private Cellula[][] cellula;
	
 
	public Tauler(int l, int a) {
		this.l = l;
		this.a = a;
		cellula = new Cellula[this.a][this.l];
	}
	
	// GETTERS I SETTERS
	public Cellula[][] getCaselles() {
		return cellula;
	}

	public void setCaselles(Cellula[][] cellula) {
		this.cellula = cellula;
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
    
	// MÈTODES

}
