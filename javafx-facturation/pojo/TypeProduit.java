/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Ajout des fonctionnalités String,Integer Property pour JavaFX
 * 
 * @author MagicBanana
 * @class TypeProduit
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TypeProduit {
	
	private IntegerProperty id_type;
	private StringProperty libelle;
	private IntegerProperty id_tva;
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * Constructeur de base
	 * @param int id_type
	 * @param String libelle
	 * @param int id_tva
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public TypeProduit(int id_type, String libelle, int id_tva) {
		this.setIdType(id_type);
		this.setLibelle(libelle);
		this.setIdTva(id_tva);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * Constructeur utilisé pour la création MySQL
	 * @param String libelle
	 * @param int id_tva
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */	
	public TypeProduit(String libelle, int id_tva) {
		this.setLibelle(libelle);
		this.setIdTva(id_tva);
	}
	
	/**
	 * * * * * * * * * ID PRODUIT * * * * * * * * *
	 * public int getIdProduit()
	 * public void setIdProduit(int)
	 * public IntegerProperty idProduitProperty()
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
	 * * * * * * * * * LIBELLE * * * * * * * * *
	 * public String getLibelle()
	 * public void setLibelle(String)
	 * public StringProperty libelleProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public String getLibelle() {
		return this.libelle.get();
	}
	
	public void setLibelle(String libelle) {
		// Génère une exception si le libellé est vide
		if (libelle == null || libelle.trim().length() == 0)
			throw new IllegalArgumentException("Type produit vide");
		
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
		
		return this.libelle;
	}
	
	/**
	 * * * * * * * * * ID TVA * * * * * * * * *
	 * public int getIdTva()
	 * public void setIdTva(int)
	 * public IntegerProperty idTvaProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public int getIdTva() {
		return this.id_tva.get();
	}
	
	public void setIdTva(int id) {
		// Génère une exception si l'id est inférieur ou égal à 0
		if (id <= 0)
			throw new IllegalArgumentException("ID TVA incorrect");
		
		this.idTvaProperty().set(id);
	}
	
	public IntegerProperty idTvaProperty() {
		if (this.id_tva == null)
			this.id_tva = new SimpleIntegerProperty(this, "id_tva");
		
		return this.id_tva;
	}
	
	/**
	 * * * * * * *
	 * AFFICHER
	 * * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"ID du type produit : " + this.getIdType() +
			"\nType produit : " +this.getLibelle() +
			"\nID TVA : " +this.getIdTva() 
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