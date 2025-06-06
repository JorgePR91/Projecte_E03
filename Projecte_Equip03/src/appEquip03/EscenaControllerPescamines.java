package appEquip03;

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
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

public class EscenaControllerPescamines implements Initializable {
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
	@FXML
	private Label nomUsuariLabel;
	@FXML
	private Button tornarMenuBtn;
	@FXML
	private Button logoutBtn;

	private Timeline temps;
	private Label cronometre;
	private int segons;
	private String id;
	private String dif;
	private ContextPescamines context;
	private TaulerPescamines tauler;

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
		
		DadesSingleton dada = DadesSingleton.getInstancia();
		dif = dada.getCadenaCompartida();
		
		this.id = dada.getUsuari();
		nomUsuariLabel.setText("Usuari: " + id);

		if (dada.getPartidaCompartida() != null) {
			ContextPescamines contextProvisional = desserialitzacioTauler(dada.getPartidaCompartida());
			if (contextProvisional != null) {
				System.out.println("Copiant Context");
				this.context = contextProvisional;
				this.tauler = contextProvisional.getTauler();
				nouGP(this.tauler.getCaselles());
				dada.setCadenaCompartida(null);
				dada.setPartidaCompartida(null);
				this.segons = contextProvisional.getTemps();
			} else {
				System.err.println("No hi ha res serialitzat");
			}
		} else {
			System.out.println("Nou context");
			this.context = new ContextPescamines();
			this.tauler = context.crearTauler(dif, this.context);
			context.assignarMines(tauler.getCaselles(), context.tamany, dif, this.context);
			nouGP(this.tauler.getCaselles());
			this.segons = 1;
		}

		pantallaInici.setPrefWidth(taulerGrid.getMinWidth());
		pantallaInici.setPrefHeight(taulerGrid.getHeight());
		
		compAntimines.getChildren().add(this.context.caixaMines);
		cronometre = new Label("Temps\n00:00");	
		caixaTemps.getChildren().add(cronometre);
		Format formatter = new SimpleDateFormat("mm:ss");
		
		temps = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			segons++;
			cronometre.setText("Temps\n"+formatter.format(segons * 1000));
			context.setTemps(segons);
		}));
		temps.setCycleCount(3600);



		context.getPartida().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				acabarPartida();
			}
		});
	}

	public void nouGP(CasellaPescamines[][] c) {
		GridPane gp = this.taulerGrid;

		// https://falkhausen.de/docs/JavaFX-10/javafx.scene.layout/GridPane/h.html

		// Netejar les característiques per defecte del SceneBuilder
		gp.getColumnConstraints().clear();
		gp.getRowConstraints().clear();

		for (int i = 0; i < c.length; i++) {
			ColumnConstraints cC = new ColumnConstraints();
			cC.setPercentWidth(Math.floor(100.0 / c.length * 100) / 100.0);
			gp.getColumnConstraints().add(cC);

			RowConstraints rC = new RowConstraints();
			rC.setPercentHeight(Math.floor(100.0 / c.length * 100) / 100.0);
			gp.getRowConstraints().add(rC);
		}

		for (int o = 0; o < c.length; o++) {
			for (int m = 0; m < c[o].length; m++) {
				gp.add(c[o][m].getContainer(), o, m);
				gp.setHgrow(c[o][m].getContainer(), Priority.ALWAYS);
				gp.setVgrow(c[o][m].getContainer(), Priority.ALWAYS);
				c[o][m].getContainer().setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
		}

	}

	@FXML
	public void guardarPartida() {
		Format formatter = new SimpleDateFormat("mm:ss");
	
		try {
			if (!ConnexioBD.connectarBD("ProjecteProg")) {
				ConnexioBD.connectarScriptBD(".././BD/script.sql");
				ConnexioBD.connectarBD("ProjecteProg");
			}

			if (this.id != null) {
				byte[] arxiu = serialitzacioTauler(context);

				if (arxiu.length == 0)
					System.err.println("Ha fallat la serialització");
				else {
					if (guardarEnPC(arxiu, this.id)) {
						System.out.println("Partida guardada al teu PC: /Partides");
					} else
						System.err.println("Ha fallat el desat en ordinador.");
					
					String[] camps = { "usuari", "temps", "partida" };
					String[] valors = { this.id, formatter.format(segons * 1000), " " };
					for(String d : valors)
					System.out.println(d);

					ConnexioBD.insertarDades("partida_pescamines", arxiu, camps, valors);
					System.out.println(ConnexioBD.ultimaID("partida_pescamines", "id_partida"));
				}
			}

			ConnexioBD.tancarBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public byte[] serialitzacioTauler(ContextPescamines cntxt) {
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

	public boolean guardarEnPC(byte[] arxiu, String id) {

		File partides = new File("Partides");
		if (!partides.exists() || !partides.isDirectory())
			partides.mkdir();

		try {
			FileOutputStream fos = new FileOutputStream("./Partides/" + id + ".dat");
			fos.write(arxiu);
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ContextPescamines desserialitzacioTauler(File f) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
			ContextPescamines c = (ContextPescamines) ois.readObject();
			return c;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return new ContextPescamines();
		}
	}

	@FXML
	public void reiniciar(ActionEvent e) {
		temps.stop();
		context.buidar(this.tauler);
		taulerGrid.getChildren().clear();
		pantallaInici.setVisible(true);
		pantallaInici.setMouseTransparent(false);
		initialize(null, null);
	}

	@FXML
	public void iniciarJoc(MouseEvent event) {
		System.out.println("Inici del joc");
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
		Format formatter = new SimpleDateFormat("mm:ss");

		try {
			if (!ConnexioBD.connectarBD("ProjecteProg")) {
				ConnexioBD.connectarScriptBD("./BD/script.sql");
				ConnexioBD.connectarBD("ProjecteProg");
			}
			String[] camps = { "usuari", "dificultat", "temps" };
			String[] valors = { "usuari", formatter.format(segons * 1000), cronometre.getText() };
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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaDificultatPescamines.fxml"));

			Stage window = (Stage) boto_abandonar.getScene().getWindow();

			Parent root = loader.load();
			Scene escena2 = new Scene(root);

			escena2.getStylesheets().add(getClass().getResource("applicationWordle.css").toExternalForm());
			window.setScene(escena2);
			window.setTitle("Pescamines");

			Platform.runLater(() -> { // espera que l'inicialització siga completa.
				window.show();
			});

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	private void tornarInici() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaInici.fxml"));
			Parent root = loader.load();
			Scene novaEscena = new Scene(root, 700, 600);

			novaEscena.getStylesheets().add(getClass().getResource("applicationWordle.css").toExternalForm());

			Stage stageActual = (Stage) logoutBtn.getScene().getWindow();
			stageActual.setScene(novaEscena);
			stageActual.setTitle("Inici");

			for (Window window : Stage.getWindows()) {
				if (window instanceof Stage && window != stageActual) {
					((Stage) window).close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void tornarMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaJocs.fxml"));
			Parent root = loader.load();
			Scene novaEscena = new Scene(root, 700, 600);

			Main.canviarEscena(novaEscena);

			Stage actual = (Stage) logoutBtn.getScene().getWindow();
			actual.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}