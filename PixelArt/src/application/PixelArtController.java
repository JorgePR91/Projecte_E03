package application;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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

public class PixelArtController implements Initializable {
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
	private Tauler nouTauler;
	private int tamany;
	private String id;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		DadesSingleton dada = DadesSingleton.getInstancia();
		//-------------------------------------------------------
		id = "usuario";
		if (dada.getPartidaCompartida() != null) {
			ContextPixelArt contextProvisional = desserialitzacioLlenç(dada.getPartidaCompartida());
			if (contextProvisional != null) {
				System.out.println("Copiant Context");
				this.context = contextProvisional;
				this.nouTauler = contextProvisional.getTauler();
				nouGP(nouTauler.getCaselles());
				dada.setCadenaCompartida(null);
				dada.setPartidaCompartida(null);
			} else {
				System.err.println("No hi ha res serialitzat");
			}
		} else {
			System.out.println("Nou context");
			context = new ContextPixelArt(dada.getCadenaCompartida());
			nouTauler = context.crearTauler(dada.getTamanyCompartit(),
					dada.getTamanyCompartit());
			nouGP(nouTauler.getCaselles());

		}
	}

	public void nouGP(Casella[][] c) {
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
				Pane planol = new Pane();
				planol.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

				if (c[o][m] == null) {
					c[o][m] = new Pixel(o, m, context);
					Pixel p = (Pixel) c[o][m];
					String fons = context.conversioAHex(p.getBase());
					planol.setStyle("-fx-background-color: " + fons + ";");
				} else {
					Pixel p = (Pixel) c[o][m];
					p.setBase(context.perDefecte(o, m));
					if (!p.colorHex.isEmpty()) {
						planol.setStyle("-fx-background-color: " + p.colorHex + ";");
					} else {
						planol.setStyle("-fx-background-color: " + context.conversioAHex(p.getBase()) + ";");
					}
				}
				context.pintar(planol, (Pixel) c[o][m]);
				gp.add(planol, o, m);
				this.taulerGrid = gp;
			}
		}
	}

	@FXML
	public void establirColor() {
		context.setColor(color.getValue());
	};

	@FXML
	public void pintarLlenç() {
		context.setColor(context.color);

		if (context.isBorrador())
			context.borrador = false;
	};

	@FXML
	public void netejarLlenc() {
		this.context.buidar(nouTauler);
		nouGP(nouTauler.getCaselles());
	};

	@FXML
	public void esborrarLlenç() {
		context.setBorrador(!context.borrador);
	};

	@FXML
	public void guardar() {
		// https://www.delftstack.com/es/howto/java/create-a-bitmap-image-in-java/

		try {
			if (!ConnexioBD.connectarBD("ProjecteProg")) {
				ConnexioBD.connectarScriptBD(".././BD/script.sql");
				ConnexioBD.connectarBD("ProjecteProg");
			}
			if (id != null) {
				byte[] arxiu = serialitzacioLlenç(context, id);
				
				if(arxiu.length == 0)
					System.err.println("Ha fallat la serialització");
					else
						if(guardarEnPC(arxiu, id)) {
							
						} else
							System.err.println("Ha fallat el desat en ordinador.");
				String[] camps = { "usuari", "mida", "imatge" };
				String[] valors = { "usuari", (""+tamany), " " };
				ConnexioBD.insertarDades("pixelart", arxiu, camps, valors);
				id = ConnexioBD.ultimaID("pixelart", "id_llenc");
			}
						
			ConnexioBD.tancarBD();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// SERIALIZED
		// https://javarush.com/es/groups/posts/es.710.cmo-funciona-la-serializacin-en-java

	};

	@FXML
	public void tornar() {
		System.out.println("Entrar en abandonar");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaDificultat.fxml"));

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

//https://stackoverflow.com/questions/4216123/how-to-scale-a-bufferedimage
		if (file != null) {
			try {
				int tamany = this.taulerGrid.getColumnCount();
				int escala = 10;

				BufferedImage imatge = new BufferedImage((tamany * escala), (tamany * escala),
						BufferedImage.TYPE_INT_RGB);

				for (int r = 0; r < nouTauler.getCaselles().length; r++) {
					for (int c = 0; c < nouTauler.getCaselles().length; c++) {
						Pixel p = (Pixel) nouTauler.getCaselles()[r][c];

						int roig;
						int verd;
						int blau;
						int rgb = 0;

						if (!p.getColorHex().isEmpty()) {
							Color colAux = Color.web(p.getColorHex());

							roig = (int) (colAux.getRed() * 255);
							verd = (int) (colAux.getGreen() * 255);
							blau = (int) (colAux.getBlue() * 255);

						} else {

							roig = (int) (Color.TRANSPARENT.getRed() * 255);
							verd = (int) (Color.TRANSPARENT.getGreen() * 255);
							blau = (int) (Color.TRANSPARENT.getBlue() * 255);

						}

						// estes dos Fórmules són de la IA
						rgb = (roig << 16) | (verd << 8) | blau;

						for (int dy = 0; dy < escala; dy++) {
							for (int dx = 0; dx < escala; dx++) {
								int x = c * escala + dx;
								int y = r * escala + dy;
								imatge.setRGB(x, y, rgb);
							}
						}

					}
				}
				ImageIO.write(imatge, "png", file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public byte[] serialitzacioLlenç(ContextPixelArt cntxt, String id) {
		try  {
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
		try {
			FileOutputStream fos = new FileOutputStream("./Llenços/" + id + ".d");
			fos.write(arxiu);
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ContextPixelArt desserialitzacioLlenç(File f) {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
			ContextPixelArt c = (ContextPixelArt) ois.readObject();
			return c;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return new ContextPixelArt();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		// super.finalize();
	}

}
