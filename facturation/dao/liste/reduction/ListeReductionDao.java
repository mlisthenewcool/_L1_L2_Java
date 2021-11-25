package dao.liste.reduction;

import java.util.ArrayList;

import dao.pojo.reduction.ReductionDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import pojo.reduction.Reduction;

public class ListeReductionDao implements ReductionDao {
	
	private ArrayList<Reduction> reductions;
	
	public ListeReductionDao() {
		if (this.reductions == null)
			this.reductions = new ArrayList<Reduction>();
	}

	@Override
	public int create(Reduction objet) throws DataAccessException, ObjectAlreadyExistsException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Reduction objet) throws DataAccessException, ObjectAlreadyExistsException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(int id) throws DataAccessException, ObjectConstraintException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Reduction getById(int id) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Reduction> getAll() throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
