package application;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class Lliure extends Casella implements AccioCasella {

	private boolean frontera;
	private int recompte;
	private Text text;
	private Button boto;
	private boolean antimines;
	private final Text Anti = new Text("(A)");
	private Casella[][] c;

	// RECERCA DE MINES
	// SI RECOMPTE NO ÉS 0 = FRONTERA TRUE

	public Lliure(int x, int y, Casella[][] c) {
		super(x, y);
		this.c = c;
		boto = new Button();
		boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		text = new Text(" ");
		recompte = 0;
		frontera = false;
		antimines = false;
		Anti.setVisible(false);
		Anti.setMouseTransparent(true);
		super.setContingut(this.text);
		super.container.getChildren().addAll(this.text, this.boto, this.Anti);
		text.setVisible(!super.estat);
		reaccio();
	}

	public Lliure(int n, int x, int y, Casella[][] c) {
		super(x, y);
		this.c = c;
		this.boto = new Button();
		boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		this.text = new Text("" + n);
		this.recompte = n;
		this.frontera = true;
		this.antimines = false;
		this.Anti.setVisible(false);
		Anti.setMouseTransparent(true);
		super.container.getChildren().addAll(this.text, this.boto, this.Anti);
		this.text.setVisible(!super.estat);
		reaccio();
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

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
		this.setContingut(text);
	}

	public Button getBoto() {
		return boto;
	}

	public void setBoto(Button boto) {
		this.boto = boto;
	}

	public boolean isAntimines() {
		return antimines;
	}

	public void setAntimines(boolean antimines) {
		this.antimines = antimines;
	}

	public Text getAnti() {
		return Anti;
	}

	// METODES

	@Override
	public void reaccio() {
		boto.setOnMouseClicked(null);
		Anti.setOnMouseClicked(null);

		boto.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY) {
					 if(!antimines && Context.disminuirComptador()) {
							antimines = !antimines;
							Anti.setVisible(antimines);
							e.consume();
						} else if (antimines && Context.augmentarComptador()) {
							antimines = !antimines;
							Anti.setVisible(antimines);
							e.consume();
						}
				}

				if ((e.getButton() == MouseButton.PRIMARY) && !antimines) {
					despejar(Lliure.this, Lliure.this.c);
					e.consume();
				}
			}
		});

	}

	public Lliure despejar(Lliure l, Casella[][] c) {
		// ES LLIURE
		// NO ES FRONTERA
		// DESTAPA FINS QUE ES FRONTERA EN TOTES DIRECCIONS

		if (l == null || !l.descobrir(l)) {
			return l;
		}
		if (!l.isFrontera()) {
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

		if (l instanceof Lliure && l.isEstat() == true && l.antimines) {

			l.setEstat(false);
			l.boto.setVisible(l.isEstat());
			l.text.setVisible(true);
			l.Anti.setVisible(false);
			Context.augmentarComptador();
			descoberta = true;
			
		} else if (l instanceof Lliure && l.isEstat() == true && !l.antimines) {

			l.setEstat(false);
			l.boto.setVisible(l.isEstat());
			l.text.setVisible(true);
			l.Anti.setVisible(false);
			descoberta = true;
		}

		return descoberta;
	}
}
