package modele;

//représente les places 
public class Place {
	private int noRang;
	private int noPlace;
	
	public Place (int noRang,int noPlace){
		this.noRang=noRang ;
		this.noPlace=noPlace;
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

}

