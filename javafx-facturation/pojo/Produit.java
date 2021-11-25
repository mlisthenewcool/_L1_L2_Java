/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Ajout des fonctionnalités String,Integer,Double Property pour JavaFX
 * 
 * @author MagicBanana
 * @class Produit
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package pojo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Produit {
	
	private IntegerProperty id_produit;
	private StringProperty libelle;
	private IntegerProperty id_type;
	private DoubleProperty prix;

	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * Constructeur de base
	 * @param int id_produit
	 * @param String libelle
	 * @param int id_type
	 * @param double prix
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Produit (int id_produit, String libelle, int id_type, double prix) {
		this.setIdProduit(id_produit);
		this.setLibelle(libelle);
		this.setIdType(id_type);
		this.setPrix(prix);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * Constructeur utilisé pour la création MySQL
	 * @param String libelle
	 * @param int id_type
	 * @param double prix
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */	
	public Produit (String libelle, int id_type, double prix) {
		this.setLibelle(libelle);
		this.setIdType(id_type);
		this.setPrix(prix);
	}
	
	/**
	 * * * * * * * * * ID PRODUIT * * * * * * * * *
	 * public int getIdProduit()
	 * public void setIdProduit(int)
	 * public IntegerProperty idProduitProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public int getIdProduit() {
		return this.id_produit.get();
	}
	public void setIdProduit(int id) {
		// Génère une exception si l'id est inférieur ou égal à 0
		if (id <= 0)
			throw new IllegalArgumentException("ID du produit incorrect");
		
		this.idProduitProperty().set(id);
	}
	public IntegerProperty idProduitProperty() {
		if (this.id_produit == null)
			this.id_produit = new SimpleIntegerProperty(this, "id_produit");
		
		return this.id_produit;
	}
	
	/**
	 * * * * * * * * * LIBELLE * * * * * * * * * *
	 * public String getLibelle()
	 * public void setLibelle(String)
	 * public StringProperty libelleProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public String getLibelle() {
		return this.libelle.get(); 
	}
	public void setLibelle(String libelle) {
		//Génère une exception si le libellé est vide
		if (libelle == null || libelle.trim().length() == 0)
			throw new IllegalArgumentException("Libellé vide");
		
		// Génère une exception si le libellé ne contient que des chiffres
		int nbre_chiffres = 0;
		int nbre_caracteres = 0;
		byte[] bytes = libelle.trim().getBytes();
		for (int i = 0; i < bytes.length; i++) {
			if (!Character.isWhitespace((char) bytes[i])) {
				nbre_caracteres +=1;
				if (Character.isDigit((char) bytes[i]))
					nbre_chiffres +=1;
			}
		}
		if (nbre_chiffres == nbre_caracteres)
			throw new IllegalArgumentException("Le libellé ne contient que des chiffres");
		
		this.libelleProperty().set(libelle.trim());
	}
	public StringProperty libelleProperty() {
		if (this.libelle == null)
			this.libelle = new SimpleStringProperty(this, "libelle");
		
		return libelle;
	}
	
	/**
	 * * * * * * * * * ID TYPE * * * * * * * * * *
	 * public int getIdType()
	 * public void setIdType(int)
	 * public IntegerProperty idTypeProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public int getIdType() {
		return this.id_type.get();
	}
	public void setIdType(int id) {
		// Génère une exception si l'id est inférieur ou égal à 0
		if (id <= 0)
			throw new IllegalArgumentException("ID du type produit incorrect");
		
		this.idTypeProperty().set(id);
	}
	public IntegerProperty idTypeProperty() {
		if (this.id_type == null)
			this.id_type = new SimpleIntegerProperty(this, "id_type");
		
		return this.id_type;
	}
	
	/**
	 * * * * * * * * * PRIX * * * * * * * * * * * 
	 * public double getPrix()
	 * public void setPrix(double)
	 * public DoubleProperty prixProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public double getPrix() {
		return this.prix.get();
	}
	public void setPrix(double prix) {
		// Génère une exception si le prix est inférieur ou égal à 0
		if (prix <= 0)
			throw new IllegalArgumentException("Prix unitaire incorrect");
		
		this.prixProperty().set(prix);
	}
	public DoubleProperty prixProperty() {
		if (this.prix == null)
			this.prix = new SimpleDoubleProperty(this, "prix");
		
		return this.prix;
	}
	
	/**
	 * * * * * * *
	 * AFFICHER
	 * * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"ID du produit : " +this.getIdProduit() + 
			"\nLibellé du produit : " +this.getLibelle() +
			"\nID du type de produit : " +this.getIdType() +
			"\nPrix unitaire du produit : " +this.getPrix()
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