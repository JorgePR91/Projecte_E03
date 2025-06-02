package appEquip03;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

public class TaulerPixelArt implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient Random alea = new Random();

    private int l; // files
    private int a; // columnes

    private CasellaPixelArt[][] caselles;

    public TaulerPixelArt(int l, int a) {
        this.l = l;
        this.a = a;
        // Nota: Normalment primer índex = files (l), segon índex = columnes (a)
        caselles = new CasellaPixelArt[l][a];
    }

    // Getters i setters
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

    // Serialització personalitzada
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        // No cal escriure alea perquè és transient i la recrearem després
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        this.setAlea(new Random()); // re-inicialitzar transient
    }

	public Random getAlea() {
		return alea;
	}

	public void setAlea(Random alea) {
		this.alea = alea;
	}

    // Altres mètodes aquí
}
