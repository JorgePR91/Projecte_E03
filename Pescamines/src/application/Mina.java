package application;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Mina extends PescaminesCasella implements Serializable {
	private static final long serialVersionUID = 1L;

	private transient Label element;
	private boolean antimines;
	private final BooleanProperty antiminesBP = new SimpleBooleanProperty(false);
	private transient Button boto;
	private PescaminesCasella[][] c;


	public Mina(int x, int y, PescaminesContext context) {
		super(x, y, context);

	}

	public Mina(int x, int y, PescaminesCasella[][] c, PescaminesContext context) {
		super(x, y, context);
		this.c = c;
		this.element = new Label("X");
		super.setContingut(this.element);
		this.element.setVisible(!super.estat);
		// reaccio();
	}

	public Label getMina() {
		return element;
	}

	public void setMina(Label mina) {
		this.element = mina;
	}

	public Label getElement() {
		return element;
	}

	public void setElement(Label element) {
		this.element = element;
	}

	public boolean isAntimines() {
		return antimines;
	}

	public void setAntimines(boolean antimines) {
		this.antimines = antimines;
	}

	public Button getBoto() {
		return boto;
	}

	public void setBoto(Button boto) {
		this.boto = boto;
	}

	public PescaminesCasella[][] getC() {
		return c;
	}

	public void setC(PescaminesCasella[][] c) {
		this.c = c;
	}

	public BooleanProperty antiminesProperty() {
		return antiminesBP;
	}

	// METODES

	@Override
	public void reaccioRatoli(MouseEvent e, Node nod) {
		if (e.getButton() == MouseButton.PRIMARY && !antimines) {
			for (PescaminesCasella[] fila : c) {
				for (PescaminesCasella cas : fila) {
					if((cas instanceof Mina) && ((Mina) cas).isAntimines()) {
						cas.setEstat(true);
					} else
					cas.setEstat(false);
				}
			}
		} else if (e.getButton() == MouseButton.SECONDARY) {
			if(isAntimines()) {
				setAntimines(false);
			}
		}
	}

}
