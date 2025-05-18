package appEquip03;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

	public class Mina extends Casella implements AccioCasella {
		private Text text;
		private Button boto;
		
		public Mina() {			
			super(new Text ("X"));
			this.text = new Text ("X");
			this.boto = new Button();
			boto.setMinWidth(50);
			boto.setMinHeight(50);
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
      	getContingut().setVisible(!getEstat());
      	});
	}
	//EXPLOTAR -> DESTAPAR TOT: ENVIAR A TABLER
	
	
}
