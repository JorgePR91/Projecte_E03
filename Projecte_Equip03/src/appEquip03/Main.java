package appEquip03;
	
import java.io.IOException;
import java.util.Random;

import appEquip03.pescamines.CasellaPM;
import appEquip03.pescamines.LliurePM;
import appEquip03.pescamines.MinaPM;
import appEquip03.pescamines.TaulerPM;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {
	Random alea = new Random();
	
	
	
	@Override
	public void init() throws Exception {
		super.init();
		//CARREGAR BD
		//CARREGAR ARXIUS EXTERNS I RECURSOS
		
	}

	@Override
	public void start(Stage primaryStage) {
		
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("./pescamines/EscenaPescamines.fxml"));
				Scene scene = new Scene(root,800,800);
				scene.getStylesheets().add(getClass().getResource("applicationPescamines.css").toExternalForm());
				primaryStage.setTitle("Pescamines");
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	//CREAR SEGONS DIFICULTAT
	public Tauler crearTauler(String dificultat) {
	int tamany = 0;
	
	switch(dificultat) {
	case "f" -> tamany = 9;
	case "n" -> tamany = 10;
	case "d" -> tamany = 12;
	default -> {dificultat = "n"; tamany = 10;}
	}
	return new Tauler(tamany, tamany);
	}
	//OMPLIR BOMBES i OMPLIR CASELLES LLIURES
	private Casella[][] assignarMines(Casella[][] c, int tamany, Random alea, String dificultat){

		//nombre de bombes com atribut?
		int nMines = 0;
		//decidir bombes
		if(dificultat == "f") {
			nMines=10;
		} else if(dificultat == "d") {
			nMines=15;
		} else {
			nMines=12;
		}
		
		//col·locar bombes
		for(int b=0;b<nMines;) {
			
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
				if(c[o][m]==null) {
					int nombre = 0;
					nombre = recompteMines(c,o,m);
					c[o][m] = new Lliure(nombre);
				}
			}
		}
		return c;
	}
	
	//RECOMPTE DE MINES
	public int recompteMines(Casella[][] c, int a, int b) {
		int comptador = 0;

		// CANTO SUP ESQUERRE
		if (a == 0 && b == 0) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// CANTO INF ESQUERRE
		else if (a == c.length - 1 && b == 0) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// CANTO SUP DRET
		else if (a == 0 && b == c.length - 1) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// CANTO INF DRET
		else if (a == c.length - 1 && b == c.length - 1) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// PRIMERA FILA
		else if (a == 0 && (b != 0 && b != c.length - 1)) {
			for (int k = a; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// ÚLTIMA FILA
		else if (a == c.length - 1 && (b != 0 && b != c.length - 1)) {
			for (int k = a - 1; k <= a; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// PRIMERA COLUMNA
		else if (b == 0 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}
		// ULTIMA COLUMNA
		else if (b == c.length - 1 && (a != 0 && a != c.length - 1)) {
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}

		} else {
			// RESTA
			for (int k = a - 1; k <= a + 1; k++) {
				for (int l = b - 1; l <= b + 1; l++)
					if (c[k][l] instanceof Mina)
						comptador++;
			}
		}

		return comptador;
	}
	
	//MONTAR BOTONS I CLAVAR-LOS AL ROOT
}
