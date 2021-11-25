/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package dao.pojo;

import java.util.ArrayList;

import dao.Dao;
import exceptions.DataAccessException;
import pojo.TVA;

public interface TVADao extends Dao<TVA>{
	public abstract ArrayList<TVA> rechercherLibelle(String recherche) throws DataAccessException;
}