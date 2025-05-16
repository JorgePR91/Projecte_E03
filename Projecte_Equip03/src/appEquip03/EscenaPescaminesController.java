package appEquip03;

import java.util.Random;

import appEquip03.pescamines.Tauler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class EscenaPescaminesController {
    @FXML
    private Pane root;
	@FXML
    public void initialize() {
		Tauler tablerNou = new Tauler("",new Random());
		root.getChildren().add(tablerNou.getGridPane());
	//REINICIAR PARTIDA
	}
}
