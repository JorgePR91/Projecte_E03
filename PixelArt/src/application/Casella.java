package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public abstract class Casella implements Serializable {
	private static final long serialVersionUID = 1L;

	
		protected boolean estat;
		protected transient StackPane container;
		protected transient Node contingut;
		protected int x;
		protected int y;
		
		public Casella() {}


		
		private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
			ois.defaultReadObject(); 
		    this.contingut = new StackPane();
			this.container = new StackPane();
			}
		
		
		public Casella(int x, int y) {
			super();
			this.x = x;
			this.y = y;
			
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
