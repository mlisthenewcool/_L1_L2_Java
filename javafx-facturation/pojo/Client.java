/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Ajout des fonctionnalités String,Integer Property pour JavaFX
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package pojo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Client {

	private IntegerProperty id_client;
	private StringProperty nom;
	private StringProperty prenom;
	
	/**
	 * * * * * * * * * * * * 
	 * @param int id_client
	 * @param String nom
	 * @param String prenom
	 * 
	 * Constructeur de base
	 * * * * * * * * * * * *
	 */
	public Client (int id_client, String nom, String prenom) {
		this.setIdClient(id_client);
		this.setNom(nom);
		this.setPrenom(prenom);
	}
	
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
		// Génère une IllegalArgumentException si l'id est inférieur ou égal à 0
		if (id <= 0)
			throw new IllegalArgumentException("ID du client incorrect");
		
		this.idClientProperty().set(id);
	}
	
	public IntegerProperty idClientProperty() {
		if (this.id_client == null)
			this.id_client = new SimpleIntegerProperty(this, "id_client");
		
		return this.id_client;
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
		// Génère une IllegalArgumentException si le nom est vide
		if (nom == null || nom.trim().length() == 0)
			throw new IllegalArgumentException("Nom vide");
		
		// Génère une IllegalArgumentException si le nom contient des chiffres
		byte[] bytes = nom.trim().getBytes();
		for (int i = 0; i < bytes.length; i++) {
			if (!Character.isWhitespace((char) bytes[i])) {
				if (Character.isDigit((char) bytes[i]))
					throw new IllegalArgumentException("Le nom contient des chiffres");
			}
		}
		
		this.nomProperty().set(nom.toUpperCase().replace(" ", ""));
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
		// Génère une IllegalArgumentException si le prénom est vide
		if (prenom == null || prenom.trim().length() == 0)
			throw new IllegalArgumentException("Prénom vide");
		
		// Génère une IllegalArgumentException si le prénom contient des chiffres
		byte[] bytes = prenom.trim().getBytes();
		for (int i = 0; i < bytes.length; i++) {
			if (!Character.isWhitespace((char) bytes[i])) {
				if (Character.isDigit((char) bytes[i]))
					throw new IllegalArgumentException("Le prénom contient des chiffres");
			}
		}
		
		this.prenomProperty().set(prenom.trim());
	}
	public StringProperty prenomProperty() {
		if (this.prenom == null)
			this.prenom = new SimpleStringProperty(this, "prenom");
		
		return this.prenom;
	}
	
	/**
	 * * * * * * * *
	 * TO STRING
	 * * * * * * * *
	 */
	public String toString() {
		return this.getNom() + " " + this.getPrenom();
	}
	
	/**
	 * * * * * *
	 * AFFICHER
	 * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"ID du client : " +this.getIdClient() +
			"\nNom : " +this.getNom() +
			"\nPrénom : " +this.getPrenom()
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