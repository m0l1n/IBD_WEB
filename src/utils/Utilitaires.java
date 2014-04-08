package utils;

import javax.servlet.*;
import javax.servlet.http.*;

import database.BDCategories;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Vector;
import java.util.Date;

import modele.*;

import java.awt.Frame;

import database.BDProgramme;
import database.BDConnexion;
import modele.Utilisateur;
import modele.Categorie;
import modele.Spectacle;
import modele.Place;
import exceptions.ExceptionUtilisateur;
import exceptions.ExceptionConnexion;
import exceptions.CategorieException;

import java.io.File;

public class Utilitaires {

    public Utilitaires() {
    }
     public static String AffichageAchat(Utilisateur user) throws IOException {
          Vector<ProgrammeListe> res = new Vector<ProgrammeListe>();
          String resultat ="";
          resultat=resultat+"<div align=\"center\";\">\n";
          resultat=resultat+"<p><strong>Liste des Representations Disponibles </strong></p>\n";
          resultat+="<div class=\"menu_list\" id=\"secondpane\">\n";
          //add to make post wihout button
          try {
              res = BDProgramme.getProgramListes(user);
              if (res.isEmpty()) {
                  resultat=resultat+"</div>";
              } else {
                  for (int i = 0; i < res.size(); i++) {
                      resultat+="<p class=\"menu_head\">"+ res.elementAt(i).spectacle.getNum() + " :  "+res.elementAt(i).spectacle.getNom()+"</p>\n";
                     
                      resultat+="<div class=\"menu_body\">\n";
                      for(Representation rp:  res.elementAt(i).representations){
                          resultat+="<form class=\"link\" action=Panier\n";
                          resultat+="method=POST>\n";
                          resultat+="<input type=\"hidden\" name=\"num\" value=\""+rp.getNum()+"\" />\n";
                          resultat+="<input type=\"hidden\" name=\"nom\" value=\""+res.elementAt(i).spectacle.getNom()+"\" />\n";
                          resultat+="<button name=\"date\" value =\""+rp+"\" type=\"submit\">"+"Date de la repr&eacute;sentation :"+rp+"</button>\n";          
                          resultat+="</form>\n";
                      }
                             
                      resultat+= "</div>\n";
                  }
              }

            } catch (CategorieException e) {
                 
                  resultat=resultat+" Erreur dans l'affichage du programme 1: "+ e.getMessage()+"<br>";
            } catch (ExceptionConnexion e) {
                resultat=resultat+" Erreur dans l'affichage du programme 2: "+ e.getMessage()+"<br>";}
            resultat+="</div>\n";
            resultat+="</div>\n";
            return resultat;
    }
        public static PanierListe  getPanier(Utilisateur user,String login) throws IOException {
        PanierListe p =null;
       
        try {
            p = BDProgramme.getPanier(user,login);
           
            }
        catch (CategorieException e) {
        } catch (ExceptionConnexion e) {
        }
        return p;
    }



   
    public static Vector<Spectacle> getProgramme(Utilisateur user) throws IOException {
        Vector<Spectacle> res = new Vector<Spectacle>();
        String resultat ="";
            try {
                resultat=resultat+"Listes des Spectacles de la Saison<br>";
                res = BDProgramme.getSpectacle(user);
               
               
            } catch (CategorieException e) {
                resultat=resultat+" Erreur dans l'affichage du programme 1: "+ e.getMessage()+"<br>";
            } catch (ExceptionConnexion e) {
                resultat=resultat+" Erreur dans l'affichage du programme 2: "+ e.getMessage()+"<br>";}
        return res;
    }

   
    public static String AjouterRepresentation(Utilisateur user,Representation R){
        String resultat ="Ajout des valeurs "+R.getNum()+"dateS <br>";
        try {
            BDProgramme.addRepresentation(user,R);
            resultat=resultat+"Valeurs ins&eacute;r&eacute; avec succ&egrave<br>";
        }
       
        catch (CategorieException e) {
            resultat=resultat+" Erreur dans l'insertion: le numero."+R.getNum()+"ne correspond pas &agrave; un spectacle valide<br>";
        }
        catch (ExceptionConnexion e) {
            resultat= resultat+" Erreur dans l'insertion: "+ e.getMessage()+"<br>";
        }
        return resultat;
    }
   
    /**
     *cette methode permet de lister les Representations associer a un spectacle
     *@return renvoit une chaine de caracteres au format html
     */

    public static String listerRepresentations(Utilisateur user,int numS){
        Vector<Representation> res = new Vector<Representation>();
        String resultat ="";
        try {
           
            res = BDProgramme.getRepresentation(user,numS);
            if (res.isEmpty()) {
                resultat=resultat+" Aucune repr&eacute;sentation disponible<br>";
            }
            else {
       
                for (int i = 0; i < res.size(); i++) {
                    Representation rep=res.elementAt(i);
                    resultat+="<a href=\"NoPlaceServlet?DateS="+rep+"&numS="+rep.getNum()+"\">"+rep+"</a><br>";
                    resultat+="<a href=\"ReserverPlaceServlet?daterep="+rep+"&numS="+rep.getNum()+"\"> acheter dans une zone </a><br>";
                }
            }
           
        } catch (CategorieException e) {
            resultat=resultat+" Erreur dans l'affichage du programme 1: "+ e.getMessage()+"<br>";
        } catch (ExceptionConnexion e) {
            resultat=resultat+" Erreur dans l'affichage du programme 2: "+ e.getMessage()+"<br>";}
            return resultat;    
    }
   
   

