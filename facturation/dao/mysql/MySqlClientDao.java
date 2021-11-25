/**
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue les requêtes MySql pour la table client
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;

import dao.pojo.ClientDao;
import database.DatabaseConnection;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import pojo.Client;

public class MySqlClientDao implements ClientDao {
	
	private ArrayList<Client> clients;
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Client : Le client à créer
	 * @return int : L'identifiant du client créé
	 * @throws DataAccessException, ObjectAlreadyExistException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int create(Client client) throws DataAccessException, ObjectAlreadyExistsException {
		
		// Tentative de connexion à la bdd
		Connection connection = new DatabaseConnection().getConnection();

		try {
			// Requête d'insertion du client
			PreparedStatement query = connection.prepareStatement(
				"INSERT INTO client " +
				"(nom, prenom) " +
				"VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS);
			query.setString(1, client.getNom()); 
			query.setString(2, client.getPrenom());
			query.executeUpdate();
				
			// Si le client a été ajouté, on actualise l'id
			ResultSet queryRes = query.getGeneratedKeys();
			if (queryRes.next()) {
				client.setIdClient(queryRes.getInt(1));
				System.out.println("Client " + client.getIdClient() + " ajouté");
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
			throw new ObjectAlreadyExistsException("Ce client existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
						
		return client.getIdClient();
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Client : Le client à mettre à jour
	 * @return int : L'identifiant du client mis à jour, -1 sinon
	 * @throws DataAccessException, ObjectAlreadyExistException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int update(Client client) throws DataAccessException, ObjectAlreadyExistsException {
		// Initialiastion de l'id
		int id = -1;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Si le client existe, on le met à jour
			PreparedStatement query = connection.prepareStatement(
					"UPDATE client " +
					"SET nom = ?, prenom = ? " +
					"WHERE id_client = ?");
			query.setString(1,  client.getNom());
			query.setString(2, client.getPrenom());
			query.setInt(3, client.getIdClient());
			int row = query.executeUpdate();
			
			// Si le client a été mis à jour, on actualise l'id
			if (row == 1) {
				id = client.getIdClient();
				System.out.println("Client " + client.getIdClient() + " mis à jour");
			}
			
			// Fermeture de la requête de mise à jour
			if (query != null)
				query.close();
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();
			
		} catch (SQLIntegrityConstraintViolationException sqle) {
			throw new ObjectAlreadyExistsException("Ce client existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return id;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du client à supprimer
	 * @return boolean : TRUE si supprimé, FALSE sinon
	 * @throws DataAccessException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public boolean delete(int id) throws DataAccessException, ObjectConstraintException {
		// Initialisation du booléen
		boolean supprime = false;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de suppression du client
			PreparedStatement query = connection.prepareStatement(
				"DELETE " +
				"FROM client " +
				"WHERE id_client = ?");
			query.setInt(1, id);
			int row = query.executeUpdate();
			
			// Si le client a été supprimé, on actualise le booléen
			if (row == 1) {
				supprime = true;
				System.out.println("Client " + id + " supprimé");
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
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du client à chercher
	 * @return Client : Le client associé à l'identifiant, null sinon
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public Client getById(int idClient) throws DataAccessException {
		// Initialisation du client
		Client c = null;

		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();

		try {
			// Requête de recherche du client
			PreparedStatement query = connection.prepareStatement(
				"SELECT nom, prenom " +
				"FROM client " +
				"WHERE id_client = ?");
			query.setInt(1, idClient);
			ResultSet queryRes = query.executeQuery();
						
			// Si un client a été trouvé, on actualise la valeur de c
			if (queryRes.next()) {
				String nom = queryRes.getString("nom");
				String prenom = queryRes.getString("prenom");
				c = new Client (idClient, nom, prenom);
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
		
		return c;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Client> : La liste de tous les clients
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public
	ArrayList<Client> getAll() throws DataAccessException {
		// Initialisation de la liste de clients
		ArrayList<Client> clients = new ArrayList<Client>();
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();

		try {
			// Requête de recherche des clients
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " + 
				"FROM client");
			ResultSet queryRes = query.executeQuery();
				
			// Tant qu'un client est trouvé, on l'ajoute à la liste
			while (queryRes.next()) {
				int id_client = queryRes.getInt("id_client");
				String nom = queryRes.getString("nom");
				String prenom = queryRes.getString("prenom");
				clients.add(new Client(id_client, nom, prenom));
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
		
		return clients;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Client> : Recherche par nom ou prénom
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public ArrayList<Client> rechercherNomOuPrenom(String recherche) throws DataAccessException {
		// Initialisation de la liste de clients
		if (this.clients == null)
			this.clients = new ArrayList<Client>();
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();

		try {
			// Requête de recherche des clients avec le nom ou prénom
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " + 
				"FROM client " +
				"WHERE nom LIKE '" + recherche + "%' OR prenom LIKE '" + recherche + "%'");
			//query.setString(1, "'" + recherche + "%'");
			//query.setString(2, "'%" + recherche + "%'");
			ResultSet queryRes = query.executeQuery();
				
			// Tant qu'un client est trouvé, on l'ajoute à la liste
			while (queryRes.next()) {
				int id_client = queryRes.getInt("id_client");
				String nom = queryRes.getString("nom");
				String prenom = queryRes.getString("prenom");
				clients.add(new Client(id_client, nom, prenom));
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
		
		return this.clients;
	}
}