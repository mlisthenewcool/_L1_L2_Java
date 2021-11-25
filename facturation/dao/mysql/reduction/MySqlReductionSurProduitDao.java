package dao.mysql.reduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.pojo.reduction.ReductionSurProduitDao;
import database.DatabaseConnection;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import pojo.reduction.ReductionSurProduit;

public class MySqlReductionSurProduitDao implements ReductionSurProduitDao {

	@Override
	public int create(ReductionSurProduit reduction) throws DataAccessException, ObjectAlreadyExistsException {
		// Tentative de connexion à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			PreparedStatement query = connection.prepareStatement(
				"INSERT INTO reduction" +
				"(id_type_reduction, taux, id_produit, quantite) " + 
				"VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS
			);
			query.setInt(1, 1); // on pourrait changer pour mettre en String
			query.setInt(2, reduction.getTauxReduction());
			query.setInt(3, reduction.getProduit().getIdProduit());
			query.setInt(4, reduction.getQuantite());
			query.executeUpdate();
				
			// Si la réduction a été ajoutée, on actualise l'id
			ResultSet queryRes = query.getGeneratedKeys();
			if (queryRes.next()) {
				reduction.setIdReduction(queryRes.getInt(1));
				System.out.println("Réduction (sur produit) " + reduction.getIdReduction() + " ajoutée");
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
			sqle.printStackTrace();
			throw new ObjectAlreadyExistsException("Cette réduction existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return reduction.getIdReduction();
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param ReductionSurProduit : La réduction à mettre à jour
	 * @return int : L'identifiant de la réduction mise à jour, -1 sinon
	 * @throws DataAccessException, ObjectNotExistsException, ObjectAlreadyExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int update(ReductionSurProduit reduction) throws DataAccessException, ObjectNotExistsException, ObjectAlreadyExistsException {
		// Initialisation de l'id
		int id = -1;
		
		// Tentative de connexion à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			PreparedStatement query = connection.prepareStatement(
				"UPDATE reduction " +
				"SET taux = ?, id_produit = ?, quantite = ? " +
				"WHERE id_reduction = ?"
			);
			query.setDouble(1, reduction.getTauxReduction());
			query.setInt(2, reduction.getProduit().getIdProduit());
			query.setInt(3, reduction.getQuantite());
			query.setInt(4, reduction.getIdReduction());
			int row = query.executeUpdate();
			
			// Si la réduction a été mise à jour, on actualise l'id
			if (row == 1) {
				id = reduction.getIdReduction();
				System.out.println("Réduction (sur produit) " + reduction.getIdReduction() + " mise à jour");
			}
			
			// Fermeture de la requête de mise à jour
			if (query != null)
				query.close();
			
			// Fermeture de la connexion MySql
			if (connection != null)
				connection.close();
			
		} catch (SQLIntegrityConstraintViolationException sqle) {
			throw new ObjectAlreadyExistsException("Cette réduction existe déjà");
		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return id;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la réduction à supprimer
	 * @return boolean : TRUE si supprimé, FALSE sinon
	 * @throws DataAccessException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public boolean delete(int id) throws DataAccessException, ObjectConstraintException {
		// Initialisation du booléen
		boolean supprime = false;
		
		// Tentative de connexion à  la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
		// Requête de suppression de la réduction
		PreparedStatement query = connection.prepareStatement(
			"DELETE " +
			"FROM reduction " +
			"WHERE id_reduction = ?");
		query.setInt(1, id);
		int row = query.executeUpdate();
		
		// Si la réduction a été supprimée, on actualise le booléen
		if (row == 1) {
			supprime = true;
			System.out.println("Réduction " + id + " supprimée");
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
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la réduction à chercher
	 * @return Reduction : La réduction associée à l'identifiant, null sinon
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public ReductionSurProduit getById(int idReduction) throws DataAccessException {
		// Initialisation de la réduction
		ReductionSurProduit reduc = null;
		
		// Tentative de connexion à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des réductions
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM reduction " + 
				"WHERE id_reduction = ? AND id_type_reduction = 1");
			query.setInt(1, idReduction);
			ResultSet queryRes = query.executeQuery();
			
			// Si une reduction est trouvée, on actualise la valeur de reduc
			while (queryRes.next()) {
				int taux = queryRes.getInt("taux");
				int idProduit = queryRes.getInt("id_produit");
				int quantite = queryRes.getInt("quantite");
					
				reduc = new ReductionSurProduit(idReduction, taux, idProduit, quantite);
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
		
		return reduc;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<ReductionSurProduit> : La liste de toutes les réductions
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public ArrayList<ReductionSurProduit> getAll() throws DataAccessException {
		// Initialisation de la liste de réduction
		ArrayList<ReductionSurProduit> reductions = new ArrayList<ReductionSurProduit>();
		
		// Tentative de connexion à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des réductions
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM reduction r, produit p " +
				"WHERE id_type_reduction = 1 " +
				"AND r.id_produit = p.id_produit");
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'une reduction est trouvée, on l'ajoute à la liste
			while (queryRes.next()) {
				int idReduction = queryRes.getInt("id_reduction");
				int taux = queryRes.getInt("taux");
				int idProduit = queryRes.getInt("r.id_produit");
				int quantite = queryRes.getInt("quantite");
				String libelle = queryRes.getString("libelle");
								
				ReductionSurProduit reduc = new ReductionSurProduit(idReduction, taux, idProduit, quantite);
				reduc.getProduit().setLibelle(libelle);
				
				reductions.add(reduc);
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

		return reductions;
	}
}