/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Permet de changer facilement d'environnement de travail
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.factory;

import dao.pojo.*;

public abstract class DaoFactory {
	
	public static DaoFactory getDaoFactory(String persistance) {
		switch (persistance) {
		case "MySql" :
			return new MySqlDaoFactory();
		case "Liste" :
			return new ListeDaoFactory();
		default : 
			return null;
		}
	}
	
	public abstract ProduitDao getProduitDao();
	
	public abstract TypeProduitDao getTypeProduitDao();
	
	public abstract TVADao getTVADao();
	
	public abstract FactureDao getFactureDao();
	
	public abstract ClientDao getClientDao();
}
