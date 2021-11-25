/**
 * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Classe mère des réductions
 * * * * * * * * * * * * * * *
 */

package pojo.reduction;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public abstract class Reduction {
	
	protected IntegerProperty idReduction;
	protected IntegerProperty tauxReduction;
	
	public Reduction() {}
	
	public Reduction (int tauxReduction) {
		this.setTauxReduction(tauxReduction);
	}
	
	public Reduction (int idReduction, int tauxReduction) {
		this.setIdReduction(idReduction);
		this.setTauxReduction(tauxReduction);
	}
	
	/**
	 * * * * * * * ID REDUCTION * * * * * * * 
	 * @int getIdReduction()
	 * @void setIdReduction(int id)
	 * @IntegerProperty idReductionProperty()
	 * * * * * * * * * * * * * * * * * * * * *
	 */
	public int getIdReduction() {
		return this.idReductionProperty().get();
	}
	
	public void setIdReduction(int id) {
		if (id <= 0)
			throw new IllegalArgumentException("L'identifiant de la réduction est incorrect");
		
		this.idReductionProperty().set(id);
	}
	
	public IntegerProperty idReductionProperty() {
		if (this.idReduction == null)
			this.idReduction = new SimpleIntegerProperty(this, "idReduction");
		
		return this.idReduction;
	}
	
	/**
	 * * * * * * * TAUX REDUCTION * * * * * * *
	 * @int getTauxReduction()
	 * @void setTauxReduction(int)
	 * @IntegerProperty tauxReductionProperty()
	 * * * * * * * * * * * * * * * * * * * * * *
	 */
	public int getTauxReduction() {
		return this.tauxReductionProperty().get();
	}
	
	public void setTauxReduction(int taux) {
		if (taux <= 0)
			throw new IllegalArgumentException("Réduction incorrecte");
		
		this.tauxReductionProperty().set(taux);
	}
	
	public IntegerProperty tauxReductionProperty() {
		if (this.tauxReduction == null)
			this.tauxReduction = new SimpleIntegerProperty(this, "tauxReduction");
		
		return this.tauxReduction;
	}
	
	/**
	 * * * * * *
	 * AFFICHER
	 * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"Réduction N°" + this.getIdReduction() + " - Taux : " + this.getTauxReduction());
	}
}