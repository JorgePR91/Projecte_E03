package appEquip03;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class ContextPixelArt implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int comptador;
	protected transient Color color;
	protected boolean borrador;
	protected int tamany;
	protected String mida;
	protected transient Random alea;
	protected Tauler tauler;
	
	{
		color = Color.BLACK;
		borrador = false;
		alea = new Random();
	}
	public ContextPixelArt() {
		super();
	}
	
	public ContextPixelArt(String mida) {
		super();
		this.mida = mida;
	}


	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject(); 
	    
	    color = Color.BLACK;
	    alea = new Random();
	}
	


	// GETTERS I SETTERS
	public int getComptador() {
		return comptador;
	}

	public void setComptador(int comptador) {
		this.comptador = comptador;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getTamany() {
		return tamany;
	}

	public void setTamany(int tamany) {
		this.tamany = tamany;
	}

	public Random getAlea() {
		return alea;
	}

	public void setAlea(Random alea) {
		this.alea = alea;
	}

	public boolean isBorrador() {
		return borrador;
	}

	public void setBorrador(boolean borrador) {
		this.borrador = borrador;
	}

	public String getMida() {
		return mida;
	}
	public void setMida(String mida) {
		this.mida = mida;
	}
	public Tauler getTauler() {
		return tauler;
	}

	public void setTauler(Tauler tauler) {
		this.tauler = tauler;
	}

	// METODES
	public int tamany(String t) {
		switch (t) {
		case "Xicotet": return 20;
		case "Mitjà": return 30;
		case "Gran": return 40;
		default:
			return 30;
		}
	}
		
	public Tauler crearTauler(int llarg, int ample) {
		this.tauler = new Tauler(llarg, ample);
		return tauler;

	}
	
	public Color perDefecte( int x, int y) {

		if ((x + y) % 2 == 0)
			return Color.LIGHTGREY;
		else
			return Color.WHITE;
	}
	
	public String conversioAHex(Color color) {
		
		//Sí IA, és lo que hi ha
		
		 String colorCad = String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
				(int) (color.getBlue() * 255));
		 System.out.println(colorCad);
		 return colorCad;
	}
	public Color conversioAColor(String color) {
		
		//Aquest és meu 100%, volia utilitzar el Color.web però me donava problemes
		color = color.substring(1);
		
		double roig = (double) (Integer.parseInt(color.substring(0,2), 16)/255);
		double verd = (double) (Integer.parseInt(color.substring(2,4), 16)/255);
		double blau = (double) (Integer.parseInt(color.substring(4,6), 16)/255);
		Color col = new Color(blau, roig, verd, blau);
//		 String colorCad = String.format("#%02X%02X%02X", 
//				 
//				 (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
//				(int) (color.getBlue() * 255));
		 
		 
		 return col;
	}

	public void buidar(Tauler t) {
		Casella[][] c = t.getCaselles();

		for (int fil = 0; fil < c.length; fil++) {
			for (int col = 0; col < c.length; col++) {
				c[fil][col] = null;
			}
		}
	}

	public void pintar(Node n, Pixel l) {
	    n.setOnMousePressed(e -> {
	        if (borrador) {
	            n.setStyle("-fx-background-color: " + conversioAHex(l.getBase()) + ";");
	            l.setColorHex(""); 
	        } else {
	            if (e.getButton() == MouseButton.PRIMARY) {
	                String c = conversioAHex(color);
	                l.setColorHex(c);
	                n.setStyle("-fx-background-color: " + c + ";");

	            } else if (e.getButton() == MouseButton.SECONDARY) {
	                n.setStyle("-fx-background-color: " + conversioAHex(l.getBase()) + ";");
	                l.setColorHex("");
	            }
	        }

	    });

	    n.setOnDragDetected(e -> {
	        n.startFullDrag();
	        e.consume();
	    });

	    n.setOnMouseDragEntered(e -> {
	        if (borrador) {
	            n.setStyle("-fx-background-color: " + conversioAHex(l.getBase()) + ";");
	            l.setColorHex("");
	        } else {
	            if (e.isPrimaryButtonDown()) {
	                String c = conversioAHex(color);
	                l.setColorHex(c);
	                n.setStyle("-fx-background-color: " + c + ";");
	            } else if (e.isSecondaryButtonDown()) {
	                n.setStyle("-fx-background-color: " + conversioAHex(l.getBase()) + ";");
	                l.setColorHex("");
	            }
	        }
	        e.consume();
	    });
	}
	

	
}
