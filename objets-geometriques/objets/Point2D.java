package Objets;

import java.awt.Graphics;

public class Point2D
{
	private int x,y;
	private double x1, y1;
	
	// CONSTRUCTEURS
	public Point2D ()             { this.x=0; this.y=0; }
	public Point2D (int x)        { this.x=x; this.y=0; }
	public Point2D (int x, int y) { this.x=x; this.y=y; }
	public Point2D (double x1, double y1) { this.x1=x1; this.y1=y1;}
	
	// GETTERS ET SETTERS
	public int getX ()       { return x; }
	public void setX (int x) { this.x = x; }
	
	public int getY ()       { return y; }
	public void setY (int y) { this.y = y; }
	
	// METHODES
	public String toString ()
	{
		return ("[" +this.x +"," +this.y +"] ");
	}
	
	public void afficher_fenetre (Graphics g)
	{
		g.drawString("Point " +toString(),0, 0);
	}
	
	public void afficher ()
	{
		System.out.println ("Point " +toString());
	}
	
	public void dessiner (Graphics g)
	{	
		// Methode abstraite
	}
	
	public void deplacer (int x, int y)
	{
		setX(getX() + x); setY(getY() + y);
	}
}
