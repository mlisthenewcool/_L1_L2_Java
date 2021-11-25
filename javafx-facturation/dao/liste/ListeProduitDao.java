/*
 * @author MagicBanana
 */

package dao.liste;

import java.util.ArrayList;

import dao.pojo.ProduitDao;
import pojo.Produit;

public class ListeProduitDao implements ProduitDao {
	
	private static ListeProduitDao instance;
	private ArrayList<Produit> produits;

	/*
	 * Constructeur et singleton
	 */
	private ListeProduitDao() {
		this.produits = new ArrayList<Produit>();
	}
	
	public static ListeProduitDao getInstance() {
		if (instance == null) {
			instance = new ListeProduitDao();
		}
		return instance;
	}

	/*
	 * Méthodes
	 */
	@Override
	public int create(Produit objet) {
		if (this.produits.contains(objet)) {
			throw new IllegalArgumentException("Tentative d'insertion d'un doublon");
		} else {
			this.produits.add(objet);
			return objet.getIdProduit();
		}
	}
	
	@Override
	public int update(Produit objet) {
		int id = this.produits.indexOf(objet);
		
		if (id == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");			
		} else {
			this.produits.set(id, objet);
			return objet.getIdProduit();
		}
	}
	
	@Override
	public void delete(int id) {
		int idx = this.produits.indexOf(this.getById(id));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");			
		} else {
			this.produits.remove(idx);
		}
	}
	
	@Override
	public Produit getById(int id) {
		int idx = this.produits.indexOf(new Produit(id, "random", 1000, 1000));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun objet ne possède cet identifiant");			
		} else {
			return this.produits.get(idx);
		}
	}
	
	@Override
	public ArrayList<Produit> getAll() {
		return this.produits;
	}
}