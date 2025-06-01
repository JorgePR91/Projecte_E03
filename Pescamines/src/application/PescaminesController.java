package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import javafx.stage.Stage;
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

	private Tauler nouTauler;
	private Timeline temps;
	private Label cronometre;
	private int segons;
	private String dif;
	private Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {

		this.context = context;
//	    this.dif = context.getDificultat();
//
//	    // Reconstruir tablero
//	    this.nouTauler = new Tauler(context.getTamany(), context.getTamany());
//	    nouTauler.setCaselles(context.getCaselles());
//
//	    // Limpiar y reconstruir UI
//	    taulerGrid.getChildren().clear();
//	    nouGP(nouTauler.getCaselles());
//
//	    // Restaurar estado de las casillas
//	    for (int i = 0; i < context.getTamany(); i++) {
//	        for (int j = 0; j < context.getTamany(); j++) {
//	            Casella casella = context.getCaselles()[i][j];
//	            if (!casella.estat) {
//	                // Si la casilla estaba descubierta, mostrarla
//	                Node node = getNodeFromGridPane(taulerGrid, j, i);
//	                if (node != null) {
//	                    StackPane container = (StackPane) node;
//	                    container.getChildren().clear();
//	                    container.getChildren().add(casella.getContingut());
//	                }
//	            }
//	        }
//	    }
//
//	    // Restaurar contador de antiminas
//	    context.caixaMines.setText("Antimines\n" + context.getComptador() + "/" + context.getTamany());
//
//	    // Restaurar tiempo
//	    segons = context.getTemps();
//	    cronometre.setText("Temps: " + segons);
//
//	    // Ocultar pantalla inicial
//	    pantallaInici.setVisible(false);
//	    pantallaInici.setMouseTransparent(true);
//
//	    	

	}

//	private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
//		for (Node node : gridPane.getChildren()) {
//			if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
//				return node;
//			}
//		}
//		return null;
//	}

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
//Posar booleà si la cadena compartida és null
		DadesSingleton dada = DadesSingleton.getInstancia();

		if (dada.getPartidaCompartida() != null) {
			Context contextProvisional = Context.desserialitzacioTauler(dada.getPartidaCompartida());
			if (contextProvisional != null) {
				System.out.println("Copiant Context");
				context = contextProvisional;
				dif = context.getDificultat();
				// Tauler
				nouTauler = context.crearTauler(dif, context);
				context.assignarMines(nouTauler.getCaselles(), context.tamany, dif, context);
				taulerGrid.getChildren().clear();
				nouGP(nouTauler.getCaselles());
				segons = nouTauler.getTemps();
				dada.setCadenaCompartida(null);
				dada.setPartidaCompartida(null);
			}
		} else {
			System.out.println("Nou context");
			context = new Context();
			dif = dada.getCadenaCompartida();
			nouTauler = context.crearTauler(dif, context);
			context.assignarMines(nouTauler.getCaselles(), context.tamany, dif, context);
			nouGP(nouTauler.getCaselles());
			segons = 1;

		}

		cronometre = new Label();
		compAntimines.getChildren().add(context.caixaMines);

		caixaTemps.getChildren().add(cronometre);
		Format formatter = new SimpleDateFormat("mm:ss");

		temps = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
			segons++;
			nouTauler.setTemps(segons);
			cronometre.setText(formatter.format(segons * 1000));
		}));
		// posar límit en 59 minuts amb 59 segons
		temps.setCycleCount(Timeline.INDEFINITE);

		pantallaInici.setPrefWidth(taulerGrid.getMinWidth());
		pantallaInici.setPrefHeight(taulerGrid.getHeight());

		// ACABAR EL PROGRAMA I DIR EL RESULTAT
		// temps.stop
		// botons inhabilitats
		// pulsar antimines inhabilitat
		// pantallaInici.setMouseTransparent(false);

		context.getPartida().addListener((obs, oldVal, newVal) -> {
			if (!newVal) {
				acabarPartida();
			}
		});
		/*
		 * Parámetros del Listener observable (ObservableValue<? extends Tipo>): Es la
		 * propiedad que está siendo observada (en este caso, context.partida). Permite
		 * acceder a métodos como getValue() si necesitas el valor actual.
		 * 
		 * oldValue (Tipo): El valor anterior de la propiedad antes del cambio. Ejemplo:
		 * Si partida cambiaba de 5 a 10, oldValue sería 5.
		 * 
		 * newValue (Tipo): El valor nuevo de la propiedad después del cambio. Siguiendo
		 * el ejemplo anterior, newValue sería 10.
		 */
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

				if (c[o][m].isEstat()) {
					Button bot = new Button();
					gp.add(bot, o, m);
					bot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
					int col = o;
					int row = m;

					bot.setOnMouseClicked((EventHandler<MouseEvent>) e -> {
						
						if (e.getButton() == MouseButton.PRIMARY) {
							c[col][row].reaccioRatoli(e, bot);
							
							if (!c[col][row].isEstat()) {
								gp.getChildren().remove(bot);
								
								if(c[col][row] instanceof Mina) {
									System.out.println("Polsat Mina");
									Text element = new Text("X");
									gp.add(element, col, row);

								} else {
									System.out.println("Polsat Lliure");

									Text element = ((Lliure) c[col][row]).getText();
									gp.add(element, col, row);
								}
								
							}
							
						} else if (e.getButton() == MouseButton.SECONDARY) {
							if(c[col][row] instanceof Mina)
							((Mina) c[col][row]).antiminesProperty().addListener((obs, oldVal, newVal) -> {
								if(newVal) {
									Text simbolAntimines = new Text("(A)");
									gp.add(simbolAntimines, col, row);
								}


							});
							if(c[col][row] instanceof Lliure)
							((Lliure) c[col][row]).antiminesProperty().addListener((obs, oldVal, newVal) -> {
								if(newVal) {
									Text simbolAntimines = new Text("(A)");
									gp.add(simbolAntimines, col, row);
								}
							});
							c[col][row].reaccioRatoli(e, bot);

						}
							

						
					});
					
					if (c[o][m] instanceof Mina) {
						((Mina)c[o][m]).antiminesProperty().addListener((obs, oldVal, newVal) -> {
							context.caixaMines.setText("Antimines\n" + context.getComptador() + "/" + context.getTamany());
						});
					}
					
					c[o][m].getEstatBP().addListener((obs, oldVal, newVal) -> {
						if (!newVal) {
							gp.getChildren().remove(bot);

						}
					});

				} else {

				}

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
			context.serialitzacioPartida(context, id);
		// UTILITZAR CLASSE
		// ENVIAR VARIABLES NECESSÀRIES DESDE CONTEXT
	}

//¿Posar ací el mètode de carregar partida??
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
		// ACABAR EL PROGRAMA I DIR EL RESULTAT
		//
		// botons inhabilitats
		// pulsar antimines inhabilitat
	}

	public void tornar() {
		System.out.println("Entrar en abandonar");
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaDificultad.fxml"));

			Stage window = (Stage) boto_abandonar.getScene().getWindow();
			// DificultadController controller ;

			// loader.setController( new DificultadController());
			Parent root = loader.load();
			Scene escena2 = new Scene(root);

			escena2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			window.setScene(escena2);
			window.setTitle("Pescamines");
			window.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
