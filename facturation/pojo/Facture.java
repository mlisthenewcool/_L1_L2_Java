/**
 * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Ajout des Properties pour JavaFX
 * * * * * * * * * * * * * * * * * *
 */

package pojo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Facture {
	
	private IntegerProperty idFacture;
	private Client client;
	private Date dateFacture;
	private HashMap<Produit, Integer> lignesFacture;
	private DoubleProperty totalHT;
	private DoubleProperty totalTTC;
	
	/**
	 * * * * * * * * * * * * * * * * * * * * *
	 * Constructeur vide pour permettre
	 * d'ajouter des lignes de facture
	 * sans avoir à instancier quoique ce soit
	 * * * * * * * * * * * * * * * * * * * * *
	 */
	public Facture() {
		// Initialise la HashMap si elle est vide
		if (this.getLignesFacture() == null)
			this.setLignesFacture(new HashMap<Produit, Integer>());
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id_client
	 * @param HashMap<Produit, Integer> lignes
	 * 
	 * Constructeur utilisé pour la création MySQL
	 * @see dao.mysql.MySqlFactureDao
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Facture (int idClient, HashMap<Produit, Integer> lignes) {
		this();
		this.getClient().setIdClient(idClient);
		this.setLignesFacture(lignes);
		this.setTotalHT();
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * *
	 * @param int idFacture
	 * @param int idClient
	 * @param HashMap<Produit, Integer> lignes
	 * 
	 * Constructeur complet
	 * * * * * * * * * * * * * * * * * * * * * *
	 */
	public Facture(int idFacture, int idClient, Date dateFacture, HashMap<Produit, Integer> lignes) {		
		this (idClient, lignes);		
		this.setIdFacture(idFacture);
		this.setDateFacture(dateFacture);
	}

	/**
	 * * * * * * * ID FACTURE * * * * * * *
	 * @int getIdFacture()
	 * @void setIdFacture(int id)
	 * @IntegerProperty idFactureProperty()
	 * * * * * * * * * * * * * * * * * * * *
	 */
	public int getIdFacture() {
		return this.idFactureProperty().get();
	}

	public void setIdFacture(int id) {
		if (id <= 0)
			throw new IllegalArgumentException("Le numéro de la facture est incorrect");
		
		this.idFactureProperty().set(id);
	}
	
	public IntegerProperty idFactureProperty() {
		if (this.idFacture == null)
			this.idFacture = new SimpleIntegerProperty(this, "idFacture");
		
		return this.idFacture;
	}

	/**
	 * * * * * * * DATE FACTURE * * * * * * *
	 * @Date getDateFacture()
	 * @void setDateFacture(Date dateFacture)
	 * * * * * * * * * * * * * * * * * * * *
	 */
	public Date getDateFacture() {
		return this.dateFacture;
	}
	
	public void setDateFacture(Date dateFacture) {
		this.dateFacture = dateFacture;
	}
	
	/**
	 * * * * * * * * * * LIGNES FACTURE * * * * * * * * * *
	 * @HashMap<Produit, Integer> getLignesFacture()
	 * @void setLignesFacture(HashMap<Produit, Integer>)
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	public HashMap<Produit, Integer> getLignesFacture() {
		return this.lignesFacture;
	}
	
	public void setLignesFacture(HashMap<Produit, Integer> lignes) {
		this.lignesFacture = lignes;
	}
	
	/**
	 * * * * * * * * TOTAL * * * * * * * *
	 * @double getTotal()
	 * @void setTotal()
	 * @void setTotal(double)
	 * @IntegerProperty totalProperty()
	 * * * * * * * * * * * * * * * * * * *
	 */
	public double getTotalHT() {
		return this.totalHTProperty().get();
	}

	public void setTotalHT() {
		double total = 0;

		if (this.getLignesFacture().size() == 0)
			this.totalHTProperty().set(total);
		// Si la facture a des lignes on construit son total
		// avec la quantité et le prix unitaire des produits 
		else {
			for (Map.Entry<Produit, Integer> lignes : this.getLignesFacture().entrySet()) {
				total = total + lignes.getValue() * lignes.getKey().getPrix();
				this.totalHTProperty().set(total);
			}
		}
	}
	
	// Utilisé uniquement pour la récupération 
	// depuis la persistance (bdd, xml, mémoire etc.)
	public void setTotalHT(double total) {
		this.totalHTProperty().set(total);
	}
	
	public DoubleProperty totalHTProperty() {
		if (this.totalHT == null)
			this.totalHT = new SimpleDoubleProperty(this, "totalHT");
		
		return this.totalHT;
	}
	
	/**
	 * * * * * * * * TOTAL * * * * * * * *
	 * @double getTotal()
	 * @void setTotal()
	 * @void setTotal(double)
	 * @IntegerProperty totalProperty()
	 * * * * * * * * * * * * * * * * * * *
	 */
	public double getTotalTTC() {
		return this.totalTTCProperty().get();
	}

	public void setTotalTTC() {
		double total = this.getTotalHT();

		if (this.getLignesFacture().size() == 0)
			this.totalTTCProperty().set(total);
		// Si la facture a des lignes on construit son total
		// avec la quantité et le prix unitaire des produits 
		else {
			for (Map.Entry<Produit, Integer> lignes : this.getLignesFacture().entrySet()) {
				total = total + 
					(lignes.getValue() // nbre de produits
					* lignes.getKey().getPrix() // prix unitaire
					* lignes.getKey().getTypeProduit().getTVA().getTaux() / 100 // TVA
				);
				this.totalTTCProperty().set(total);
			}
		}
	}
	
	// Utilisé uniquement pour la récupération 
	// depuis la persistance (bdd, xml, mémoire etc.)
	public void setTotalTTC(double total) {
		this.totalTTCProperty().set(total);
	}
	
	public DoubleProperty totalTTCProperty() {
		if (this.totalTTC == null)
			this.totalTTC = new SimpleDoubleProperty(this, "totalTTC");
		
		return this.totalTTC;
	}
	
	/**
	 * * * * CLIENT * * * *
	 * @Client getClient()
	 * * * * * * * * * * * 
	 */
	public Client getClient() {
		if (this.client == null)
			this.client = new Client();
		
		return this.client;
	}
	
	/**
	 * * * * * * *
	 * TO STRING
	 * * * * * * *
	 */
	@Override public String toString() {
		return this.getIdFacture() + " " + this.getClient().toString();
	}
	
	/**
	 * * * * * * *
	 * AFFICHER
	 * * * * * * *
	 */
	public void afficher() {
		System.out.println(
			"N° de la facture : " + this.getIdFacture() +
			" - Passée par : " + this.getClient().toString() +
			" - Le : " + this.getDateFacture() +
			" - Achats : " + this.getLignesFacture() +
			" - Prix total : " + this.getTotalHT()
		);
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * *
	 * Gère les doublons dans la liste mémoire
	 * 
	 * La facture existe si son id a été trouvé
	 * * * * * * * * * * * * * * * * * * * * * *
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