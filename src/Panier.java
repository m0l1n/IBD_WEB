import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import utils.Utilitaires;
import modele.Utilisateur;
import modele.*;
import modele.Place;
import java.io.*;
import java.util.Enumeration;
import java.util.Date;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class Panier extends HttpServlet {
	/**
	 * Permet de repondre a une requete web affiche le contenu du panier ansi
	 * que les differentes Places disponible pour la Representation passee via
	 * la methode POST HTML Creation du panier , des différent Item mis dedans
	 * et le rajoute dans les cookie Du client si necessaire.
	 * 
	 * @param HttpServletRequest
	 *            request requete
	 * @param HttpServletResponse
	 *            response réponse
	 * @throw IOException, ServletException
	 * @return void
	 */

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession(true);

		ServletOutputStream out = res.getOutputStream();
		res.setContentType("text/html");

		try {
			out.println("<HEAD><TITLE>Panier</TITLE></HEAD><BODY>");
			out.println("<h1>Contenu du panier:</h1>");
			out.println("<BODY bgproperties=\"fixed\" background=\"/images/rideau.JPG\">");

			String nom = req.getParameter("nom");
			String num = req.getParameter("num");
			String date = req.getParameter("date");
			String place = req.getParameter("place");
			String rang = req.getParameter("rang");
			SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy HH");
			// ajout d'une place au panier

			if (place != null && rang != null && num != null && date != null) {
				if (session.getAttribute("session.PanierListe") != null) {
					((PanierListe) session.getAttribute("session.PanierListe"))
							.addPlace(num, date, place, rang);
					if ((String) session.getAttribute("session.log") != null) {
						// utilisateur loggé
						out.println("votre panier est enregistre");
						Utilisateur user = Utilitaires.Identification(this);
						out.println(Utilitaires.enregistrerPlacePanier(user,
								(String) session.getAttribute("session.log"),
								num, date, place, rang));
					}

				}
			}
			if (nom != null && num != null && date != null) {
				if (session.getAttribute("session.PanierListe") != null) {
					if (!((PanierListe) session
							.getAttribute("session.PanierListe")).InBasket(
							new Integer(num), date))
						((PanierListe) session
								.getAttribute("session.PanierListe")).Liste
								.add(new Item(new Spectacle(nom, new Integer(
										num)), new Representation(new Integer(
										num), s.parse(date))));
				} else {
					// creation d'un nouveau panier
					PanierListe p = new PanierListe();
					p.Liste.add(new Item(new Spectacle(new String(nom),
							new Integer(num)), new Representation(new Integer(
							num), s.parse(date))));
					session.setAttribute("session.PanierListe", p);
				}
			}
			// caddie est obligatoirement alloué
			if (session.getAttribute("session.PanierListe") != null
					&& date != null && num != null) {
				out.println("contenu  actuel du caddie:<br>");
				out.println(((PanierListe) session
						.getAttribute("session.PanierListe")).toString());
				out.println("<h1>Veuillez selectionner une place pour la representation numero: "
						+ num + "a la date du : " + date + ":</h1><br>");
				// Affichage des places Dispo pour la representation:
				Utilisateur user = Utilitaires.Identification(this);
				out.println(Utilitaires.AffichagePlaceAchat(user, num, date));
				out.println("<form class=\"link\" action=Validate \"method=POST>\n");
				out.println("<button type=\"submit\">VALIDER LE PANIER</button>\n");
				out.println("</form>");
			} else
				out.println("Le caddie est vide<br>");

			out.println("<hr><p><font color=\"#FFFFFF\"><a href=\"/index.html\">Accueil</a></p>");
			out.close();

		} catch (Exception e) {// Catch exception if any
			out.println("Error: " + e.getMessage());
		}
	}

	public String getServletInfo() {
		return "Reservation servlet";
	}

	/*
	 * Permet de répondre à une reque POST , renvoie vers la requete GET
	 * associée a cette meme classe Elle permet notamment d'ajouter des Item
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
