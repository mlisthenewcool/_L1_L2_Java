/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue la gestion de liste mémoire pour les factures
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.liste;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import dao.pojo.FactureDao;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistsException;
import pojo.Facture;

public class ListeFactureDao implements FactureDao {
	
	private ArrayList<Facture> factures;
	private static final AtomicInteger compteur = new AtomicInteger(0);
	
	private static ListeFactureDao instance;
	
	private ListeFactureDao() {
		this.factures = new ArrayList<Facture>();
	}
	
	public static ListeFactureDao getInstance() {
		if (instance == null)
			instance = new ListeFactureDao();
		
		return instance;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Facture facture : La facture à créer
	 * @return int : L'identifiant de la facture créé
	 * 
	 * Crée une nouvelle facture dans la liste mémoire
	 * 
	 * @throw ObjectAlreadyExistsException
	 * @see pojo.Facture
	 * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int create(Facture facture) throws ObjectAlreadyExistsException {
		facture.setIdFacture(compteur.incrementAndGet());
		
		if (this.factures.contains(facture))
			throw new ObjectAlreadyExistsException("Cette facture existe déjà");
		else {
			this.factures.add(facture);
			System.out.println("Facture " + facture.getIdFacture() + " ajoutée");
			return facture.getIdFacture();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Facture facture : La facture à mettre à jour
	 * @return int : L'identifiant de la facture mise à jour
	 * 
	 * Met à jour une facture dans la liste mémoire
	 * 
	 * @throw ObjectAlreadyExistsException, ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int update(Facture facture) throws ObjectNotExistsException, ObjectAlreadyExistsException {
		int idx = this.factures.indexOf(facture);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucune facture ne correspond à l'identifiant " + facture.getIdFacture());			
		else {
			if (this.factures.contains(facture))
				throw new ObjectAlreadyExistsException("Aucune facture ne correspond à l'identifiant " + facture.getIdFacture());
			
			this.factures.set(idx, facture);
			return facture.getIdFacture();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la facture à supprimer
	 * 
	 * Supprime une facture dans la liste mémoire
	 * 
	 * @throw IllegalArgumentException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public boolean delete(int id) {
		int idx = this.factures.indexOf(this.getById(id));
		
		if (idx == -1)
			throw new IllegalArgumentException("Aucune facture ne correspond à l'identifiant " + id);			
		else {
			this.factures.remove(idx);
			return true;
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la facture à chercher
	 * @return Facture : La facture associée à l'identifiant
	 * 
	 * Si la facture n'existe pas @throw IllegalArgumentException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public Facture getById(int id) {
		Facture facture = new Facture();
		facture.setIdFacture(id);
		
		int idx = this.factures.indexOf(facture);
		
		if (idx == -1)
			throw new IllegalArgumentException("Aucune facture ne correspond à l'identifiant " + id);			
		else {
			return this.factures.get(idx);
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Facture> : La liste de toutes les factures
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<Facture> getAll() {
		return this.factures;
	}

	@Override
	public ArrayList<Facture> rechercherClient(String recherche) {
		// TODO Auto-generated method stub
		return null;
	}
}