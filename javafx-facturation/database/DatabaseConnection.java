/**
 * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Connecte l'application a la base de donnees
 * * * * * * * * * * * * * * * * * * * * * * *
 */

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	
	private final static String DATABASE_URL_LOCAL = "jdbc:mysql://localhost/tdJavaFactureTest";
	private final static String DATABASE_USERNAME_LOCAL = "root";
	private final static String DATABASE_PASSWORD_LOCAL = "";
	
	//private final static String DATABASE_URL_IUT = "jdbc:mysql://infodb.iutmetz.univ-lorraine.fr:3306/debernar2u_javaFX";
	//private final static String DATABASE_USERNAME_IUT = "debernar2u_appli";
	//private final static String DATABASE_PASSWORD_IUT = "";
	
	private Connection connection = null;

	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * @return connection
	 * @throws SQLException
	 * 
	 * Tente de se connecter à la base de données
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Connection getConnection() throws SQLException {
		
		if (this.connection == null) {
			this.connection = DriverManager.getConnection(
					DATABASE_URL_LOCAL,
					DATABASE_USERNAME_LOCAL,
					DATABASE_PASSWORD_LOCAL);
			/*
			this.connection = DriverManager.getConnection(
					DATABASE_URL_IUT,
					DATABASE_USERNAME_IUT,
					DATABASE_PASSWORD_IUT);
			*/
			
			System.out.println("Connexion établie");
		}
		
		return this.connection;
	}
}