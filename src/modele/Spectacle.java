package modele;


// mets en place les spectacle 
public class Spectacle {

    private String categorie;
    private int prix;

   
    public Spectacle (String c, int p) {
        this.categorie = c;
        this.prix = p;
    }

    public String getNom () {
        return this.categorie;
    }
       
    public int getNum () {
        return this.prix;
    }
       
    public void setNom (String c) {
        this.categorie = c;
    }
       
    public void setNum (int p) {
        this.prix = p;
    }
}
