package jeux;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import rmi.TicTacToeInterface;
import vueSwing.VueTicTacToe;
import controleurClient.ControleurTicTacToe;

public class TicTacToeImpl extends UnicastRemoteObject implements TicTacToeInterface {
	private Case grille[][] = new Case[3][3];
	private Case tourActuel = Case.CLIENT;
	private Object verrou = new Object(); // verouille la partie tant que l'autre joueur n'a pas joué.
	
	
	public TicTacToeImpl() throws RemoteException {
		super();
		initGrille();
	}
	
	private void initGrille() {
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				grille[i][j] = Case.VIDE;
			}
		}
	}
	
	private Case verifSiGagne(Case joueur) {
		boolean gagne;
		// Horizontal
		for (int i = 0; i < DIM; i++) {
			gagne = true;
			for (int j = 0; j < DIM; j++) {
				if (grille[i][j] != joueur) {
					gagne = false; break;
				}
			}
			if (gagne) {
				return joueur;
			}
		}
		
		// Vertical
		for (int j = 0; j < DIM; j++) {
			gagne = true;
			for (int i = 0; i < DIM; i++) {
				if (grille[i][j] != joueur) {
					gagne = false; break;
				}
			}
			if (gagne) {
				return joueur;
			}
		}
		
		// Diagonal
		gagne = true;
		for (int i = 0; i < DIM; i++) {
			if (grille[i][i] != joueur) {
				gagne = false; break;
			}
		}
		if (gagne) {
			return joueur;
		}
		gagne = true;
		for (int i = 0; i < DIM; i++) {
			if (grille[i][DIM-1-i] != joueur) {
				gagne = false; break;
			}
		}
		if (gagne) {
			return joueur;
		}
		return Case.VIDE;
	}
	
	public void Joue(Case joueur, int i, int j) throws RemoteException, IllegalArgumentException {
		if (grille[i][j] != Case.VIDE) {
			throw new IllegalArgumentException("Cette case est déjà occupée !");
		}
		tourActuel = joueur;
		grille[i][j] = joueur;
		if (tourActuel == Case.CLIENT) {
			tourActuel = Case.SERVEUR;
		}
		else {
			tourActuel = Case.CLIENT;
		}
		synchronized(verrou) {
			verrou.notify();
		}
		
	}

	@Override
	public StatusGrilleTicTacToe getStatusGrille(Case joueur) throws RemoteException {
		
		StatusGrilleTicTacToe ret = new StatusGrilleTicTacToe(grille, verifSiGagne(joueur), tourActuel);
		
		if (ret.getGagne() != Case.VIDE) { // Un joueur a gagné, donc on réinitialise la grille.
			initGrille();
			if (joueur == Case.CLIENT) {
				tourActuel = Case.SERVEUR;
			}
			else {
				tourActuel = Case.CLIENT;
			}
		}
		
		return ret;
	}

	@Override
	public void attenteAutreJoueur(Case joueur) throws RemoteException {
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
	public void lanceVueTicTacToeServeur() throws RemoteException {
		ControleurTicTacToe ctrlTTT = new ControleurTicTacToe(this, Case.SERVEUR);
		ctrlTTT.setVue(new VueTicTacToe(ctrlTTT, Case.SERVEUR));
		ctrlTTT.main();
	}
	
}
