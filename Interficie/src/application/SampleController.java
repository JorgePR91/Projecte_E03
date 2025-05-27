package application;

import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import java.util.EventListener;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class SampleController implements Initializable {
	@FXML
	private GridPane taulerGrid;
	@FXML
	private Button reinici;
	@FXML
	private Pane caixaTemps;
	@FXML
	private Pane compAntimines;
	@FXML
	private HBox capçalera;
	@FXML
	private Pane pantallaInici;
	
	private Tauler nouTauler;
	private Timeline temps;
	private Label cronometre;
	private int segons;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		caixaTemps.getChildren().clear();
		compAntimines.getChildren().clear();
		
		Context context = new Context();
		nouTauler = Context.crearTauler("");
		context.assignarMines(nouTauler.getCaselles(), Context.tamany, "n");
		nouGP(nouTauler.getCaselles());
		segons = 0;

		cronometre = new Label();
		compAntimines.getChildren().add(Context.caixaMines);

		caixaTemps.getChildren().add(cronometre);
		Format formatter = new SimpleDateFormat("mm:ss");

		temps = new Timeline(
				new KeyFrame(Duration.seconds(1), e -> {
					segons++;
					cronometre.setText(formatter.format(segons*1000));
					})
				);
		temps.setCycleCount(Timeline.INDEFINITE);
		
		pantallaInici.setPrefWidth(taulerGrid.getMinWidth());
		pantallaInici.setPrefHeight(taulerGrid.getHeight());
				
			//ACABAR EL PROGRAMA I DIR EL RESULTAT
			//temps.stop
			//botons inhabilitats
			//pulsar antimines inhabilitat
			//pantallaInici.setMouseTransparent(false);

	}
	
	public void nouGP(Casella[][] c) {
		GridPane gp = this.taulerGrid;
		
		//https://falkhausen.de/docs/JavaFX-10/javafx.scene.layout/GridPane/h.html
		
		//Netejar les característiques per defecte del SceneBuilder
	    gp.getColumnConstraints().clear();
	    gp.getRowConstraints().clear();

	    //Com les cree per bucle lògic, ho hem de fer per ací i no en el SB
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
	public void finalitzar() {
		
	}
	public void guardarPartida() {
		//SERIALIZED
		//https://javarush.com/es/groups/posts/es.710.cmo-funciona-la-serializacin-en-java
		
		//UTILITZAR CLASSE
		//ENVIAR VARIABLES NECESSÀRIES DESDE CONTEXT
	}

	@FXML
	public void reiniciar(ActionEvent e) {
		temps.stop();
		Context.buidar(this.nouTauler);
		taulerGrid.getChildren().clear();
		pantallaInici.setVisible(true);
		pantallaInici.setMouseTransparent(false);
		initialize(null, null);
	}
	@FXML
	public void iniciarJoc(MouseEvent e) {
		pantallaInici.setVisible(false);
		pantallaInici.setMouseTransparent(true);
		temps.play();
		e.consume();
	}
}
