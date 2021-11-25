package dao.liste.reduction;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import dao.pojo.reduction.ReductionSurProduitDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistsException;
import pojo.reduction.ReductionSurProduit;

public class ListeReductionSurProduitDao implements ReductionSurProduitDao {

	private ArrayList<ReductionSurProduit> reductions;
	private static final AtomicInteger compteur = new AtomicInteger(0);
	
	private static ListeReductionSurProduitDao instance;
	
	private ListeReductionSurProduitDao() {
		if (this.reductions == null)
			this.reductions = new ArrayList<ReductionSurProduit>();
	}
	
	public static ListeReductionSurProduitDao getInstance() {
		if (instance == null)
			instance = new ListeReductionSurProduitDao();
		
		return instance;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param ReductionSurProduit : La réduction à créer
	 * @return int : L'identifiant de la réduction créée
	 * 
	 * @throws ObjectAlreadyExistsException
	 * @see pojo.reduction.ReductionSurProduit
	 * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int create(ReductionSurProduit reduction) throws DataAccessException, ObjectAlreadyExistsException {
		reduction.setIdReduction(compteur.incrementAndGet());
		
		if (this.reductions.contains(reduction))
			throw new ObjectAlreadyExistsException("Cette réduction existe déjà");
		else {
			this.reductions.add(reduction);
			System.out.println("Réduction " + reduction.getIdReduction() + " ajoutée");
			return reduction.getIdReduction();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param ReductionSurProduit : La réduction à mettre à jour
	 * @return int : L'identifiant de la réduction mise à jour
	 * 
	 * @throws ObjectNotExistsException, ObjectAlreadyExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int update(ReductionSurProduit reduction)
			throws DataAccessException, ObjectNotExistsException, ObjectAlreadyExistsException {
		int idx = this.reductions.indexOf(reduction);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucune réduction ne correspond à l'identifiant " + reduction.getIdReduction());			
		else {
			//if (this.reductions.contains(reduction))
				//throw new ObjectAlreadyExistsException("Cette réduction existe déjà");
			
			this.reductions.set(idx, reduction);
			return reduction.getIdReduction();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la réduction à supprimer
	 * 
	 * @throws ObjectNotExistsException, ObjectConstraintException, DataAccessException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *  * * *
	 */
	// TODO ADD OBJECT CONSTRAINT EXCEPTION
	@Override
	public boolean delete(int id) throws ObjectNotExistsException, DataAccessException {
		int idx = this.reductions.indexOf(this.getById(id));
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucune réduction ne correspond à l'identifiant " + id);		
		else {
			this.reductions.remove(idx);
			return true;
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la réduction à chercher
	 * @return ReductionSurProduit : La réduction associée à l'identifiant
	 * 
	 * @throw ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public ReductionSurProduit getById(int id) throws DataAccessException, ObjectNotExistsException {
		ReductionSurProduit reduc = new ReductionSurProduit();
		reduc.setIdReduction(id);
		int idx = this.reductions.indexOf(reduc);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucune réduction ne correspond à l'identifiant " + id);			
		else {
			return this.reductions.get(idx);
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<ReductionSurProduit> : La liste de toutes les réductions
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public ArrayList<ReductionSurProduit> getAll() throws DataAccessException {
		return this.reductions;
	}
}
