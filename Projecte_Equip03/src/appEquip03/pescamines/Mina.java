package appEquip03.pescamines;

import javafx.scene.text.Text;

	public class Mina extends Casella {
		private Text text;
		
		public Mina() {			
			super(new Text ("X"));
		}

	public Text getMina() {
		return text;
	}

	public void setMina(Text mina) {
		this.text = mina;
	}
	
	
	//EXPLOTAR -> DESTAPAR TOT: ENVIAR A TABLER
	
	
}
