/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package dao.factory;

import dao.liste.*;
import dao.liste.reduction.ListeReductionDao;
import dao.liste.reduction.ListeReductionSurProduitDao;
import dao.liste.reduction.ListeReductionSurTotalDao;
import dao.liste.reduction.ListeReductionSurTypeProduitDao;
import dao.pojo.*;
import dao.pojo.reduction.ReductionDao;
import dao.pojo.reduction.ReductionSurProduitDao;
import dao.pojo.reduction.ReductionSurTotalDao;
import dao.pojo.reduction.ReductionSurTypeProduitDao;

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

	@Override
	public ReductionSurProduitDao getReductionSurProduitDao() {
		return ListeReductionSurProduitDao.getInstance();
	}

	@Override
	public ReductionSurTypeProduitDao getReductionSurTypeProduitDao() {
		return ListeReductionSurTypeProduitDao.getInstance();
	}

	@Override
	public ReductionSurTotalDao getReductionSurTotalDao() {
		return ListeReductionSurTotalDao.getInstance();
	}

	@Override
	public ReductionDao getReductionDao() {
		// TODO Auto-generated method stub
		return null;
	}
}