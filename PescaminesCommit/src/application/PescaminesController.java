package application;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class PescaminesController implements Initializable {
	@FXML
	private GridPane taulerGrid;
	@FXML
	private Button reinici;
	@FXML
	private Button boto_abandonar;
	@FXML
	private Button boto_guardarPartida;
	@FXML
	private Pane caixaTemps;
	@FXML
	private Pane compAntimines;
	@FXML
	private HBox capçalera;
	@FXML
	private Pane pantallaInici;

	private PescaminesTauler nouTauler;
	private Timeline temps;
	private Label cronometre;
	private int segons;
	private String id;
	private String dif;
	private PescaminesContext context;

	public String getDif() {
		return dif;
	}

	public void setDif(String dif) {
		this.dif = dif;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		id = "usuari";
		caixaTemps.getChildren().clear();
		compAntimines.getChildren().clear();

		DadesSingleton dada = DadesSingleton.getInstancia();
		dif = dada.getCadenaCompartida();
		
		context = new PescaminesContext();
		nouTauler = context.crearTauler(dif, context);
		context.assignarMines(nouTauler.getCaselles(), context.tamany, dif, context);
		nouGP(nouTauler.getCaselles());
		segons = 1;

		cronometre = new Label();
		compAntimines.getChildren().add(context.caixaMines);

		caixaTemps.getChildren().add(cronometre);
		Format formatter = new SimpleDateFormat("mm:ss");

		temps = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			segons++;
			cronometre.setText(formatter.format(segons * 1000));
		}));
		temps.setCycleCount(3600);

		pantallaInici.setPrefWidth(taulerGrid.getMinWidth());
		pantallaInici.setPrefHeight(taulerGrid.getHeight());

		context.getPartida().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				acabarPartida();
			}
		});
	}

	public void nouGP(PescaminesCasella[][] c) {
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

		for (int o = 0; o < c.length; o++) {
			for (int m = 0; m < c[o].length; m++) {
				gp.add(c[o][m].getContainer(), o, m);
			}
		}
	}

	@FXML
	public void guardarPartida() {

		try {
			if (!ConnexioBD.connectarBD("ProjecteProg")) {
				ConnexioBD.connectarScriptBD(".././BD/script.sql");
				ConnexioBD.connectarBD("ProjecteProg");
			}
			
			if (id != null) {
				byte[] arxiu = serialitzacioTauler(context);

				if (arxiu.length == 0)
					System.err.println("Ha fallat la serialització");
				else if (guardarEnPC(arxiu)) {

				} else
					System.err.println("Ha fallat el desat en ordinador.");

				String[] camps = { "usuari", "temps", "partida" };
				String[] valors = { id, cronometre.getText() };
				ConnexioBD.insertarDades("partida_pescamines", arxiu,  camps, valors);
				id = ConnexioBD.ultimaID("partida_pescamines", "id_partida");

			}

			ConnexioBD.tancarBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] serialitzacioTauler(PescaminesContext cntxt) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);

			oos.writeObject(cntxt);
			baos.close();
			oos.close();
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}
	}

	public boolean guardarEnPC(byte[] arxiu) {
		try {
			FileOutputStream fos = new FileOutputStream("./partides/" + id + ".d");
			fos.write(arxiu);
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public PescaminesContext desserialitzacioTauler(File f) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
			PescaminesContext c = (PescaminesContext) ois.readObject();
			return c;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return new PescaminesContext();
		}
	}
	
	@FXML
	public void reiniciar(ActionEvent e) {
		temps.stop();
		context.buidar(this.nouTauler);
		taulerGrid.getChildren().clear();
		pantallaInici.setVisible(true);
		pantallaInici.setMouseTransparent(false);
		initialize(null, null);
	}

	@FXML
	public void iniciarJoc(MouseEvent event) {
		pantallaInici.setVisible(false);
		pantallaInici.setMouseTransparent(true);
		temps.play();
		event.consume();
	}
	@FXML
	public void acabarPartida() {
		temps.stop();
		boto_guardarPartida.setDisable(true);
		taulerGrid.getChildren().forEach(node -> node.setMouseTransparent(true));

		if (context.comptador == 0 && context.lliures == 0) {
			enviarRanquing();
			System.out.println("Rànquing enviat");
		}
		System.out.println("Acabada la partida");

		tornar();
	}

	public void enviarRanquing() {
		
		try {
			if (!ConnexioBD.connectarBD("ProjecteProg")) {
				ConnexioBD.connectarScriptBD("./BD/script.sql");
				ConnexioBD.connectarBD("ProjecteProg");
			}
			String[] camps = { "usuari", "dificultat", "temps" };
			String[] valors = { "usuari", context.getDificultat(), cronometre.getText() };
			ConnexioBD.insertarDades("ranking_pescamines", camps, valors);

			ConnexioBD.tancarBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boto_guardarPartida.setDisable(true);
		boto_abandonar.setDisable(true);

	}

	public void tornar() {
		System.out.println("Entrar en abandonar");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaPescaminesDificultat.fxml"));

			Stage window = (Stage) boto_abandonar.getScene().getWindow();

			Parent root = loader.load();
			Scene escena2 = new Scene(root);

			escena2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(escena2);
			window.setTitle("Pescamines");
			
			Platform.runLater(() -> { // espera que l'inicialització siga completa.
				window.show();
			});

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}