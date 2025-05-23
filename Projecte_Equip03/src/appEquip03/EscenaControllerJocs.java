package appEquip03;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EscenaControllerJocs {

    @FXML
    private void anarAlWordle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaWordle.fxml"));
            Parent root = loader.load();
            Scene escenaWordle = new Scene(root);
            MainWordle.canviarEscena(escenaWordle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
