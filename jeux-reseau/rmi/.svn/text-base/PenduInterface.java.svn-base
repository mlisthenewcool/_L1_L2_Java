package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PenduInterface extends Remote {
	
	public void attenteSaisieMot() throws RemoteException;
	public void saisieMot(String mot) throws RemoteException, IllegalArgumentException;
	public String joue(char lettre) throws RemoteException;
	public int getNbEssais() throws RemoteException;
	String getStatusMot() throws RemoteException;
	public void lanceVuePenduServeur() throws RemoteException;

}
