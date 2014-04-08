package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.Date;


import exceptions.CategorieException;
import exceptions.ExceptionConnexion;

import modele.Representation;
import modele.Spectacle;
import modele.Utilisateur;
import modele.Place;
import modele.*;
import database.BDProgramme;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;



public class BDProgramme {
    public BDProgramme () {
    }
    /**
     * retourne la liste des spectacles dÃ©finies dans la bd
     * @param Utilisateur
     * @return Vector<Spectacles>
     * @throws CategorieException
     * @throws ExceptionConnexion
     */
    public static Vector<Spectacle> getSpectacle (Utilisateur user)
        throws CategorieException, ExceptionConnexion {
        Vector<Spectacle> res = new Vector<Spectacle>();
        String requete ;
        Statement stmt ;
        ResultSet rs ;
        Connection conn = BDConnexion.getConnexion();
               
        requete = "select numS, nomS from LesSpectacles";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
            while (rs.next()) {
                res.addElement(new Spectacle (rs.getString(2), rs.getInt(1)));
            }
        } catch (SQLException e) {
            throw new CategorieException (" ProblÃ¨me dans l'interrogation des spectacles.."
                                          + "Code Oracle " + e.getErrorCode()
                                          + "Message " + e.getMessage());
        }
        BDConnexion.FermerTout(conn, stmt, rs);
        return res;
    }
    /**
     * Permet l'ajout d'une representation
     * @param Utilisateur
     * @throws CategorieException
     * @throws ExceptionConnexion
     *
     **/
    public static void addRepresentation(Utilisateur user,Representation R)throws CategorieException, ExceptionConnexion {
        String requete ;
        Statement stmt ;
        ResultSet rs ;
        Connection conn = BDConnexion.getConnexion();
        //      insert into LESREPRESENTATIONS values ('101', TO_DATE('06/11/2009 20h','DD/MM/YYYY HH24"h"'));  
        requete = "insert into LESREPRESENTATIONS values ('"+R.getNum()+"', TO_DATE('"+R+"','DD/MM/YYYY HH24\"h\"'))";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
           
        } catch (SQLException e) {
            throw new CategorieException (" ProblÃ¨me dans l'interrogation des spectacles.."
                                          + "Code Oracle " + e.getErrorCode()
                                          + "Message " + e.getMessage());
        }
        BDConnexion.FermerTout(conn, stmt, rs);
       
    }
     /**
     * Permet de retirer une place du panier stocké
     * @param Utilisateur
     * @return un message indiquant que tout c'est bien déroulé ou une errreur
     * @throws CategorieException
     * @throws ExceptionConnexion
     *
     **/
        public static String retirerPlacePanier(Utilisateur user,String login ,String num, String date,String place,String rang)
            throws CategorieException, ExceptionConnexion {
            String res="";
            String requete ;
            Statement stmt ;
            ResultSet rs ;
            Connection conn = BDConnexion.getConnexion();
            requete = "DELETE FROM panier where numS='"+num+"'and TO_DATE('"+date+"','DD/MM/YYYY HH24\"h\"')=dateRep and '"+place+"'=NOPLACE   and '"+rang+"'=NORANG and '"+login+"'=identifian    ";
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(requete);
                res+=requete;
            } catch (SQLException e) {
            throw new CategorieException (" ProblÃ¨me dans l'interrogation des spectacles.."
                                          + "Code Oracle " + e.getErrorCode()
                                          + "Message " + e.getMessage());
            }
            BDConnexion.FermerTout(conn, stmt, rs);    
            return res;
        }
            public static String enregistrerPlacePanier(Utilisateur user,String login ,String num, String date,String place,String rang)
            throws CategorieException, ExceptionConnexion {
            String res="";
            String requete ;
            Statement stmt ;
            ResultSet rs ;
            Connection conn = BDConnexion.getConnexion();
            requete = "insert into panier values ('"+num+"', TO_DATE('"+date+"','DD/MM/YYYY HH24\"h\"'), '"+place+"','"+rang+"' ,'"+login+"')";
            try {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(requete);
                res+="la place a bien ete enregistree dans la base <br>";
            } catch (SQLException e) {
            throw new CategorieException (" ProblÃ¨me dans l'interrogation des spectacles.."
                                          + "Code Oracle " + e.getErrorCode()
                                          + "Message " + e.getMessage());
            }
            BDConnexion.FermerTout(conn, stmt, rs);    
            return res;
    }


  /**
     * Permet de recuperer la liste des representations
     * @throws CategorieException
     * @throws ExceptionConnexion
     *
     **/

    public static Vector<Representation>  getRepresentation(Utilisateur user,int numS)throws CategorieException, ExceptionConnexion {
        Vector<Representation> res= new Vector<Representation>();
        String requete ;
        Statement stmt ;
        ResultSet rs ;
        Connection conn = BDConnexion.getConnexion();

        requete = "Select dateRep from LESREPRESENTATIONS R where R.numS="+numS;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
         
            while (rs.next()) {
                res.addElement(new Representation(numS, rs.getDate(1),rs.getTime(1)));
       
            }

         
        } catch (SQLException e) {
            throw new CategorieException (" ProblÃ¨me dans l'interrogation des representation.."
                                          + "Code Oracle " + e.getErrorCode()
                                          + "Message " + e.getMessage());
        }
        BDConnexion.FermerTout(conn, stmt, rs);
        return res;
    }
 /**
     * Permet de recuperer la liste des places disponible pour une représentation
     * @param Utilisateur
     * @throws CategorieException
     * @throws ExceptionConnexion
     *
     **/

    public static Vector<Place> getPlacesDispo(Utilisateur user,int numS,String date)throws CategorieException, ExceptionConnexion {
        Vector<Place> res= new Vector<Place>();
        String requete ;
        Statement stmt ;
        ResultSet rs ;
        Connection conn = BDConnexion.getConnexion();
        //      insert into LESREPRESENTATIONS values ('101', TO_DATE('06/11/2009 20h','DD/MM/YYYY HH24"h"'));  
        requete = "Select P.noPlace, P.noRang from LESPLACES P, LESREPRESENTATIONS R where ";
        requete=requete+"R.DateRep=TO_DATE('"+date+"','DD/MM/YYYY HH24\"h\"') and ";
        requete=requete+"not exists(Select * from LESTICKETS T where T.DateRep=TO_DATE('"+date+"','DD/MM/YYYY HH24\"h\"')";
        requete=requete+ " and T.noPlace = P.noPlace and T.noRang=P.noRang)";
        System.out.println(requete);
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
            while (rs.next()) {
                res.addElement(new Place(rs.getInt(2),rs.getInt(1)));
            }
        } catch (SQLException e) {
            throw new CategorieException (" ProblÃ¨me dans l'interrogation des Places disponibles..<br>"+requete+
                                          "<br> Code Oracle " + e.getErrorCode()
                                          + "Message " + e.getMessage());
        }
        BDConnexion.FermerTout(conn, stmt, rs);
        return res;
    }

    public static String valide(Utilisateur user,PanierListe  p ){
        String res = "";
        String requete="" ;
        Statement stmt ;
        ResultSet rs ;
        try{
            Connection conn = BDConnexion.getConnexion();
            //verification de donnees
           
            requete+= "Select T.noPlace, T.noRang,T.numS,T.dateRep from LESTICKETS T where ";
            boolean first=true;
            for(int i= 0; i<p.Liste.size();i++)
                for(int j=0; j<p.Liste.get(i).lesPlaces.size();j++){
                    if (i==p.Liste.size()-1&&j==p.Liste.get(i).lesPlaces.size()-1)
                        requete+="T.noPlace="+p.Liste.get(i).lesPlaces.get(j).getNoPlace()+" and "+p.Liste.get(i).lesPlaces.get(j).getNoRang()+"=T.noRang  and T.DateRep=TO_DATE('"+p.Liste.get(i).representation+"','DD/MM/YYYY HH24\"h\"')"+" and T.numS="+p.Liste.get(i).representation.getNum();
                    else
                        requete+="T.noPlace="+p.Liste.get(i).lesPlaces.get(j).getNoPlace()+" and "+p.Liste.get(i).lesPlaces.get(j).getNoRang()+"=T.noRang  and T.DateRep=TO_DATE('"+p.Liste.get(i).representation+"','DD/MM/YYYY HH24\"h\"')"+" and T.numS="+p.Liste.get(i).representation.getNum()+" OR \n";
                }
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
            boolean err=false;
            while(rs.next()) {
                res+="la place num "+rs.getInt(1)+" de rang "+rs.getInt(2)+" de spectale "+rs.getInt(3)+" de date "+rs.getDate(4)+" est non disponible <br>";
                err=true;
            }
            if (err)
                return res;
            else {
               
                // on fait la reservation
                requete="";
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                //recuperer le dernier numero de serie
                requete ="Select noserie from LESTICKETS order by noSerie DESC" ;
                rs = stmt.executeQuery(requete);
                rs.next();
                int lastnoSerie=(rs.getInt(1))+1;
       
                for(int i= 0; i<p.Liste.size();i++)
                    for(int j=0; j<p.Liste.get(i).lesPlaces.size();j++){
                        requete="insert into LESTICKETS values (";
                        requete+="\'"+lastnoSerie+"\',\'"+p.Liste.get(i).representation.getNum()+"\', TO_DATE(\'"+p.Liste.get(i).representation;
                        requete+="\',\'DD/MM/YYYY HH24\"h\"\'),"+"\'"+p.Liste.get(i).lesPlaces.get(j).getNoPlace();
                        requete+="\',\'"+p.Liste.get(i).lesPlaces.get(j).getNoRang()+"\',TO_DATE(\'"+dateFormat.format(date)+ "\',\'DD/MM/YYYY HH24\"h\"\'),\'104\')";
                       
                        lastnoSerie++;
                       
                        rs = stmt.executeQuery(requete);
                        rs.close();
                       
                        res+="Reservation valide<br>";
                    }
                       
            }
           
            BDConnexion.FermerTout(conn, stmt, rs);
        }
       
        catch (SQLException e){
            res+="<br> Code Oracle " + e.getErrorCode();
            res+= "erreur"+e.getMessage();
            res+=e.getSQLState();
        }
        catch(Exception e ){
            return e.getMessage();
        }

       
        return res;
    }
