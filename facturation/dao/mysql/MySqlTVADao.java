/**
 * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue les requêtes MySql pour la table tva
 * * * * * * * * * * * * * * * * * * * * * * * *
 */
package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.pojo.TVADao;
import database.DatabaseConnection;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import pojo.TVA;

public class MySqlTVADao implements TVADao {
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param TVA : La tva à créer
	 * @return int : L'identifiant de la tva créée
	 * @throws DataAccessException, ObjectAlreadyExistException 
	 * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int create(TVA tva) throws DataAccessException, ObjectAlreadyExistsException {
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
	
		try {
			// Requête d'insertion de la tva
			PreparedStatement query = connection.prepareStatement(
				"INSERT INTO tva " +
				"(libelle, taux) " +
				"VALUES (?,?)",
				Statement.RETURN_GENERATED_KEYS);
			query.setString(1, tva.getLibelle()); 
			query.setDouble(2, tva.getTaux());
			query.executeUpdate();
			
			// Si la tva a été ajoutée, on actualise l'id
			ResultSet queryRes = query.getGeneratedKeys();
			if (queryRes.next()) {
				tva.setIdTva(queryRes.getInt(1));
				System.out.println("TVA" + tva.getIdTva() + " ajoutée");
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
			throw new ObjectAlreadyExistsException("Cette TVA existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return tva.getIdTva();
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param TVA : La tva à mettre à jour
	 * @return int : L'identifiant de la tva mise à jour, -1 sinon
	 * @throws DataAccessException, ObjectAlreadyExistException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int update(TVA tva) throws DataAccessException, ObjectAlreadyExistsException {
		// Initialisation de l'id
		int id = -1;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		try {
			// Si la tva existe, on la met à jour
			PreparedStatement query = connection.prepareStatement(
				"UPDATE tva " +
				"SET libelle = ?, taux = ? " +
				"WHERE id_tva = ?");
			query.setString(1, tva.getLibelle());
			query.setDouble(2, tva.getTaux());
			query.setInt(3, tva.getIdTva());
			int row = query.executeUpdate();
			
			// Si la tva a été mise à jour, on actualise l'id
			if (row == 1) {
				id = tva.getIdTva();
				System.out.println("TVA " + tva.getIdTva() + " mise à jour");
			}
			
			// Fermeture de la requête de mise à jour
			if (query != null)
				query.close();
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();
			
		} catch (SQLIntegrityConstraintViolationException sqle) {
			throw new ObjectAlreadyExistsException("Cette TVA existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
			
		return id;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la tva à supprimer
	 * @return boolean : TRUE si supprimé, FALSE sinon
	 * @throws DataAccessException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public boolean delete(int id) throws DataAccessException, ObjectConstraintException {
		// Initialisation du booléen
		boolean supprime = false;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de suppression de la tva
			PreparedStatement query = connection.prepareStatement(
				"DELETE " +
				"FROM tva "
				+"WHERE id_tva = ?");
			query.setInt(1, id);
			int row = query.executeUpdate();
			
			// Si la tva a été supprimée, on actualise le booléen
			if (row == 1) {
				supprime = true;
				System.out.println("TVA " + id + " supprimée");
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
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la tva à chercher
	 * @return TVA : La tva associée à l'identifiant, null sinon
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public TVA getById(int id) throws DataAccessException {
		// Initialisation de la tva
		TVA tva = null;
		
		try {
			// Tentative de connection à la bdd
			Connection connection = new DatabaseConnection().getConnection();
			
			// Requête de recherche de la tva
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM tva " +
				"WHERE id_tva = ?");
			query.setInt(1, id); 
			ResultSet queryRes = query.executeQuery();
			
			// Si une tva a été trouvée, on actualise la valeur de tva
			if (queryRes.next()) {
				String libelle = queryRes.getString("libelle");
				Double taux = queryRes.getDouble("taux");
				tva = new TVA (id, libelle, taux);
			}
			
			// Ferme le curseur de recherche queryRes
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
		
		return tva;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<TVA> : La liste de toutes les tva
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<TVA> getAll() throws DataAccessException {
		// Initialisation de la liste de tva
		ArrayList<TVA> tvas = new ArrayList<TVA>();

		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des tva
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM tva");
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'une tva est trouvée, on l'ajoute à la liste
			while (queryRes.next()) {
				int id_tva = queryRes.getInt("id_tva");
				String libellé = queryRes.getString("libelle");
				Double taux = queryRes.getDouble("taux");				
				tvas.add(new TVA(id_tva, libellé, taux));
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
			
		return tvas;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<TVA> : Recherche par libellé
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<TVA> rechercherLibelle(String recherche) throws DataAccessException {
		// Initialisation de la liste de tva
		ArrayList<TVA> tvas = new ArrayList<TVA>();

		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des tva
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM tva " +
				"WHERE libelle LIKE '%" + recherche +"%'");
			//query.setString(1, recherche);
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'une tva est trouvée, on l'ajoute à la liste
			while (queryRes.next()) {
				int id_tva = queryRes.getInt("id_tva");
				String libellé = queryRes.getString("libelle");
				Double taux = queryRes.getDouble("taux");				
				tvas.add(new TVA(id_tva, libellé, taux));
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
		
		return tvas;
	}
}