    public static String ListerPlacesDispo(Utilisateur user,int numS,String date){
        Vector<Place> res = new Vector<Place>();
        String resultat ="";
        try{
            resultat=resultat+"Liste des places disponible pour la représentation du "+date+" du spectacle "+numS+"<br>";
            res=BDProgramme.getPlacesDispo(user,numS,date);
            if(res.isEmpty()){
                resultat=resultat+" Aucune places disponibles pour votre requete <br>";
            }
            else{
                for (int i = 0; i < res.size(); i++) {
                    resultat=resultat+"<br>" + res.elementAt(i);
                }
            }
        }
        catch (CategorieException e) {
            resultat=resultat+" Erreur dans l'affichage du programme 1: "+ e.getMessage()+"<br>";
        }
        catch (ExceptionConnexion e) {
            resultat=resultat+" Erreur dans l'affichage du programme 2: "+ e.getMessage()+"<br>";}
        return resultat;
    }


    public static String AffichagePlaceAchat(Utilisateur user,String numS,String date){
        Vector<Place> res = new Vector<Place>();
        String resultat ="";
        try{
            resultat=resultat+"Liste des places disponible pour la représentation du "+date+" du spectacle "+numS+"<br>";
            res=BDProgramme.getPlacesDispo(user,new Integer(numS),date);
            if(res.isEmpty()){
                resultat=resultat+" Aucune places disponibles pour votre requete <br>";
            }
            else{
                int noRang=-1;
               
                   
                for (Place place : res) {
                    if (noRang!=place.getNoRang()){
                        if(noRang!=-1){
                             resultat+="</form>";
                        }

                        resultat+="<br>";
                        noRang=place.getNoRang();
                        resultat+="Places au rang"+noRang+":<br>";
                        resultat+="<form class=\"link\" action=Panier\n";
                        resultat+="method=POST>\n";
                    }
                    //insert button with good parameters
                    resultat+="<input type=\"hidden\" name=\"num\" value=\""+numS+"\" />\n";
                    resultat+="<input type=\"hidden\" name=\"rang\" value=\""+noRang+"\" />\n";
                    resultat+="<input type=\"hidden\" name=\"date\" value=\""+date+"\" />\n";
                    resultat+="<button  name=\"place\" value =\""+place.getNoPlace()+"\" type=\"submit\">"+place.getNoPlace()+"</button>\n";
                           
                }
                resultat+="</form>";
            }
        }
        catch (CategorieException e) {
            resultat=resultat+" Erreur dans l'affichage du programme 1: "+ e.getMessage()+"<br>";
        }
        catch (ExceptionConnexion e) {
            resultat=resultat+" Erreur dans l'affichage du programme 2: "+ e.getMessage()+"<br>";}
        return resultat;
    }

    /*   public static String getPlaceZone(Utilisateur user,Representation R,int numZ){
        String rslt="";
        Place P=BDProgramme.getPlaceZone(user,R,numZ);
        if(P==null){
            rslt="aucun resultats associ&eacute; &agrave; la representation du "+R+" ou &agrave la zone"+numZ;      
        }
        return rslt;
        }*/

    public static String ValiderPanier(Utilisateur user,PanierListe p){
        String res= "";
        res=BDProgramme.valide(user,p);
        return res;
       
    }


    public static Utilisateur Identification(HttpServlet servletParent) throws ExceptionConnexion,
                                                      ExceptionUtilisateur, IOException {

        Utilisateur user = null;
        String login;
        String passwd;
        // lecture des parametres de connexion dans connection.conf
        Properties p = new Properties();
        InputStream is = null;
        String relativeWebPath =utils.Constantes.Config;
        String absoluteDiskPath = servletParent.getServletContext().getRealPath(relativeWebPath);
        File file = new File(absoluteDiskPath);
        is = new FileInputStream(file);
        p.load(is);
        login = p.getProperty("user");
        passwd = p.getProperty("mdp");
        /* test de la connexion */
        Connection conn = BDConnexion.getConnexion();
        if (conn != null) {
            BDConnexion.FermerTout(conn, null, null);
            user = new Utilisateur(login, passwd);
        } else {
            throw new ExceptionConnexion("Connexion impossible\n");
        }
        return user;
    }
        public static String enregistrerPlacePanier(Utilisateur user,String login ,String num, String date,String place,String rang){
        String res = "";
           
        try {
            BDProgramme.enregistrerPlacePanier(user,login,num,date,place,rang);
           
        }
        catch (CategorieException e) {
            res+=e.getMessage();
        } catch (ExceptionConnexion e) {
            res+= e.getMessage();
        }
        return res;
 
           
    }
    public static String retirerPlacePanier(Utilisateur user,String login ,String num, String date,String place,String rang){
        String res = "";
       
        try {
            BDProgramme.retirerPlacePanier(user,login,num,date,place,rang);
           
        }
        catch (CategorieException e) {
            res+=e.getMessage();
        } catch (ExceptionConnexion e) {
            res+= e.getMessage();
        }
        return res;
       
           
    }

}

