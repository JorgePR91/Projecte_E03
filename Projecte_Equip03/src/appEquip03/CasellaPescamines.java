package appEquip03;

import java.io.Serializable;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public abstract class CasellaPescamines implements AccioCasellaPescamines, Serializable {
		protected boolean estat;
		protected transient StackPane container;
		protected transient Node contingut;
		protected int x;
		protected int y;
		protected ContextPescamines context;
		
		public CasellaPescamines(int x, int y, ContextPescamines context) {
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


		public void reaccio() {
			// TODO Auto-generated method stub
			
		}


}