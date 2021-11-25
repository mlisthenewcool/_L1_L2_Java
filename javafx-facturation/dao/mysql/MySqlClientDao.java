/**
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue les requetes MySql pour la table client
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;

import dao.pojo.ClientDao;
import database.DatabaseConnection;
import pojo.Client;

public class MySqlClientDao implements ClientDao {
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * @param Client : le client a creer
	 * @return int : l'id du client
	 * @throws SQLException 
	 * 
	 * Cree un nouveau client dans la base de donnees
	 * 
	 * Si succes, renvoie l'id du client
	 * Sinon @throw IllegalArgumentException
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int create(Client client) throws SQLException {
		// Initialisation de l'id du client
		int id = 0;
		// Booleen pour savoir si le client existe
		boolean trouve = false;

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
		// Requete de recherche d'un client avec la meme id
		PreparedStatement test = connection.prepareStatement(
				"SELECT * " +
				"FROM client " +
				"WHERE id_client = ?");
		test.setInt(1, client.getIdClient());
		ResultSet testRes = test.executeQuery();
		if (testRes.next())
			trouve = true;
		
		// Requete de creation du client
		if (!trouve) {
			PreparedStatement query = connection.prepareStatement(
				"INSERT INTO client " +
				"(nom, prenom) " +
				"VALUES (?, ?)",
				Statement.RETURN_GENERATED_KEYS);
			query.setString(1, client.getNom()); 
			query.setString(2, client.getPrenom());
			query.executeUpdate();
				
			// Si le client a ete ajoute, on actualise id avec l'id du client cree
			ResultSet queryRes = query.getGeneratedKeys();
			if (queryRes.next()) {
				id = queryRes.getInt(1);
				System.out.println("Le client " + id + " a bien ete ajoute");
			}
			
			// Fermeture du curseur de recherche queryRes
			queryRes.close();	
			// Fermeture de la requete query
			query.close();
		}
		// Si le client existe deja
		else
			throw new IllegalArgumentException("Ce client existe deja");
		
		// Fermeture du curseur de recherche testRes
		if (testRes != null)
			testRes.close();
		// Fermeture de la requete test
		if (test != null)
			test.close();
		// Fermeture de la connexion SQL
		if (connection != null)
			connection.close();
						
		return id;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * 
	 * @param Client : le client a mettre a jour
	 * @return int : l'id du client
	 * @throws SQLException 
	 * 
	 * Met a jour un client dans la base de donnees
	 * 
	 * Si succes, renvoie l'id du client
	 * Sinon @throw IllegalArgumentException
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int update(Client client) throws SQLException {

		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
		// Requete de recherche du client
		PreparedStatement test = connection.prepareStatement("SELECT * FROM client WHERE id_client = ?");
		test.setInt(1, client.getIdClient());
		ResultSet testRes = test.executeQuery();
		
		// Requete de mise a jour
		if (testRes.next()) {
			PreparedStatement query = connection.prepareStatement(
					"UPDATE client " +
					"SET nom = ?, prenom = ? " +
					"WHERE id_client = ?");
			query.setString(1,  client.getNom());
			query.setString(2, client.getPrenom());
			query.setInt(3, client.getIdClient());
			query.executeUpdate();
				
			System.out.println("Client " + client.getIdClient() + " mis a jour");
			
			// Fermeture de la requete de mise a jour
			if (query != null)
				query.close();
		}
		// Si le client n'existe pas
		else 
			throw new IllegalArgumentException("Aucun client avec l'identifiant " + client.getIdClient());
		
		// Fermeture du curseur de recherche
		if (testRes != null)
			testRes.close();
		// Fermeture de la requete de recherche
		if (test != null)
			test.close();
		// Fermeture de la connexion MySql
		if (connection != null)
			connection.close();
		
		return client.getIdClient();
	}

	@Override
	public void delete(int id) throws SQLException {
		
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
		// Verifie que le client existe pour le supprimer
		PreparedStatement test = connection.prepareStatement("SELECT * FROM client WHERE id_client = ?");
		test.setInt(1, id);
		ResultSet testRes = test.executeQuery();
		
		// Si le client existe, on le supprime
		if (testRes.next()) {
			PreparedStatement query = connection.prepareStatement("DELETE FROM client WHERE id_client = ?");
			query.setInt(1, id);
			query.executeUpdate();
				
			System.out.println("Client " + id + " supprime");
			
			// Fermeture de la requete de suppression
			if (query != null)
				query.close();
		}
		// Si le client n'existe pas
		else {
			throw new IllegalArgumentException("Aucun client avec l'identifiant " + id);
		}
		
		// Fermeture du curseur de recherche
		if (testRes != null)
			testRes.close();
		// Fermeture de la requete de recherche
		if (test != null)
			test.close();
		// Fermeture de la connexion MySql
		if (connection != null)
			connection.close();
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id :
	 * @throws SQLException 
	 * @throws ClassNotFoundException
	 * 
	 * Renvoie le client avec l'id_client = id
	 * * * * * * * * * * * * * * * * * * * * * 
	 */
	@Override public Client getById(int id_client) throws SQLException {
		// Initialisation du client
		Client client = null;

		// Tentative de connection à la base de données
		Connection connection = new DatabaseConnection().getConnection();
			
		// Execution de la requete SQL
		PreparedStatement query = connection.prepareStatement(
			"SELECT nom, prenom "
			+ "FROM client "
			+ "WHERE id_client = ?");
		query.setInt(1, id_client);
		
		// Recuperation des resultats de la requete
		ResultSet res = query.executeQuery();
					
		// Si un client a été trouvé, on actualise la valeur de client
		if (res.next()) {
			String nom = res.getString("nom");
			String prenom = res.getString("prenom");
			client = new Client (id_client, nom, prenom);
		}
		
		// Ferme le curseur de recherche res
		if (res != null)
			res.close();
			
		// Ferme le curseur de recherche query
		if (query != null)
			query.close();
		
		// Ferme la connexion SQL
		if (connection != null)
			connection.close();
		
		if (client == null)
			throw new IllegalArgumentException("Aucun client ne possède cet identifiant");
		
		return client;
	}
	
	@Override public ArrayList<Client> getAll() throws SQLException {
		// Initialisation de la liste de clients
		ArrayList<Client> clients = new ArrayList<Client>();
		
		// Tentative de connection à la base de données
		Connection connection = new DatabaseConnection().getConnection();
		System.out.println("methode getAll() appellée");
		
		// Cherche tous les clients
		PreparedStatement query = connection.prepareStatement("SELECT * FROM client");
		ResultSet res = query.executeQuery();
			
		// Dès qu'un client est trouvé, on l'ajoute à la liste et on continue
		while (res.next()) {
			int id_client = res.getInt("id_client");
			String nom = res.getString("nom");
			String prenom = res.getString("prenom");
			clients.add(new Client(id_client, nom, prenom));
		}
		
		// Fermeture du curseur de recherche query
		if (query != null)
			query.close();
		// Fermeture du curseur de recherche res
		if (res != null)
			res.close();
		
		// Si la liste est nulle, on génère une IllegalArgumentException
		if (clients.size() == 0)
			throw new IllegalArgumentException("La liste est vide");
		
		return clients;
	}
}