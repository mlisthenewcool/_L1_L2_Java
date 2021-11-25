/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Permet de choisir facilement l'environnement de travail
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.factory;

import dao.pojo.*;
import dao.pojo.reduction.ReductionDao;
import dao.pojo.reduction.ReductionSurProduitDao;
import dao.pojo.reduction.ReductionSurTotalDao;
import dao.pojo.reduction.ReductionSurTypeProduitDao;

public abstract class DaoFactory {
	
	private static DaoFactory choixDao;

	public static DaoFactory getCurrentDao() {
		return choixDao;
	}
	
	public static void setCurrentDao(DaoFactory choixDao) {
		DaoFactory.choixDao = choixDao;
	}
	
	public abstract ProduitDao getProduitDao();
	public abstract TypeProduitDao getTypeProduitDao();
	public abstract TVADao getTVADao();
	public abstract FactureDao getFactureDao();
	public abstract ClientDao getClientDao();
	public abstract ReductionDao getReductionDao();
	
	public abstract ReductionSurProduitDao getReductionSurProduitDao();
	public abstract ReductionSurTypeProduitDao getReductionSurTypeProduitDao();
	public abstract ReductionSurTotalDao getReductionSurTotalDao();
}
