package modele;
import java.util.ArrayList;
import modele.*;
import java.text.SimpleDateFormat;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

 /*
  * Cette classe permet de mettre en place un panier utilisateur. 
 */
public class PanierListe {
    public ArrayList<Item> Liste;
   
    public PanierListe(){
        Liste=new ArrayList<Item>();
    }
    /** Permet d'ajouter une place dans le panier
     * @param num  le  numero du spectacle
     * @param date la date de la  representation
     * @param place le numero de la place
     * @param rang le numero du rang
     */

    public void addPlace(String num,String date, String place , String rang){
        try {SimpleDateFormat s= new SimpleDateFormat("dd/MM/yyyy HH");
        Date d=s.parse(date);
        Calendar calendar;
        calendar= Calendar.getInstance();
        calendar.setTime(d);
        for(Item item:Liste)
            if (item.spectacle.getNum()==new Integer(num)&&item.representation.calendar.compareTo(calendar)==0)
                if (!item.InBasket(new Integer(place),new Integer(rang)))
                    item.lesPlaces.add(new Place(new Integer(rang),new Integer(place)));
       
        }
        catch (Exception e){}
    }
   
     public void addPlace(int num,Date date, int place , int rang){
        try {
          Calendar calendar;
          calendar= Calendar.getInstance();
          calendar.setTime(date);
       
          for(Item item:Liste)
              if (item.spectacle.getNum()==num&&item.representation.getDate().compareTo(date)==0)
                  if (!item.InBasket(place,rang))
                      item.lesPlaces.add(new Place(rang,place));
           
        }
        catch (Exception e){}
    }
   
   


    /** Permet de verifier qu'il y a au moins un place dans le panier
     * @return true si au moins un place est dans le panier false sinon
     */
    public boolean contientPlace(){
        for(Item item:Liste)
            if (item.lesPlaces.size()>0)
                return true;
        return false;
    }
   
   
    /** Permet de connaitre le nombre de representations dans le panier
     * @return  le nombre de representation dans le panier
     */
    public int getSize(){
        return Liste.size();
    }
   
   
    /** Permet de connaitre le contenu du panier
     * @return une chaine de caractere contenant l'ensemble des spectacles
     */
    public String toString(){
        String res="";
        for(Item item : Liste){
            res+=item.toString();
            res+="<br>";
        }
        return res;
    }
   
    // meme chose que pour dans item sauf qu'ici selon la representation et la date 
    public boolean InBasket(int numRep,String date){
        try{
            SimpleDateFormat s= new SimpleDateFormat("dd/MM/yyyy HH");
            Date d=s.parse(date);
            Calendar calendar;
            calendar= Calendar.getInstance();
            calendar.setTime(d);
           
        for (Item item : Liste)
            if (item.representation.getNum()==numRep && item.representation.calendar.compareTo(calendar)==0)
                        return true;
        }
        catch(Exception e){}
        return false;
                   
    }
   
   
}

