package dao.mysql.reduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.pojo.reduction.ReductionSurTotalDao;
import database.DatabaseConnection;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import pojo.reduction.ReductionSurTotal;

public class MySqlReductionSurTotalDao implements ReductionSurTotalDao {

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param ReductionSurTotal : La reduction à créer
	 * @return int : L'identifiant du reduction créer
	 * @throws SQLException
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int create(ReductionSurTotal reduction) throws DataAccessException, ObjectAlreadyExistsException {
		// Tentative de connexion à la bdd
		Connection connection = new DatabaseConnection().getConnection();

		try {
			PreparedStatement query = connection.prepareStatement(
					"INSERT INTO reduction" +
					"(id_type_reduction, taux, total_facture) " + 
					"VALUES (?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
				);
			query.setInt(1, 3);
			query.setInt(2, reduction.getTauxReduction());
			query.setDouble(3, ((ReductionSurTotal) reduction).getTotalFacture());
			query.executeUpdate();
			
			// Si la réduction a été ajoutée, on actualise l'id
			ResultSet queryRes = query.getGeneratedKeys();
			if (queryRes.next()) {
				reduction.setIdReduction(queryRes.getInt(1));
				System.out.println("Réduction (sur total facture) " + reduction.getIdReduction() + " ajoutée");
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
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param ReductionSurTotal : La réduction à mettre à jour
	 * @return int : L'identifiant de la réduction mise à jour, -1 sinon
	 * @throws DataAccessException, ObjectAlreadyExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int update(ReductionSurTotal reduction) throws DataAccessException, ObjectAlreadyExistsException {
		// Initialisation de l'id
		int id = -1;
		
		// Tentative de connexion à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			PreparedStatement query = connection.prepareStatement(
					"UPDATE reduction " +
					"SET taux = ?, total_facture = ?" +
					"WHERE id_reduction = ?");
			query.setDouble(1, reduction.getTauxReduction());
			query.setDouble(2, ((ReductionSurTotal) reduction).getTotalFacture());
			query.setInt(3, reduction.getIdReduction());
			
			int row = query.executeUpdate();
			
			// Si la réduction a été mise à jour, on actualise l'id
			if (row == 1) {
				id = reduction.getIdReduction();
				System.out.println("Réduction (sur total facture) " + reduction.getIdReduction() + " mise à jour");
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
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la réduction à supprimer
	 * @return boolean : TRUE si supprimé, FALSE sinon
	 * @throws DataAccessException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public boolean delete(int id) throws DataAccessException, ObjectNotExistsException, ObjectConstraintException {
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
	public ReductionSurTotal getById(int idReduction) throws DataAccessException, ObjectNotExistsException {
		// Initialisation de la réduction
		ReductionSurTotal reduc = null;
		
		// Tentative de connexion à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des réductions
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM reduction " + 
				"WHERE id_reduction = ? AND id_type_reduction = 3");
			query.setInt(1, idReduction);
			ResultSet queryRes = query.executeQuery();
			
			// Si une reduction est trouvée, on actualise la valeur de reduc
			while (queryRes.next()) {
				int taux = queryRes.getInt("taux");
				Double totalFacture = queryRes.getDouble("total_facture");
				
				reduc = new ReductionSurTotal(idReduction, taux, totalFacture);
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
	 * @return ArrayList<ReductionSurTotal> : La liste de toutes les réductions
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public ArrayList<ReductionSurTotal> getAll() throws DataAccessException {
		// Initialisation de la liste de réductions
			ArrayList<ReductionSurTotal> reductions = new ArrayList<ReductionSurTotal>();
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des réductions
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM reduction " +
				"WHERE id_type_reduction = 3");
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'une reduction est trouvée, on l'ajoute à la liste
			while (queryRes.next()) {
				int idReduction = queryRes.getInt("id_reduction");
				int taux = queryRes.getInt("taux");
				
				Double totalFacture = queryRes.getDouble("total_facture");
				
				reductions.add(new ReductionSurTotal(idReduction, taux, totalFacture));
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