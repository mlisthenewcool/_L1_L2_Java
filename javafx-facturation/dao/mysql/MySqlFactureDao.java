/*
 * @author MagicBanana
 */

package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import dao.pojo.FactureDao;
import database.DatabaseConnection;
import pojo.Facture;

public class MySqlFactureDao implements FactureDao {

	@Override public Facture getById(int id_facture) throws SQLException{
		Facture facture = null;
		
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement query = connection.prepareStatement("SELECT * FROM facture WHERE id_facture = ?");
			query.setInt(1, id_facture); 
			ResultSet res = query.executeQuery();
			
			if (res.next()) {
				Date date_facture = res.getDate("date_facture");
				int id_client = res.getInt("id_client");
				facture = new Facture (id_facture, date_facture, id_client);
			}
			
			if (query != null) {
				query.close();
			}

		if (facture == null) {
			throw new IllegalArgumentException("Aucune facture ne possède cet identifiant");
		}
		return facture;
	}

	@Override public int create(Facture objet) throws SQLException {
		int id = 0;

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * FROM facture WHERE date_facture = ?");
			test.setTimestamp(1, new Timestamp(objet.getDateFacture().getTime()));
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				throw new IllegalArgumentException("Tentative d'insertion d'un doublon");
			}
			else {
				PreparedStatement query = connection.prepareStatement("INSERT INTO facture (date_facture, id_client) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
				query.setTimestamp(1, new Timestamp(objet.getDateFacture().getTime()));
				query.setInt(2,  objet.getIdClient());
				query.executeUpdate();
				
				ResultSet res = query.getGeneratedKeys();
				if (res.next()) {
					id = res.getInt(1);
					res.close();
				}
				System.out.println("Facture " +id + " ajoutée");
				
				if (query != null)
					query.close();
			}
			if (test != null) {
				test.close();
			}
		
		return id;
	}

	@Override public int update(Facture objet) throws SQLException {
		int id = 0;
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * FROM facture WHERE id_facture = ?");
			test.setInt(1, objet.getIdFacture());
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				PreparedStatement query = connection.prepareStatement("UPDATE facture SET date_facture = ?, id_client = ? WHERE id_facture = ?");
				id = objet.getIdFacture();
				query.setDate(1, (java.sql.Date) objet.getDateFacture());
				query.setInt(2,  objet.getIdClient());
				query.setInt(2, objet.getIdFacture());
				query.executeUpdate();
				
				System.out.println("Facture " + objet.getIdFacture() + " mise à jour");
		
				if (query != null)
					query.close();
			}
			else {
				throw new IllegalArgumentException("Aucune facture ne posède cet identifiant");
			}
		
		return id;
	}

	@Override public void delete(int id) throws SQLException {

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * FROM facture WHERE id_facture = ?");
			test.setInt(1, id);
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				PreparedStatement query = connection.prepareStatement("DELETE FROM facture WHERE id_facture = ?");
				query.setInt(1, id);
				query.executeUpdate();
				
				System.out.println("Facture " + id + " supprimée");
				if (query != null)
					query.close();
			}
			else {
				throw new IllegalArgumentException("Aucune facture ne possède cet identifiant");
			}
			
			if (test != null) {
				test.close();
			}
	}
	@Override public ArrayList<Facture> getAll() throws SQLException {
		ArrayList<Facture> factures = new ArrayList<Facture>();
		
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement query = connection.prepareStatement("SELECT * FROM facture");
			ResultSet res = query.executeQuery();
			
			while (res.next()) {
				int id_facture = res.getInt("id_facture");
				Date date_facture = res.getDate("date_facture");
				int id_client = res.getInt("id_client");
				factures.add(new Facture(id_facture, date_facture, id_client));
			}
			if (query != null)
				query.close();

		if (factures.size() == 0) {
			throw new IllegalArgumentException("Liste de facture vide");
		}
		return factures;
	}
}