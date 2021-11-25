package controleurClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import jeux.StatusGrilleTicTacToe;
import rmi.TicTacToeInterface;
import rmi.TicTacToeInterface.Case;
import vue.IVueTicTacToe;

public class ControleurTicTacToe implements ActionListener {
	private IVueTicTacToe vue;
	private TicTacToeInterface rmi;
	private Case joueur;


	public ControleurTicTacToe(TicTacToeInterface rmi, Case joueur) {
		this.rmi = rmi;
		this.joueur = joueur;
		
		if (joueur == Case.CLIENT) {
			try {
				rmi.lanceVueTicTacToeServeur();
			} catch (RemoteException e) {
				vue.afficheErreur(e.getMessage());
				System.exit(1);
			}
		}
		
	}
	
	public void main() {
		// gere la syncronisation entre les commandes du serveur et du client
		new Thread() {
			public void run() {
				try {
					while (true) {
						rmi.attenteAutreJoueur(joueur);
						StatusGrilleTicTacToe status = rmi.getStatusGrille(joueur);
						vue.updateGrille(status);
						if (status.getGagne() != Case.VIDE) {
							vue.perdu();
						}
					}
					
				} catch (RemoteException e) {
					vue.afficheErreur(e.getMessage());
					System.exit(1);
				}
				
			}
		}.start();
	}

	public void setVue(IVueTicTacToe vue) {
		this.vue = vue;
		try {
			vue.updateGrille(rmi.getStatusGrille(joueur));
		} catch (RemoteException e) {
			vue.afficheErreur(e.getMessage());
			System.exit(1);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String commande = ae.getActionCommand();

		try {
			if (commande.startsWith("b")) {
				int i = Integer.parseInt(commande.substring(1, 2)); // ligne
				int j = Integer.parseInt(commande.substring(2, 3)); // colone
				rmi.Joue(joueur, i, j);
				StatusGrilleTicTacToe status = rmi.getStatusGrille(joueur);
				vue.updateGrille(status);
				if (status.getGagne() != Case.VIDE) {
					vue.gagne();
				}
				
			}
		} catch (IllegalArgumentException e) {
			vue.afficheErreur(e.getMessage());
		} catch (RemoteException e) {
			vue.afficheErreur(e.getMessage());
			System.exit(1);
		}
	}

}
