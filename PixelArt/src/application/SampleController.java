package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

public class SampleController implements Initializable {
	@FXML
	private GridPane taulerGrid;
	@FXML
	private Button pinzell;
	@FXML
	private Button esborrar;
	@FXML
	private Button undo;
	@FXML
	private Button avant;
	@FXML
	private Button guardar;
	@FXML
	private ColorPicker color;


	private Tauler nouTauler;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		nouTauler = ContextPixelArt.crearTauler(50, 50);
		ContextPixelArt.ompllirLlenç(nouTauler.getCaselles());
		nouGP(nouTauler.getCaselles());

	}

	public void nouGP(Casella[][] c) {
		GridPane gp = this.taulerGrid;

		// https://falkhausen.de/docs/JavaFX-10/javafx.scene.layout/GridPane/h.html

		// Netejar les característiques per defecte del SceneBuilder
		gp.getColumnConstraints().clear();
		gp.getRowConstraints().clear();

		// Com les cree per bucle lògic, ho hem de fer i no en el SB
		for (int i = 0; i < c.length; i++) {
			ColumnConstraints cC = new ColumnConstraints();
			cC.setPercentWidth(100.0 / c.length);
			gp.getColumnConstraints().add(cC);

			RowConstraints rC = new RowConstraints();
			rC.setPercentHeight(100.0 / c.length);
			gp.getRowConstraints().add(rC);
		}

		for (int o = 0; o < c.length; o++) {
			for (int m = 0; m < c[o].length; m++) {
				gp.add(c[o][m].getContainer(), o, m);
			}
		}
	}

	@FXML
	public void establirColor() {
		ContextPixelArt.setColor(color.getValue());
	};
	@FXML
	public void pintarLlenç() {
		ContextPixelArt.setColor(color.getValue());
		if(ContextPixelArt.borrador)
			ContextPixelArt.borrador = false;
	};
	@FXML
	public void netejarLlenç() {
		ContextPixelArt.buidar(nouTauler);
		initialize(null, null);
	};
	@FXML
	public void esborrarLlenç() {
		ContextPixelArt.borrador = !ContextPixelArt.borrador;
	};
	@FXML
	public void guardar() {
		//https://www.delftstack.com/es/howto/java/create-a-bitmap-image-in-java/
		//SERIALITZAR PER A DESAR EL PROJECTE
		//FER MAPA DE BITS PER A EXPORTAR
		
		
		//A LA CLASSE CLAVAR CARPETA: O EN EL PROJECTE O EN DOCUMENTS(I SI FALLA EN EL PROJECTE
	};
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		//super.finalize();
	}
}
