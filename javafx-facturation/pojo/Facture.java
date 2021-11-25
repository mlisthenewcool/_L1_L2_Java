/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Ajout des fonctionnalités Integer Property pour JavaFX
 * 
 * @author MagicBanana
 * @class Facture
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package pojo;

import java.util.Date;
import java.util.HashMap;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import pojo.Produit;

public class Facture {
	
	private IntegerProperty id_facture;
	private Date date_facture;
	//TODO private HashMap<Produit, Integer> achats;
	private IntegerProperty id_client;
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * Constructeur de base
	 * @param int id_facture
	 * @param Date date_facture
	 * @param int id_client
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Facture (int id_facture, Date date_facture, int id_client) {
		this.setIdFacture(id_facture);
		this.setDateFacture(date_facture);
		//TODO implémenter les achats
		this.setIdClient(id_client);
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * Constructeur utilisé pour la création MySQL
	 * @param Date date_facture
	 * @param int id_client
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Facture (Date date_facture, int id_client, HashMap<Produit, Integer> achats) {
		this.setDateFacture(date_facture);
		//TODO implémenter les achats
		this.setIdClient(id_client);
	}

	/**
	 * * * * * * * * * ID CLIENT * * * * * * * * *
	 * public int getIdClient()
	 * public void setIdClient(int)
	 * public IntegerProperty idClientProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public int getIdFacture() {
		return this.id_facture.get();
	}

	public void setIdFacture(int id) {
		// Génère une exception si l'id est inférieur ou égal à 0
		if (id <= 0)
			throw new IllegalArgumentException("ID de la facture incorrect");
		
		this.idFactureProperty().set(id);
	}
	
	public IntegerProperty idFactureProperty() {
		if (this.id_facture == null)
			this.id_facture = new SimpleIntegerProperty(this, "id_facture");
		
		return this.id_facture;
	}

	/**
	 * * * * * * * * DATE FACTURE * * * * * * * * 
	 * public Date getDateFacture()
	 * public void setDateFacture(Date)
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Date getDateFacture() {
		return this.date_facture;
	}
	
	public void setDateFacture(Date date_facture) {
		// Génère une exception si la date est ultérieure à la date actuelle
		Date date_actuelle = new Date();
		if (date_facture.compareTo(date_actuelle) > 0)
			throw new IllegalArgumentException("Date de la facture ultérieure à la date actuelle");
		
		this.date_facture = date_facture;
	}
	
	/**
	 * * * * * * * * * ID CLIENT * * * * * * * * *
	 * public int getIdClient()
	 * public void setIdClient(int)
	 * public IntegerProperty idClientProperty()
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */	
	public int getIdClient() {
		return this.id_client.get();
	}
	
	public void setIdClient(int id) {
		// Génère une exception si l'id est inférieur ou égal à 0
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
	 * * * * * * *
	 * AFFICHER
	 * * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"ID de la facture : " +this.getIdFacture() + 
			"\nDate de la facture : " +this.getDateFacture() +
			"\nID du client : " +this.getIdClient()
		);
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * Gère les doublons dans la liste mémoire
	 * 
	 * La facture existe si son id a été trouvé
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public boolean equals (Object object) {
		boolean existe = false;
		if (object == null || object.getClass() != this.getClass())
			existe = false;
		else {
			Facture f = (Facture) object;
			if (this.getIdFacture() == f.getIdFacture())
				existe = true;
		}
		return existe;
	}
}