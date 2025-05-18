package appEquip03;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

	public class Mina extends Casella implements AccioCasella {
		private Text text;
		private Button boto;
		//= new Button();
		
		public Mina() {
			super();
			this.text = new Text ("X");
			this.boto = new Button();
			boto.setMinWidth(50);
			boto.setMinHeight(50);
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
		boto.setOnAction(e -> {
      	setEstat(false);
      	boto.setVisible(getEstat());
      	this.text.setVisible(!super.getEstat());

      	});
	}
	//EXPLOTAR -> DESTAPAR TOT: ENVIAR A TABLER
	
	
}
