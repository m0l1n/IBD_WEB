/*
 * @(#)NouvelleRepresentationServlet.java	1.0 2007/10/31
 * 
 * Copyright (c) 2007 Sara Bouchenak.
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.RepresentationDb;
import exceptions.RepresentationException;

/**
 * NouvelleRepresentation Servlet.
 * 
 * This servlet dynamically adds a new date a show.
 * 
 * @author <a href="mailto:Sara.Bouchenak@imag.fr">Sara Bouchenak</a>
 * @version 1.0, 31/10/2007
 */

public class NouvelleRepresentationServlet extends HttpServlet {

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
		String date = req.getParameter("date");
		String heure = req.getParameter("heure");
		boolean isAdded = false;
		if (!(numS == null || date == null || heure == null)) {
			try {
				RepresentationDb.addRepresentation(Integer.parseInt(numS), date + " " + heure);
				isAdded = true;
			} catch (NumberFormatException e) {
				req.setAttribute("erreurMessage", "Le formulaire contient des données invalides");
			} catch (RepresentationException e) {
				req.setAttribute("erreurMessage", e.getMessage());
			}
		}
		req.setAttribute("isAdded", isAdded);
		getServletContext().getRequestDispatcher("/WEB-INF/add_representation.jsp").forward(req, res);
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
		return "Ajoute une représentation à une date donnée pour un spectacle existant";
	}

}
