package appEquip03.pescamines;

import javafx.scene.text.Text;

public class LliurePM extends CasellaPM {

	private boolean frontera;
	private int recompte;
	private Text text;
	//RECERCA DE MINES
	//SI RECOMPTE NO ÉS 0 = FRONTERA TRUE

	public LliurePM() {
		super(new Text() );
		// TODO Auto-generated constructor stub
	}
	public LliurePM(String n) {
		super(new Text(n) );
		// TODO Auto-generated constructor stub
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

	//METODES
	
}
