package application;

import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import java.util.Arrays;
import java.util.EventListener;

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

public class PescaminesController implements Initializable {
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
	private String dif;
	
	public String getDif() {
		return dif;
	}

	public void setDif(String dif) {
		this.dif = dif;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		caixaTemps.getChildren().clear();
		compAntimines.getChildren().clear();

		Context context = new Context();
		nouTauler = Context.crearTauler("");
		context.assignarMines(nouTauler.getCaselles(), Context.tamany, dif);
		nouGP(nouTauler.getCaselles());
		segons = 1;

		cronometre = new Label();
		compAntimines.getChildren().add(Context.caixaMines);

		caixaTemps.getChildren().add(cronometre);
		Format formatter = new SimpleDateFormat("mm:ss");

		temps = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			segons++;
			cronometre.setText(formatter.format(segons * 1000));
		}));
		temps.setCycleCount(Timeline.INDEFINITE);

		pantallaInici.setPrefWidth(taulerGrid.getMinWidth());
		pantallaInici.setPrefHeight(taulerGrid.getHeight());

		// ACABAR EL PROGRAMA I DIR EL RESULTAT
		// temps.stop
		// botons inhabilitats
		// pulsar antimines inhabilitat
		// pantallaInici.setMouseTransparent(false);

		Context.partida.addListener((obs, oldval, newval) -> {
			acabarPartida();
		});
	}

	public void nouGP(Casella[][] c) {
		GridPane gp = this.taulerGrid;

		// https://falkhausen.de/docs/JavaFX-10/javafx.scene.layout/GridPane/h.html

		// Netejar les característiques per defecte del SceneBuilder
		gp.getColumnConstraints().clear();
		gp.getRowConstraints().clear();

		// Com les cree per bucle lògic, ho hem de fer per ací i no en el SB
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

//    @FXML
//    private void tornarEnrere() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaLogin.fxml"));
//            Parent root = loader.load();
//            Scene escenaLogin = new Scene(root);
//            MainWordle.canviarEscena(escenaLogin);
//        } catch (Exception e) {
//            e.printStackTrace();
//            mostrarMissatge("No s'ha pogut tornar a l'inici.");
//        }
//    }
	@FXML
	public void guardarPartida() {
		String id = "";
		try {
			if (!ConnexioBD.connectarBD("ProjecteProg")) {
				ConnexioBD.connectarScriptBD(".././BD/script.sql");
				ConnexioBD.connectarBD("ProjecteProg");
			}
			String[] camps = { "usuari", "temps" };
			String[] valors = { "usuari", cronometre.getText() };
			ConnexioBD.insertarDades("partida_pescamines", camps, valors);
			id = ConnexioBD.ultimaID("partida_pescamines", "id_partida");

			ConnexioBD.tancarBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// SERIALIZED
		// https://javarush.com/es/groups/posts/es.710.cmo-funciona-la-serializacin-en-java
		if (id != null)
			Context.serialitzacioTauler(nouTauler, id);
		// UTILITZAR CLASSE
		// ENVIAR VARIABLES NECESSÀRIES DESDE CONTEXT
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

	@FXML
	public void acabarPartida() {
		temps.stop();
		taulerGrid.getChildren().forEach(element -> {
			element.setMouseTransparent(true);
		});
		
		if (Context.comptador == 0 && Context.lliures == 0) {
			try {
				if (!ConnexioBD.connectarBD("ProjecteProg")) {
					ConnexioBD.connectarScriptBD("./BD/script.sql");
					ConnexioBD.connectarBD("ProjecteProg");
				}
				String[] camps = { "usuari", "dificultat", "temps" };
				String[] valors = { "usuari", Context.getDificultat(), cronometre.getText() };
				ConnexioBD.insertarDades("ranking_pescamines", camps, valors);

				ConnexioBD.tancarBD();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// ACABAR EL PROGRAMA I DIR EL RESULTAT
		//
		// botons inhabilitats
		// pulsar antimines inhabilitat

	}


}
