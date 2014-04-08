package modele;

// mets en place les spectacle 
public class Spectacle {

	private int id;
	private String nom;
	private int prix;

	public Spectacle(int id, String n, int p) {
		this.setId(id);
		setNom(n);
		this.prix = p;
	}

	public Spectacle(int id , String n){
		this.setId(id);
		setNom(n);
	}
	
	public Spectacle(String n, int id){
		this.setId(id);
		setNom(n);
	}
	
	public int getNum() {
		return this.prix;
	}

	public void setNum(int p) {
		this.prix = p;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}