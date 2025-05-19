package appEquip03;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Lliure extends Casella implements AccioCasella {

	private boolean frontera;
	private int recompte;
	private Text text;
	private Button boto;
	private Casella[][] c;
	private int x;
	private int y;

	// RECERCA DE MINES
	// SI RECOMPTE NO ÉS 0 = FRONTERA TRUE
	{

	}

	public Lliure(int x, int y, Casella[][] c) {
		super(x, y);
		this.c = c;
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

	public Lliure(int n, int x, int y, Casella[][] c) {
		super(x, y);
		this.c = c;
		this.boto = new Button();
		boto.setMinWidth(50);
		boto.setMinHeight(50);
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
//			super.setEstat(false);
//			boto.setVisible(super.getEstat());
//			this.text.setVisible(!super.getEstat());
//			if(!this.isFrontera()) 
				descobrir(this, this.c);
		});

	}

//	public static Lliure descobrir(Lliure l, Casella[][] c) {
//		//ES LLIURE
//		//NO ES FRONTERA
//		//DESTAPA FINS QUE ES FRONTERA EN TOTES DIRECCIONS
//		if(l.isFrontera() == true || l.getEstat() == false) {
//			l.reaccio();
//			return l;
//		} else {
//			//SI NO ES FRONTERA
//			if(!l.isFrontera() && (c[l.getX()].length != l.getY()) && l.getEstat() == true) {
//				l.reaccio();
//				return 
//						descobrir((Lliure) c[l.getX()][l.getY()+1], c);
//			} else if (!l.isFrontera() && (l.getY()>0) && l.getEstat() == true) {
//				l.reaccio();
//				return descobrir((Lliure) c[l.getX()][l.getY()-1], c);
//			} else if (!l.isFrontera() && (c.length != l.getX()) && l.getEstat() == true) {
//				l.reaccio();
//				return descobrir((Lliure) c[l.getX()+1][l.getY()], c);
//			} else if (!l.isFrontera() && (l.getX()>0) && l.getEstat() == true) {
//				l.reaccio();
//				return descobrir((Lliure) c[l.getX()-1][l.getY()], c);
//				} else return l;
//				//-----------------------------------------
// 
//			//SI ESTÀ EN EL FINAL DE LA MATRIU
//			//
//		}
//	}

	public static void descobrir(Lliure l, Casella[][] c) {
		// ES LLIURE
		// NO ES FRONTERA
		// DESTAPA FINS QUE ES FRONTERA EN TOTES DIRECCIONS
		
		l.setEstat(false);
		l.getBoto().setVisible(l.getEstat());
		l.text.setVisible(!l.getEstat());
		
			if (!l.isFrontera()) {
				Casella aux = (Casella) l;
				int x = aux.getX();
				int y = aux.getY();
				//Lliure l2 = (Lliure) c[x+1][y];

				if ((x < c.length-2) && l2.getEstat()) {
					
					l2.setEstat(false);
					l2.getBoto().setVisible(l2.getEstat());
					l2.text.setVisible(!l2.getEstat());
					
					descobrir(l2, c);
					
				} 
//				if (x > 0) {
//					return descobrir(c[x)-1][y], c);
//				} 
//				if (y < c[0].length-1) {
//					return descobrir(c[x][y+1], c);
//				} 
//				if (y > 0) {
//					return descobrir(c[x][y-1], c);
//				} else
//					return cas;
			} 
	}
}
