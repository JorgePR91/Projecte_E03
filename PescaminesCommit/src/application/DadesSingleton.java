package application;

import java.io.File;

public class DadesSingleton {
		private String cadenaCompartida; 
		private File partidaCompartida; 

		
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

		public File getPartidaCompartida() {
			return partidaCompartida;
		}

		public void setPartidaCompartida(File partidaCompartida) {
			this.partidaCompartida = partidaCompartida;
		} 
		
		
}
