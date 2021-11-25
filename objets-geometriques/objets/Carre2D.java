package Objets;

import java.awt.Graphics;

public class Carre2D extends Forme_Geometrique2D
{
	private int cote;
	
	// CONSTRUCTEURS
	public Carre2D (int x, int y, int cote)
	{
		super(x,y);
		this.cote = cote;
	}
	
	// GETTERS ET SETTERS

	public int getCote() {return cote;}
	public void setCote(int cote) {this.cote = cote;}
	
	// METHODES
	public String toString ()
	{
		return ("d'origine " +super.toString() +"de côté de longueur " +this.cote);
	}
	
	public void afficher_fenetre (Graphics g)
	{
		g.drawString("Carre " +toString(),0, 0);
	}
	
	public void afficher ()
	{
		System.out.println ("Carre " +toString());
	}
	
	public void deplacer (int x, int y)
	{
		super.deplacer(x, y);
	}
	
	public void dessiner (Graphics g)
	{
		g.drawRect(super.getX(), super.getY(), this.cote, this.cote);
	}
}