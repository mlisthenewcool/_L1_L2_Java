/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * @author MagicBanana
 * 
 * Effectue la gestion de liste mémoire pour les clients
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.liste;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import dao.pojo.ClientDao;
import exceptions.ObjectAlreadyExistsException;
import exceptions.ObjectNotExistsException;
import pojo.Client;

public class ListeClientDao implements ClientDao {

	private ArrayList<Client> clients;
	private static final AtomicInteger compteur = new AtomicInteger (0);
	
	private static ListeClientDao instance;
	
	private ListeClientDao() {
		this.clients = new ArrayList<Client>();
	}
	
	public static ListeClientDao getInstance() {
		if (instance == null)
			instance = new ListeClientDao();
		
		return instance;
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Client client : Le client à créer
	 * @return int : L'identifiant du client créé
	 * 
	 * @throws ObjectAlreadyExistsException
	 * @see pojo.Client
	 * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int create(Client client) throws ObjectAlreadyExistsException {
		client.setIdClient(compteur.incrementAndGet());
		
		if (this.clients.contains(client))
			throw new ObjectAlreadyExistsException("Ce client existe déjà");
		else {
			this.clients.add(client);
			System.out.println("Client " + client.getIdClient()+ " ajouté");
			return client.getIdClient();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param Client client : Le client à mettre à jour
	 * @return int : L'identifiant du client mis à jour
	 * 
	 * @throws ObjectAlreadyExistsException, ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public int update(Client client) throws ObjectNotExistsException, ObjectAlreadyExistsException {
		int idx = this.clients.indexOf(client);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucun client ne correspond à l'identifiant " + client.getIdClient());			
		else {
			//if (this.clients.contains(client))
				//throw new ObjectAlreadyExistsException("Ce client existe déjà");
			
			this.clients.set(idx, client);
			return client.getIdClient();
		}
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du client à supprimer
	 * 
	 * @throws ObjectNotExistsException, ObjectConstraintException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	// TODO ADD OBJECT CONSTRAINT EXCEPTION
	@Override
	public boolean delete(int id) throws ObjectNotExistsException {
		int idx = this.clients.indexOf(this.getById(id));
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucun client ne correspond à l'identifiant " + id);		
		else {
			this.clients.remove(idx);
			return true;
		}
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @param int id : L'identifiant du client à chercher
	 * @return Client : Le client associé à l'identifiant
	 * 
	 * @throws ObjectNotExistsException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public Client getById(int id) throws ObjectNotExistsException {
		Client client = new Client();
		client.setIdClient(id);
		int idx = this.clients.indexOf(client);
		
		if (idx == -1)
			throw new ObjectNotExistsException("Aucun client ne correspond à l'identifiant " + id);			
		else {
			return this.clients.get(idx);
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Client> : La liste de tous les clients
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<Client> getAll() {
		return this.clients;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @return ArrayList<Client> : La liste de tous les clients correspondant à la recherche
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override
	public ArrayList<Client> rechercherNomOuPrenom(String recherche) {
		// TODO Auto-generated method stub
		return this.clients;
	}
}