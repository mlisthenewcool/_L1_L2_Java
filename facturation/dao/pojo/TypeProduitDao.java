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
import pojo.TypeProduit;

public interface TypeProduitDao extends Dao<TypeProduit>{
	public abstract ArrayList<TypeProduit> rechercherTypeOuTva(String recherche) throws DataAccessException;
}
