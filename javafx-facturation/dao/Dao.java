/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Dao<T> {
	public abstract int create(T objet) throws SQLException;
	public abstract int update(T objet) throws SQLException;
	public abstract void delete(int id) throws SQLException;
	public abstract T getById(int id) throws SQLException;
	public abstract ArrayList<T> getAll() throws SQLException;
}
