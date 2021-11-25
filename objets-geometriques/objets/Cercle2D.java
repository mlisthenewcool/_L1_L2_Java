package Objets;

import java.awt.Graphics;

public class Cercle2D extends Forme_Geometrique2D
{
	private int rayon;
	
	// CONSTRUCTEURS
	public Cercle2D (int a)
	{
		super(0,0);
		this.rayon = a;
	}	
	public Cercle2D (int a, int b, int c)
	{
		super(a,b);
		this.rayon = c;
	}
	
	// GETTERS ET SETTERS
	public int getRayon ()              { return rayon; }
	public void setRayon (int rayon)    { this.rayon = rayon; }
	
	// METHODES
	public String toString ()
	{
		return ("de centre " +super.toString() +"et de rayon " +getRayon());
	}
	
	public void afficher_fenetre (Graphics g)
	{
		g.drawString("Cercle " +toString(),0, 0);
	}
	
	
	public void afficher ()
	{
		System.out.println ("Cercle " +toString());
	}
	
	public void deplacer (int x, int y)
	{
		super.deplacer(x, y);
	}
	
	public void dessiner (Graphics g)
	{
		g.drawOval(super.getX(), super.getY(), getRayon(), getRayon());
	}
}
