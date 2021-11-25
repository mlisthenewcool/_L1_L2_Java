package jeux;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.Normalizer;
import java.util.Random;
import java.util.regex.Pattern;

import rmi.PenduInterface;
import vueSwing.VuePenduServeur;
import controleurServeur.ControleurPenduServeur;

public class PenduImpl extends UnicastRemoteObject implements PenduInterface {
	private String motCherche;
	private String statusMot;
	private int nbEssais = 0;
	
	private Object verrou = new Object(); // verouille la partie tant que le serveur n'a pas choisi le mot.
	
	public PenduImpl() throws RemoteException {
		super();
	}

	@Override
	public void attenteSaisieMot() throws RemoteException {
		try {
			synchronized(verrou) {
				verrou.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void saisieMot(String mot) throws RemoteException, IllegalArgumentException {
		motCherche = nettoye(mot);
		if (motCherche.isEmpty()) {
			throw new IllegalArgumentException("Mot vide ou invalide");
		}
		
		
		char temp[] = new char[motCherche.length()];
		
		// On revele une lettre au hasard du mot
		Random rand = new Random();
		int index = rand.nextInt(motCherche.length());
		temp[index] = motCherche.charAt(index);
		
		// On rempli le reste avec "_"
		statusMot = new String(temp).replace("\0", "_");
		
		nbEssais = 0;
		synchronized(verrou) {
			verrou.notify();
		}
	}

	@Override
	public String joue(char lettre) throws RemoteException {
		char temp[] = statusMot.toCharArray();
		int index = motCherche.indexOf(lettre);
		
		while (index >= 0) {
			temp[index] = lettre;
		    index = motCherche.indexOf(lettre, index + 1);
		}
		
		statusMot = new String(temp);
		nbEssais++;
		return statusMot;
	}
	
	private String nettoye(String in) {
		// retire ou convertit tous les caractères qui ne sont pas [A-Z]
		String temp = Normalizer.normalize(in, Normalizer.Form.NFD);
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").trim().toUpperCase().replaceAll("[^A-Z]", "");
	}

	@Override
	public int getNbEssais() throws RemoteException {
		return nbEssais;
	}
	
	@Override
	public String getStatusMot() throws RemoteException {
		return statusMot;
	}

	@Override
	public void lanceVuePenduServeur() throws RemoteException {
		ControleurPenduServeur ctrlPS = new ControleurPenduServeur(this);
		ctrlPS.setVue(new VuePenduServeur(ctrlPS));
		
	}
	
	
}
