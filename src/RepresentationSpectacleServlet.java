import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Representation;
import modele.Spectacle;
import database.RepresentationDb;
import database.SpectacleDb;
import exceptions.RepresentationException;
import exceptions.SpectacleException;


public class RepresentationSpectacleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * HTTP GET request entry point.
	 * 
	 * @param req
	 *            an HttpServletRequest object that contains the request the
	 *            client has made of the servlet
	 * @param res
	 *            an HttpServletResponse object that contains the response the
	 *            servlet sends to the client
	 * 
	 * @throws ServletException
	 *             if the request for the GET could not be handled
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the GET request
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		ServletOutputStream out = res.getOutputStream();

		res.setContentType("text/html");

		out.println("<HEAD><TITLE> Chercher les représentations d'un spectacle </TITLE></HEAD>");
		out.println("<BODY bgproperties=\"fixed\" background=\"/images/rideau.JPG\">");
		out.println("<font color=\"#FFFFFF\"><h1> Chercher les représentations d'un spectacle </h1>");

		String numS = req.getParameter("numS");
		if (numS == null) {
			printForm(out);
		} else {
			try {
				int idSpectacle = Integer.parseInt(numS);
				Spectacle spectacle = SpectacleDb.getSpectacle(idSpectacle);
				if (spectacle == null) {
					out.println("Le spectacle demandée n'existe pas");
					printForm(out);
				} else {
					out.println("Réprésentations du spectacle "+ spectacle.getNom());
					List<Representation> representations = RepresentationDb.getRepresentations(idSpectacle);
					for (Representation representation : representations) {
						out.println(representation.getDate() + "<br />");
					}
				}
			} catch (NumberFormatException e) {
				out.println("Le formulaire contient des données invalides");
				printForm(out);
			} catch (SpectacleException | RepresentationException e) {
				out.println(e.getMessage());
				printForm(out);
			}
		}

		out.println("<hr><p><font color=\"#FFFFFF\"><a href=\"/admin/admin.html\">Page d'administration</a></p>");
		out.println("<hr><p><font color=\"#FFFFFF\"><a href=\"/index.html\">Page d'accueil</a></p>");
		out.println("</BODY>");
		out.close();

	}
	
	private void printForm (ServletOutputStream out) throws IOException {
		out.println("<font color=\"#FFFFFF\">Veuillez saisir les informations relatives &agrave; votre recherche :");
		out.println("<P>");
		out.print("<form action=\"");
		out.print("RepresentationSpectacleServlet\" ");
		out.println("method=POST>");
		out.println("Num&eacute;ro de spectacle :");
		out.println("<input type=text size=20 name=numS>");
		out.println("<br>");
		out.println("<input type=submit>");
		out.println("</form>");
	}

	/**
	 * HTTP POST request entry point.
	 * 
	 * @param req
	 *            an HttpServletRequest object that contains the request the
	 *            client has made of the servlet
	 * @param res
	 *            an HttpServletResponse object that contains the response the
	 *            servlet sends to the client
	 * 
	 * @throws ServletException
	 *             if the request for the POST could not be handled
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the POST request
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}

	/**
	 * Returns information about this servlet.
	 * 
	 * @return String information about this servlet
	 */

	public String getServletInfo() {
		return "Retourne les représentations d'un spectacle";
	}
}
