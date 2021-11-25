/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package dao.factory;

import dao.mysql.*;
import dao.pojo.*;

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
}