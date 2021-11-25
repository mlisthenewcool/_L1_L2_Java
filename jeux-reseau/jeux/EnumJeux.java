package jeux;

public enum EnumJeux {
	TICTACTOE("Tic Tac Toe"),
	//TAQUIN("Taquin"),
	PENDU("Pendu");
	
	
	
	private final String nom;
	private EnumJeux(String n) {
		nom = n;
	}
	
	@Override
	public String toString() {
		return nom;
	  }
}
