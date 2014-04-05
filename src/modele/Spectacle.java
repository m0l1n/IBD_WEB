package modele;

public class Spectacle {
	private int id;
	private String nom;
	
	public Spectacle(int id, String n) {
		this.setId(id);
		setNom(n);
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
