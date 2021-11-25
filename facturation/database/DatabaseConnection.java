/**
 * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Connecte l'application à la base de données
 * * * * * * * * * * * * * * * * * * * * * * *
 */

package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import exceptions.DataAccessException;

public class DatabaseConnection {
	
	private final static String DATABASE_URL_LOCAL = "jdbc:mysql://localhost/facturation2";
	private final static String DATABASE_USERNAME_LOCAL = "root";
	private final static String DATABASE_PASSWORD_LOCAL = "";
	
	//private final static String DATABASE_URL_IUT = "jdbc:mysql://infodb.iutmetz.univ-lorraine.fr:3306/debernar2u_javaFX";
	//private final static String DATABASE_USERNAME_IUT = "debernar2u_appli";
	//private final static String DATABASE_PASSWORD_IUT = "";
	
	private Connection connection = null;

	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * @return connection
	 * @throws DataAccessException 
	 * 
	 * Tente de se connecter à la base de données
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Connection getConnection() throws DataAccessException {
		
		if (this.connection == null) {
			try {
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
			
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				throw new DataAccessException("La base de données MySql est inaccessible, veuillez contacter l'administrateur");
			}
		}
		
		return this.connection;
	}
}