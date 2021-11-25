/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue la gestion de liste mémoire pour les produits
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.liste;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import dao.pojo.ProduitDao;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistsException;
import pojo.Produit;

public class ListeProduitDao implements ProduitDao {
	
	private ArrayList<Produit> produits;
	private static final AtomicInteger compteur = new AtomicInteger(0);

	private static ListeProduitDao instance;
	
	private ListeProduitDao() {
		this.produits = new ArrayList<Produit>();
	}
	
	public static ListeProduitDao getInstance() {
		if (instance == null)
			instance = new ListeProduitDao();
		
		return instance;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Produit produit : Le produit à créer
	 * @return int : L'identifiant du produit créé
	 * 
	 * @throws ObjectAlreadyExistsException
	 * @see pojo.Produit
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int create(Produit produit) throws ObjectAlreadyExistsException {
		produit.setIdProduit(compteur.incrementAndGet());
		
		if (this.produits.contains(produit))
			throw new ObjectAlreadyExistsException("Ce produit existe déjà");
		else {
			this.produits.add(produit);
			System.out.println("Produit " + produit.getIdProduit() + " ajouté");
			return produit.getIdProduit();
		}
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Produit produit : Le produit à mettre à jour
	 * @return int : L'identifiant du produit mis à jour
	 * 
	 * @throws ObjectAlreadyExistsException, ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int update(Produit produit) throws ObjectAlreadyExistsException, ObjectNotExistsException {
		int idx = this.produits.indexOf(produit);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucun produit ne correspond à l'identifiant " + produit.getIdProduit());			
		else {
			this.produits.set(idx, produit);
			return produit.getIdProduit();
		}
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du produit à supprimer
	 * 
	 * @throws ObjectNotExistsException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	// TODO ADD OBJECT CONSTRAINT EXCEPTION
	@Override public boolean delete(int id) throws ObjectNotExistsException {
		int idx = this.produits.indexOf(this.getById(id));
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucun produit ne correspond à l'identifiant " + id);			
		else {
			this.produits.remove(idx);
			return true;
		}
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du produit à chercher
	 * @return Produit : Le produit associé à l'identifiant
	 * 
	 * @throws ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public Produit getById(int id) throws ObjectNotExistsException {
		Produit produit = new Produit();
		produit.setIdProduit(id);
		int idx = this.produits.indexOf(produit);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucun produit ne correspond à l'identifiant " + id);			
		else {
			return this.produits.get(idx);
		}
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Produit> : La liste de tous les produits
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<Produit> getAll() {
		return this.produits;
	}

	@Override
	public ArrayList<Produit> rechercherLibelleOuType(String recherche) {
		// TODO Auto-generated method stub
		return this.produits;
	}
}