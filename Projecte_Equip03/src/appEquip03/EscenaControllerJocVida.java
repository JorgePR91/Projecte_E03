package appEquip03;

import java.io.IOException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

public class EscenaControllerJocVida implements Initializable {
	@FXML
	private GridPane taulerGrid;
	@FXML
	private Button tornar;
	@FXML
	private Label cronometre;
	@FXML
	private Label cicles;
	@FXML
	private Label r_moribundes;
	@FXML
	private Label r_vives;
	@FXML
	private Label r_mortes;
	@FXML
	private Label r_naix;

	private ContextJocVida context;
	private int segons;
	private Timeline temps;
	private boolean estancament;
	public int contCicles;
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

	public GridPane getTaulerGrid() {
		return taulerGrid;
	}

	public void setTaulerGrid(GridPane taulerGrid) {
		this.taulerGrid = taulerGrid;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		DadesSingletonJocVida dada = DadesSingletonJocVida.getInstancia();

		context = new ContextJocVida(dada.getCadenaCompartida());
		
		context.creacio(context.getAlea(), context.getLIMIT_inici());
		
		nouGP(context.getCellula());

		taulerGrid.prefHeightProperty().bind(taulerGrid.widthProperty());
		
		if (segons > 0 )
			segons = 0;
		if (contCicles > 0 )
			contCicles = 0;

		Format formatter = new SimpleDateFormat("mm:ss");
		
		
		temps = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			contCicles++;
			segons++;
			
			cronometre.setText(formatter.format(segons * 1000));
			estancament = context.preCicle();

			context.Resum(0);

			context.cicleDeLaVida();
			
			actualizarGrid(context.getCellula());
			
			context.recompteCel();
			
			if ((context.getVives() == 0) || estancament) {
				context.depurar();
				temps.stop();
				
				actualizarGrid(context.getCellula());

				System.out.println(
						"SIMULACIÓ DETESA - Cèl·lules: " + context.getComptCel() + ", Estancament: " + estancament);
			}
			actualitzarLabel(this.context);
		}));
		temps.setCycleCount(Timeline.INDEFINITE);
		temps.play();

	}

	
	public void nouGP(CellulaJocVida[][] c) {
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


		planols = new Pane[c.length][c.length];

		for (int o = 0; o < c.length; o++) {
			for (int m = 0; m < c[o].length; m++) {
				planols[o][m] = new Pane();
				planols[o][m].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

				String fons = context.conversioAHex(Color.GREEN);
				planols[o][m].setStyle("-fx-background-color: " + fons + ";");
				gp.add(planols[o][m], m, o);

				escoltar(planols[o][m], (CellulaJocVida) c[o][m]);
			}
		}
		setTaulerGrid(gp);
	}

	public void actualizarGrid(CellulaJocVida[][] c) {
		taulerGrid.getChildren().clear();

		for (int i = 0; i < c.length; i++) {
			for (int j = 0; j < c[i].length; j++) {
				Pane p = planols[i][j];
				CellulaJocVida cell = c[i][j];
				escoltar(p, cell);
				taulerGrid.add(p, j, i);
			}
		}
	}

	public void escoltar(Pane p, CellulaJocVida c) {

		if (c.getEstat().equals("viva")) {
		    String fons = context.conversioAHex(Color.GREEN);
		    p.setStyle("-fx-background-color: " + fons + ";");
		} else if (c.getEstat().equals("naixement")) {
		    String fons = context.conversioAHex(Color.LIGHTGREEN);
		    p.setStyle("-fx-background-color: " + fons + ";");
		} else if (c.getEstat().equals("moribunda")) {
		    String fons = context.conversioAHex(Color.CORAL);
		    p.setStyle("-fx-background-color: " + fons + ";");
		} else {
		    p.setStyle("-fx-background-color: white;");
		}
		
		// Falten Proporcions!!
	}
	
	public void actualitzarLabel(ContextJocVida context) {
		cicles.setText("Cicle: "+this.contCicles+"\nTotal creades: "+context.comptCel+"\nTotal mortes: "+context.comptMor);
		r_moribundes.setText(""+context.getMoribundes());
		r_vives.setText(""+context.getVives());
		r_mortes.setText(""+context.getMorts());
		r_naix.setText(""+context.getNaixements());
	}

	@FXML
	public void accelerar() {
		temps.setRate(temps.getRate() / 0.25);
	};

	@FXML
	public void endarrerir() {
		temps.setRate(temps.getRate() * 0.25);
	};

	@FXML
	public void reiniciar() {
		temps.stop();
		context.buidar(context.getCellula());

		CellulaJocVida[][] c = context.getCellula();

		for (int fil = 0; fil < c.length; fil++) {
			for (int col = 0; col < c.length; col++) {

				c[fil][col] = null;
			}

		}

		taulerGrid.getChildren().clear();
		initialize(null, null);

	};

	@FXML
	public void tornar() {
		temps.stop();
		System.out.println("Entrar en abandonar");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaDificultatJocVida.fxml"));

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

	@FXML
	protected void aturar() {
		if (temps.getStatus() == Status.PAUSED)
			temps.play();
		else
			temps.pause();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		// super.finalize();
	}

}