/**
     * Permet de recuperer  le nom d'une representation depuis son numero
     * @param Utilisateur
     * @throws CategorieException
     * @throws ExceptionConnexion
     *
     **/


    public static String getName(Utilisateur user,int numS){
        String rslt="";
        Statement stmt=null ;
        ResultSet rs=null ;
        Connection conn=null;
        String requete="Select S.nomS from LESSPECTACLES S where S.numS='"+numS+"'";
        try{
            conn = BDConnexion.getConnexion();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
            if(rs.next())
                rslt=rs.getString(1);
        }
        catch(Exception e){
            rslt= null;
           
        }
        BDConnexion.FermerTout(conn, stmt, rs);


        return rslt;
    }

/**
     * Permet de recuperer  un place disponible dans une zone
     * @param Utilisateur
     * @throws CategorieException
     * @throws ExceptionConnexion
     *
     **/
    public static Place  getPlaceZone(Utilisateur user,Representation R,int zone){
        String requete ;
        Statement stmt=null ;
        ResultSet rs=null ;
        Connection conn=null;
        Place rslt=null;
       
       
       
       
        requete = "Select P.noPlace, P.noRang from LESPLACES P, LESREPRESENTATIONS R where ";
        requete=requete+"R.DateRep=TO_DATE('"+R+"','DD/MM/YYYY HH24\"h\"') and ";
        requete=requete+"P.numZ='"+zone +"'and not exists(Select * from LESTICKETS T where T.DateRep=TO_DATE('"+R+"','DD/MM/YYYY HH24\"h\"')";
        requete=requete+ " and T.noPlace = P.noPlace and T.noRang=P.noRang)";
        try{
            conn = BDConnexion.getConnexion();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
            if(rs.next())
                rslt=new Place(rs.getInt(2),rs.getInt(1));
        }
        catch(Exception e){
            rslt= null;
        }
        BDConnexion.FermerTout(conn, stmt, rs);
        return rslt;    

    }
   
   
