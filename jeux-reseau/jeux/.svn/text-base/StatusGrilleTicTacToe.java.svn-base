package jeux;

import java.io.Serializable;

import rmi.TicTacToeInterface.Case;

public class StatusGrilleTicTacToe implements Serializable {

	private Case[][] grille;
	private Case gagne; // qui a gagné ?
	private Case tourActuel; // qui est en train de jouer ?
	
	public StatusGrilleTicTacToe(Case[][] grille, Case gagne, Case tourActuel) {
		this.grille = grille;
		this.gagne = gagne;
		this.tourActuel = tourActuel;
	}

	public Case[][] getGrille() {
		return grille;
	}

	public Case getGagne() {
		return gagne;
	}
	
	public Case getTourActuel() {
		return tourActuel;
	}
	
	

}
