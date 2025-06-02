package appEquip03;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EscenaControllerPixelArt implements Initializable {
	@FXML
	private GridPane taulerGrid;
	@FXML
	private Button pinzell;
	@FXML
	private Button esborrar;
	@FXML
	private Button exportarPNG;
	@FXML
	private Button undo;
	@FXML
	private Button avant;
	@FXML
	private Button guardar;
	@FXML
	private ColorPicker color;
	@FXML
	private Button tornar;

	private ContextPixelArt context;
	private TaulerPixelArt nouTauler;
	private int tamany;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DadesSingletonPixelArt dada = DadesSingletonPixelArt.getInstancia();

		if (dada.getPartidaCompartida() != null) {
			ContextPixelArt contextProvisional = desserialitzacioTauler(dada.getPartidaCompartida());
			if (contextProvisional != null) {
				this.context = contextProvisional;
				this.nouTauler = contextProvisional.getTauler();
				taulerGrid.getChildren().clear();
				nouGP(nouTauler.getCaselles());
				dada.setCadenaCompartida(null);
				dada.setPartidaCompartida(null);
			}
		} else {
			context = new ContextPixelArt(dada.getCadenaCompartida());
			nouTauler = context.crearTauler(context.tamany(dada.getCadenaCompartida()), context.tamany(dada.getCadenaCompartida()));
			nouGP(nouTauler.getCaselles());
		}
	}

	public void nouGP(CasellaPixelArt[][] c) {
		GridPane gp = this.taulerGrid;
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
				Pane planol = new Pane();
				planol.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

				if (c[o][m] == null) {
					c[o][m] = new PixelPixelArt(o, m, context);
					String fons = context.conversioAHex(((PixelPixelArt) c[o][m]).getBase());
					planol.setStyle("-fx-background-color: " + fons + ";");
				} else {
					PixelPixelArt p = (PixelPixelArt) c[o][m];
					p.context = context;
					p.base = context.perDefecte(o, m);
					planol.setStyle("-fx-background-color: #" + p.colorHex + ";");
				}
				context.pintar(planol, (PixelPixelArt) c[o][m]);
				gp.add(planol, o, m);
			}
		}
	}

	@FXML
	public void establirColor() {
		context.setColor(color.getValue());
	}

	@FXML
	public void pintarLlenç() {
		context.setColor(context.color);
		context.borrador = false;
	}

	@FXML
	public void netejarLlenc() {
		this.context.buidar(nouTauler);
		nouGP(nouTauler.getCaselles());
	}

	@FXML
	public void esborrarLlenç() {
		context.setBorrador(!context.borrador);
	}

	@FXML
	public void guardar() {
		String id = "";
		try {
			if (!ConnexioBD.connectarBD("ProjecteProg")) {
				ConnexioBD.connectarScriptBD(".././BD/script.sql");
				ConnexioBD.connectarBD("ProjecteProg");
			}
			String[] camps = { "usuari", "mida", "imatge" };
			String[] valors = { "usuari", "" + tamany, "imatge" };
			ConnexioBD.insertarDades("pixelart", camps, valors);
			id = ConnexioBD.ultimaID("pixelart", "id_llenc");
			ConnexioBD.tancarBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (id != null)
			serialitzacioPartida(context, id);
	}

	@FXML
	public void tornar() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaControllerDificultatPixelArt.fxml"));
			Stage window = (Stage) tornar.getScene().getWindow();
			Parent root = loader.load();
			Scene escena2 = new Scene(root);
			escena2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(escena2);
			window.setTitle("PixelArt");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@FXML
	public void exportar() {
		Stage window = (Stage) exportarPNG.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar imagen");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG files", "*.png"));
		File file = fileChooser.showSaveDialog(window);

		if (file != null) {
			int tamany = this.taulerGrid.getColumnCount();
			try {
				BufferedImage imatge = new BufferedImage(tamany, tamany, BufferedImage.TYPE_INT_RGB);
				for (int r = 0; r < tamany; r++) {
					for (int c = 0; c < tamany; c++) {
						PixelPixelArt p = (PixelPixelArt) nouTauler.getCaselles()[r][c];
						Color color = Color.web("#" + p.colorHex);
						int rgb = (int) (color.getRed() * 255) << 16 | (int) (color.getGreen() * 255) << 8 | (int) (color.getBlue() * 255);
						imatge.setRGB(c, r, rgb);
					}
				}
				ImageIO.write(imatge, "PNG", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ContextPixelArt desserialitzacioTauler(File f) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
			return (ContextPixelArt) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean serialitzacioPartida(ContextPixelArt cntxt, String id) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./Llenços/" + id + ".ser"))) {
			oos.writeObject(cntxt);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
