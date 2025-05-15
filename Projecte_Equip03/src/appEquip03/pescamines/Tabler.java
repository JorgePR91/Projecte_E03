package appEquip03.pescamines;

import java.util.Scanner;

public class Tabler {
	
	private String dificultat;
	private int tamany;

	private Casella[][] caselles = new Casella[tamany][tamany];

	public Tabler(String dificultat, Random alea) {
		super();
		this.dificultat = dificultat;
		switch(dificultat) {
		case "f" -> this.tamany = 9;
		case "n" -> this.tamany = 10;
		case "d" -> this.tamany = 12;
		default -> {this.dificultat = "n"; this.tamany = 10;}
		}
		this.caselles = assignarBombes(this.caselles, alea);
	}
	//GETTERS I SETTERS
	public String getDificultat() {
		return dificultat;
	}
	public void setDificultat(String dificultat) {
		this.dificultat = dificultat;
	}
	public int getTamany() {
		return tamany;
	}
	public void setTamany(int tamany) {
		this.tamany = tamany;
	}
	public Casella[][] getCaselles() {
		return caselles;
	}
	public void setCaselles(Casella[][] caselles) {
		this.caselles = caselles;
	}
	
	//MÈTODES
	private Casella[][] assignarBombes(Casella[][] c, Scanner alea){
		//nombre de bombes com atribut?
		int nBomb = 0;
		//decidir bombes
		if(this.dificultat == "f") {
			nBomb=10;
		} else if(this.dificultat == "d") {
			nBomb=15;
		} else {
			nBomb=12;
		}
		//col·locar bombes
		for(int b=0;b<c.length;) {
			
			int x = alea.nextInt(tamany);
			int y = alea.nextInt(tamany);
			
			if(c[x][y]==null) {
				c[x][y] = new Mina();
				b++;
			}
		}
		//col·locar caselles
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
				if(c[o][m]!=null) {
					c[o][m] = new Lliure();
				}
			}
		}
		
		
		return c;
	}
}
