/**
 * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Ajout des Properties pour JavaFX
 * * * * * * * * * * * * * * * * * *
 */

package pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TypeProduit {
	
	private IntegerProperty idType;
	private StringProperty libelle;
	private TVA tva;
	
	public TypeProduit() {}
	
	/**
	 * * * * * * * * * * * * *
	 * @param int id_type
	 * @param String libelle
	 * @param int id_tva
	 * 
	 * Constructeur de base
	 * * * * * * * * * * * * *
	 */
	public TypeProduit(int idType, String libelle, int idTva) {
		this(libelle, idTva);
		this.setIdType(idType);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * @param String libelle
	 * @param int idTva
	 * 
	 * Constructeur utilisé pour la création MySQL
	 * @see dao.mysql.MySqlTypeProduitDao
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */	
	public TypeProduit(String libelle, int idTva) {
		this.setLibelle(libelle);
		this.getTVA().setIdTva(idTva);
	}
	
	/**
	 * * * * * * * * * ID PRODUIT * * * * * * * * *
	 * @int getIdProduit()
	 * @void setIdProduit(int)
	 * @IntegerProperty idProduitProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public int getIdType() {
		return this.idType.get();
	}
	
	public void setIdType(int id) {
		if (id <= 0)
			throw new IllegalArgumentException("Le numéro du type produit est incorrect");
		
		this.idTypeProperty().set(id);
	}
	
	public IntegerProperty idTypeProperty() {
		if (this.idType == null)
			this.idType = new SimpleIntegerProperty(this, "idType");
		
		return this.idType;
	}
	
	/**
	 * * * * * * * * * LIBELLE * * * * * *
	 * @String getLibelle()
	 * @void setLibelle(String)
	 * @StringProperty libelleProperty()
	 * * * * * * * * * * * * * * * * * * *
	 */
	public String getLibelle() {
		return this.libelle.get();
	}
	
	public void setLibelle(String libelle) {
		libelle = libelle.trim();
		
		if (libelle == null || libelle.length() < 2)
			throw new IllegalArgumentException("Le libellé est vide ou trop court (minimum 2 caractères)");
		
		if ( ! libelle.matches("^([ \u00c0-\u01ff a-zA-Z '-])+$")) {
			throw new IllegalArgumentException("Le libellé contient des caractères illégaux");
		}
		
		this.libelleProperty().set(
			libelle.substring(0, 1).toUpperCase() +
			libelle.substring(1));
	}
	
	public StringProperty libelleProperty() {
		if (this.libelle == null)
			this.libelle = new SimpleStringProperty(this, "libelle");
		
		return this.libelle;
	}
	
	/**
	 * * * * TVA * * * *
	 * @TVA getTVA()
	 * * * * * * * * * *
	 */
	public TVA getTVA() {
		if (this.tva == null)
			this.tva = new TVA();
		
		return this.tva;
	}
	
	/**
	 * * * * * * *
	 * TO STRING
	 * 
	 * * * * * * *
	 */
	public String toString() {
		return this.getLibelle();
	}
	
	/**
	 * * * * * * *
	 * AFFICHER
	 * 
	 * * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"N° du type produit : " + this.getIdType() +
			" - Libellé : " + this.getLibelle() +
			" - N° de la TVA : " + this.getTVA().getIdTva() 
		);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Gère les doublons dans la liste mémoire
	 * 
	 * Le type produit existe si son id ou son libellé a été trouvé
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public boolean equals (Object object) {
		boolean existe = false;
		if (object == null || object.getClass() != this.getClass())
			existe = false;
		else {
			TypeProduit type = (TypeProduit) object;
			if (this.getIdType() == type.getIdType() || this.getLibelle() == type.getLibelle())
				existe = true;
		}
		return existe;
	}
}