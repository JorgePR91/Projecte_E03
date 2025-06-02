package appEquip03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWordle extends Application {

    private static Stage stagePrincipal;

    @Override
    public void start(Stage primaryStage) {
        try {
            stagePrincipal = primaryStage;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaInici.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 700, 600);

            scene.getStylesheets().add(getClass().getResource("applicationWordle.css").toExternalForm());

            primaryStage.setTitle("Inici");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false); // evita redimensionar
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void canviarEscena(Scene novaEscena) {
        novaEscena.getStylesheets().add(MainWordle.class.getResource("applicationWordle.css").toExternalForm());
        stagePrincipal.setScene(novaEscena);
        stagePrincipal.show();
    }

    public static Stage getStagePrincipal() {
        return stagePrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
