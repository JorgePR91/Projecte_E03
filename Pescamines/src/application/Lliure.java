package application;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Label;

public class Lliure extends PescaminesCasella implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean frontera;
	private int recompte;
	private transient Label text;
	// private transient Button boto;
	private boolean antimines;
	private final transient Label Anti = new Label("(A)");
	private final BooleanProperty antiminesBP = new SimpleBooleanProperty(false);

	private PescaminesCasella[][] c;

	// RECERCA DE MINES
	// SI RECOMPTE NO ÉS 0 = FRONTERA TRUE

	public Lliure(int x, int y, PescaminesCasella[][] c, PescaminesContext context) {
		super(x, y, context);
		this.c = c;
		// boto = new Button();
		// boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		text = new Label(" ");
		recompte = 0;
		frontera = false;
		antimines = false;
		Anti.setVisible(false);
		Anti.setMouseTransparent(true);
		super.setContingut(this.text);
		super.container.getChildren().addAll(this.text, /* this.boto, */ this.Anti);
		text.setVisible(!super.estat);
		// reaccio();
	}

	public Lliure(int x, int y, PescaminesContext context) {
		super(x, y, context);
	}

	public Lliure(int n, int x, int y, PescaminesCasella[][] c, PescaminesContext context) {
		super(x, y, context);
		this.c = c;

		// this.boto = new Button();
		// boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		this.text = new Label("" + n);
		this.recompte = n;
		this.frontera = true;
		this.antimines = false;
		this.Anti.setVisible(false);
		Anti.setMouseTransparent(true);
		super.container.getChildren().addAll(this.text, /* this.boto, */this.Anti);
		this.text.setVisible(!super.estat);
		// reaccio();
	}

	public boolean isFrontera() {
		return frontera;
	}

	public void setFrontera(boolean frontera) {
		this.frontera = frontera;
	}

	public int getRecompte() {
		return recompte;
	}

	public void setRecompte(int recompte) {
		this.recompte = recompte;
	}

	public void setText(Label text) {
		this.text = text;
		this.setContingut(text);
	}

	public BooleanProperty antiminesProperty() {
		return antiminesBP;
	}

	/*
	 * public Button getBoto() { return boto; }
	 * 
	 * public void setBoto(Button boto) { this.boto = boto; }
	 * 
	 * public boolean isAntimines() { return antimines; }
	 */
	public void setAntimines(boolean antimines) {
		this.antimines = antimines;
	}

	public Label getAnti() {
		return Anti;
	}

	public PescaminesCasella[][] getC() {
		return c;
	}

	// METODES

	public void setC(PescaminesCasella[][] c) {
		this.c = c;
	}

	public Label getText() {
		return text;
	}

	public boolean isAntimines() {
		return antimines;
	}

	public BooleanProperty getAntiminesBP() {
		return antiminesBP;
	}

//	@Override
//	public void reaccioRatoli(MouseEvent e, Node nod) {
//		((Button) nod).setOnMouseClicked(null);
//		Anti.setOnMouseClicked(null);
//
//		boto.setOnMouseClicked(new EventHandler<MouseEvent>() {
//			@Override
//			public void handle(MouseEvent e) {
//
//				if (e.getButton() == MouseButton.SECONDARY) {
//					if (!antimines && context.disminuirComptador()) {
//						antimines = !antimines;
//						Anti.setVisible(antimines);
//						e.consume();
//					} else if (antimines && context.augmentarComptador()) {
//						antimines = !antimines;
//						Anti.setVisible(antimines);
//						e.consume();
//					}
//				} else
//
//				if ((e.getButton() == MouseButton.PRIMARY) && !antimines) {
//					despejar(Lliure.this, Lliure.this.c);
//
//					e.consume();
//				}
//			}
//		});
//		System.out.println("boto de lliure");
//	}



	public Lliure despejar(Lliure l, PescaminesCasella[][] c) {
		// ES LLIURE
		// NO ES FRONTERA
		// DESTAPA FINS QUE ES FRONTERA EN TOTES DIRECCIONS

		if (l == null || !l.descobrir(l)) {
			return l;
		}
		if (l.recompte == 0) {
			if (l.getX() < c.length - 1) {
				despejar((Lliure) c[l.getX() + 1][l.getY()], c);
			}
			if (l.getX() > 0) {
				despejar((Lliure) c[l.getX() - 1][l.getY()], c);
			}
			if (l.getY() < c[0].length - 1) {
				despejar((Lliure) c[l.getX()][l.getY() + 1], c);
			}
			if (l.getY() > 0) {
				despejar((Lliure) c[l.getX()][l.getY() - 1], c);
			}
			return l;
		}
		return l;

	}

	public boolean descobrir(Lliure l) {
		boolean descoberta = false;
//		if (l instanceof Lliure && l.isEstat() == true && l.antimines) {
//			descoberta = true;
//			Platform.runLater(() -> {
//				l.setEstat(false);
//				l.boto.setVisible(l.isEstat());
//				l.text.setVisible(true);
//				l.Anti.setVisible(false);
//				context.augmentarComptador();
//
//			});
//
//		} else 
		if (l instanceof Lliure && l.isEstat() == true && !l.antimines) {
			descoberta = true;

			l.setEstat(false);
			/*
			 * l.boto.setVisible(l.isEstat()); l.text.setVisible(true);
			 * l.Anti.setVisible(false);
			 */
			context.setLliures(--context.lliures);
			context.comprovarPartida();
		}

		return descoberta;
	}
}
