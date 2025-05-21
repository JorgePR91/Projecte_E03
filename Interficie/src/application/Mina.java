package application;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

	public class Mina extends Casella implements AccioCasella {
		private Text text;
		private boolean antimines;
		private Button boto;
		//= new Button();
		
		public Mina(int x, int y) {
			super(x, y);
			this.text = new Text ("X");
			this.boto = new Button();
			boto.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			super.setContingut(this.text);
			super.container.getChildren().addAll(this.boto, this.text);
			this.text.setVisible(!super.estat);
			reaccio();
		}
					
	public Text getMina() {
		return text;
	}

	public void setMina(Text mina) {
		this.text = mina;
	}
	
	//METODES
	@Override
	public void reaccio() {
		//ACCEDIR A LA MATRIU I AL GIRD
		//COMPROVAR ELS ANTIMINES
		//CAMVIAR L'ESTAT I LA VISIBILITAT
		
		boto.setOnAction(e -> {
      	setEstat(false);
      	boto.setVisible(isEstat());
      	this.text.setVisible(!super.isEstat());

      	});
	}
	//EXPLOTAR -> DESTAPAR TOT: ENVIAR A TABLER
	
	
}
