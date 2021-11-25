package dao.liste.reduction;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import dao.pojo.reduction.ReductionSurTotalDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import pojo.Client;
import pojo.reduction.ReductionSurTotal;

public class ListeReductionSurTotalDao implements ReductionSurTotalDao {

	private ArrayList<ReductionSurTotal> reductions = new ArrayList<ReductionSurTotal>();
	private static final AtomicInteger compteur = new AtomicInteger(0);
	
	private static ListeReductionSurTotalDao instance;
	
	private ListeReductionSurTotalDao() {
		this.reductions = new ArrayList<ReductionSurTotal>();
	}
	
	public static ListeReductionSurTotalDao getInstance() {
		if (instance == null)
			instance = new ListeReductionSurTotalDao();
		
		return instance;
	}
	
	@Override
	public int create(ReductionSurTotal reduction) throws DataAccessException, ObjectAlreadyExistsException {
		reduction.setIdReduction(compteur.incrementAndGet());
		
		if (this.reductions.contains(reduction))
			throw new ObjectAlreadyExistsException("Cette réduction existe déjà");
		else {
			this.reductions.add(reduction);
			System.out.println("Réduction " + reduction.getIdReduction()+ " ajouté");
			return reduction.getIdReduction();
		}
	}

	@Override
	public int update(ReductionSurTotal reduction) throws DataAccessException, ObjectNotExistsException, ObjectAlreadyExistsException {
		int idx = this.reductions.indexOf(reduction);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucune réduction ne correspond à l'identifiant " + reduction.getIdReduction());			
		else {
			//if (this.clients.contains(client))
				//throw new ObjectAlreadyExistsException("Ce client existe déjà");
			
			this.reductions.set(idx, reduction);
			return reduction.getIdReduction();
		}
	}

	@Override
	public boolean delete(int id) throws DataAccessException, ObjectNotExistsException, ObjectConstraintException {
		int idx = this.reductions.indexOf(this.getById(id));
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucune réduction ne correspond à l'identifiant " + id);		
		else {
			this.reductions.remove(idx);
			return true;
		}
	}

	@Override
	public ReductionSurTotal getById(int id) throws DataAccessException, ObjectNotExistsException {
		ReductionSurTotal reduction = new ReductionSurTotal();
		reduction.setIdReduction(id);
		int idx = this.reductions.indexOf(reduction);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucun client ne correspond à l'identifiant " + id);			
		else {
			return this.reductions.get(idx);
		}
	}

	@Override
	public ArrayList<ReductionSurTotal> getAll() throws DataAccessException {
		return this.reductions;
	}
}