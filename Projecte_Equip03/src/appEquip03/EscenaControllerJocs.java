package appEquip03;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class EscenaControllerJocs {

    @FXML
    private void anarWordle() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaWordle.fxml"));
            Parent root = loader.load();
            Scene escenaWordle = new Scene(root);
            MainWordle.canviarEscena(escenaWordle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



	@FXML
	private void anarPescamines() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaPescamines.fxml"));
	        Parent root = loader.load();
	        Scene escenaPescamines = new Scene(root);
	        MainWordle.canviarEscena(escenaPescamines);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void anarJocVida() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaJocVida.fxml"));
	        Parent root = loader.load();
	        Scene escenaJocVida = new Scene(root);
	        MainWordle.canviarEscena(escenaJocVida);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	@FXML
	private void anarPixelArt() {
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("EscenaPixelArt.fxml"));
	        Parent root = loader.load();
	        Scene escenaPixelArt = new Scene(root);
	        MainWordle.canviarEscena(escenaPixelArt);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
}
