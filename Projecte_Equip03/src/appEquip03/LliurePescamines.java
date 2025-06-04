package appEquip03;

import java.io.Serializable;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class LliurePescamines extends CasellaPescamines implements AccioCasellaPescamines, Serializable {
	private static final long serialVersionUID = 1L;

	private boolean antimines;
	private int recompte;
	private transient Label text;
	private transient Button boto;
	private final transient Label Anti = new Label("(A)");
	private CasellaPescamines[][] c;

	public LliurePescamines(int x, int y, CasellaPescamines[][] c, ContextPescamines context) {
		super(x, y, context);
		this.c = c;
		boto = new Button();
		boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		boto.setMinSize(0, 0);
		text = new Label(" ");
		recompte = 0;
		antimines = false;
		Anti.setVisible(false);
		Anti.setMouseTransparent(true);
		super.setContingut(this.text);
		super.container.getChildren().addAll(this.text, this.boto, this.Anti);
		StackPane.setAlignment(this.boto, Pos.CENTER);
		text.setVisible(!super.estat);
		reaccio();
	}

	public LliurePescamines(int n, int x, int y, CasellaPescamines[][] c, ContextPescamines context) {
		super(x, y, context);
		this.c = c;

		this.boto = new Button();
		boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		this.text = new Label("" + n);
		this.recompte = n;
		this.antimines = false;
		this.Anti.setVisible(false);
		Anti.setMouseTransparent(true);
		super.container.getChildren().addAll(this.text, this.boto, this.Anti);
		this.text.setVisible(!super.estat);
		reaccio();
	}

	public int getRecompte() {
		return recompte;
	}

	public void setRecompte(int recompte) {
		this.recompte = recompte;
	}

	public Label getText() {
		return text;
	}

	public void setText(Label text) {
		this.text = text;
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

	public Label getAnti() {
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
					if (!antimines && context.disminuirComptador()) {
						antimines = !antimines;
						Anti.setVisible(antimines);
						e.consume();
					} else if (antimines && context.augmentarComptador()) {
						antimines = !antimines;
						Anti.setVisible(antimines);
						e.consume();
					}
				}

				if ((e.getButton() == MouseButton.PRIMARY) && !antimines) {
					despejar(LliurePescamines.this, LliurePescamines.this.c);

					e.consume();
				}
			}
		});

	}

	public LliurePescamines despejar(LliurePescamines l, CasellaPescamines[][] c) {

		if (l == null || !l.descobrir(l)) {
			return l;
		}
		if (l.recompte == 0) {
			if (l.getX() < c.length - 1) {
				despejar((LliurePescamines) c[l.getX() + 1][l.getY()], c);
			}
			if (l.getX() > 0) {
				despejar((LliurePescamines) c[l.getX() - 1][l.getY()], c);
			}
			if (l.getY() < c[0].length - 1) {
				despejar((LliurePescamines) c[l.getX()][l.getY() + 1], c);
			}
			if (l.getY() > 0) {
				despejar((LliurePescamines) c[l.getX()][l.getY() - 1], c);
			}
			return l;
		}
		return l;

	}

	public boolean descobrir(LliurePescamines l) {
		boolean descoberta = false;

		if (l instanceof LliurePescamines && l.isEstat() == true && !l.antimines) {
			descoberta = true;

			l.setEstat(false);
			l.boto.setVisible(l.isEstat());
			l.text.setVisible(true);
			l.Anti.setVisible(false);
			context.setLliures(--context.lliures);
			context.getCaixaMines().setText("Antimines\n" + context.getComptador() + "/" + context.getTamany()
					+ "\nCaselles descobertes: " + (context.getGrandaria() - context.getLliures()));

			context.comprovarPartida();
		}

		return descoberta;
	}
}