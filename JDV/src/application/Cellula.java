package application;


public class Cellula {
	public static final String[] ESTATS = {"viva", "naixement", "moribunda", "morta"};
	
	private String estat;

	public Cellula() {

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
