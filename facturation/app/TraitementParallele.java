/**
 * * * * * * * * * * * * * * * * * *
 * Démonstration d'une application
 * avec un Thread parallèle
 * * * * * * * * * * * * * * * * * *
 */

package app;

import java.util.ArrayList;
import java.util.Date;

import dao.factory.DaoFactory;
import dao.factory.MySqlDaoFactory;
import dao.mysql.MySqlFactureDao;
import dao.pojo.FactureDao;
import dao.pojo.LigneFactureDao;
import dao.pojo.reduction.ReductionSurProduitDao;
import dao.pojo.reduction.ReductionSurTotalDao;
import dao.pojo.reduction.ReductionSurTypeProduitDao;
import exceptions.DataAccessException;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectConstraintException;
import exceptions.ObjectNotExistsException;
import pojo.Facture;
import pojo.Produit;
import pojo.reduction.Reduction;
import pojo.reduction.ReductionSurProduit;
import pojo.reduction.ReductionSurTotal;
import pojo.reduction.ReductionSurTypeProduit;

public class TraitementParallele implements Runnable{

	public TraitementParallele() {
		Thread t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		
		DaoFactory.setCurrentDao(new MySqlDaoFactory());
		FactureDao dao = DaoFactory.getCurrentDao().getFactureDao();
		try {
			ArrayList<Facture> factures = dao.getAll();
			for (int i = 0; i < factures.size(); i++) {
				factures.get(i).afficher();
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		/*
		for (int i=0; i < Integer.MAX_VALUE; i++) {
			System.out.println(i);
		}
		DaoFactory.setCurrentDao(new MySqlDaoFactory());
		
		LigneFactureDao daoLigne = new LigneFactureDao();
		Facture facture = new Facture();
		facture.setTotal(2000);
		Produit p = new Produit();
		p.setIdProduit(1);
		daoLigne.ajouterLigne(facture, p, 1);
		
		FactureDao dao = DaoFactory.getCurrentDao().getFactureDao();
		try {
			ArrayList<Reduction> red = dao.rechercherReduction(facture);
			for (int i = 0; i < red.size(); i++) {
				red.get(i).afficher();
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				*/
		
		/*
		DaoFactory.setCurrentDao(new MySqlDaoFactory());
		ReductionSurProduitDao dao = DaoFactory.getCurrentDao().getReductionSurProduitDao();
		ReductionSurTotalDao dao2 = DaoFactory.getCurrentDao().getReductionSurTotalDao();
		ReductionSurTypeProduitDao dao3 = DaoFactory.getCurrentDao().getReductionSurTypeProduitDao();
		
		try {
			// RED PRODUIT
			ArrayList<ReductionSurProduit> red = dao.getAll();
			for (int i = 0; i < red.size(); i++) {
				red.get(i).afficher();
			}
			
			// RED TOTAL
			int test2 = dao2.create(new ReductionSurTotal(10, 1000));
			dao2.delete(test2);
			ArrayList<ReductionSurTotal> red2 = dao2.getAll();
			for (int i = 0; i < red2.size(); i++) {
				red2.get(i).afficher();
			}
			
			// RED TYPE PRODUIT
			int test3 = dao3.create(new ReductionSurTypeProduit(50, 1, new Date(), new Date()));
			dao3.delete(test3);
			ArrayList<ReductionSurTypeProduit> red3 = dao3.getAll();
			for (int i = 0; i < red3.size(); i++) {
				red3.get(i).afficher();
			}
			
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ObjectAlreadyExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ObjectNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ObjectConstraintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}
}

/*
package app;

public class TraitementSequentiel {

	public TraitementSequentiel() {
		for (int i=0; i < Integer.MAX_VALUE; i++) {
			System.out.println(i);
		}
	}
}
*/