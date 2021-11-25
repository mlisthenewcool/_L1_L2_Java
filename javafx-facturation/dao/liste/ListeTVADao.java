/*
 * @author MagicBanana
 */

package dao.liste;

import java.util.ArrayList;

import dao.pojo.TVADao;
import pojo.TVA;

public class ListeTVADao implements TVADao {

	private static ListeTVADao instance;
	private ArrayList<TVA> tvas;

	/*
	 * Constructeur et singleton
	 */
	private ListeTVADao() {
		this.tvas = new ArrayList<TVA>();
	}
	
	public static ListeTVADao getInstance() {
		if (instance == null) {
			instance = new ListeTVADao();
		}
		return instance;
	}

	/*
	 * Méthodes
	 */
	@Override
	public int create(TVA objet) {
		if (this.tvas.contains(objet)) {
			throw new IllegalArgumentException("Tentative d'insertion d'un doublon");
		} else {
			this.tvas.add(objet);
			return objet.getIdTva();
		}
	}

	@Override
	public int update(TVA objet) {
		int idx = this.tvas.indexOf(objet);
		
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");			
		} else {
			this.tvas.set(idx, objet);
			return objet.getIdTva();
		}
	}

	@Override
	public void delete(int id) {
		int idx = this.tvas.indexOf(this.getById(id));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");			
		} else {
			this.tvas.remove(idx);
		}
	}

	@Override
	public TVA getById(int id) {
		int idx = this.tvas.indexOf(new TVA(id, "random", 1000));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun objet ne possède cet identifiant");			
		} else {
			return this.tvas.get(idx);
		}
	}

	@Override
	public ArrayList<TVA> getAll() {
		return this.tvas;
	}
}