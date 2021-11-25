/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue les requêtes MySql pour les tables facture et ligne_facture
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import dao.factory.DaoFactory;
import dao.pojo.FactureDao;
import dao.pojo.LigneFactureDao;
import dao.pojo.ProduitDao;
import database.DatabaseConnection;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import pojo.Facture;
import pojo.Produit;

public class MySqlFactureDao implements FactureDao {

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Facture : La facture à créer
	 * @return int : L'identifiant de la facture créée
	 * @throws DataAccessException, ObjectAlreadyExistException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int create(Facture facture) throws DataAccessException, ObjectAlreadyExistsException {
		// Tentative de connexion à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête d'insertion de la facture
			PreparedStatement query = connection.prepareStatement(
				"INSERT INTO facture " +
				"(id_client, date_facture, total) " +
				"VALUES (?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
			query.setInt(1, facture.getClient().getIdClient());
			query.setTimestamp(2, new Timestamp(facture.getDateFacture().getTime()));
			query.setDouble(3, facture.getTotalTTC());
			query.executeUpdate();
			ResultSet queryRes = query.getGeneratedKeys();
			
			// Si la facture a été ajoutée, on actualise l'id et on peut ajouter les lignes de facture
			if (queryRes.next()) {
				facture.setIdFacture(queryRes.getInt(1));
				System.out.println("Facture " + facture.getIdFacture() + " ajoutée");
				// Requête d'insertion des lignes de facture
				for (Map.Entry<Produit, Integer> lignes : facture.getLignesFacture().entrySet()) {
					PreparedStatement query2 = connection.prepareStatement(
						"INSERT INTO ligne_facture " +
						"(id_facture, id_produit, quantite) " +
						"VALUES (?, ?, ?)");
					query2.setInt(1, facture.getIdFacture());
					query2.setInt(2, lignes.getKey().getIdProduit());
					query2.setInt(3, lignes.getValue());
					int row = query2.executeUpdate();
					
					if (row != 0)
						System.out.println("Produit " + lignes.getKey().getIdProduit() + " ajouté " + lignes.getValue() + " fois");
					
					// Fermeture de la requête query2
					if (query2 != null)
						query2.close();
				}
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
			throw new ObjectAlreadyExistsException("Cette facture existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
			
		return facture.getIdFacture();
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Facture : La facture à mettre à jour
	 * @return int : L'identifiant de la facture mise à jour, -1 sinon
	 * @throws DataAccessException, ObjectAlreadyExistException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int update(Facture facture) throws DataAccessException, ObjectAlreadyExistsException {
		// Initialisation de l'id
		int id = -1;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Si la facture existe, on la met à jour
			PreparedStatement query = connection.prepareStatement(
				"UPDATE facture " +
				"SET id_client = ?, date_facture = ?, total = ? " +
				"WHERE id_facture = ?");
			query.setInt(1,  facture.getClient().getIdClient());
			query.setDate(2, (java.sql.Date) facture.getDateFacture());
			query.setDouble(3, facture.getTotalHT());
			query.setInt(4, facture.getIdFacture());
			int row = query.executeUpdate();
			
			// Si la facture a été mise à jour, on actualise l'id
			if (row == 1) {
				id = facture.getIdFacture();
				System.out.println("Facture " + facture.getIdFacture() + " mise à jour");
			}
			
			// Fermeture de la requête de mise à jour
			if (query != null)
				query.close();
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();
		
		} catch (SQLIntegrityConstraintViolationException sqle) {
			throw new ObjectAlreadyExistsException("Cette facture existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
			
		return id;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la facture à supprimer
	 * @return boolean : TRUE si supprimé, FALSE sinon
	 * @throws DataAccessException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public boolean delete(int id) throws DataAccessException, ObjectConstraintException {
		// Initialisation du booléen
		boolean supprime = false;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de suppression des lignes de la facture
			PreparedStatement query = connection.prepareStatement(
				"DELETE " +
				"FROM ligne_facture " +
				"WHERE id_facture = ?");
			query.setInt(1, id);
			int row = query.executeUpdate();
			
			// Si les lignes de la facture ont été supprimées, alors on peut supprimer la facture
			if (row != 0) {			
				// Requête de suppression de la facture
				PreparedStatement query2 = connection.prepareStatement(
					"DELETE " +
					"FROM facture " +
					"WHERE id_facture = ?");
				query2.setInt(1, id);
				int row2 = query2.executeUpdate();
				
				// Si la facture a été supprimée, on actualise le booléen
				if (row2 == 1) {
					supprime = true;
					System.out.println("Facture " + id + " supprimée");
				}
				
				// Fermeture de la requête de suppression des lignes
				if (query != null)
					query.close();
				// Fermeture de la requête de suppression de la facture
				if (query2 != null)
					query2.close();
			}
	
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
	 * @param int id : L'identifiant de la facture à chercher
	 * @return Facture : La facture associée à l'identifiant
	 * @throws DataAccessException, ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
	 */
	@Override public Facture getById(int idFacture) throws DataAccessException, ObjectNotExistsException {
		// Initialisation de la facture
		Facture facture = new Facture();
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche de la facture
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM facture " +
				"WHERE id_facture = ?");
			query.setInt(1, idFacture); 
			ResultSet queryRes = query.executeQuery();
			
			// Si une facture a été trouvée, on actualise la valeur de facture et on ajoute les lignes
			if (queryRes.next()) {
				int idClient = queryRes.getInt("id_client");
				Date dateFacture = queryRes.getDate("date_facture");
				//double total = queryRes.getDouble("total");
				
				facture.setIdFacture(idFacture);
				facture.getClient().setIdClient(idClient);
				facture.setDateFacture(dateFacture);
				
				// Requête de recherche des lignes de la facture
				PreparedStatement query2 = connection.prepareStatement(
					"SELECT * " +
					"FROM ligne_facture " +
					"WHERE id_facture = ?");
				query2.setInt(1, idFacture);
				ResultSet queryRes2 = query2.executeQuery();
				
				// Tant que des lignes sont trouvées, on les ajoute à la facture
				while (queryRes2.next()) {
					int idProduit = queryRes2.getInt("id_produit");
					int quantite = queryRes2.getInt("quantite");
					
					ProduitDao dao = DaoFactory.getCurrentDao().getProduitDao();
					Produit p = dao.getById(idProduit);
					
					LigneFactureDao ligneFacture = new LigneFactureDao();
					ligneFacture.ajouterLigne(facture, p, quantite);
				}
				facture.setTotalHT();
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
			
		return facture;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Facture> : La liste de toutes les factures
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<Facture> getAll() throws DataAccessException {
		// Initialisation de la liste de factures
		ArrayList<Facture> factures = new ArrayList<Facture>();
		
		try {
			// Tentative de connection a la base de donnees
			Connection connection = new DatabaseConnection().getConnection();
			
			// Requête de recherche des factures
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM facture f, client c " +
				"WHERE f.id_client = c.id_client");
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'une facture est trouvée, on l'ajoute à la liste
			while (queryRes.next()) {			
				Facture facture = new Facture();
				
				facture.setIdFacture(queryRes.getInt("id_facture"));
				facture.setDateFacture(queryRes.getDate("date_facture"));
				facture.setTotalTTC(queryRes.getDouble("total"));
				facture.getClient().setNom(queryRes.getString("nom"));
				facture.getClient().setPrenom(queryRes.getString("prenom"));
				
				factures.add(facture);
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
		
		return factures;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Facture> : Recherche par client
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<Facture> rechercherClient(String recherche) throws DataAccessException {
		// Initialisation de la liste de factures
		ArrayList<Facture> factures = new ArrayList<Facture>();
		
		// Tentative de connection a la base de donnees
		Connection connection = new DatabaseConnection().getConnection();
		
			try {
			// Requête de recherche des factures
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM facture f, client c " +
				"WHERE f.id_client = c.id_client " +
				"AND nom LIKE '" + recherche + "%' OR prenom LIKE '" + recherche + "%'");
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'une facture est trouvée, on l'ajoute à la liste
			while (queryRes.next()) {			
				Facture facture = new Facture();
				
				facture.setIdFacture(queryRes.getInt("id_facture"));
				facture.setDateFacture(queryRes.getDate("date_facture"));
				facture.setTotalTTC(queryRes.getDouble("total"));
				facture.getClient().setNom(queryRes.getString("nom"));
				facture.getClient().setPrenom(queryRes.getString("prenom"));
				
				factures.add(facture);
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
			
		return factures;
	}
}