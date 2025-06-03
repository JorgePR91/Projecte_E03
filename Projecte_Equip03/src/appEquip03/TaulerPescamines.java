package appEquip03;

import java.io.Serializable;
import java.util.Random;

public class TaulerPescamines extends ContextPescamines implements Serializable {
    private static final long serialVersionUID = 1L;

	
	Random alea = new Random();
// Fer Random un atribut però transient
	private int l;
	private int a;
	private CasellaPescamines[][] caselles;
		
	public TaulerPescamines() {
	};
	
	public TaulerPescamines(int l, int a) {
		super();
		this.l = l;
		this.a = a;
		caselles = new CasellaPescamines[this.a][this.l];
		// caselles = Context.assignarMines(caselles, a, alea, "n");
	}

	// GETTERS I SETTERS
	public CasellaPescamines[][] getCaselles() {
		return caselles;
	}

	public void setCaselles(CasellaPescamines[][] caselles) {
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