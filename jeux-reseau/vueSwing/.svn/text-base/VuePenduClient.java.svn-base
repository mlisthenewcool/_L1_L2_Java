package vueSwing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vue.IVuePenduClient;
import controleurClient.ControleurPenduClient;

public class VuePenduClient extends JFrame implements IVuePenduClient {
	private JPanel jPNorth=new JPanel();
	private JPanel jPButton=new JPanel();
	private JPanel jPSouth=new JPanel();
	private JLabel jLtitre=new JLabel("En attente du mot...");
	private JLabel jLMot=new JLabel("Mot Cherché:");
	private JTextField jt=new JTextField(20);
	//private ArrayList<JButton> alb =new ArrayList<JButton>();
	private Hashtable<Character, JButton> tableLettres = new Hashtable<Character, JButton>();

	public VuePenduClient(ControleurPenduClient ctl) {
		this.setLocation(400,300);
		this.setSize(400, 250);
		this.setResizable(false);
		jPButton.setLayout(new FlowLayout());
		this.add(jPNorth,BorderLayout.NORTH);

		jPNorth.setLayout(new FlowLayout());
		jPNorth.add(jLtitre);
		this.add(jPButton,BorderLayout.CENTER);

		jPSouth.setLayout(new FlowLayout());
		this.add(jPSouth,BorderLayout.SOUTH);
		//jt.setEnabled(false);
		jt.setEditable(false);
		jPSouth.add(jLMot);
		jPSouth.add(jt);

		for (char c = 'A'; c <= 'Z'; c++) {
			JButton button = new JButton();
			button.setActionCommand(c + "");
			button.setText(c + "");
			button.addActionListener(ctl);
			//button.setEnabled(false);
			jPButton.add(button);
			//alb.add(button);
			tableLettres.put(c, button);
		}
		this.verrouille();
		this.setVisible(true);
		this.addWindowListener(ctl);
	}

	@Override
	public void afficheErreur(String erreur) {
		JOptionPane.showMessageDialog(this,  "Une erreur : " + erreur, "Erreur", JOptionPane.ERROR_MESSAGE);

	}

	@Override
	public void updateMot(String mot) {
		jt.setText(mot);
	}

	@Override
	public void gagne(int nbEssais) {
		JOptionPane.showMessageDialog(this,  "Vous avez gagné ! Nombre d'essais : " + nbEssais, "Félicitations", JOptionPane.INFORMATION_MESSAGE);

	}

	@Override
	public void verrouille() {
		jLtitre.setText("Pendu");
		for (JButton button : tableLettres.values()) {
			button.setEnabled(false);
		}
	}

	@Override
	public void lancePartie() {
		jLtitre.setText("Pendu");
		for (JButton button : tableLettres.values()) {
			button.setEnabled(true);
		}
	}

	@Override
	public void retireLettre(char lettre) {
		tableLettres.get(lettre).setEnabled(false);

	}




}
