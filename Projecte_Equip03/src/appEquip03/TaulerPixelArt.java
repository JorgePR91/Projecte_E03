package appEquip03;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Random;

public class TaulerPixelArt extends ContextPixelArt implements Serializable {
    private static final long serialVersionUID = 1L;

	private Random alea = new Random();
	private int l;
	private int a;
	private CasellaPixelArt[][] caselles;
	    
    private void readObject(ObjectInputStream ois) {
    	try {
            ois.defaultReadObject();
            this.alea = new Random();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	public TaulerPixelArt(int l, int a) {
		this.l = l;
		this.a = a;
		caselles = new CasellaPixelArt[this.a][this.l];
	}

	// GETTERS I SETTERS
	public CasellaPixelArt[][] getCaselles() {
		return caselles;
	}

	public void setCaselles(CasellaPixelArt[][] caselles) {
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


	public Random getAlea() {
		return alea;
	}


	public void setAlea(Random alea) {
		this.alea = alea;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	// MÈTODES

}