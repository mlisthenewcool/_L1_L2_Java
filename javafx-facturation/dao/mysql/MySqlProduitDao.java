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

import dao.pojo.ProduitDao;
import database.DatabaseConnection;
import pojo.Produit;

public class MySqlProduitDao implements ProduitDao {
	

	@Override public Produit getById(int id_produit) throws SQLException {
		Produit p = null;
		
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement query = connection.prepareStatement(
					"SELECT libelle, id_type, prix " +
					"FROM produit " +
					"WHERE id_produit = ?");
			query.setInt(1, id_produit); 
			ResultSet res = query.executeQuery();
			
			if (res.next()) {
				String libelle = res.getString("libelle");
				int id_type = res.getInt("id_type");
				Double prix = res.getDouble("prix");
				p = new Produit (id_produit, libelle, id_type, prix);
			}
			
			if (query != null) {
				query.close();
			}

		if (p == null)
			throw new IllegalArgumentException("Aucun produit ne possède cet identifiant");
		
		return p;
	}
	
	@Override public int create(Produit objet) throws SQLException {
		int id = 0;
		
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement(
					"SELECT * "
					+ "FROM produit "
					+ "WHERE libelle = ?");
			test.setString(1, objet.getLibelle());
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				throw new IllegalArgumentException("Ce produit existe déjà");
			}
			else {
				PreparedStatement query = connection.prepareStatement(
						"INSERT INTO produit "
						+ "(libelle, id_type, prix) "
						+ "VALUES (?,?,?)",
						Statement.RETURN_GENERATED_KEYS);
				query.setString(1, objet.getLibelle()); 
				query.setInt(2, objet.getIdType());
				query.setDouble(3, objet.getPrix());
				query.executeUpdate();
				
				ResultSet res = query.getGeneratedKeys();
				if (res.next()) {
					id = res.getInt(1);
					res.close();
				}
				System.out.println("Produit " +id + " ajouté");
				
				if (query != null)
					query.close();
				if (test != null)
					test.close();
			}	
			
		return id;
	}

	@Override public int update(Produit objet) throws SQLException {
		int id = 0;
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * FROM produit WHERE id_produit = ?");
			test.setInt(1, objet.getIdProduit());
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				PreparedStatement query = connection.prepareStatement("UPDATE produit SET libelle = ?, id_type = ?, prix = ? WHERE id_produit = ?");
				id = objet.getIdProduit();
				query.setString(1, objet.getLibelle());
				query.setInt(2, objet.getIdType());
				query.setDouble(3, objet.getPrix());
				query.setInt(4, objet.getIdProduit());
				query.executeUpdate();
				
				System.out.println("Produit " + objet.getIdProduit() + " mis à jour");
		
				if (query != null)
					query.close();
			}
			else {
				throw new IllegalArgumentException("Aucun produit ne possède cet identifiant");
			}
			
			if (test != null) {
				test.close();
			}
			
		return id;
	}

	@Override public void delete(int id) throws SQLException {
		
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * FROM produit WHERE id_produit = ?");
			test.setInt(1, id);
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				PreparedStatement query = connection.prepareStatement("DELETE FROM produit WHERE id_produit = ?");
				query.setInt(1, id);
				query.executeUpdate();
				
				System.out.println("Produit " + id + " supprimé");
				if (query != null)
					query.close();
			}
			else {
				throw new IllegalArgumentException("Aucun produit ne possède cet identifiant");
			}
			
			if (test != null) {
				test.close();
			}
		}

	@Override public ArrayList<Produit> getAll() throws SQLException {
		ArrayList<Produit> produits = new ArrayList<Produit>();
		
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement query = connection.prepareStatement("SELECT * FROM produit");
			ResultSet res = query.executeQuery();
			
			while (res.next()) {
				int id_produit = res.getInt("id_produit");
				String libellé = res.getString("libelle");
				int id_type = res.getInt("id_type");
				Double prix = res.getDouble("prix");				
				produits.add(new Produit(id_produit, libellé, id_type, prix));
			}
			if (query != null)
				query.close();

		if (produits.size() == 0) {
			throw new IllegalArgumentException("Liste de produit vide");
		}

		return produits;
	}
}