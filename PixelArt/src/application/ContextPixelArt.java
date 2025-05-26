package application;

import java.util.Random;

import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class ContextPixelArt {

	protected static int comptador;
	protected static Color color;
	protected static boolean borrador;
	protected static int tamany = 0;
	protected static Random alea = new Random();
	static {
		color = Color.BLACK;
		borrador = false;
	}

	// GETTERS I SETTERS
	public static int getComptador() {
		return comptador;
	}

	public static void setComptador(int comptador) {
		ContextPixelArt.comptador = comptador;
	}

	public static Color getColor() {
		return color;
	}

	public static void setColor(Color color) {
		ContextPixelArt.color = color;
	}

	public static int getTamany() {
		return tamany;
	}

	public static void setTamany(int tamany) {
		ContextPixelArt.tamany = tamany;
	}

	public static Random getAlea() {
		return alea;
	}

	public static void setAlea(Random alea) {
		ContextPixelArt.alea = alea;
	}

	// METODES
	public static Tauler crearTauler(String dificultat) {

		switch (dificultat) {
		case "f" -> {
			tamany = 9;
			comptador = tamany;
		}
		case "n" -> {
			tamany = 10;
			comptador = tamany;
		}
		case "d" -> {
			tamany = 15;
			comptador = tamany;
		}
		default -> {
			dificultat = "n";
			tamany = 10;
			comptador = tamany;
		}
		}
		System.out.println(comptador);
		return new Tauler(tamany, tamany);

	}

	public static Tauler crearTauler(int llarg, int ample) {

		return new Tauler(llarg, ample);

	}

	public static void buidar(Tauler t) {
		Casella[][] c = t.getCaselles();

		for (int fil = 0; fil < c.length; fil++) {
			for (int col = 0; col < c.length; col++) {

				c[fil][col] = null;
			}

		}
	}

	public static void ompllirLlenç(Casella[][] c) {

		for (int o = 0; o < c.length; o++) {
			for (int m = 0; m < c[o].length; m++) {
				if (c[o][m] == null) {
					c[o][m] = new Pixel(o, m);

				}
			}
		}

	}

	public static void pintar(Node n, Pixel l) {
		String c = "#"+color.toString().substring(color.toString().indexOf("x") + 1);
		
		n.setOnMousePressed(e->{
			if(e.getButton() == MouseButton.PRIMARY && !borrador) {
				n.setStyle("-fx-background-color: " + c + ";");
			} else if(e.getButton() == MouseButton.SECONDARY) {
				n.setStyle("-fx-background-color: #" + l.base.toString().substring(l.base.toString().indexOf("x") + 1));
			}
			e.consume();
		});
		
		n.setOnDragDetected(e->{
			n.startFullDrag();
			e.consume();
		});
		
		n.setOnMouseDragEntered(e-> {

			if(e.isPrimaryButtonDown() && !borrador) {
				n.setStyle("-fx-background-color: " + c + ";");
			} else if(e.isSecondaryButtonDown()) {
				n.setStyle("-fx-background-color: #" + l.base.toString().substring(l.base.toString().indexOf("x") + 1));
			}
			e.consume();
		});
	}
	public static Color perDefecte(Pixel p, int x, int y) {

	if((x+y)%2==0)
		return Color.LIGHTGREY;
	 else 
		 return Color.WHITE;
	}

}
