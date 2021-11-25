/**
 * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Ajout des Properties pour JavaFX
 * * * * * * * * * * * * * * * * * *
 */

package pojo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Produit {
	
	private IntegerProperty idProduit;
	private StringProperty libelle;
	private DoubleProperty prix;
	private TypeProduit typeProduit;
	
	public Produit() {}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * @param String libelle
	 * @param int idType
	 * @param double prix
	 * 
	 * Constructeur utilisé pour la création MySQL
	 * @see dao.mysql.MySqlProduitDao
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Produit (String libelle, int idType, double prix) {
		this.setLibelle(libelle);
		this.getTypeProduit().setIdType(idType);
		this.setPrix(prix);
	}
	
	/**
	 * * * * * * * * * * * *
	 * @param int idProduit
	 * @param String libelle
	 * @param int idType
	 * @param double prix
	 * 
	 * Constructeur complet
	 * * * * * * * * * * * *
	 */
	public Produit (int idProduit, String libelle, int idType, double prix) {
		this(libelle, idType, prix);
		this.setIdProduit(idProduit);
	}
	
	/**
	 * * * * * * * * ID PRODUIT * * * * * *
	 * @int getIdProduit()
	 * @void setIdProduit(int)
	 * @IntegerProperty idProduitProperty()
	 * * * * * * * * * * * * * * * * * * * *
	 */
	public int getIdProduit() {
		return this.idProduit.get();
	}
	
	public void setIdProduit(int id) {
		if (id <= 0)
			throw new IllegalArgumentException("Le numéro du produit est incorrect");
		
		this.idProduitProperty().set(id);
	}
	
	public IntegerProperty idProduitProperty() {
		if (this.idProduit == null)
			this.idProduit = new SimpleIntegerProperty(this, "idProduit");
		
		return this.idProduit;
	}
	
	/**
	 * * * * * * * * LIBELLE * * * * * * *
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
		
		if (libelle == null || libelle.trim().length() < 2)
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
		
		return libelle;
	}
	
	/**
	 * * * * * * * PRIX * * * * * * *
	 * @double getPrix()
	 * @void setPrix(double)
	 * @DoubleProperty prixProperty()
	 * * * * * * * * * * * * * * * * *
	 */
	public double getPrix() {
		return this.prix.get();
	}
	
	public void setPrix(double prix) {
		if (prix <= 0)
			throw new IllegalArgumentException("Le prix unitaire est nul ou négatif");
		
		this.prixProperty().set(prix);
	}
	
	public DoubleProperty prixProperty() {
		if (this.prix == null)
			this.prix = new SimpleDoubleProperty(this, "prix");
		
		return this.prix;
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
	 * * * * * * *
	 * TO STRING
	 * 
	 * * * * * * *
	 */
	@Override public String toString() {
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
			"N° du produit : " + this.getIdProduit() + 
			" - Libellé : " + this.getLibelle() +
			" - N° du type de produit : " + this.getTypeProduit().getIdType() +
			" - Prix unitaire : " + this.getPrix()
		);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Gère les doublons dans la liste mémoire
	 * 
	 * Le produit existe si son id ou son libellé a été trouvé
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public boolean equals (Object object) {
		boolean existe = false;
		if (object == null || object.getClass() != this.getClass())
			existe = false;
		else {
			Produit p = (Produit) object;
			if (this.getIdProduit() == p.getIdProduit() || this.getLibelle() == p.getLibelle())
				existe = true;
		}
		return existe;
	}
}