/**
 * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * * * * * * * * * * *
 */

package dao.factory;

import dao.liste.*;
import dao.pojo.*;

public class ListeDaoFactory extends DaoFactory {
	
	@Override
	public ProduitDao getProduitDao() {
		return ListeProduitDao.getInstance();
	}
	
	@Override
	public TypeProduitDao getTypeProduitDao() {
		return ListeTypeProduitDao.getInstance();
	}
	
	@Override
	public TVADao getTVADao() {
		return ListeTVADao.getInstance();
	}

	@Override
	public FactureDao getFactureDao() {
		return ListeFactureDao.getInstance();
	}

	@Override
	public ClientDao getClientDao() {
		return ListeClientDao.getInstance();
	}
}