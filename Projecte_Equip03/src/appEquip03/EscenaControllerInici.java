package appEquip03;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class EscenaControllerInici {

    @FXML
    private ImageView imatgeTitol;

    @FXML
    public void initialize() {
        Image img = new Image(getClass().getResourceAsStream("/interf4ce.png"));
        imatgeTitol.setImage(img);
    }

    @FXML
    private void anarALogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaLogin.fxml"));
            Parent root = loader.load();
            Scene loginScene = new Scene(root);
            MainWordle.canviarEscena(loginScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
