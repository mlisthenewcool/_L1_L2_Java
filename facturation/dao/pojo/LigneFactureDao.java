/**
 * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Permet d'ajouter des lignes de facture
 * * * * * * * * * * * * * * * * * * * * *
 */

package dao.pojo;

import pojo.Facture;
import pojo.Produit;

public class LigneFactureDao {
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Facture facture
	 * @param Produit produit
	 * @param int quantite
	 * @return boolean : TRUE si ajouté, FALSE sinon
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public boolean ajouterLigne(Facture facture, Produit produit, int quantite) {
		boolean ajoute = false;
		
		if (quantite <= 0)
			throw new IllegalArgumentException("La quantité est nulle ou négative");
		
		// Si le produit existe déjà, on ajoute la quantité à la précédente
		if (facture.getLignesFacture().containsKey(produit)) {
			if (facture.getLignesFacture().put(produit, facture.getLignesFacture().get(produit).intValue() + quantite) != null)
				ajoute = true;
		}
		// Sinon on ajoute juste le produit et la quantité
		else {
			if (facture.getLignesFacture().put(produit, quantite) != null)
				ajoute = true;
		}
		
		facture.setTotalHT();
		facture.setTotalTTC();
		
		return ajoute;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Facture facture
	 * @param Produit produit
	 * @param int quantite
	 * @return boolean : TRUE si modifié, FALSE sinon
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public boolean modifierLigne(Facture facture, Produit produit, int quantite) {
		boolean modifie = false;
		
		// Si le produit existe, on définit la quantité par celle passée en paramètre
		if (facture.getLignesFacture().containsKey(produit)) {
			if (facture.getLignesFacture().put(produit, quantite) != null)
				modifie = true;
		}
		
		facture.setTotalHT();
		facture.setTotalTTC();
		
		return modifie;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Facture facture
	 * @param Produit produit
	 * @return boolean : TRUE si supprimé, FALSE sinon
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public boolean supprimerLigne(Facture facture, Produit produit) {
		boolean supprime = false;
		
		// Si le produit existe, on le supprime
		if (facture.getLignesFacture().containsKey(produit)) {
			if (facture.getLignesFacture().remove(produit) != null)
				supprime = true;
		}
		
		facture.setTotalHT();
		facture.setTotalTTC();
		
		return supprime;
	}
}