/*
 * @author MagicBanana
 */

package dao.liste;

import java.util.ArrayList;
import java.util.Date;

import dao.pojo.FactureDao;
import pojo.Facture;

public class ListeFactureDao implements FactureDao {

	private static ListeFactureDao instance;
	private ArrayList<Facture> factures;
	
	/*
	 * Constructeur et singleton
	 */
	private ListeFactureDao() {
		this.factures = new ArrayList<Facture>();
	}
	
	public static ListeFactureDao getInstance() {
		if (instance == null) {
			instance = new ListeFactureDao();
		}
		return instance;
	}

	@Override
	public int create(Facture objet) {
		if (this.factures.contains(objet)) {
			throw new IllegalArgumentException("Tentative d'insertion d'un doublon");
		} else {
			this.factures.add(objet);
			return objet.getIdFacture();
		}
	}

	@Override
	public int update(Facture objet) {
		int idx = this.factures.indexOf(objet);
		
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de modification d'un objet inexistant");			
		} else {
			this.factures.set(idx, objet);
			return objet.getIdFacture();
		}
	}

	@Override
	public void delete(int id) {
		int idx = this.factures.indexOf(this.getById(id));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Tentative de suppression d'un objet inexistant");			
		} else {
			this.factures.remove(idx);
		}
	}

	@Override
	public Facture getById(int id) {
		int idx = this.factures.indexOf(new Facture(id, new Date(), 1000));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun objet ne possède cet identifiant");			
		} else {
			return this.factures.get(idx);
		}
	}

	@Override
	public ArrayList<Facture> getAll() {
		return this.factures;
	}
}