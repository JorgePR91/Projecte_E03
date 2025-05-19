package appEquip03;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Lliure extends Casella implements AccioCasella {

	private boolean frontera;
	private int recompte;
	private Text text;
	private Button boto;
	private int x;
	private int y;

	//RECERCA DE MINES
	//SI RECOMPTE NO ÉS 0 = FRONTERA TRUE
	{

	}
	public Lliure(int x, int y) {
		super(x,y);

		this.boto = new Button();
		boto.setMinWidth(50);
		boto.setMinHeight(50);
		this.text = new Text(" ");
		this.recompte = 0;
		this.frontera = false;
		super.container.getChildren().addAll(this.text, this.boto);
		this.text.setVisible(!super.estat);
		reaccio();
	}
	public Lliure(int n, int x, int y) {
		super(x, y);

		this.boto = new Button();
		boto.setMinWidth(50);
		boto.setMinHeight(50);
		this.text = new Text(""+n);
		this.recompte = n;
		this.frontera = true;
		super.container.getChildren().addAll(this.text, this.boto);
		this.text.setVisible(!super.estat);
		reaccio();
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
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
	//METODES
	@Override
	public void reaccio() {
		boto.setOnAction(e -> {
      	super.setEstat(false);
      	boto.setVisible(super.getEstat());
      	this.text.setVisible(!super.getEstat());
      	descobrir(this, Tauler.get , this.x, this.y);
      	});
	}
	

}
