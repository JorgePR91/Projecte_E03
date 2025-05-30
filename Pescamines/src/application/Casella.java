package application;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public abstract class Casella {
		protected boolean estat;
		protected transient StackPane container;
		protected transient Node contingut;
		protected int x;
		protected int y;
		protected Context context;
		
		public Casella(int x, int y, Context context) {
			super();
			this.x = x;
			this.y = y;
			this.context = context;
			this.estat = true;
			this.container = new StackPane();
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


}
