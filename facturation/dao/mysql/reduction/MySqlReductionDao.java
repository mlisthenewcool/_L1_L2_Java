/**
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue les requÃªtes MySql pour la table reduction
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.mysql.reduction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import dao.pojo.reduction.ReductionDao;
import database.DatabaseConnection;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import pojo.reduction.Reduction;
import pojo.reduction.ReductionSurProduit;
import pojo.reduction.ReductionSurTotal;
import pojo.reduction.ReductionSurTypeProduit;

public class MySqlReductionDao implements ReductionDao {
	
	private ArrayList<Reduction> reductions;
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Reduction : La reduction à créer
	 * @return int : L'identifiant du reduction créer
	 * @throws SQLException
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int create(Reduction reduction) throws DataAccessException, ObjectAlreadyExistsException {
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();

		try {
			if (reduction instanceof ReductionSurProduit) {
				PreparedStatement query = connection.prepareStatement(
					"INSERT INTO reduction" +
					"(id_type_reduction, taux, id_produit, quantite) " + 
					"VALUES (?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
				);
				query.setInt(1, 1); // on pourrait changer pour mettre en String
				query.setInt(2, reduction.getTauxReduction());
				query.setInt(3, ((ReductionSurProduit) reduction).getProduit().getIdProduit());
				query.setInt(4, ((ReductionSurProduit) reduction).getQuantite());
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
			}
				
			else if (reduction instanceof ReductionSurTypeProduit) {
				PreparedStatement query = connection.prepareStatement(
					"INSERT INTO reduction" +
					"(id_type_reduction, taux, id_type_produit, date_start, date_end) " + 
					"VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS
				);
				query.setInt(1, 2);
				query.setInt(2, reduction.getTauxReduction());
				query.setInt(3, ((ReductionSurTypeProduit) reduction).getTypeProduit().getIdType());
				query.setTimestamp(4, new Timestamp(((ReductionSurTypeProduit) reduction).getDateStart().getTime()));
				query.setTimestamp(5, new Timestamp(((ReductionSurTypeProduit) reduction).getDateEnd().getTime()));
				query.executeUpdate();
				
				// Si la réduction a été ajoutée, on actualise l'id
				ResultSet queryRes = query.getGeneratedKeys();
				if (queryRes.next()) {
					reduction.setIdReduction(queryRes.getInt(1));
					System.out.println("Réduction (sur type produit) " + reduction.getIdReduction() + " ajoutée");
				}
				
				// Fermeture du résultat queryRes
				if (queryRes != null)
					queryRes.close();	
				// Fermeture de la requête query
				if (query != null)
					query.close();
			}
			
			else if (reduction instanceof ReductionSurTotal) {
				PreparedStatement query = connection.prepareStatement(
						"INSERT INTO reduction" +
						"(id_type_reduction, taux, total_facture) " + 
						"VALUES (?, ?, ?)",
						Statement.RETURN_GENERATED_KEYS
					);
					query.setInt(1, 2);
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
			}
			
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
	 * @param Reduction : La réduction à mettre à jour
	 * @return int : L'identifiant de la réduction mise à jour, -1 sinon
	 * @throws DataAccessException, ObjectAlreadyExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int update(Reduction reduction) throws DataAccessException, ObjectAlreadyExistsException {
		// Initialisation de l'id
		int id = -1;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			if (reduction instanceof ReductionSurProduit) {
				PreparedStatement query = connection.prepareStatement(
					"UPDATE reduction " +
					"SET taux = ?, id_produit = ?, quantite = ? " +
					"WHERE id_reduction = ?");
				query.setDouble(1, reduction.getTauxReduction());
				query.setInt(2, ((ReductionSurProduit) reduction).getProduit().getIdProduit());
				query.setInt(3, ((ReductionSurProduit) reduction).getQuantite());
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
			}
			
			else if (reduction instanceof ReductionSurTypeProduit) {
				PreparedStatement query = connection.prepareStatement(
						"UPDATE reduction " +
						"SET taux = ?, id_type_produit = ?, date_start = ? , date_end = ?" +
						"WHERE id_reduction = ?");
					query.setDouble(1, reduction.getTauxReduction());
					query.setInt(2, ((ReductionSurTypeProduit) reduction).getTypeProduit().getIdType());
					query.setTimestamp(3, new Timestamp (((ReductionSurTypeProduit) reduction).getDateStart().getTime()));
					query.setTimestamp(4, new Timestamp (((ReductionSurTypeProduit) reduction).getDateEnd().getTime()));
					query.setInt(5, reduction.getIdReduction());
					
					int row = query.executeUpdate();
					
					// Si la réduction a été mise à jour, on actualise l'id
					if (row == 1) {
						id = reduction.getIdReduction();
						System.out.println("Réduction (sur type produit) " + reduction.getIdReduction() + " mise à jour");
					}
					
					// Fermeture de la requête de mise à jour
					if (query != null)
						query.close();	
			}
			
			else if (reduction instanceof ReductionSurTotal) {
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
			}
			
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
	public boolean delete(int id) throws DataAccessException, ObjectConstraintException {
		// Initialisation du booléen
		boolean supprime = false;
		
		// Tentative de connection à  la bdd
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
	public Reduction getById(int idReduction) throws DataAccessException {
		// Initialisation de la réduction
		Reduction reduc = null;
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des réductions
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM reduction " + 
				"WHERE id_reduction = ?");
			query.setInt(1, idReduction);
			ResultSet queryRes = query.executeQuery();
			
			// Si une reduction est trouvée, on actualise la valeur de reduc
			while (queryRes.next()) {
				int idTypeReduction = queryRes.getInt("id_type_reduction");
				int taux = queryRes.getInt("taux");
				
				switch (idTypeReduction) {
				// Réduction sur produit
				case 1 :
					int idProduit = queryRes.getInt("id_produit");
					int quantite = queryRes.getInt("quantite");
					
					reduc = new ReductionSurProduit(idReduction, taux, idProduit, quantite);
					
					break;
				
				// Réduction sur type de produit
				case 2 :
					int idTypeProduit = queryRes.getInt("id_type_produit");
					Date dateStart = queryRes.getDate("date_start");
					Date dateEnd = queryRes.getDate("date_end");
					
					reduc = new ReductionSurTypeProduit(idReduction, taux, idTypeProduit, dateStart, dateEnd);
					
					break;
				
				// Réduction sur total facture
				case 3 :
					Double totalFacture = queryRes.getDouble("total_facture");
					
					reduc = new ReductionSurTotal(idReduction, taux, totalFacture);
					
					break;
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
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}
		
		return reduc;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Reduction> : La liste de toutes les réductions
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<Reduction> getAll() throws DataAccessException {
		// Initialisation de la liste de réductions
		if (this.reductions == null)
			this.reductions = new ArrayList<Reduction>();
		
		// Tentative de connection à la bdd
		Connection connection = new DatabaseConnection().getConnection();
		
		try {
			// Requête de recherche des réductions
			PreparedStatement query = connection.prepareStatement(
				"SELECT * " +
				"FROM reduction");
			ResultSet queryRes = query.executeQuery();
			
			// Tant qu'une reduction est trouvée, on l'ajoute à la liste
			while (queryRes.next()) {
				int idReduction = queryRes.getInt("id_reduction");
				int idTypeReduction = queryRes.getInt("id_type_reduction");
				int taux = queryRes.getInt("taux");
				
				switch (idTypeReduction) {
				// Réduction sur produit
				case 1 :
					int idProduit = queryRes.getInt("id_produit");
					int quantite = queryRes.getInt("quantite");
					
					this.reductions.add(new ReductionSurProduit(idReduction, taux, idProduit, quantite));
					
					break;
				
				// Réduction sur type de produit
				case 2 :
					int idTypeProduit = queryRes.getInt("id_type_produit");
					Date dateStart = queryRes.getDate("date_start");
					Date dateEnd = queryRes.getDate("date_end");
					
					this.reductions.add(new ReductionSurTypeProduit(idReduction, taux, idTypeProduit, dateStart, dateEnd));
				
					break;
					
				// Réduction sur total facture
				case 3 :
					Double totalFacture = queryRes.getDouble("total_facture");
					
					this.reductions.add(new ReductionSurTotal(idReduction, taux, totalFacture));
					
					break;
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
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			throw new DataAccessException("Erreur MySql : " + sqle.getMessage());
		}

		return this.reductions;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Reduction> : TODO
	 * @throws DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	/*
	@Override
	public ArrayList<Reduction> rechercherTaux(Double recherche) throws DataAccessException {
	
	}
	*/
}