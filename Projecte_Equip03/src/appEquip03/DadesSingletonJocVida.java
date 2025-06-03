package appEquip03;

import java.io.File;

public class DadesSingletonJocVida {
		private String cadenaCompartida; 
		private File partidaCompartida; 
		private int tamanyCompartit; 


		
		private final static DadesSingletonJocVida INSTANCIA =  new DadesSingletonJocVida();
		
		private DadesSingletonJocVida() {}

		public String getCadenaCompartida() {
			return cadenaCompartida;
		}

		public void setCadenaCompartida(String cadenaCompartida) {
			this.cadenaCompartida = cadenaCompartida;
		}

		public static DadesSingletonJocVida getInstancia() {
			return INSTANCIA;
		}

		public File getPartidaCompartida() {
			return partidaCompartida;
		}

		public void setPartidaCompartida(File partidaCompartida) {
			this.partidaCompartida = partidaCompartida;
		}

		public int getTamanyCompartit() {
			return tamanyCompartit;
		}

		public void setTamanyCompartit(int tamanyCompartit) {
			this.tamanyCompartit = tamanyCompartit;
		} 
		
		
		
}
