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

public class Client {

	private IntegerProperty idClient;
	private StringProperty nom;
	private StringProperty prenom;
	
	public Client() {}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * @param String nom
	 * @param String prenom
	 * 
	 * Constructeur utilisé pour la création MySQL
	 * @see dao.mysql.MySqlClientDao
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Client (String nom, String prenom) {
		this.setNom(nom);
		this.setPrenom(prenom);
	}
	
	/**
	 * * * * * * * * * * * * 
	 * @param int idClient
	 * @param String nom
	 * @param String prenom
	 * 
	 * Constructeur complet
	 * * * * * * * * * * * *
	 */
	public Client (int idClient, String nom, String prenom) {
		this(nom, prenom);
		this.setIdClient(idClient);
	}
		
	/**
	 * * * * * * * ID CLIENT * * * * * * * 
	 * @int getIdClient()
	 * @void setIdClient(int id)
	 * @IntegerProperty idClientProperty()
	 * * * * * * * * * * * * * * * * * * *
	 */
	public int getIdClient() {
		return this.idClientProperty().get();
	}
	
	public void setIdClient(int id) {
		if (id <= 0)
			throw new IllegalArgumentException("Le numéro du client est incorrect");
		
		this.idClientProperty().set(id);
	}
	
	public IntegerProperty idClientProperty() {
		if (this.idClient == null)
			this.idClient = new SimpleIntegerProperty(this, "idClient");
		
		return this.idClient;
	}
	
	/**
	 * * * * * * * NOM * * * * * * *
	 * @String getNom()
	 * @void setNom(String nom)
	 * @StringProperty nomProperty()
	 * * * * * * * * * * * * * * * *
	 */
	public String getNom() {
		return this.nomProperty().get();
	}
	
	public void setNom(String nom) {
		nom = nom.trim();
		
		if (nom == null || nom.length() < 2)
			throw new IllegalArgumentException("Le nom est vide ou trop court (minimum 2 caractères)");
		
		if ( ! nom.matches("^([ \u00c0-\u01ff a-zA-Z '-])+$")) {
			throw new IllegalArgumentException("Le nom contient des caractères illégaux");
		}
		
		this.nomProperty().set(nom.toUpperCase());
	}
	
	public StringProperty nomProperty() {
		if (this.nom == null)
			this.nom = new SimpleStringProperty(this, "nom");
		
		return this.nom;
	}
	
	/**
	 *  * * * * * * * PRENOM * * * * * * * *
	 * @String getPrenomClient()
	 * @void setPrenomClient(String prenom)
	 * @StringProperty prenomProperty()
	 * * * * * * * * * * * * * * * * * * * * 
	 */
	public String getPrenom() {
		return this.prenomProperty().get();
	}
	
	public void setPrenom(String prenom) {
		prenom = prenom.trim();
		
		if (prenom == null || prenom.length() < 2)
			throw new IllegalArgumentException("Prénom vide ou trop court (minimum 2 caractères)");
		
		if ( ! prenom.matches("^([ \u00c0-\u01ff a-zA-Z '-])+$")) {
			throw new IllegalArgumentException("Le prénom contient des caractères illégaux");
		}
		
		this.prenomProperty().set(
			prenom.substring(0, 1).toUpperCase() +
			prenom.substring(1));
	}
	
	public StringProperty prenomProperty() {
		if (this.prenom == null)
			this.prenom = new SimpleStringProperty(this, "prenom");
		
		return this.prenom;
	}
	
	/**
	 * * * * * * *
	 * TO STRING
	 * 
	 * * * * * * *
	 */
	@Override public String toString() {
		return this.getNom() + " " + this.getPrenom() + " (" + this.getIdClient() + ")";
	}
	
	/**
	 * * * * * *
	 * AFFICHER
	 * 
	 * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"N° du client : " + this.getIdClient() +
			" - Nom : " + this.getNom() +
			" - Prénom : " + this.getPrenom()
		);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * *
	 * Gère les doublons dans la liste mémoire
	 * 
	 * Le client existe si son id a été trouvé
	 * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public boolean equals (Object object) {
		boolean existe = false;
		if (object == null || object.getClass() != this.getClass())
			existe = false;
		else {
			Client client = (Client) object;
			if (this.getIdClient() == client.getIdClient())
				existe = true;
		}
		return existe;
	}
}