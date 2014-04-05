package modele;

import java.sql.Date;

public class Representation {
	private Spectacle spect;
	private Date date;
	
	public Representation(Spectacle s, Date d) {
		spect = s;
		date = d;
	}
	
	public Spectacle getSpect() {
		return spect;
	}
	
	public void setSpect(Spectacle spect) {
		this.spect = spect;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
