package application;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		DadesSingleton dada = DadesSingleton.getInstancia();

		if (dada.getPartidaCompartida() != null) {
			ContextPixelArt contextProvisional = desserialitzacioTauler(dada.getPartidaCompartida());
			if (contextProvisional != null) {
				System.out.println("Copiant Context");
				System.out.println(contextProvisional.getTauler() == null);
				System.out.println(contextProvisional.getTauler().getCaselles().length);
				this.context = contextProvisional;
				// Tauler
				this.nouTauler = contextProvisional.getTauler();
				printDebug();

				taulerGrid.getChildren().clear();
				nouGP(nouTauler.getCaselles());
				dada.setCadenaCompartida(null);
				dada.setPartidaCompartida(null);
			} else {
				System.err.println("No hi ha res serialitzat");
			}
		} else {
			System.out.println("Nou context");
			context = new ContextPixelArt(dada.getCadenaCompartida());
			nouTauler = context.crearTauler(context.tamany(dada.getCadenaCompartida()),
					context.tamany(dada.getCadenaCompartida()));
			nouGP(nouTauler.getCaselles());

		}
    	Pixel p = (Pixel) nouTauler.getCaselles()[3][8];
    	
	}
	public void printDebug() {
	    System.out.println("Matriz caselles:");
	    if (nouTauler.getCaselles() == null) {
	        System.out.println("caselles es null");
	        return;
	    }
	    for (int i = 0; i < nouTauler.getCaselles().length; i++) {
	        for (int j = 0; j < nouTauler.getCaselles()[i].length; j++) {
	            System.out.print((nouTauler.getCaselles()[i][j] != null ? "X" : ".") + " ");
	        }
	        System.out.println();
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
			        System.out.println("Crea nou pixel");
					c[o][m] = new Pixel(o, m, context);
					String fons = context.conversioAHex(((Pixel) c[o][m]).getBase());
					System.out.println(""+context.conversioAColor(fons));
					planol.setStyle("-fx-background-color: " + fons + ";");
					//Falten Proporcions!!
				} else {
					Pixel p = (Pixel) c[o][m];
		            p.context = context;
		            p.base = context.perDefecte(o, m);

					if (!p.colorHex.isEmpty())
					//	int longitud = p.colorHex.length();
						System.out.println("-fx-background-color: " +p.colorHex + ";");
						
						planol.setStyle("-fx-background-color: #" + p.colorHex + ";");
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
		// SERIALITZAR PER A DESAR EL PROJECTE
		// FER MAPA DE BITS PER A EXPORTAR

		// A LA CLASSE CLAVAR CARPETA: O EN EL PROJECTE O EN DOCUMENTS(I SI FALLA EN EL
		// PROJECTE
			
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
		// SERIALIZED
		// https://javarush.com/es/groups/posts/es.710.cmo-funciona-la-serializacin-en-java
		if (id != null)
			serialitzacioPartida(context, id);

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

		        if (file != null) {
		        	int tamany = this.taulerGrid.getColumnCount();
		        	
		            try {
		                BufferedImage imatge = new BufferedImage(tamany, tamany, BufferedImage.TYPE_INT_RGB);

		                for (int r = 0; r < nouTauler.getCaselles().length; r++) {
		                    for (int c = 0; c < nouTauler.getCaselles().length; c++) {
		                    	
		                    	Pixel p = (Pixel) nouTauler.getCaselles()[r][c];
		                    	int roig = Integer.parseInt(p.colorHex.substring(0, 2), 16);
		                    	int verd = Integer.parseInt(p.colorHex.substring(2, 4), 16);
		                    	int blau = Integer.parseInt(p.colorHex.substring(4, 6), 16); 
		                    	
		                        imatge.setRGB(roig, verd, blau);
		                    }
		                }

		                ImageIO.write(imatge, "PNG", file);
		               System.out.println("Imatge exportada: " + file.getName());

		            } catch (IOException e) {
		                e.getMessage();
		            }
		        }
		    }

	

	public ContextPixelArt desserialitzacioTauler(File f) {
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
	        ContextPixelArt c = (ContextPixelArt) ois.readObject();
	        	//this.context = c;
//	        if(c.getTauler() != null) {
//	        	taulerGrid.getChildren().clear();
//	        	nouGP(c.getTauler().getCaselles());
	        	
	        	
//	            Casella[][] caselles = c.getTauler().getCaselles();
//	            
//	    		for (int o = 0; o < caselles.length; o++) {
//	    			for (int m = 0; m < caselles[o].length; m++) {
//	    				Pane planol = new Pane();
//	    				planol.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//
//	    				if (caselles[o][m] == null) {
//	    					caselles[o][m] = new Pixel(o, m, context);
//	    					System.out.println("pixel copiat");
//
//	    					String fons = context.conversioAHex(((Pixel) caselles[o][m]).getBase());
//	    					planol.setStyle("-fx-background-color: " + fons + ";");
//	    					//Falten Proporcions!!
//	    				} else {
//	    					System.out.println("pixel creat");
//	    					Pixel p = (Pixel) caselles[o][m];
//	    					if (!p.colorHex.isBlank())
//	    						planol.setStyle("-fx-background-color: " + Color.web(p.colorHex) + ";");
//	    				}
//	    				context.pintar(planol, (Pixel) caselles[o][m]);
//	    				taulerGrid.add(planol, o, m);
//	    				
//	    			}
//	    		}
//	        }
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
	
	public boolean serialitzacioPartida(ContextPixelArt cntxt, String id) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./Llenços/" + id + ".ser"))) {
			//this.tauler = cntxt.crearTauler(tamany, tamany); 
			oos.writeObject(cntxt); 
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
