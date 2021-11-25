/*
 * @author MagicBanana
 */

package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.pojo.TVADao;
import database.DatabaseConnection;
import pojo.TVA;

public class MySqlTVADao implements TVADao {

	@Override public TVA getById(int id) throws SQLException {
		TVA tva = null;
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement query = connection.prepareStatement("SELECT * FROM tva WHERE id_tva = ?");
			query.setInt(1, id); 
			ResultSet res = query.executeQuery();
			
			if (res.next()) {
				String libelle = res.getString("libelle");
				Double taux = res.getDouble("taux");
				tva = new TVA (id, libelle, taux);
			}
			
			if (query != null) {
				query.close();
			}

		if (tva == null) {
			throw new IllegalArgumentException("Aucune TVA ne possède cet identifiant");
		}
		
		return tva;
	}
	
	@Override public int create(TVA objet) throws SQLException {
		int id = 0;
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * FROM tva WHERE libelle = ?");
			test.setString(1, objet.getLibelle());
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				throw new IllegalArgumentException("Tentative d'insertion d'un doublon");
			}
			else {
				PreparedStatement query = connection.prepareStatement("INSERT INTO tva (libelle, taux) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
				query.setString(1, objet.getLibelle()); 
				query.setDouble(2, objet.getTaux());
				query.executeUpdate();
				
				ResultSet res = query.getGeneratedKeys();
				if (res.next()) {
					id = res.getInt(1);
					res.close();
				}
				System.out.println("TVA" +id + " ajoutée");
				
				if (query != null)
					query.close();
			}
			
			if (test != null) {
				test.close();
			}
		
		return id;
	}

	@Override public int update(TVA objet) throws SQLException {
		int id = 0;
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * FROM tva WHERE id_tva = ?");
			test.setInt(1, objet.getIdTva());
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				PreparedStatement query = connection.prepareStatement("UPDATE tva SET libelle = ?, taux = ? WHERE id_tva = ?");
				id = objet.getIdTva();
				query.setString(1, objet.getLibelle());
				query.setDouble(2, objet.getTaux());
				query.setInt(3, objet.getIdTva());
				query.executeUpdate();
				
				System.out.println("TVA " + objet.getIdTva() + " mise à jour");
		
				if (query != null)
					query.close();
			}
			else {
				throw new IllegalArgumentException("Aucune TVA ne possède cet identifiant");
			}
			
			if (test != null) {
				test.close();
			}
		
		return id;
	}

	@Override public void delete(int id) throws SQLException {

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * tva produit WHERE id_tva = ?");
			test.setInt(1, id);
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				PreparedStatement query = connection.prepareStatement("DELETE FROM tva WHERE id_tva = ?");
				query.setInt(1, id);
				query.executeUpdate();
				
				System.out.println("TVA " + id + " supprimée");
				if (query != null)
					query.close();
			}
			else {
				throw new IllegalArgumentException("Aucune TVA ne possède cet identifiant");
			}
			
			if (test != null) {
				test.close();
			}
	}

	@Override public ArrayList<TVA> getAll() throws SQLException{
		ArrayList<TVA> tvas = new ArrayList<TVA>();

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement query = connection.prepareStatement("SELECT * FROM tva");
			ResultSet res = query.executeQuery();
			
			while (res.next()) {
				int id_tva = res.getInt("id_tva");
				String libellé = res.getString("libelle");
				Double taux = res.getDouble("taux");				
				tvas.add(new TVA(id_tva, libellé, taux));
			}
			
			if (query != null)
				query.close();
			
		if (tvas.size() == 0) {
			throw new IllegalArgumentException("Liste de TVA vide");
		}
		return tvas;
	}
}