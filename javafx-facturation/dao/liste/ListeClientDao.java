/**
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 
 * @author MagicBanana
 * @class ListeClientDao
 * 
 * Implémente le ClientDao pour la gestion de listes mémoires
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 */

package dao.liste;

import java.util.ArrayList;

import dao.pojo.ClientDao;
import pojo.Client;

public class ListeClientDao implements ClientDao {

	private static ListeClientDao instance;
	private ArrayList<Client> clients;

	/**
	 * * * * * * * * * * * * * * * * * * *
	 * Constructeur 
	 * 
	 * Initialise une ArrayList de clients
	 * * * * * * * * * * * * * * * * * * *
	 */
	private ListeClientDao() {
		this.clients = new ArrayList<Client>();
	}
	
	/**
	 * * * * * * *
	 * Singleton 
	 * * * * * * *
	 */
	public static ListeClientDao getInstance() {
		if (instance == null) {
			instance = new ListeClientDao();
		}
		return instance;
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @int create (Client client)
	 * 
	 * Renvoie l'id du client si l'ajout est effectué
	 * Sinon soulève une IllegalArgumentException
	 * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int create(Client client) {
		if (this.clients.contains(client)) {
			throw new IllegalArgumentException("Ce client existe déjà");
		} else {
			this.clients.add(client);
			return client.getIdClient();
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @int update (Client client)
	 * 
	 * Renvoie l'id du client si la mise à jour est effectuée
	 * Sinon soulève une IllegalArgumentException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public int update(Client client) {
		int idx = this.clients.indexOf(client);
		
		if (idx == -1) {
			throw new IllegalArgumentException("Ce client n'existe pas");			
		} else {
			this.clients.set(idx, client);
			return client.getIdClient();
		}
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @void delete (int id)
	 * 
	 * Supprime le client avec l'id passé en paramètre
	 * Sinon soulève une IllegalArgumentException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public void delete(int id) {
		int idx = this.clients.indexOf(this.getById(id));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Ce client n'existe pas");			
		} else {
			this.clients.remove(idx);
		}
	}
	
	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @Client getById(int id)
	 * 
	 * Supprime le client avec l'id passé en paramètre
	 * Sinon soulève une IllegalArgumentException
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public Client getById(int id) {
		int idx = this.clients.indexOf(new Client(id, "random", "random"));
		
		if (idx == -1) {
			throw new IllegalArgumentException("Aucun client avec l'id " + id);			
		} else {
			return this.clients.get(idx);
		}
	}

	/**
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * @ArrayList<Client> getAll()
	 * 
	 * Renvoie la liste des clients
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	@Override public ArrayList<Client> getAll() {
		return this.clients;
	}
}