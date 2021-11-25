/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package dao;

import java.util.ArrayList;

import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;

public interface Dao<T> {
	public abstract int create(T objet) throws DataAccessException, ObjectAlreadyExistsException;
	public abstract int update(T objet) throws DataAccessException, ObjectNotExistsException, ObjectAlreadyExistsException;
	public abstract boolean delete(int id) throws DataAccessException, ObjectNotExistsException, ObjectConstraintException;
	
	public abstract T getById(int id) throws DataAccessException, ObjectNotExistsException;
	public abstract ArrayList<T> getAll() throws DataAccessException;
}