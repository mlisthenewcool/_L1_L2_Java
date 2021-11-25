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

public class TVA {
	
	private IntegerProperty idTva;
	private StringProperty libelle;
	private DoubleProperty taux;
	
	public TVA() {}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * @param String libelle
	 * @param double taux
	 * 
	 * Constructeur utilisé pour la création MySQL
	 * @see dao.mysql.MySqlTVADao
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */		
	public TVA (String libelle, double taux) {
		this.setLibelle(libelle);
		this.setTaux(taux);
	}
	
	/**
	 * * * * * * * * * * * * *
	 * @param int id_tva
	 * @param String libelle
	 * @param double taux
	 * 
	 * Constructeur complet
	 * * * * * * * * * * * * *
	 */
	public TVA (int idTva, String libelle, double taux) {
		this(libelle, taux);
		this.setIdTva(idTva);
	}
	
	/**
	 * * * * * * * * ID TVA * * * * * * *
	 * @int getIdTva()
	 * @void setIdTva(int id)
	 * @IntegerProperty idTvaProperty()
	 * * * * * * * * * * * * * * * * * *
	 */
	public int getIdTva() {
		return this.idTvaProperty().get();
	}
	
	public void setIdTva(int id) {
		if (id <= 0)
			throw new IllegalArgumentException("Le numéro de la TVA est incorrect");
		
		this.idTvaProperty().set(id);
	}
	
	public IntegerProperty idTvaProperty() {
		if (this.idTva == null)
			this.idTva = new SimpleIntegerProperty(this, "idTva");
		
		return this.idTva;
	}
	
	/**
	 * * * * * * * * * LIBELLE * * * * * * * * * *
	 * @String getLibelle()
	 * @void setLibelle(String libelle)
	 * @StringProperty libelleProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public String getLibelle() {
		return this.libelleProperty().get();
	}
	
	public void setLibelle(String libelle) {
		libelle = libelle.trim();
		
		if (libelle == null || libelle.length() < 2)
			throw new IllegalArgumentException("Le libellé est vide ou trop court (minimum 2 caractères)");
		
		if ( ! libelle.matches("^([ \u00c0-\u01ff a-zA-Z '-])+$"))
			throw new IllegalArgumentException("Le libellé contient des caractères illégaux");
		
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
	 * * * * * * * * TAUX * * * * * * *
	 * @double getTaux()
	 * @void setTaux(double taux)
	 * @DoubleProperty tauxProperty()
	 * * * * * * * * * * * * * * * * * *
	 */	
	public double getTaux() {
		return this.tauxProperty().get();
	}
	
	public void setTaux(double taux) {
		if (taux <= 0)
			throw new IllegalArgumentException("Le taux de la TVA est nul ou négatif");
		
		this.tauxProperty().set(taux);
	}
		
	public DoubleProperty tauxProperty() {
		if (this.taux == null)
			this.taux = new SimpleDoubleProperty(this, "taux");
		
		return this.taux;
	}
	
	/**
	 * * * * * * *
	 * TO STRING
	 * 
	 * * * * * * *
	 */
	@Override public String toString() {
		return this.getLibelle() + " (" + this.getIdTva() + ")";
	}

	/**
	 * * * * * * *
	 * AFFICHER
	 * 
	 * * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"N° de la TVA : " + this.getIdTva() + 
			" - Libellé : " + this.getLibelle() +
			" - Taux : " + this.getTaux()
		);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Gère les doublons dans la liste mémoire
	 * 
	 * La TVA existe si son id ou son libellé a été trouvé
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