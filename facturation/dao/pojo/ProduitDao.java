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
import pojo.Produit;

public interface ProduitDao extends Dao<Produit> {
	public abstract ArrayList<Produit> rechercherLibelleOuType(String recherche) throws DataAccessException;
}
