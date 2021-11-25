/*
 * @author MagicBanana
 */

package dao.liste;

import java.util.ArrayList;

import dao.pojo.TypeProduitDao;
import pojo.TypeProduit;

public class ListeTypeProduitDao implements TypeProduitDao {

	private static ListeTypeProduitDao instance;
	private ArrayList<TypeProduit> types;

	/*
	 * Constructeur et instance
	 */
	private ListeTypeProduitDao() {
		this.types = new ArrayList<TypeProduit>();
	}
	
	public static ListeTypeProduitDao getInstance() {
		if (instance == null) {
			instance = new ListeTypeProduitDao();
		}
		return instance;
	}

	/*
	 * Méthodes
	 */
	@Override
	public int create(TypeProduit objet) {
		if (this.types.contains(objet)) {
			throw new IllegalArgumentException("Tentative d'insertion d'un doublon");
		} else {
			this.types.add(objet);
			return objet.getIdType();
		}
	}

	@Override
	public int update(TypeProduit objet) {
		int id = this.types.indexOf(objet);
		
		if (id == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");			
		} else {
			this.types.set(id, objet);
			return objet.getIdType();
		}
	}

	@Override
	public void delete(int id) {
		int idx = this.types.indexOf(this.getById(id));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");			
		} else {
			this.types.remove(idx);
		}
	}

	@Override
	public TypeProduit getById(int id) {
		int idx = this.types.indexOf(new TypeProduit(id, "random", 1000));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun objet ne possède cet identifiant");			
		} else {
			return this.types.get(idx);
		}
	}

	@Override
	public ArrayList<TypeProduit> getAll() {
		return this.types;
	}

}