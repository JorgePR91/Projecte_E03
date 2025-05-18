package appEquip03.pescamines;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
//import javafx.scene.text.Text;

public abstract class CasellaPM {
		private boolean estat;
		private StackPane container;
		private Button botoCasella;
		private Node contingut;
		
		public CasellaPM(Node t) {
			super();
			this.estat = true;
			this.botoCasella = new Button();
			this.contingut = t;
			botoCasella.setMinWidth(50);
			botoCasella.setMinHeight(50);
			t.setVisible(!estat);
			container = new StackPane();
			container.getChildren().addAll(this.botoCasella,contingut);
			polsarBoto();
		}
		public boolean getEstat() {
			return estat;
		}
		public void setEstat(boolean estat) {
			this.estat = estat;
		}
		public Button getBotoCasella() {
			return botoCasella;
		}
		public void setBotoCasella(Button botoCasella) {
			this.botoCasella = botoCasella;
		}
		public StackPane getContainer() {
			return container;
		}
		public void setContainer(StackPane container) {
			this.container = container;
		}
		public Node getContingut() {
			return contingut;
		}
		public void setContingut(Node contingut) {
			this.contingut = contingut;
		}
		private void polsarBoto() {
	        this.botoCasella.setOnAction(e -> {
	        	this.estat = false;
	        	this.botoCasella.setVisible(estat);
	        	this.contingut.setVisible(!estat);
	        	});
		}
		
//		protected void contingut(Label l) {
//		        container.getChildren().add(l);
//		}
//		
		
}
