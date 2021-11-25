/**
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue les requêtes MySql pour la table produit
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.pojo.ProduitDao;
import database.DatabaseConnection;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import pojo.Produit;

public class MySqlProduitDao implements ProduitDao {
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Produit : Le produit à créer
	 * @return int : L'identifiant du produit créé
	 * @throws DataAccessException, ObjectAlreadyExistException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int create(Produit produit) throws DataAccessException, ObjectAlreadyExistsException {

		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
	
		try {
			// Requête d'insertion du produit
			PreparedStatement query = connection.prepareStatement(
				"INSERT INTO produit " +
				"(libelle, id_type, prix) " +
				"VALUES (?,?,?)",
				Statement.RETURN_GENERATED_KEYS);
			query.setString(1, produit.getLibelle()); 
			query.setInt(2, produit.getTypeProduit().getIdType());
			query.setDouble(3, produit.getPrix());
			query.executeUpdate();
			
			// Si le produit a été ajouté, on actualise l'id
			ResultSet queryRes = query.getGeneratedKeys();
			if (queryRes.next()) {
				produit.setIdProduit(queryRes.getInt(1));
				System.out.println("Produit " + produit.getIdProduit() + " ajouté");
			}
			
			// Fermeture du résultat queryRes
			if (queryRes != null)
				queryRes.close();	
			// Fermeture de la requête query
			if (query != null)
				query.close();
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();
			
		} catch (SQLIntegrityConstraintViolationException sqle) {
			throw new ObjectAlreadyExistsException("Ce produit existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return produit.getIdProduit();
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Produit : Le produit à mettre à jour
	 * @return int : L'identifiant du produit mis à jour, -1 sinon
	 * @throws DataAccessException, ObjectAlreadyExistException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int update(Produit produit) throws DataAccessException, ObjectAlreadyExistsException {
		// Initialisation de l'id
		int id = -1;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Si le produit existe, on le met à jour
			PreparedStatement query = connection.prepareStatement(
				"UPDATE produit " +
				"SET libelle = ?, id_type = ?, prix = ? " +
				"WHERE id_produit = ?");		
			query.setString(1, produit.getLibelle());
			query.setInt(2, produit.getTypeProduit().getIdType());
			query.setDouble(3, produit.getPrix());
			query.setInt(4, produit.getIdProduit());
			int row = query.executeUpdate();
			
			// Si le produit a été mis à jour, on actualise l'id
			if (row == 1) {
				id = produit.getIdProduit();
				System.out.println("Produit " + produit.getIdProduit() + " mis à jour");
			}
			
			// Fermeture de la requête de mise à jour
			if (query != null)
				query.close();
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();
				
		} catch (SQLIntegrityConstraintViolationException sqle) {
			throw new ObjectAlreadyExistsException("Ce produit existe déjà");
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return id;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du produit à supprimer
	 * @return boolean : TRUE si supprimé, FALSE sinon
	 * @throws DataAccessException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public boolean delete(int id) throws DataAccessException, ObjectConstraintException {
		// Initialisation du booléen
		boolean supprime = false;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de suppression du produit
			PreparedStatement query = connection.prepareStatement(
				"DELETE " +
				"FROM produit " +
				"WHERE id_produit = ?");
			query.setInt(1, id);
			int row = query.executeUpdate();
			
			// Si le produit a été supprimé, on actualise le booléen
			if (row == 1) {
				supprime = true;
				System.out.println("Produit " + id + " supprimé");
			}
	
			// Fermeture de la requête de suppression
			if (query != null)
				query.close();
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();
			
		} catch (SQLIntegrityConstraintViolationException sqle) {
			throw new ObjectConstraintException();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		return supprime;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du produit à chercher
	 * @return Produit : Le produit associé à l'identifiant, null sinon
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public Produit getById(int idProduit) throws DataAccessException {
		// Initialisation du produit
		Produit p = null;
		
		try {
			// Tentative de connection à la bdd
			Connection connection = new DatabaseConnection().getConnection();
			
			// Requête de recherche du produit
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM produit " +
				"WHERE id_produit = ?");
			query.setInt(1, idProduit); 
			ResultSet queryRes = query.executeQuery();
			
			// Si un produit a été trouvé, on actualise la valeur de p
			if (queryRes.next()) {
				String libelle = queryRes.getString("libelle");
				int idType = queryRes.getInt("id_type");
				Double prix = queryRes.getDouble("prix");
				p = new Produit (idProduit, libelle, idType, prix);
			}
			
			// Ferme le résultat queryRes
			if (queryRes != null)
				queryRes.close();
			// Ferme la requête query
			if (query != null)
				query.close();
			// Ferme la connexion MySql
			if (connection != null)
				connection.close();
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return p;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Produit> : La liste de tous les produits
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<Produit> getAll() throws DataAccessException {
		// Initialisation de la liste de produits
		ArrayList<Produit> produits = new ArrayList<Produit>();
		
		try {
			// Tentative de connection à la bdd
			Connection connection = new DatabaseConnection().getConnection();
			
			// Requête de recherche des produits
			PreparedStatement query = connection.prepareStatement(
				"SELECT id_produit, p.libelle, prix, t.libelle, taux, t.id_type " +
				"FROM produit p, type_produit t, tva " +
				"WHERE p.id_type = t.id_type " +
				"AND t.id_tva = tva.id_tva");
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'un produit est trouvé, on l'ajoute à la liste
			while (queryRes.next()) {			
				Produit p = new Produit();
				
				p.setIdProduit(queryRes.getInt("id_produit"));
				p.setLibelle(queryRes.getString("p.libelle"));
				p.getTypeProduit().setLibelle(queryRes.getString("t.libelle"));
				p.getTypeProduit().getTVA().setTaux(queryRes.getDouble("taux"));
				p.setPrix(queryRes.getDouble("prix"));
				p.getTypeProduit().setIdType(queryRes.getInt("t.id_type"));
				
				produits.add(p);
			}
			
			// Fermeture du résultat queryRes
			if (queryRes != null)
				queryRes.close();
			// Fermeture de la requête query
			if (query != null)
				query.close();
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException ("Erreur MySql : " + sqle.getMessage());
		}
		
		return produits;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Produit> : Recherche par libellé ou type
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<Produit> rechercherLibelleOuType(String recherche) throws DataAccessException {
		// Initialisation de la liste de produits
		ArrayList<Produit> produits = new ArrayList<Produit>();
		
		try {
			// Tentative de connection à la bdd
			Connection connection = new DatabaseConnection().getConnection();
			
			// Requête de recherche des produits
			PreparedStatement query = connection.prepareStatement(
				"SELECT id_produit, p.libelle, prix, t.libelle " +
				"FROM produit p, type_produit t " +
				"WHERE p.id_type = t.id_type " +
				"AND p.libelle LIKE '" + recherche + "%' OR t.libelle LIKE '" + recherche + "%'");
			//query.setString(1, recherche);
			//query.setString(2, recherche);
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'un produit est trouvé, on l'ajoute à la liste
			while (queryRes.next()) {			
				Produit p = new Produit();
				p.setIdProduit(queryRes.getInt("id_produit"));
				p.setLibelle(queryRes.getString("p.libelle"));
				p.getTypeProduit().setLibelle(queryRes.getString("t.libelle"));
				p.setPrix(queryRes.getDouble("prix"));
				
				produits.add(p);
			}
			
			// Fermeture du résultat queryRes
			if (queryRes != null)
				queryRes.close();
			// Fermeture de la requête query
			if (query != null)
				query.close();
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return produits;
	}
}