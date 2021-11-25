/**
 * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Reduction de type :
 * SI montant total de la facture > X
 * ALORS réduction
 * * * * * * * * * * * * * * * * * * *
 */

package pojo.reduction;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ReductionSurTotal extends Reduction {
	
	private DoubleProperty totalFacture;
	
	public ReductionSurTotal() {
		
	}
	
	/**
	 * * * * * * * * * * * * * * * 
	 * @param tauxReduction
	 * @param totalFacture
	 * 
	 * * * * * * * * * * * * * * *
	 */
	public ReductionSurTotal(int tauxReduction, double totalFacture) {
		super(tauxReduction);
		this.setTotalFacture(totalFacture);
	}
	/**
	 * * * * * * * * * * * *
	 * @param idReduction
	 * @param tauxReduction
	 * @param totalFacture
	 * 
	 * * * * * * * * * * * *
	 */
	public ReductionSurTotal(int idReduction, int tauxReduction, double totalFacture) {
		this(tauxReduction, totalFacture);
		this.setIdReduction(idReduction);
	}
	
	/**
	 * * * * * * * TOTAL FACTURE * * * * * * *
	 * @double getTotalFacture()
	 * @void setTotalFacture()
	 * @DoubleProperty totalFactureProperty()
	 * * * * * * * * * * * * * * * * * * * * *
	 */
	public double getTotalFacture() {
		return this.totalFactureProperty().get();
	}
	public void setTotalFacture(double totalFacture) {
		if (totalFacture <= 0)
			throw new IllegalArgumentException("Le montant de la facture est nul ou négatif");
		
		this.totalFactureProperty().set(totalFacture);
	}
	
	public DoubleProperty totalFactureProperty() {
		if (this.totalFacture == null)
			this.totalFacture = new SimpleDoubleProperty(this, "totalFacture");
		
		return this.totalFacture;
	}
	
	/**
	 * * * * * *
	 * AFFICHER
	 * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"Réduction (sur total facture) N°" + this.getIdReduction() +
			" - Taux : " + this.getTauxReduction() +
			" - Total facture : " + this.getTotalFacture()
		);
	}
}