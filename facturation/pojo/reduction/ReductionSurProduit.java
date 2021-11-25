/**
 * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Reduction de type :
 * SI produit acheté X fois
 * ALORS réduction
 * * * * * * * * * * * * * * *
 */

package pojo.reduction;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pojo.Produit;

public class ReductionSurProduit extends Reduction {
	
	private Produit produit;
	private IntegerProperty quantite;
	
	public ReductionSurProduit() {}
	
	public ReductionSurProduit(int tauxReduction, int idProduit, int quantite) {
		super(tauxReduction);
		this.getProduit().setIdProduit(idProduit);
		this.setQuantite(quantite);
	}
	
	public ReductionSurProduit(int idReduction, int tauxReduction, int idProduit, int quantite) {
		this(tauxReduction, idProduit, quantite);
		this.setIdReduction(idReduction);
	}

	/**
	 * * * * * * * QUANTITE * * * * * * * 
	 * @int getQuantite()
	 * @void setQuantite(int quantite)
	 * @IntegerProperty quantiteProperty()
	 * * * * * * * * * * * * * * * * * * *
	 */
	public int getQuantite() {
		return this.quantiteProperty().get();
	}
	
	public void setQuantite(int quantite) {
		if (quantite <= 0)
			throw new IllegalArgumentException("Quantité incorrecte");
		
		this.quantiteProperty().set(quantite);
	}
	
	public IntegerProperty quantiteProperty() {
		if (this.quantite == null)
			this.quantite = new SimpleIntegerProperty(this, "quantite");
		
		return this.quantite;
	}
	
	/**
	 * * * * * * * * * * * *
	 * @Produit getProduit()
	 * 
	 * * * * * * * * * * * *
	 */
	public Produit getProduit() {
		if (this.produit == null)
			this.produit = new Produit();
		
		return this.produit;
	}
	
	/**
	 * * * * * *
	 * AFFICHER
	 * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"Réduction (sur produit) N°" + this.getIdReduction() +
			" - Taux : " + this.getTauxReduction() +
			" - Produit : " + this.getProduit().getIdProduit() +
			" - Quantité : " + this.getQuantite()
		);
	}
}
