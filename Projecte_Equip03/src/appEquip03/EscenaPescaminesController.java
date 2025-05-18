package appEquip03;

import java.util.Random;

import appEquip03.pescamines.TaulerPM;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;

public class EscenaPescaminesController {
    @FXML
    private Pane root;
	@FXML
    public void initialize() {
//		TaulerPM tablerNou = new TaulerPM("",new Random());
		Tauler taulerNou = dificultat("");
		root.getChildren().add(taulerNou.getGridPane());

		
		
	//REINICIAR PARTIDA
	}
	//CREAR SEGONS DIFICULTAT
	public Tauler dificultat(String dificultat) {
	int tamany = 0;
	
	switch(dificultat) {
	case "f" -> tamany = 9;
	case "n" -> tamany = 10;
	case "d" -> tamany = 12;
	default -> {dificultat = "n"; tamany = 10;}
	}
	return new Tauler(tamany, tamany);
	}
	
	public void recorrerTauler(Casella[][] c) {
		int nM = 0;
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
//				if(c[o][m] instanceof Lliure) {
//					nM = recompteMines(c, o, m);
//					Lliure aux = (Lliure) c[o][m];
//					aux.setRecompte(nM);
//					aux.setText(new Text(""+aux.getRecompte()));
//				}
			}
			}		
	}
	
}
