package application;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Lliure extends Casella implements AccioCasella {

	private boolean frontera;
	private int recompte;
	private Text text;
	private Button boto;
	private Casella[][] c;

	// RECERCA DE MINES
	// SI RECOMPTE NO ÉS 0 = FRONTERA TRUE

	public Lliure(int x, int y, Casella[][] c) {
		super(x, y);
		this.c = c;
		this.boto = new Button();
		boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		this.text = new Text(" ");
		this.recompte = 0;
		this.frontera = false;
		super.container.getChildren().addAll(this.text, this.boto);
		this.text.setVisible(!super.estat);
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
		super.container.getChildren().addAll(this.text, this.boto);
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

	// METODES
	@Override
	public void reaccio() {
		boto.setOnAction(e -> {
			despejar(this, this.c);
		});
	}

	public Lliure despejar(Lliure l, Casella[][] c) {
		// ES LLIURE
		// NO ES FRONTERA
		// DESTAPA FINS QUE ES FRONTERA EN TOTES DIRECCIONS

		if (l == null||!l.descobrir(l)) {
			return l;
		} 
		if(!l.isFrontera()) {
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

		if (l instanceof Lliure && l.isEstat() == true) {

			l.setEstat(false);
			l.getBoto().setVisible(l.isEstat());
			l.text.setVisible(true);
			descoberta = true;
		}

		return descoberta;
	}
}
