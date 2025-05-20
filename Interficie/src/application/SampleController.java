package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class SampleController implements Initializable {
	@FXML
	private BorderPane root;
	@FXML
	private GridPane taulerGrid;
	@FXML
	private Button reinici;
	@FXML
	private Label temps;
	@FXML
	private Label antimines;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("Initialize funcionant");

		Tauler nouTauler = crearTauler("");
		nouGP(nouTauler.getCaselles());
	}

	public Tauler crearTauler(String dificultat) {
		System.out.println("Métode crearTauler funcionant");
	int tamany = 0;
	
	switch(dificultat) {
	case "f" -> tamany = 9;
	case "n" -> tamany = 10;
	case "d" -> tamany = 12;
	default -> {dificultat = "n"; tamany = 10;}
	}
	return new Tauler(tamany, tamany);

	}
	
	public void nouGP(Casella[][] c) {
		System.out.println("Mètode nou gridPane funcionant");
		GridPane gp = this.taulerGrid;
		//Netejar les característiques per defecte del SceneBuilder
	    gp.getColumnConstraints().clear();
	    gp.getRowConstraints().clear();

	    //Com les cree per bucle lògic, ho hem de fer i no en el SB
	    for (int i = 0; i < c.length; i++) {
	        ColumnConstraints cC = new ColumnConstraints();
	        cC.setPercentWidth(100.0 / c.length);
	        gp.getColumnConstraints().add(cC);

	        RowConstraints rC = new RowConstraints();
	        rC.setPercentHeight(100.0 / c.length);
	        gp.getRowConstraints().add(rC);
	    }
        
		for(int o=0;o<c.length;o++) {
			for(int m=0;m<c[o].length;m++) {
				gp.add(c[o][m].getContainer(), o, m);

			}
		}
	}

	@FXML
	public void reiniciar(ActionEvent e) {
		initialize(null, null);
	}
}
