package appEquip03;

import java.sql.Time;

public class RanquingPartidaPescamines {
	private Integer possicio;
	private String usuari;
	private String dificultat;
	private Time temps;
	
	public RanquingPartidaPescamines(Integer possicio, String usuari, String dificultat, Time temps) {
		super();
		this.possicio = possicio;
		this.usuari = usuari;
		this.dificultat = dificultat;
		this.temps = temps;
	}
	public Integer getPossicio() {
		return possicio;
	}
	public void setPossicio(int possicio) {
		this.possicio = possicio;
	}
	public String getUsuari() {
		return usuari;
	}
	public void setUsuari(String usuari) {
		this.usuari = usuari;
	}
	public String getDificultat() {
		return dificultat;
	}
	public void setDificultat(String dificultat) {
		this.dificultat = dificultat;
	}
	public Time getTemps() {
		return temps;
	}
	public void setTemps(Time temps) {
		this.temps = temps;
	}
	
	
}
