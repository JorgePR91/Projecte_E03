package application;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

	public class Mina extends Casella implements AccioCasella {
		private Text element;
		private boolean antimines;
		private Button boto;
		private Casella[][] c;
		//= new Button();
		
		public Mina(int x, int y, Casella[][] c) {
			super(x, y);
			this.c = c;
			this.element = new Text ("X");
			this.boto = new Button();
			boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			super.setContingut(this.element);
			super.container.getChildren().addAll(this.boto, this.element);
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

	//METODES
	@Override
	public void reaccio() {
		//ACCEDIR A LA MATRIU I AL GIRD
		//COMPROVAR ELS ANTIMINES
		//CAMVIAR L'ESTAT I LA VISIBILITAT
		
		boto.setOnAction(e -> {
			
		for(Casella[] fila : c) {
			for(Casella cas : fila) {
				if(cas.isEstat()) {
					cas.setEstat(false);
					if(cas instanceof Lliure) {
						Lliure l = (Lliure) cas;
						l.getBoto().setVisible(false);
						l.getText().setVisible(true);
					} else {
						Mina m = (Mina) cas;
						m.getBoto().setVisible(false);
						m.getElement().setVisible(true);
					}

				}
			}
		}
			


      	});
	}
	//EXPLOTAR -> DESTAPAR TOT: ENVIAR A TABLER
	
	
}
