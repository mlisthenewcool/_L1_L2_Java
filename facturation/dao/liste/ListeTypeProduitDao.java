/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue la gestion de liste mémoire pour les types produit
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.liste;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import dao.pojo.TypeProduitDao;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistsException;
import pojo.TypeProduit;

public class ListeTypeProduitDao implements TypeProduitDao {

	private ArrayList<TypeProduit> types;
	private static final AtomicInteger compteur = new AtomicInteger(0);
	
	private static ListeTypeProduitDao instance;
	
	private ListeTypeProduitDao() {
		this.types = new ArrayList<TypeProduit>();
	}
	
	public static ListeTypeProduitDao getInstance() {
		if (instance == null)
			instance = new ListeTypeProduitDao();
		
		return instance;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param TypeProduit typeProduit : Le type produit à créer
	 * @return int : L'identifiant du type produit créé
	 * 
	 * @throws ObjectAlreadyExistsException
	 * @see pojo.TypeProduit
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int create(TypeProduit typeProduit) throws ObjectAlreadyExistsException {
		typeProduit.setIdType(compteur.incrementAndGet());
		
		if (this.types.contains(typeProduit))
			throw new ObjectAlreadyExistsException("Ce type produit existe déjà");
		else {
			this.types.add(typeProduit);
			System.out.println("Type de produit " + typeProduit.getIdType() + " ajouté");
			return typeProduit.getIdType();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param TypeProduit typeProduit : Le type produit à mettre à jour
	 * @return int : L'identifiant du type produit mis à jour
	 * 
	 * @throws ObjectAlreadyExistsException, ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int update(TypeProduit typeProduit) throws ObjectAlreadyExistsException, ObjectNotExistsException {
		int id = this.types.indexOf(typeProduit);
		
		if (id == -1)
			throw new ObjectNotExistsException("Aucun type produit ne correspond à l'identifiant " + typeProduit.getIdType());			
		else {
			this.types.set(id, typeProduit);
			return typeProduit.getIdType();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du type produit à supprimer
	 * 
	 * @throws ObjectNotExistsException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	// TODO ADD OBJECT CONSTRAINT EXCEPTION
	@Override public boolean delete(int id) throws ObjectNotExistsException {
		int idx = this.types.indexOf(this.getById(id));
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucun type produit ne correspond à l'identifiant " + id);			
		else {
			this.types.remove(idx);
			return true;
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du type produit à chercher
	 * @return TypeProduit : Le type produit associé à l'identifiant
	 * 
	 * @throws ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public TypeProduit getById(int id) throws ObjectNotExistsException {
		TypeProduit type = new TypeProduit();
		type.setIdType(id);
		int idx = this.types.indexOf(type);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucun type de produit ne correspond à l'identifiant " + id);			
		else {
			return this.types.get(idx);
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<typeProduit> : La liste de tous les types produit
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public ArrayList<TypeProduit> getAll() {
		return this.types;
	}

	@Override
	public ArrayList<TypeProduit> rechercherTypeOuTva(String recherche) {
		// TODO Auto-generated method stub
		return this.types;
	}
}