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
	private GridPane taulerGrid;
	@FXML
	private Button reinici;
	@FXML
	private Label temps;
	@FXML
	private Label antimines;
	
	private Tauler nouTauler;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		nouTauler = MetodeTauler.crearTauler("");
		nouGP(nouTauler.getCaselles());
		
	}
	
	public void nouGP(Casella[][] c) {
		GridPane gp = this.taulerGrid;
		
		//https://falkhausen.de/docs/JavaFX-10/javafx.scene.layout/GridPane/h.html
		
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
		
		//RECARREGAR L'ESCENA? O ELIMINAR TOTS ELS OBJECTES?
		MetodeTauler.buidar(this.nouTauler);
		taulerGrid.getChildren().clear();
		initialize(null, null);
		
	}
	
}
