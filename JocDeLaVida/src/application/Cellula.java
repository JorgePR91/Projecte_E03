package application;

import javafx.scene.paint.Color;

public class Cellula {
	protected boolean viva;
	protected boolean naix;
	protected boolean moribunda;
	protected boolean morta;
	protected int y;
	protected int x;

	public Cellula(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isViva() {
		return viva;
	}

	public void setViva(boolean viva) {
		this.viva = viva;
	}

	public boolean isNaix() {
		return naix;
	}

	public void setNaix(boolean naix) {
		this.naix = naix;
	}

	public boolean isMoribunda() {
		return moribunda;
	}

	public void setMoribunda(boolean moribunda) {
		this.moribunda = moribunda;
	}

	public boolean isMorta() {
		return morta;
	}

	public void setMorta(boolean morta) {
		this.morta = morta;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}
	
		
}
