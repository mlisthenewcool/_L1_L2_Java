package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import rmi.TicTacToeInterface.Case;
import jeux.StatusGrilleTicTacToe;

public interface TicTacToeInterface extends Remote {
	public static final int DIM = 3;
	public enum Case {
		VIDE,
		CLIENT,
		SERVEUR
	}
	
	public void Joue(Case joueur, int i, int j) throws RemoteException, IllegalArgumentException;
	public void attenteAutreJoueur(Case joueur) throws RemoteException;
	StatusGrilleTicTacToe getStatusGrille(Case joueur) throws RemoteException;
	void lanceVueTicTacToeServeur() throws RemoteException;
}
