package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class Mina extends Casella implements AccioCasella {
	private Text element;
	private boolean antimines;
	private final Text simbolAntimines = new Text("(A)");
	private Button boto;
	private Casella[][] c;

	public Mina(int x, int y, Casella[][] c) {
		super(x, y);
		this.c = c;
		this.element = new Text("X");
		this.boto = new Button();
		boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		super.setContingut(this.element);
		this.simbolAntimines.setMouseTransparent(true);
		this.simbolAntimines.setVisible(false);
		super.container.getChildren().addAll(this.boto, this.element, this.simbolAntimines);
		this.element.setVisible(!super.estat);
		reaccio();
	}

	public Text getMina() {
		return element;
	}

	public void setMina(Text mina) {
		this.element = mina;
	}

	public Text getElement() {
		return element;
	}

	public void setElement(Text element) {
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

	public Casella[][] getC() {
		return c;
	}

	public void setC(Casella[][] c) {
		this.c = c;
	}

	public Text getAnti() {
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
					 if(!antimines && Context.disminuirComptador()) {
							antimines = !antimines;
							simbolAntimines.setVisible(antimines);
							e.consume();
						} else if (antimines && Context.augmentarComptador()) {
							antimines = !antimines;
							simbolAntimines.setVisible(antimines);
							e.consume();
						}
				}
				if ((e.getButton() == MouseButton.PRIMARY) && !antimines) {
					for (Casella[] fila : c) {
						for (Casella cas : fila) {
							if (cas.isEstat()) {
								cas.setEstat(false);
								if (cas instanceof Lliure) {
									Lliure l = (Lliure) cas;
									l.getBoto().setVisible(false);
									l.getAnti().setVisible(false);
									l.getText().setVisible(true);
									// l.setAnti('imatge antimines fallit');
								} else if (cas instanceof Mina && !((Mina) cas).isAntimines()) {
									Mina m = (Mina) cas;
									m.getBoto().setVisible(false);
									m.getAnti().setVisible(false);
									m.getElement().setVisible(true);

									// FER ROIG
								} else {
									Mina m = (Mina) cas;
									m.boto.setMouseTransparent(true);
									m.simbolAntimines.setMouseTransparent(true);
								}
							}
						}
					}
				//	Context.finalitzar();
					e.consume();
				}

			}
		});
	}
	// EXPLOTAR -> DESTAPAR TOT: ENVIAR A TABLER

}
