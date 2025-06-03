package appEquip03;

import java.io.File;

public class DadesSingleton {
		private String cadenaCompartida; 
		private String usuari; 
		private File partidaCompartida; 
		private int tamanyCompartit; 


		
		private final static DadesSingleton INSTANCIA =  new DadesSingleton();

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

		public int getTamanyCompartit() {
			return tamanyCompartit;
		}

		public void setTamanyCompartit(int tamanyCompartit) {
			this.tamanyCompartit = tamanyCompartit;
		}

		public String getUsuari() {
			return usuari;
		}

		public void setUsuari(String usuari) {
			this.usuari = usuari;
		} 
		
		
		
}
