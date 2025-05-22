package application;

import javafx.scene.layout.AnchorPane;

public class Pixel extends Casella {
	protected static AnchorPane px;
	
	public Pixel(int x, int y) {
		super(x, y);
		px = new AnchorPane();
		
		if((x+y)%2==0)
			px.getStyleClass().add("pixel_parell");
		 else 
			px.getStyleClass().add("pixel_imparell");


		//px.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		super.container.getChildren().add(px);

	}

}
