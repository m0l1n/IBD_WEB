package modele;
import java.util.ArrayList;

 //permet de mettre la liste d'un programme (representation disponible d'un spectacle)
 

public class ProgrammeListe{
    public Spectacle spectacle;
    public ArrayList <Representation> representations;
    public ProgrammeListe(Spectacle s){
        spectacle=s;
        representations = new ArrayList();
    }
}

