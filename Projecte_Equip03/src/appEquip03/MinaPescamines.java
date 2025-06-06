package appEquip03;

import java.io.Serializable;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class MinaPescamines extends CasellaPescamines implements AccioCasellaPescamines, Serializable {
	private static final long serialVersionUID = 1L;

	private transient Label element;
	private boolean antimines;
	private final transient Label simbolAntimines = new Label("(A)");
	private transient Button boto;
	private CasellaPescamines[][] c;

	public MinaPescamines(int x, int y, CasellaPescamines[][] c, ContextPescamines context) {
		super(x, y, context);
		this.c = c;
		this.element = new Label("X");
		this.boto = new Button();
		boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		//boto.setMinSize(0, 0);
		boto.getStyleClass().add("botoPescamines");
		super.setContingut(this.element);
		this.simbolAntimines.setMouseTransparent(true);
		this.simbolAntimines.setVisible(false);
		super.container.getChildren().addAll(this.boto, this.element, this.simbolAntimines);
		StackPane.setAlignment(this.boto, Pos.CENTER);
		this.element.setVisible(!super.estat);
		reaccio();
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

	public CasellaPescamines[][] getC() {
		return c;
	}

	public void setC(CasellaPescamines[][] c) {
		this.c = c;
	}

	public Label getAnti() {
		return simbolAntimines;
	}

	// METODES

	@Override
	public void reaccio() {
		boto.setOnMouseClicked(null);
		simbolAntimines.setOnMouseClicked(null);
		
		boto.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY) {
					 if(!antimines && context.disminuirComptador()) {
							antimines = !antimines;
							simbolAntimines.setVisible(antimines);
							e.consume();
						} else if (antimines && context.augmentarComptador()) {
							antimines = !antimines;
							simbolAntimines.setVisible(antimines);
							e.consume();
						}
				}
				if ((e.getButton() == MouseButton.PRIMARY) && !antimines) {
					for (CasellaPescamines[] fila : c) {
						for (CasellaPescamines cas : fila) {
							if (cas.isEstat()) {
								cas.setEstat(false);
								if (cas instanceof LliurePescamines) {
									LliurePescamines l = (LliurePescamines) cas;
									l.getBoto().setVisible(false);
									l.getAnti().setVisible(false);
									l.getText().setVisible(true);
									// l.setAnti('imatge antimines fallit');
								} else if (cas instanceof MinaPescamines && !((MinaPescamines) cas).isAntimines()) {
									MinaPescamines m = (MinaPescamines) cas;
									m.getBoto().setVisible(false);
									m.getAnti().setVisible(false);
									m.getElement().setVisible(true);

									// FER ROIG
								} else {
									MinaPescamines m = (MinaPescamines) cas;
									m.boto.setMouseTransparent(true);
									m.simbolAntimines.setMouseTransparent(true);
								}
							}
						}
					}
					
					e.consume();
				}

			}
		});
	}
	// EXPLOTAR -> DESTAPAR TOT: ENVIAR A TABLER

}