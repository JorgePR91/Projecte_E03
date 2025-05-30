package application;

public class DadesSingleton {
		private String cadenaCompartida; 
		
		private final static DadesSingleton INSTANCIA =  new DadesSingleton();
		
		private DadesSingleton() {}

		public String getCadenaCompartida() {
			return cadenaCompartida;
		}

		public void setCadenaCompartida(String cadenaCompartida) {
			this.cadenaCompartida = cadenaCompartida;
		}

		public static DadesSingleton getInstancia() {
			return INSTANCIA;
		} 
		
		
}
