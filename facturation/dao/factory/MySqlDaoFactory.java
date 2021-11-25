/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package dao.factory;

import dao.mysql.*;
import dao.mysql.reduction.MySqlReductionDao;
import dao.mysql.reduction.MySqlReductionSurProduitDao;
import dao.mysql.reduction.MySqlReductionSurTotalDao;
import dao.mysql.reduction.MySqlReductionSurTypeProduitDao;
import dao.pojo.*;
import dao.pojo.reduction.ReductionDao;
import dao.pojo.reduction.ReductionSurProduitDao;
import dao.pojo.reduction.ReductionSurTotalDao;
import dao.pojo.reduction.ReductionSurTypeProduitDao;

public class MySqlDaoFactory extends DaoFactory {
	
	@Override
	public ProduitDao getProduitDao() {
		return new MySqlProduitDao();
	}
	
	@Override
	public TypeProduitDao getTypeProduitDao() {
		return new MySqlTypeProduitDao();
	}
	
	@Override
	public TVADao getTVADao() {
		return new MySqlTVADao();
	}
	
	@Override
	public FactureDao getFactureDao() {
		return new MySqlFactureDao();
	}

	@Override
	public ClientDao getClientDao() {
		return new MySqlClientDao();
	}

	@Override
	public ReductionDao getReductionDao() {
		return new MySqlReductionDao();
	}
	
	@Override
	public ReductionSurProduitDao getReductionSurProduitDao() {
		return new MySqlReductionSurProduitDao();
	}

	@Override
	public ReductionSurTypeProduitDao getReductionSurTypeProduitDao() {
		return new MySqlReductionSurTypeProduitDao();
	}

	@Override
	public ReductionSurTotalDao getReductionSurTotalDao() {
		return new MySqlReductionSurTotalDao();
	}
}