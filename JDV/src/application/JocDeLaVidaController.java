package application;

import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JocDeLaVidaController implements Initializable {
	@FXML
	private GridPane taulerGrid;
	@FXML
	private Button exportarPNG;
	@FXML
	private Button tornar;
	@FXML
	private Label cronometre;

	private ContextJDLV context;
	private int segons;
	private int tamany;
	private Timeline temps;
	private Property partida;
	private boolean estancament;
	private Pane[][] planols;

	public Label getCronometre() {
		return cronometre;
	}

	public void setCronometre(Label cronometre) {
		this.cronometre = cronometre;
	}

	public Timeline getTemps() {
		return temps;
	}

	public void setTemps(Timeline temps) {
		this.temps = temps;
	}

	public Property getPartida() {
		return partida;
	}

	public void setPartida(Property partida) {
		this.partida = partida;
	}

	public GridPane getTaulerGrid() {
		return taulerGrid;
	}

	public void setTaulerGrid(GridPane taulerGrid) {
		this.taulerGrid = taulerGrid;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		DadesSingleton dada = DadesSingleton.getInstancia();

		context = new ContextJDLV(dada.getCadenaCompartida());
		nouGP(context.getCellula());

		Format formatter = new SimpleDateFormat("mm:ss");
		partida = new SimpleBooleanProperty(true);
		temps = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			segons++;
			cronometre.setText(formatter.format(segons * 1000));
			estancament = context.preCicle(context.getCellula());

			System.out.println("Estancamient detectat: " + estancament);
			context.Resum(0);
			context.cicleDeLaVida();
			actualizarGrid(context.getCellula());

			if ((context.recompteCel() == 0) || estancament) {
				temps.stop();
				context.depurar(context.getCellula());
				actualizarGrid(context.getCellula());

				System.out.println(
						"SIMULACIÓ DETESA - Cèl·lules: " + context.recompteCel() + ", Estancament: " + estancament);
			}

		}));
		temps.setCycleCount(Timeline.INDEFINITE);
		temps.play();

//		pantallaInici.setPrefWidth(taulerGrid.getMinWidth());
//		pantallaInici.setPrefHeight(taulerGrid.getHeight());

		// ACABAR EL PROGRAMA I DIR EL RESULTAT
		// temps.stop
		// botons inhabilitats
		// pulsar antimines inhabilitat
		// pantallaInici.setMouseTransparent(false);

//		getPartida().addListener((obs, oldVal, newVal) -> {
//			if (!newVal) {
//				acabarPartida();
//			}
//		});

	}

	public void nouGP(Cellula[][] c) {
		System.out.println("entrar");
		GridPane gp = this.taulerGrid;

		// https://falkhausen.de/docs/JavaFX-10/javafx.scene.layout/GridPane/h.html

		// Netejar les característiques per defecte del SceneBuilder
		gp.getColumnConstraints().clear();
		gp.getRowConstraints().clear();

		for (int i = 0; i < c.length; i++) {
			ColumnConstraints cC = new ColumnConstraints();
			cC.setPercentWidth(100.0 / c.length);
			gp.getColumnConstraints().add(cC);

			RowConstraints rC = new RowConstraints();
			rC.setPercentHeight(100.0 / c.length);
			gp.getRowConstraints().add(rC);
		}

		context.creacio(context.getCellula(), context.getAlea(), context.getLIMIT_inici());
		planols = new Pane[c.length][c.length];

		for (int o = 0; o < c.length; o++) {
			for (int m = 0; m < c[o].length; m++) {
				planols[o][m] = new Pane();
				planols[o][m].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

				String fons = context.conversioAHex(Color.GREEN);
				planols[o][m].setStyle("-fx-background-color: " + fons + ";");
				gp.add(planols[o][m], m, o);

				escoltar(planols[o][m], (Cellula) c[o][m]);
			}
		}
		setTaulerGrid(gp);
	}

	public void actualizarGrid(Cellula[][] c) {
		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[i].length; j++) {
				Pane p = planols[i][j];
				Cellula cell = c[i][j];
				escoltar(p, cell);
			}
		}
	}

	public void escoltar(Pane p, Cellula c) {

		if (c != null) {

			if (c.isMoribunda()) {
				String fons = context.conversioAHex(Color.CORAL);
				p.setStyle("-fx-background-color: " + fons + ";");
			} else if (c.isMorta()) {
				String fons = context.conversioAHex(Color.RED);
				p.setStyle("-fx-background-color: " + fons + ";");
			} else if (c.isNaix()) {
				String fons = context.conversioAHex(Color.LIGHTGREEN);
				p.setStyle("-fx-background-color: " + fons + ";");
			} else if (c.isViva()) {
				String fons = context.conversioAHex(Color.GREEN);
				p.setStyle("-fx-background-color: " + fons + ";");
			}

		} else
			p.setStyle("-fx-background-color: white;");

		// Falten Proporcions!!
	}

	@FXML
	public void accelerar() {
		temps.setRate(temps.getRate() * 0.5);

	};

	@FXML
	public void endarrerir() {
		temps.setRate(temps.getRate() / 0.5);

	};
	@FXML
	public void reiniciar() {
		temps.setRate(temps.getRate() / 0.5);

	};

	@FXML
	public void tornar() {
		temps.stop();
		System.out.println("Entrar en abandonar");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaDificultat.fxml"));

			Stage window = (Stage) tornar.getScene().getWindow();

			Parent root = loader.load();
			Scene escena2 = new Scene(root);

			escena2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(escena2);
			window.setTitle("Joc de la Vida");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		// super.finalize();
	}

}
