package database;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import modele.Place;
import oracle.jdbc.driver.OracleTypes;
import exceptions.ExceptionConnexion;
import exceptions.PlaceException;

public class PlaceDb {
	private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
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
	
	
	public static List<Place> bookPlaces(int numZ, int numS, String dateRep, int quantity) throws PlaceException {
		try {
			return bookPlaces(numZ, numS, df.parse(dateRep), quantity);
		} catch (ParseException e) {
			throw new PlaceException("La date n'est pas dans le format attendu");
		}
	}
	
	public static List<Place> bookPlaces(int numZ, int numS, Date dateRep, int quantity) throws PlaceException {
		List<Place> res = new ArrayList<>();
		
		Connection conn = null;
		CallableStatement stmt = null;
		
		try {
			conn = BDConnexion.getConnexion();
			stmt = conn.prepareCall("call bookPlaces(?, ?, TO_DATE(?, 'dd/MM/YYYY HH24:MI'), ?, ?)");
			stmt.setInt(1, numZ);
			stmt.setInt(2, numS);
			System.out.println(numS + ":" +  df.format(dateRep));
			stmt.setString(3, df.format(dateRep));
			stmt.setInt(4, quantity);
			stmt.registerOutParameter(5, OracleTypes.ARRAY, "ARRAY_PLACE");
			stmt.execute();
			
			Object[] dataPlaces = (Object[]) stmt.getArray(5).getArray();
			System.out.println("T1 : " + dataPlaces.length);
			for (Object tmp : dataPlaces) {
				Struct row = (Struct) tmp;
				int rang = ((BigDecimal) row.getAttributes()[0]).intValue();
				int num = ((BigDecimal) row.getAttributes()[1]).intValue();
				res.add(new Place(num, rang));
			}
			conn.commit();
		} catch (ExceptionConnexion | SQLException e) {
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {}
			}
			throw new PlaceException("La demande de réservation n'a pas pu être réalisée");
		} finally {
			BDConnexion.FermerTout(conn, stmt, null);
		}
		
		return res;
	}
	
	public static float getTarif(int rang, int place) throws PlaceException {
		float res = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = BDConnexion.getConnexion();
			stmt = conn.prepareStatement("SELECT prix "
					+ "FROM LesCategories "
					+ "JOIN LesZones ON LesZones.nomC = LesCategories.nomC "
					+ "JOIN LesPlaces ON LesPlaces.numZ = LesZones.numZ "
					+ "WHERE noRang = ? AND noPlace = ?");
			stmt.setInt(1, rang);
			stmt.setInt(2, place);
			rs  = stmt.executeQuery();
			while (rs.next()) {
				res = rs.getFloat(1);
			}
		} catch (ExceptionConnexion | SQLException e) {
			throw new PlaceException("Le tarif de la place n'a pas pu être obtenu");
		} finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		
		return res;
	}

}