/**
     * Permet de recuperer  la liste des programmes
     * @param Utilisateur
     * @throws CategorieException
     * @throws ExceptionConnexion
     *
     **/
    public static Vector<ProgrammeListe> getProgramListes(Utilisateur user)
        throws CategorieException, ExceptionConnexion {
        Vector<ProgrammeListe> res = new Vector<ProgrammeListe>();
        String requete ;
        Statement stmt ;
        ResultSet rs ;
        Connection conn = BDConnexion.getConnexion();
        requete = "select S.numS ,S.nomS ,dateRep FROM LesSpectacles S, LesRepresentations R where S.numS=R.numS ORDER BY nomS";
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
            Spectacle sCourant = null;
            ProgrammeListe pc = null;
            while (rs.next()) {
                int num = rs.getInt(1);
                sCourant = new Spectacle(rs.getString(2),num);
                Representation r = new Representation(num, rs.getDate(3),rs.getTime(3));
                if ( pc==null){
                    pc = new ProgrammeListe(sCourant);
                    pc.representations.add(r);
                    res.addElement(pc);
                }
                else
                    if (pc.spectacle.getNom().equals(sCourant.getNom()))
                        pc.representations.add(r);
                    else{
                        pc = new ProgrammeListe(sCourant);
                        pc.representations.add(r);
                        res.addElement(pc);
                    }
               
            }
        } catch (SQLException e) {
            throw new CategorieException (" ProblÃ¨me dans l'interrogation des spectacles.."
                                          + "Code Oracle " + e.getErrorCode()
                                          + "Message " + e.getMessage());
        }
        BDConnexion.FermerTout(conn, stmt, rs);
        return res;
    }


