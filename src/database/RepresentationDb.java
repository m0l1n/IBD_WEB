package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modele.Representation;
import modele.Spectacle;
import exceptions.ExceptionConnexion;
import exceptions.RepresentationException;

public class RepresentationDb {
	private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	/**
	 * Récupère la liste des représentations
	 * 
	 * @return Liste des représentations
	 * @throws RepresentationException Si les représentations n'ont pas être récupérées
	 */
	public static List<Representation> getRepresentations() throws RepresentationException {
		ArrayList<Representation> res = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = BDConnexion.getConnexion();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT LesSpectacles.numS, TO_CHAR(dateRep, 'dd/MM/YYYY HH24:MI'), nomS "
					+ "FROM LesSpectacles "
					+ "JOIN LesRepresentations ON LesSpectacles.numS = LesRepresentations.numS");
			
			while(rs.next()) {
				res.add(new Representation(new Spectacle(rs.getInt(1), rs.getString(3)), df.parse(rs.getString(2))));
			}
		} catch (ExceptionConnexion | SQLException | ParseException e) {
			throw new RepresentationException("Un problème est survenue lors de l'interrogation des représentations");
		} finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		
		return res;
	}
	
	/**
	 * Récupère les représentations d'un spectacle
	 * 
	 * @param numSpectacle Numéro du spectacle
	 * @return Liste des représentations d'un spectacle
	 * @throws RepresentationException Si les représentations n'ont pas être récupérées
	 */
	public static List<Representation> getRepresentations(int numSpectacle)
			throws RepresentationException {
		ArrayList<Representation> res = new ArrayList<>();

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = BDConnexion.getConnexion();
			stmt = conn
					.prepareStatement("SELECT TO_CHAR(dateRep, 'dd/MM/YYYY HH24:MI') "
							+ "FROM LesRepresentations " + "WHERE numS = ?");
			stmt.setInt(1, numSpectacle);
			rs = stmt.executeQuery();

			while (rs.next()) {
				res.add(new Representation(null, df.parse(rs.getString(1))));
			}
		} catch (ExceptionConnexion | SQLException | ParseException e) {
			throw new RepresentationException(
					"Un problème est survenue lors de l'interrogation des représentations");
		} finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}

		return res;
	}
	
	/**
	 * Ajoute une représentation
	 * 
	 * @param numS Numéro du spectacle de la représentation
	 * @param date Date/heure de la représentation
	 * @throws RepresentationException Si la représentation n'a pas pu être ajoutée
	 */
	public static void addRepresentation(int numS, String date) throws RepresentationException {
		try {
			addRepresentation(numS, df.parse(date));
		} catch (ParseException e) {
			throw new RepresentationException("La date n'est pas dans le format attendu");
		}
	}
	
	/**
	 * Ajoute une représentation
	 * 
	 * @param numS Numéro du spectacle de la représentation
	 * @param date Date/heure de la représentation
	 * @throws RepresentationException Si la représentation n'a pas pu être ajoutée
	 * @see addRepresentation(int, Date)
	 */
	public static void addRepresentation(int numS, Date date) throws RepresentationException {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = BDConnexion.getConnexion();
			stmt = conn.prepareStatement("INSERT INTO LesRepresentations VALUES (?, TO_DATE(?, 'dd/MM/YYYY HH24:Mi'))");
			stmt.setInt(1, numS);
			stmt.setString(2, df.format(date));
			stmt.executeUpdate();
			conn.commit();
		} catch (ExceptionConnexion | SQLException e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {}
			}
			throw new RepresentationException("Un problème est survenue lors de l'enregistrement de la représentation");
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {}
			try {
				conn.close();
			} catch (Exception e) {}
		}
	}
}
