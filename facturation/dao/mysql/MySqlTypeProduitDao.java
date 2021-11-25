/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue les requêtes MySql pour la table type_produit
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.pojo.TypeProduitDao;
import database.DatabaseConnection;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import pojo.TypeProduit;

public class MySqlTypeProduitDao implements TypeProduitDao {
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param TypeProduit : Le type produit à créer
	 * @return int : L'identifiant du type produit créé
	 * @throws SQLException
	 * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int create(TypeProduit typeProduit) throws DataAccessException, ObjectAlreadyExistsException {

		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();

		try {
			// Requête d'insertion du type produit
			PreparedStatement query = connection.prepareStatement(
				"INSERT INTO type_produit " +
				"(libelle, id_tva) "+
				"VALUES (?,?)",
				Statement.RETURN_GENERATED_KEYS); 
			query.setString(1, typeProduit.getLibelle());
			query.setDouble(2, typeProduit.getTVA().getIdTva());
			query.executeUpdate();
			
			// Si le produit a été ajouté, on actualise l'id
			ResultSet queryRes = query.getGeneratedKeys();
			if (queryRes.next()) {
				typeProduit.setIdType(queryRes.getInt(1));
				System.out.println("Type produit " + typeProduit.getIdType() + " ajouté");
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
			throw new ObjectAlreadyExistsException("Ce type de produit existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return typeProduit.getIdType();
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param TypeProduit : Le type produit à mettre à jour
	 * @return int : L'identifiant du type produit mis à jour, -1 sinon
	 * @throws DataAccessException, ObjectAlreadyExistException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int update(TypeProduit typeProduit) throws DataAccessException, ObjectAlreadyExistsException {
		// Initialisation de l'id
		int id = -1;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Si le type produit existe, on le met à jour
			PreparedStatement query = connection.prepareStatement(
				"UPDATE type_produit " +
				"SET libelle = ?, id_tva = ? " +
				"WHERE id_type = ?");
			query.setString(1, typeProduit.getLibelle());
			query.setInt(2, typeProduit.getTVA().getIdTva());
			query.setInt(3, typeProduit.getIdType());
			query.executeUpdate();
			
			// On actualise l'id
			id = typeProduit.getIdType();
			
			if (id != -1)
				System.out.println("Type produit " + typeProduit.getIdType() + " mis à jour");
		
			// Fermeture de la requête de mise à jour
			if (query != null)
				query.close();
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();
			
		} catch (SQLIntegrityConstraintViolationException sqle) {
			throw new ObjectAlreadyExistsException("Ce type de produit existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return id;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du type produit à supprimer
	 * @return boolean : TRUE si supprimé, FALSE sinon
	 * @throws DataAccessException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 */
	@Override public boolean delete(int id) throws DataAccessException, ObjectConstraintException {
		// Initialisation du booléen
		boolean supprime = false;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();

		try {
			// Requête de suppression du type produit
			PreparedStatement query = connection.prepareStatement(
				"DELETE " +
				"FROM type_produit " +
				"WHERE id_type = ?");
			query.setInt(1, id);
			int row = query.executeUpdate();
			
			// Si le type produit a été supprimé, on actualise le booléen
			if (row != 0) {
				supprime = true;
				System.out.println("Type produit " + id + " supprimé");
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
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du type produit à chercher
	 * @return TypeProduit : Le type produit associé à l'identifiant, null sinon
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public TypeProduit getById(int idType) throws DataAccessException {
		// Initialisation du type produit
		TypeProduit t = null;

		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche du type produit
			PreparedStatement query = connection.prepareStatement(
				"SELECT t.libelle, tva.id_tva, tva.libelle " +
				"FROM type_produit t, tva" +
				"WHERE id_type = ?");
			query.setInt(1, idType); 
			ResultSet queryRes = query.executeQuery();
			
			// Si un type produit a été trouvé, on actualise la valeur de t
			if (queryRes.next()) {
				String libelle = queryRes.getString("t.libelle");
				int id_tva = queryRes.getInt("tva.id_tva");
				String libelleTVA = queryRes.getString("tva.libelle");
				
				t = new TypeProduit (idType, libelle, id_tva);
				t.getTVA().setLibelle(libelleTVA);
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
			
		return t;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<TypeProduit> : La liste de tous les types produit
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<TypeProduit> getAll() throws DataAccessException {
		// Initialisation de la liste de types produit
		ArrayList<TypeProduit> types = new ArrayList<TypeProduit>();

		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des types produit
			PreparedStatement query = connection.prepareStatement(
				"SELECT id_type, t.libelle, tva.id_tva, tva.libelle " +
				"FROM type_produit t, tva " +
				"WHERE t.id_tva = tva.id_tva");
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'un type produit est trouvé, on l'ajoute à la liste
			while (queryRes.next()) {
				int id_type = queryRes.getInt("id_type");
				String libelle = queryRes.getString("t.libelle");
				int id_tva = queryRes.getInt("tva.id_tva");
				String libelleTVA = queryRes.getString("tva.libelle");
				
				TypeProduit typeProduit = new TypeProduit(id_type, libelle, id_tva);
				typeProduit.getTVA().setLibelle(libelleTVA);
				
				types.add(typeProduit);
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
		
		return types;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<TypeProduit> : Recherche par type ou TVA
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<TypeProduit> rechercherTypeOuTva(String recherche) throws DataAccessException {
		// Initialisation de la liste de types produit
		ArrayList<TypeProduit> types = new ArrayList<TypeProduit>();

		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des types produit
			PreparedStatement query = connection.prepareStatement(
				"SELECT id_type, t.libelle, tva.id_tva, tva.libelle " +
				"FROM type_produit t, tva " +
				"WHERE t.id_tva = tva.id_tva " +
				"AND t.libelle LIKE '" + recherche +"%'");// OR tva.libelle LIKE '" + recherche + "%'");
			//query.setString(1, recherche);
			//query.setString(2, recherche);
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'un type produit est trouvé, on l'ajoute à la liste
			while (queryRes.next()) {
				int id_type = queryRes.getInt("id_type");
				String libelle = queryRes.getString("t.libelle");
				int id_tva = queryRes.getInt("tva.id_tva");
				String libelleTVA = queryRes.getString("tva.libelle");
				
				TypeProduit typeProduit = new TypeProduit(id_type, libelle, id_tva);
				typeProduit.getTVA().setLibelle(libelleTVA);
				
				types.add(typeProduit);
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
		
		return types;
	}
}