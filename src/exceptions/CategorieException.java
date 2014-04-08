package exceptions;

import java.sql.SQLException;

public class CategorieException extends SQLException {
	private static final long serialVersionUID = 1L;

	public CategorieException() {
		// TODO Auto-generated constructor stub
	}

	public CategorieException(String m) {
		super(m);
	}
}
