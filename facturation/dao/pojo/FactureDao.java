/**
 * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * * * * * * * * * * * *
 */

package dao.pojo;

import java.util.ArrayList;
import java.util.Map;

import dao.Dao;
import dao.factory.DaoFactory;
import dao.pojo.reduction.ReductionSurProduitDao;
import dao.pojo.reduction.ReductionSurTotalDao;
import dao.pojo.reduction.ReductionSurTypeProduitDao;
import exceptions.DataAccessException;
import pojo.Facture;
import pojo.Produit;
import pojo.reduction.Reduction;
import pojo.reduction.ReductionSurProduit;
import pojo.reduction.ReductionSurTotal;
import pojo.reduction.ReductionSurTypeProduit;

public interface FactureDao extends Dao<Facture>{
	public abstract ArrayList<Facture> rechercherClient(String recherche) throws DataAccessException;
	
	public default ArrayList<Reduction> rechercherReduction(Facture facture) throws DataAccessException {
		
		ArrayList<Reduction> reductions = new ArrayList<Reduction>();
		
		// On récupère les réductions sur le total de la facture
		ReductionSurTotalDao daoTotal = DaoFactory.getCurrentDao().getReductionSurTotalDao();
		ArrayList<ReductionSurTotal> reductionsTotal = daoTotal.getAll();
		
		for (int i = 0; i < reductionsTotal.size(); i++) {
			// Si la facture est égale ou supérieure au montant requis, on ajoute la réduction à la liste
			if (facture.getTotalHT() >= reductionsTotal.get(i).getTotalFacture()) {
				// On applique la réduction
				facture.setTotalHT(
					facture.getTotalHT() - // Ancien prix HT
					facture.getTotalHT() * reductionsTotal.get(i).getTauxReduction() / 100 // Valeur de la réduction
				);
				
				// On ajoute la réduction à la liste
				reductions.add(reductionsTotal.get(i));
			}
		}
		
		// On récupère les réductions sur les produits
		ReductionSurProduitDao daoProduit = DaoFactory.getCurrentDao().getReductionSurProduitDao();
		ArrayList<ReductionSurProduit> reductionsProduit = daoProduit.getAll();
		
		for (Map.Entry<Produit, Integer> lignes : facture.getLignesFacture().entrySet()) {
			for (int i = 0; i < reductionsProduit.size(); i++) {
				// Si le produit se trouve dans la facture et que sa quantité achetée est suffisante
				if (lignes.getKey().getIdProduit() == reductionsProduit.get(i).getProduit().getIdProduit()
					 && lignes.getValue() >= reductionsProduit.get(i).getQuantite()) {
					// On applique la réduction
					facture.setTotalHT(
						facture.getTotalHT() - // Ancien prix HT
						facture.getTotalHT() * reductionsProduit.get(i).getTauxReduction() / 100 // Valeur de la réduction
					);
					
					// On ajoute la réduction à la liste
					reductions.add(reductionsProduit.get(i));
				}
			}
		}
		
		// On récupère les réductions sur les types de produit
		ReductionSurTypeProduitDao daoTypeProduit = DaoFactory.getCurrentDao().getReductionSurTypeProduitDao();
		ArrayList<ReductionSurTypeProduit> reductionsTypeProduit = daoTypeProduit.getAll();
		
		for (Map.Entry<Produit, Integer> lignes : facture.getLignesFacture().entrySet()) {
			for (int i = 0; i < reductionsTypeProduit.size(); i++) {
				// Si le type produit se trouve dans la facture et que l'achat se situe entre la date start et end
				if (lignes.getKey().getTypeProduit().getIdType() == reductionsTypeProduit.get(i).getTypeProduit().getIdType()
					 && facture.getDateFacture().after(reductionsTypeProduit.get(i).getDateStart())
					 && facture.getDateFacture().before(reductionsTypeProduit.get(i).getDateEnd())) {
					// On applique la réduction
					facture.setTotalHT(
						facture.getTotalHT() - // Ancien prix HT
						facture.getTotalHT() * reductionsTypeProduit.get(i).getTauxReduction() / 100 // Valeur de la réduction
					);
					
					// On ajoute la réduction à la liste
					reductions.add(reductionsTypeProduit.get(i));
				}
			}
		}
		
		facture.setTotalTTC();
		
		return reductions;
	}
}
