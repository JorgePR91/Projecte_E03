package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

public class PescaminesTauler extends PescaminesContext implements Serializable {
    private static final long serialVersionUID = 1L;

	
	Random alea = new Random();
// Fer Random un atribut però transient
	private int l;
	private int a;
	private PescaminesCasella[][] caselles;
		
	public PescaminesTauler() {
	};
	
	public PescaminesTauler(int l, int a) {
		super();
		this.l = l;
		this.a = a;
		caselles = new PescaminesCasella[this.a][this.l];
		// caselles = Context.assignarMines(caselles, a, alea, "n");
	}

	// GETTERS I SETTERS
	public PescaminesCasella[][] getCaselles() {
		return caselles;
	}

	public void setCaselles(PescaminesCasella[][] caselles) {
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
    
	// MÈTODES

}