/**
     * Permet de recuperer  le panier d'un utilistaeur
     * @param le login de l'utilisateur
     * @param Utilisateur
     * @throws CategorieException
     * @throws ExceptionConnexion
     *
     **/
        public static PanierListe getPanier(Utilisateur user, String login )throws CategorieException, ExceptionConnexion {
        String requete ;
        Statement stmt ;
        ResultSet rs ;
        PanierListe p= new PanierListe();
        SimpleDateFormat s= new SimpleDateFormat("dd/MM/yyyy HH");
        String res="";
        Connection conn = BDConnexion.getConnexion();
        requete = "Select numS ,dateRep , noPlace,noRang,identifian  from panier  where \'"+login+"\'=identifian";
       
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(requete);
            res+=requete;
            Item i =null;
            while (rs.next()) {
                Representation r =  new Representation(rs.getInt(1), rs.getDate(2),rs.getTime(2));
                if(!p.InBasket(rs.getInt(1),r.toString())){
                    i = new Item(new Spectacle (" ",rs.getInt(1)), new Representation(rs.getInt(1), rs.getDate(2),rs.getTime(2)));
                    p.Liste.add(i);
                }
                if(i!=null)
                    p.addPlace(rs.getInt(1),i.representation.getDate(),rs.getInt(3),rs.getInt(4));
               
            }
            BDConnexion.FermerTout(conn, stmt, rs);    
        }
        catch (Exception e) {
            res+=e.getMessage();
        }

        return p;
    }


}
