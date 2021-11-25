/**
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue la gestion de liste mémoire pour les tva
 * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.liste;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import dao.pojo.TVADao;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistsException;
import pojo.TVA;

public class ListeTVADao implements TVADao {

	private ArrayList<TVA> tvas;
	private static final AtomicInteger compteur = new AtomicInteger(0);
	
	private static ListeTVADao instance;
	
	private ListeTVADao() {
		this.tvas = new ArrayList<TVA>();
	}
	
	public static ListeTVADao getInstance() {
		if (instance == null)
			instance = new ListeTVADao();
		
		return instance;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param TVA tva : La tva à créer
	 * @return int : L'identifiant de la tva créée
	 * 
	 * @throws ObjectAlreadyExistsException
	 * @see pojo.TVA
	 * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int create(TVA tva) throws ObjectAlreadyExistsException {
		tva.setIdTva(compteur.incrementAndGet());
		
		if (this.tvas.contains(tva))
			throw new ObjectAlreadyExistsException("Cette TVA existe déjà");
		else {
			this.tvas.add(tva);
			System.out.println("TVA " + tva.getIdTva() + " ajoutée");
			return tva.getIdTva();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param TVA tva : La tva à mettre à jour
	 * @return int : L'identifiant de la tva mise à jour
	 * 
	 * @throws ObjectAlreadyExistsException, ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int update(TVA tva) throws ObjectAlreadyExistsException, ObjectNotExistsException {
		int idx = this.tvas.indexOf(tva);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucune TVA ne correspond à l'identifiant " + tva.getIdTva());			
		else {
			this.tvas.set(idx, tva);
			return tva.getIdTva();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la tva à supprimer
	 * 
	 * @throws ObjectNotExistsException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	// TODO ADD OBJECT CONSTRAINT EXCEPTION
	@Override
	public boolean delete(int id) throws ObjectNotExistsException {
		int idx = this.tvas.indexOf(this.getById(id));
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucune TVA ne correspond à l'identifiant " + id);			
		else {
			this.tvas.remove(idx);
			return true;
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant de la tva à chercher
	 * @return TVA : La tva associée à l'identifiant
	 * 
	 * @throw ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public TVA getById(int id) throws ObjectNotExistsException {
		TVA tva = new TVA();
		tva.setIdTva(id);
		int idx = this.tvas.indexOf(tva);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucune TVA ne correspond à l'identifiant " + id);			
		else {
			return this.tvas.get(idx);
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Client> : La liste de tous les clients
	 * 
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<TVA> getAll() {
		return this.tvas;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Client> : La liste de tous les clients correspondant à la recherche
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public ArrayList<TVA> rechercherLibelle(String recherche) {
		// TODO Auto-generated method stub
		return this.tvas;
	}
}