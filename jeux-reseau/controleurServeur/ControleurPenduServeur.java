package controleurServeur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import rmi.PenduInterface;
import vue.IVuePenduServeur;
import vueSwing.VuePenduServeur;

public class ControleurPenduServeur implements ActionListener {

	private IVuePenduServeur vue;
	private PenduInterface rmi;
	
	public ControleurPenduServeur(PenduInterface rmi) {
		this.rmi = rmi;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		try {
			rmi.saisieMot(vue.getMot());
		} catch (RemoteException e) {
			vue.afficheErreur(e.getMessage());
			System.exit(1);
		} catch (IllegalArgumentException e) {
			vue.afficheErreur(e.getMessage());
		}
		
	}

	public void setVue(VuePenduServeur vuePenduServeur) {
		vue = vuePenduServeur;
		
	}

}
