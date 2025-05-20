package appEquip03;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
//import javafx.scene.text.Text;

public abstract class Casella {
		protected boolean estat;
		protected StackPane container;
		protected Node contingut;
		protected int x;
		protected int y;
		
		public Casella(int x, int y) {
			super();
			this.x = x;
			this.y = y;
			
			this.estat = true;
			this.container = new StackPane();
			//container.getChildren().add(contingut);
			// contingut.setVisible(!estat);
			//accio();
		}
		
		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public boolean isEstat() {
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
