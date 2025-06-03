package application;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public abstract class PescaminesCasella implements AccioCasella {
	
		protected boolean estat;
		protected BooleanProperty estatBP;
		protected transient StackPane container;
		protected transient Node contingut;
		protected int x;
		protected int y;
		protected PescaminesContext context;
		
		private void writeObject(ObjectOutputStream oos) {
	        try {
	            oos.defaultWriteObject();
	            
	            oos.writeBoolean(estat);
	            oos.writeInt(x);
	            oos.writeInt(y);
	            oos.writeObject(context);
	            estatBP = new SimpleBooleanProperty(estat);
			} catch (IOException e) {
			}
	    }
	    
	    private void readObject(ObjectInputStream ois) {
	    	try {
	            ois.defaultReadObject();
	            
	            this.estat = ois.readBoolean();
	            this.x = ois.readInt();
	            this.y = ois.readInt();
	            this.context = (PescaminesContext) ois.readObject();
			} catch (IOException e) {
				// TODO: handle exception
			} catch (ClassNotFoundException e) {
				// TODO: handle exception
			}
	    }
				
		public PescaminesCasella() {
			super();
			this.container = new StackPane();

		}

		public PescaminesCasella(int x, int y, PescaminesContext context) {
			super();
			this.x = x;
			this.y = y;
			this.context = context;
			this.estat = true;
			this.estatBP = new SimpleBooleanProperty(estat);
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

		public BooleanProperty getEstatBP() {
			return estatBP;
		}

		public void setEstatBP(BooleanProperty estatBP) {
			this.estatBP = estatBP;
		}
		
		public  void setEstatBP(boolean estat) {
			this.estatBP.set(estat);
		}
		
		public PescaminesContext getContext() {
			return context;
		}

		public void setContext(PescaminesContext context) {
			this.context = context;
		}

		@Override
		public void reaccioRatoli(MouseEvent e, Node nod) {
			// TODO Auto-generated method stub
		}

		

}
