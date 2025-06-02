package appEquip03;


public class CellulaJocVida {
	public static final String[] ESTATS = {"viva", "naixement", "moribunda", "morta"};
	
	private String estat;

	public CellulaJocVida() {

	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public static String[] getEstats() {
		return ESTATS;
	}
	
	
	
		
}
