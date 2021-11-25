package vueSwing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controleurServeur.ControleurPenduServeur;
import vue.IVuePenduServeur;

public class VuePenduServeur extends JFrame implements IVuePenduServeur {

	private JPanel jP=new JPanel();
	private JLabel jLMot=new JLabel("Mot à saisir:");
	private JTextField jt=new JTextField(20);
	private  Button ok =new Button("OK");
	private ControleurPenduServeur ctl;
	
	public VuePenduServeur(ControleurPenduServeur ctl ) {
		 this.ctl=ctl;
		 this.setLocation(400, 300);
		 this.setSize(300, 100);
		 jP.setLayout(new FlowLayout());
		 this.add(jP,BorderLayout.CENTER);
		 jP.add(jLMot);
		 jP.add(jt);
		 jP.add(ok);
		 ok.addActionListener(ctl);
		 this.setVisible(true);
		 
	}

	@Override
	public void afficheErreur(String erreur) {
		JOptionPane.showMessageDialog(this,  "Une erreur : " + erreur, "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}

	@Override
	public String getMot() {
		// TODO Auto-generated method stub
		return jt.getText().trim();
	}

}
