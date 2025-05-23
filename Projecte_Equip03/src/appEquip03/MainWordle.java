package appEquip03;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

public class MainWordle extends Application {

    private static Stage stagePrincipal;

    @Override
    public void start(Stage primaryStage) {
        try {
            stagePrincipal = primaryStage;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaLogin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Login");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void canviarEscena(Scene novaEscena) {
        stagePrincipal.setScene(novaEscena);
        stagePrincipal.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
