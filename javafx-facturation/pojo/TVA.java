/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Ajout des fonctionnalités String,Integer,Double Property pour JavaFX
 * 
 * @author MagicBanana
 * @class TVA
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package pojo;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TVA {
	
	private IntegerProperty id_tva;
	private StringProperty libelle;
	private DoubleProperty taux;
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * Constructeur de base
	 * @param int id_tva
	 * @param String libelle
	 * @param double taux
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public TVA (int id_tva, String libelle, double taux) {
		this.setIdTva(id_tva);
		this.setLibelle(libelle);
		this.setTaux(taux);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * Constructeur utilisé pour la création MySQL
	 * @param String libelle
	 * @param double taux
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */		
	public TVA (String libelle, double taux) {
		this.setLibelle(libelle);
		this.setTaux(taux);
	}
	
	/**
	 * * * * * * * * * ID TVA * * * * * * * * * * *
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
		// Génère une exception si le libellé est vide
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
		
		return this.libelle;
	}
	
	/**
	 * * * * * * * * * TAUX * * * * * * * * * * *
	 * public int getTaux()
	 * public void setTaux(int)
	 * public IntegerProperty tauxProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */	
	public double getTaux() {
		return this.taux.get();
	}
	
	public void setTaux(double taux) {
		// Génère une exception si le taux est inférieur ou égal à 0
		if (taux <= 0)
			throw new IllegalArgumentException("Taux de la TVA incorrect");
		
		this.tauxProperty().set(taux);
	}
		
	public DoubleProperty tauxProperty() {
		if (this.taux == null)
			this.taux = new SimpleDoubleProperty(this, "taux");
		
		return this.taux;
	}
	
	/**
	 * * * * * * *
	 * AFFICHER
	 * * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"ID TVA : " +this.getIdTva() + 
			"\nLibellé : " +this.getLibelle() +
			"\nTaux : " +this.getTaux()
		);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Gère les doublons dans la liste mémoire
	 * 
	 * La TVA existe si son id ou son libelle a été trouvé
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public boolean equals (Object object) {
		boolean existe = false;	
		if (object == null || object.getClass() != this.getClass())
			existe = false;
		else {
			TVA tva = (TVA) object;
			if (this.getIdTva() == tva.getIdTva() || this.getLibelle() == tva.getLibelle())
				existe = true;
		}
		return existe;
	}
}