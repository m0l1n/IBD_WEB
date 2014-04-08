import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modele.Place;
import modele.Spectacle;
import database.PlaceDb;
import database.SpectacleDb;
import exceptions.PlaceException;
import exceptions.SpectacleException;


public class RepresentationPlaceAvailableServlet extends HttpServlet {
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
		String numS = req.getParameter("numS");
		String dateRep = req.getParameter("dateRep");
		System.out.println(numS+":"+dateRep);
		if (numS == null || dateRep == null || dateRep.equals("")) {
			getServletContext().getRequestDispatcher("/WEB-INF/search_place.jsp").forward(req, res);
		} else {
			try {
				int idSpectacle = Integer.parseInt(numS);
				Spectacle spectacle = SpectacleDb.getSpectacle(idSpectacle);
				if (spectacle == null) {
					req.setAttribute("erreurMessage", "Le formulaire contient des données invalides");
					getServletContext().getRequestDispatcher("/WEB-INF/search_place.jsp").forward(req, res);
				} else {
					List<Place> places = PlaceDb.getPlaces(idSpectacle, dateRep);
					req.setAttribute("spectacle", spectacle);
					req.setAttribute("dateRep", dateRep);
					req.setAttribute("places", places);
					getServletContext().getRequestDispatcher("/WEB-INF/show_place.jsp").forward(req, res);
				}
			} catch (NumberFormatException e) {
				req.setAttribute("erreurMessage", "Le formulaire contient des données invalides");
				getServletContext().getRequestDispatcher("/WEB-INF/search_place.jsp").forward(req, res);
			} catch (SpectacleException | PlaceException e) {
				req.setAttribute("erreurMessage", e.getMessage());
				getServletContext().getRequestDispatcher("/WEB-INF/search_place.jsp").forward(req, res);
			}
		}
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
		return "Retourne les places disponibles pour une représentation";
	}

}
