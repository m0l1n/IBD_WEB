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
	private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
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
			throw new RepresentationException("Un problème est survenue lors de l'interrogation des représenations");
		} finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		
		return res;
	}
	
	public static void addRepresentation(int numS, String date) throws RepresentationException {
		try {
			addRepresentation(numS, df.parse(date));
		} catch (ParseException e) {
			throw new RepresentationException("La date n'est pas dans le format attendu");
		}
	}
	
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
			throw new RepresentationException("Un problème est survenue lors de l'enregistrement de la représentation");
		}
	}
}
