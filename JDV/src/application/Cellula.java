package application;

import javafx.scene.paint.Color;

public class Cellula {
	protected Color colViva;
	protected boolean viva;
	protected Color colNaixement;
	protected boolean naix;
	protected Color colMoribunda;
	protected boolean moribunda;
	protected Color colMorta;
	protected boolean morta;
	protected int y;
	protected int x;
	
	public Cellula(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Color getColViva() {
		return colViva;
	}

	public void setColViva(Color colViva) {
		this.colViva = colViva;
	}

	public boolean isViva() {
		return viva;
	}

	public void setViva(boolean viva) {
		this.viva = viva;
	}

	public Color getColNaixement() {
		return colNaixement;
	}

	public void setColNaixement(Color colNaixement) {
		this.colNaixement = colNaixement;
	}

	public boolean isNaix() {
		return naix;
	}

	public void setNaix(boolean naix) {
		this.naix = naix;
	}

	public Color getColMoribunda() {
		return colMoribunda;
	}

	public void setColMoribunda(Color colMoribunda) {
		this.colMoribunda = colMoribunda;
	}

	public boolean isMoribunda() {
		return moribunda;
	}

	public void setMoribunda(boolean moribunda) {
		this.moribunda = moribunda;
	}

	public Color getColMorta() {
		return colMorta;
	}

	public void setColMorta(Color colMorta) {
		this.colMorta = colMorta;
	}

	public boolean isMorta() {
		return morta;
	}

	public void setMorta(boolean morta) {
		this.morta = morta;
	}

	

}
