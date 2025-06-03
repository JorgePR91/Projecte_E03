package appEquip03;

import java.io.File;

public class DadesSingletonPescamines {
		private String cadenaCompartida; 
		private File partidaCompartida; 

		
		private final static DadesSingletonPescamines INSTANCIA =  new DadesSingletonPescamines();
		
		private DadesSingletonPescamines() {}

		public String getCadenaCompartida() {
			return cadenaCompartida;
		}

		public void setCadenaCompartida(String cadenaCompartida) {
			this.cadenaCompartida = cadenaCompartida;
		}

		public static DadesSingletonPescamines getInstancia() {
			return INSTANCIA;
		}

		public File getPartidaCompartida() {
			return partidaCompartida;
		}

		public void setPartidaCompartida(File partidaCompartida) {
			this.partidaCompartida = partidaCompartida;
		} 
		
		
}
