package dao.liste.reduction;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import dao.pojo.reduction.ReductionSurTypeProduitDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import pojo.reduction.ReductionSurTypeProduit;

public class ListeReductionSurTypeProduitDao implements ReductionSurTypeProduitDao {

	private ArrayList<ReductionSurTypeProduit> reductions;
	private static final AtomicInteger compteur = new AtomicInteger(0);
	
	private static ListeReductionSurTypeProduitDao instance;
	
	private ListeReductionSurTypeProduitDao () {
		if (this.reductions == null)
			this.reductions = new ArrayList<ReductionSurTypeProduit>();
	}
	
	public static ListeReductionSurTypeProduitDao getInstance() {
		if (instance == null)
			instance = new ListeReductionSurTypeProduitDao();
		
		return instance;
	}
	
	@Override
	public int create(ReductionSurTypeProduit objet) throws DataAccessException, ObjectAlreadyExistsException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(ReductionSurTypeProduit objet)
			throws DataAccessException, ObjectNotExistsException, ObjectAlreadyExistsException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(int id) throws DataAccessException, ObjectNotExistsException, ObjectConstraintException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ReductionSurTypeProduit getById(int id) throws DataAccessException, ObjectNotExistsException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ReductionSurTypeProduit> getAll() throws DataAccessException {
		return this.reductions;
	}

}
