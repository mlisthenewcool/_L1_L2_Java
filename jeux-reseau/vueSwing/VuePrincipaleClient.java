package vueSwing;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import jeux.EnumJeux;
import vue.IVuePrincipaleClient;
import controleurClient.ControleurVuePrincipaleClient;

public class VuePrincipaleClient extends JFrame implements IVuePrincipaleClient {
	
	 
	
	private JPanel jPanelNord = new JPanel();
	private JPanel jPanelSud = new JPanel();
	private  JLabel ipLabel =new JLabel("adresse ip de serveur:");
	private  JTextField  ipTextField=new JTextField(30);
	private  Button connecter =new Button("connecter");
	JComboBox<EnumJeux> jBox=new JComboBox<EnumJeux>(EnumJeux.values());
	
    private ControleurVuePrincipaleClient cvpc;
	
	public VuePrincipaleClient( ControleurVuePrincipaleClient cvpc) {
		super();
		
		this.cvpc=cvpc;
		this.setLocation(400,300);
		
		jPanelNord.setLayout(new FlowLayout());
		jPanelNord.add(ipLabel);
		jPanelNord.add(ipTextField);
		jPanelNord.add(connecter);
		connecter.setActionCommand("connecter");
		this.add(jPanelNord,BorderLayout.NORTH);
		
		ipTextField.setText("localhost");
		
		
		jPanelSud.setLayout(new FlowLayout());
		jBox.setActionCommand("jBox");
		jPanelSud.add(jBox);
		this.add(jPanelSud,BorderLayout.SOUTH);
		
		this.pack();
		this.setVisible(true);
		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		connecter.addActionListener(cvpc);
		
	
	}
	
	
	@Override
	public String getIpSaisie() {
		return ipTextField.getText().trim();
	}

	@Override
	public EnumJeux getJeuChoisi() {
		return (EnumJeux) jBox.getSelectedItem();
	}


	@Override
	public void afficheErreur(String e) {
		JOptionPane.showMessageDialog(this,  "Une erreur : " + e, "Erreur", JOptionPane.ERROR_MESSAGE);
		
	}


	@Override
	public void fermer() {
		 this.setVisible(false);
	}


	@Override
	public void reouvrir() {
		this.setVisible(true);
	}
	

	
	
}
