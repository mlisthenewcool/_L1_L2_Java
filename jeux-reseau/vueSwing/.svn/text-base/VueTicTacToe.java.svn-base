package vueSwing;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import rmi.TicTacToeInterface.Case;
import jeux.StatusGrilleTicTacToe;
import vue.IVueTicTacToe;
import controleurClient.ControleurTicTacToe;

public class VueTicTacToe extends JFrame implements IVueTicTacToe {

	private JPanel jPanelTop = new JPanel();
	private JPanel jPanelLeft = new JPanel();
	private JPanel jPanelRight = new JPanel();
	private JLabel jLabel=new JLabel("Tic Tac Toe");
	private JButton[][] jb=new JButton[3][3];
	private Case joueur;
	
	public VueTicTacToe(ControleurTicTacToe c, Case joueur){
		this.joueur = joueur;
		this.setLocation(400,300);
		this.setSize(300, 300);
		this.setResizable(false);
		this.add(jPanelLeft,BorderLayout.CENTER);
		this.add(jPanelRight,BorderLayout.EAST);
		this.add(jPanelTop,BorderLayout.NORTH);
		jPanelTop.add(jLabel);
		jPanelLeft.setLayout(new GridLayout(3,3,3,3));
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				jb[i][j]=new JButton();
				jb[i][j].setActionCommand("b"+i+j);
				jPanelLeft.add(jb[i][j]);
				jb[i][j].addActionListener(c);
			}
			
		}
	    this.setVisible(true);
	}
	
	@Override
	public void updateGrille(StatusGrilleTicTacToe statusGrille) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				switch (statusGrille.getGrille()[i][j]) {
				case VIDE:
					jb[i][j].setEnabled(true);
					jb[i][j].setDisabledIcon(null);
					jb[i][j].setIcon(null);
					break;
				case CLIENT:
					jb[i][j].setEnabled(false);
					jb[i][j].setDisabledIcon(new ImageIcon(getClass().getResource("/image/tic-tac-toe-O.png")));
					jb[i][j].setIcon(new ImageIcon(getClass().getResource("/image/tic-tac-toe-O.png")));
					break;
				case SERVEUR:
					jb[i][j].setEnabled(false);
					jb[i][j].setDisabledIcon(new ImageIcon(getClass().getResource("/image/tic-tac-toe-X.png")));
					jb[i][j].setIcon(new ImageIcon(getClass().getResource("/image/tic-tac-toe-X.png")));
					break;
				}
			}
		}
		if (statusGrille.getTourActuel() != joueur) {
			verouille();
		}
	}
	
	@Override
	public void verouille() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				jb[i][j].setEnabled(false);
			}
		}
	}
	
	
	@Override
	public void afficheErreur(String erreur) {
		JOptionPane.showMessageDialog(this,  "Une erreur réseau est survenue : " + erreur, "Erreur", JOptionPane.ERROR_MESSAGE);
	}


	@Override
	public void gagne() {
		JOptionPane.showMessageDialog(this,  "Vous avez gagné !", "Félicitations !", JOptionPane.INFORMATION_MESSAGE);
	}


	@Override
	public void perdu() {
		JOptionPane.showMessageDialog(this,  "Vous avez perdu !", "Défaite", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	@Override
	public void egalite() {
		JOptionPane.showMessageDialog(this,  "C'est un match nul", "Egalité", JOptionPane.INFORMATION_MESSAGE);
		
	}

}
