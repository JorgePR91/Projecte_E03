package appEquip03;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
//import javafx.scene.text.Text;

public abstract class Casella {
		protected boolean estat;
		protected StackPane container;
		protected Node contingut;
		
		public Casella() {
			super();
			this.estat = true;
			this.container = new StackPane();
			//container.getChildren().add(contingut);
			// contingut.setVisible(!estat);
			//accio();
		}
		public boolean getEstat() {
			return estat;
		}
		public void setEstat(boolean estat) {
			this.estat = estat;
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

		
//		protected void contingut(Label l) {
//		        container.getChildren().add(l);
//		}
//		
		
}
