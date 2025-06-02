package application;

import java.io.Serializable;
import javafx.scene.paint.Color;

public class Pixel extends Casella implements Serializable {
	private static final long serialVersionUID = 1L;

	protected transient Color base;
	protected String colorHex;
	protected transient ContextPixelArt context;

	public Pixel(int x, int y, ContextPixelArt c) {
		super(x, y);
		this.context = c;
		base = context.perDefecte(x, y);
		colorHex = "";
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
