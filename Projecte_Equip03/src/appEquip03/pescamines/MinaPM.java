package appEquip03.pescamines;

import javafx.scene.text.Text;

	public class MinaPM extends CasellaPM {
		private Text text;
		
		public MinaPM() {			
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
