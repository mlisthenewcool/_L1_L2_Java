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

import dao.pojo.TypeProduitDao;
import database.DatabaseConnection;
import pojo.TypeProduit;

public class MySqlTypeProduitDao implements TypeProduitDao {
	
	@Override public TypeProduit getById(int id_type) throws SQLException {
		TypeProduit type_produit = null;

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement query = connection.prepareStatement("SELECT * FROM type_produit WHERE id_type = ?");
			query.setInt(1, id_type); 
			ResultSet res = query.executeQuery();
			
			if (res.next()) {
				String libelle = res.getString("libelle");
				int id_tva = res.getInt("id_tva");
				type_produit = new TypeProduit (id_type, libelle, id_tva);
			}
			
			if (query != null) {
				query.close();
			}

		if (type_produit == null) {
			throw new IllegalArgumentException("Aucun type produit ne possède cet identifiant");
		}
		return type_produit;
	}

	@Override public int create(TypeProduit objet) throws SQLException {
		int id = 0;

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement query = connection.prepareStatement("INSERT INTO type_produit (libelle, id_tva) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS); 
			query.setString(1, objet.getLibelle());
			query.setDouble(2, objet.getIdTva());
			query.executeUpdate();
			
			ResultSet res = query.getGeneratedKeys();
			if (res.next()) {
				id = res.getInt(1);
				res.close();
			}
			System.out.println("Type produit " +id + " ajouté");
			
			if (query != null)
				query.close();
		
		return id;
	}

	@Override public int update(TypeProduit objet) throws SQLException {
		int id = 0;

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * FROM type_produit WHERE id_type = ?");
			test.setInt(1, objet.getIdType());
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				PreparedStatement query = connection.prepareStatement("UPDATE type_produit SET libelle = ?, id_tva = ? WHERE id_type = ?");
				id = objet.getIdType();
				query.setString(1, objet.getLibelle());
				query.setInt(2, objet.getIdTva());
				query.setInt(3, objet.getIdType());
				query.executeUpdate();
				
				System.out.println("Type produit " + objet.getIdType() + " mis à jour");
		
				if (query != null)
					query.close();
				if (test != null)
					test.close();
			}
			else {
				throw new IllegalArgumentException("Aucun type produit ne possède cet identifiant");
			}
		
		return id;
	}

	@Override public void delete(int id) throws SQLException {

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement test = connection.prepareStatement("SELECT * FROM type_produit WHERE id_type = ?");
			test.setInt(1, id);
			ResultSet trouve = test.executeQuery();
			
			if (trouve.next()) {
				PreparedStatement query = connection.prepareStatement("DELETE FROM type_produit WHERE id_type = ?");
				query.setInt(1, id);
				query.executeUpdate();
				
				System.out.println("Type produit " + id + " supprimé");
				if (query != null)
					query.close();
			}
			else {
				throw new IllegalArgumentException("Aucun type produit ne possède cet identifiant");
			}
			
			if (test != null) {
				test.close();
			}

	}
	@Override public ArrayList<TypeProduit> getAll() throws SQLException {
		ArrayList<TypeProduit> types = new ArrayList<TypeProduit>();

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			PreparedStatement query = connection.prepareStatement("SELECT * FROM type_produit");
			ResultSet res = query.executeQuery();
			
			while (res.next()) {
				int id_type = res.getInt("id_type");
				String libelle = res.getString("libelle");
				int id_tva = res.getInt("id_tva");				
				types.add(new TypeProduit(id_type, libelle, id_tva));
			}
			if (query != null)
				query.close();

		if (types.size() == 0) {
			throw new IllegalArgumentException("Liste de type produit vide");
		}
		return types;
	}
}