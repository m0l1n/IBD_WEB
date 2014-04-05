package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modele.Representation;
import modele.Spectacle;
import exceptions.ExceptionConnexion;
import exceptions.RepresentationException;

public class RepresentationDb {
	
	public static List<Representation> getRepresentations() throws RepresentationException {
		ArrayList<Representation> res = new ArrayList<>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = BDConnexion.getConnexion();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT LesSpectacles.numS, dateRep, nomS "
					+ "FROM LesSpectacles "
					+ "JOIN LesRepresentations ON LesSpectacles.numS = LesRepresentations.numS");
			while(rs.next()) {
				res.add(new Representation(new Spectacle(rs.getInt(1), rs.getString(3)), rs.getDate(2)));
			}
		} catch (ExceptionConnexion | SQLException e) {
			throw new RepresentationException("Un problème est survenue lors de l'interrogation des représenations");
		} finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		
		return res;
	}
}
