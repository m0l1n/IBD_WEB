package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modele.Spectacle;
import exceptions.ExceptionConnexion;
import exceptions.SpectacleException;

public class SpectacleDb {
	
	public static List<Spectacle> getSpectacles() throws SpectacleException {
		List<Spectacle> res = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = BDConnexion.getConnexion();
			stmt = conn.prepareStatement("SELECT numS, nomS FROM LesSpectacles");
			rs = stmt.executeQuery();
			while (rs.next()) {
				res.add(new Spectacle(rs.getInt(1), rs.getString(2)));
			}
			
		} catch (ExceptionConnexion | SQLException e) {
			throw new SpectacleException(
					"Un problème est survenue lors de l'interrogation des spectacles");
		} finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		return res;
	}
	
	public static Spectacle getSpectacle(int id) throws SpectacleException {
		Spectacle res = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = BDConnexion.getConnexion();
			stmt = conn.prepareStatement("SELECT nomS FROM LesSpectacles WHERE numS = ?");
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				res = new Spectacle(id, rs.getString(1));
			}
			
		} catch (ExceptionConnexion | SQLException e) {
			throw new SpectacleException(
					"Un problème est survenue lors de l'interrogation des spectacles");
		} finally {
			BDConnexion.FermerTout(conn, stmt, rs);
		}
		
		return res;
	}

}
