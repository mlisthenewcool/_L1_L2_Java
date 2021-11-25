/**
 * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Reduction de type :
 * SI TypeProduit acheté
 * ENTRE date départ ET date fin
 * ALORS réduction
 * * * * * * * * * * * * * * * * *
 */

package pojo.reduction;

import java.util.Date;

import pojo.TypeProduit;

public class ReductionSurTypeProduit extends Reduction {

	private TypeProduit typeProduit;
	private Date dateStart;
	private Date dateEnd;
	
	public ReductionSurTypeProduit(int tauxReduction, int idTypeProduit, Date dateStart, Date dateEnd) {
		super(tauxReduction);
		this.getTypeProduit().setIdType(idTypeProduit);
		this.setDateStart(dateStart);
		this.setDateEnd(dateEnd);
	}
	
	public ReductionSurTypeProduit(int idReduction, int tauxReduction, int idTypeProduit, Date dateStart, Date dateEnd) {
		this(tauxReduction, idTypeProduit, dateStart, dateEnd);
		this.setIdReduction(idReduction);
	}
	
	/**
	 * * * * DATE START * * * *
	 * @Date getDateStart()
	 * @void setDateStart(Date)
	 * * * * * * * * * * * * * *
	 */
	public Date getDateStart() {
		return this.dateStart;
	}
	
	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}
	
	/**
	 * * * * DATE END * * * *
	 * @Date getDateEnd()
	 * @void setDateEnd(Date)
	 * * * * * * * * * * * * *
	 */
	public Date getDateEnd() {
		return this.dateEnd;
	}
	
	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}
	
	/**
	 * * * * * TYPE PRODUIT * * * * *
	 * @TypeProduit getTypeProduit()
	 * * * * * * * * * * * * * * * * *
	 */
	public TypeProduit getTypeProduit() {
		if (this.typeProduit == null)
			this.typeProduit = new TypeProduit();
		
		return this.typeProduit;
	}
	
	/**
	 * * * * * *
	 * AFFICHER
	 * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"Réduction (sur type de produit) N°" + this.getIdReduction() +
			" - Taux : " + this.getTauxReduction() +
			" - Type de produit : " + this.getTypeProduit().getIdType() +
			" - Date départ : " + this.getDateStart() +
			" - Date fin : " + this.getDateEnd()
		);
	}
}
