package controleurClient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

import rmi.PenduInterface;
import vue.IVuePenduClient;

public class ControleurPenduClient extends WindowAdapter implements ActionListener {

	private IVuePenduClient vue;
	private PenduInterface rmi;
	private ControleurVuePrincipaleClient ctrlVPC;

	public ControleurPenduClient(PenduInterface rmi, ControleurVuePrincipaleClient ctrlVPC) {
		this.rmi = rmi;
		this.ctrlVPC = ctrlVPC;
	}

	public void setVue(IVuePenduClient vue) {
		this.vue = vue;
	}

	public void main() {
		// gere la syncronisation entre les commandes du serveur et du client
		new Thread() {
			public void run() {
				try {
					rmi.lanceVuePenduServeur();
					while (true) {
						rmi.attenteSaisieMot();
						vue.updateMot(rmi.getStatusMot());
						vue.lancePartie();
					}
					
				} catch (RemoteException e) {
					vue.afficheErreur(e.getMessage());
					System.exit(1);
				}
				
			}
		}.start();


	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String commande = ae.getActionCommand();

		try {
			if (commande.length() == 1) { // Une lettre a été choisie
				char lettre = commande.charAt(0);
				String motObtenu = rmi.joue(lettre);
				vue.retireLettre(lettre);
				vue.updateMot(motObtenu);
				if (! motObtenu.contains("_")) { // pas de _ restant
					vue.gagne(rmi.getNbEssais());
					vue.verrouille();
				}
			}
		} catch (RemoteException e) {
			vue.afficheErreur(e.getMessage());
			System.exit(1);
		}


	}


	@Override
	public void windowClosing(WindowEvent we) {
		ctrlVPC.reouvre();
	}

	

}
