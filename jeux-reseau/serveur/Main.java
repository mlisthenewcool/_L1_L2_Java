package serveur;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import javax.swing.JOptionPane;

import jeux.PenduImpl;
import jeux.TicTacToeImpl;

public class Main {

	public static void main(String[] args) {
		int port = 1248;
		String url = "rmi://localhost:" + port + "/";
		
		try {
			LocateRegistry.createRegistry(port);
			
			Naming.rebind(url + "tictactoe", new TicTacToeImpl());
			Naming.rebind(url + "pendu", new PenduImpl());
			
			
			
		} catch (RemoteException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Une erreur réseau est survenue", JOptionPane.ERROR_MESSAGE);
		} catch (MalformedURLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "URL invalide", JOptionPane.ERROR_MESSAGE);
		}
	}

}
