package application;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Pixel extends Casella {
	//protected StackPane base;
	protected AnchorPane px;
	protected Color base;
	
	public Pixel(int x, int y) {
		super(x, y);
		px = new AnchorPane();
		//base = new StackPane();
		base = ContextPixelArt.perDefecte(this, x,y);
		px.setStyle("-fx-background-color: #" + base.toString().substring(base.toString().indexOf("x") + 1)
		+ ";");

		px.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		super.container.getChildren().add(px);
		ContextPixelArt.pintar(px, this);
		
	}
	

	

}
