package controleurClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import rmi.PenduInterface;
import rmi.TicTacToeInterface;
import rmi.TicTacToeInterface.Case;
import vue.IVuePrincipaleClient;
import vueSwing.VuePenduClient;
import vueSwing.VueTicTacToe;

public class ControleurVuePrincipaleClient implements ActionListener {

	private final static int PORT = 1248;
	
	private IVuePrincipaleClient vue;
	
	public void setVue(IVuePrincipaleClient vue) {
		this.vue = vue;
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String s = ae.getActionCommand();
		
		if (s == "connecter") {
			try {
				switch (vue.getJeuChoisi()) {
				case TICTACTOE:
					TicTacToeInterface iTicTacToe = (TicTacToeInterface) Naming.lookup("rmi://" + vue.getIpSaisie() + ":" + PORT + "/tictactoe");
					ControleurTicTacToe ctrlTTT = new ControleurTicTacToe(iTicTacToe, TicTacToeInterface.Case.CLIENT);
					ctrlTTT.setVue(new VueTicTacToe(ctrlTTT, Case.CLIENT));
					ctrlTTT.main();
					break;
				case PENDU:
					PenduInterface iPendu = (PenduInterface) Naming.lookup("rmi://" + vue.getIpSaisie() + ":" + PORT + "/pendu");
					ControleurPenduClient ctrlPC = new ControleurPenduClient(iPendu, this);
					ctrlPC.setVue(new VuePenduClient(ctrlPC));
					ctrlPC.main();
					vue.fermer();
					break;
				}
			} catch (MalformedURLException e) {
				vue.afficheErreur("URL Invalide");
			} catch (RemoteException e) {
				vue.afficheErreur("Erreur de connexion");
			} catch (NotBoundException e) {
				vue.afficheErreur("Erreur de connexion, le serveur a peut être une version différente ?");
			}
			
		}
		
	}

	public void reouvre() {
		vue.reouvrir();
	}

}
