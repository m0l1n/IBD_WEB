package modele;

//représente les places 
public class Place {
	private int noRang;
	private int noPlace;
	private float tarif;
	
	public Place (int noPlace,int noRang){
		this.noRang=noRang ;
		this.noPlace=noPlace;
		setTarif(0);
	}
	
	public int getNoRang(){
		return noRang;
	}
	
	public int getNoPlace(){
		return noPlace;
	}
	
	public void setnoPlace(int noPlace){
		this.noPlace=noPlace;
	}
	
	public void setnoRang(int noRang){
		this.noRang=noRang;
	}
	
	public String toString(){
        String rslt;
        rslt="rang: "+ this.noRang+"  place: "+this.noPlace;
        return rslt;
    }

	public float getTarif() {
		return tarif;
	}

	public void setTarif(float tarif) {
		this.tarif = tarif;
	}

}


