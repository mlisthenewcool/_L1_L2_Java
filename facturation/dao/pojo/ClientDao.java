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
import pojo.Client;

public interface ClientDao extends Dao<Client> {
	public abstract ArrayList<Client> rechercherNomOuPrenom(String recherche) throws DataAccessException;
}
