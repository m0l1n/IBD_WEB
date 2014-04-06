package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modele.Place;
import exceptions.ExceptionConnexion;
import exceptions.PlaceException;

public class PlaceDb {
	private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	public static List<Place> getPlaces(int numS, String dateRep) throws PlaceException {
		try {
			return getPlaces(numS, df.parse(dateRep));
		} catch (ParseException e) {
			throw new PlaceException("La date n'est pas dans le format attendu");
		}
	}
	
	public static List<Place> getPlaces(int numS, Date dateRep) throws PlaceException {
		List<Place> res = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = BDConnexion.getConnexion();
			stmt = conn.prepareStatement("SELECT noPlace, noRang "
					+ "FROM LesPlaces P "
					+ "WHERE NOT EXISTS ("
					+ "SELECT * "
					+ "FROM LesTickets T "
					+ "WHERE numS = ? AND dateRep = TO_DATE(?, 'dd/MM/YYYY HH24:MI') "
					+ "AND T.noPlace = P.noPlace AND T.noRang = P.noRang)");
			stmt.setInt(1, numS);
			stmt.setString(2, df.format(dateRep));
			rs  = stmt.executeQuery();
			while (rs.next()) {
				res.add(new Place(rs.getInt(1), rs.getInt(2)));
			}
		} catch (ExceptionConnexion | SQLException e) {
			throw new PlaceException("Un problème est survenu lors de l'interrogation des places disponibles");
		} finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		
		
		return res;
	}
	
	
	public static boolean bookPlaces(int numZ, int numS, String dateRep, int quantity) throws PlaceException {
		try {
			return bookPlaces(numZ, numS, df.parse(dateRep), quantity);
		} catch (ParseException e) {
			throw new PlaceException("La date n'est pas dans le format attendu");
		}
	}
	
	public static boolean bookPlaces(int numZ, int numS, Date dateRep, int quantity) {
		boolean res = false;
		
		
		
		return res;
	}

}
