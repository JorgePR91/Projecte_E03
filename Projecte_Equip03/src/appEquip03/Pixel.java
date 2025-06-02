package appEquip03;



import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Pixel extends Casella implements Serializable {
	private static final long serialVersionUID = 1L;


	//color per defecte
	protected transient Color base;
	//color si está pintat
	protected String colorHex;
	//context compartit
	protected transient ContextPixelArt context;


	public Pixel(int x, int y, ContextPixelArt c) {
		super(x, y);
		this.context = c;
		base = context.perDefecte(x, y);
		colorHex = "";
	}
	
	private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		ois.defaultReadObject();
		
		//context = new ContextPixelArt();
		//base = context.perDefecte(x, y);
		
	}



	public Color getBase() {
		return base;
	}

	public void setBase(Color base) {
		this.base = base;
	}

	public String getColorHex() {
		return colorHex;
	}

	public void setColorHex(String colorHex) {
		this.colorHex = colorHex;
	}

	public ContextPixelArt getContext() {
		return context;
	}

	public void setContext(ContextPixelArt context) {
		this.context = context;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
