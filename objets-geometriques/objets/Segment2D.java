package Objets;

import java.awt.Graphics;

public class Segment2D extends Forme_Geometrique2D
{
	private Point2D point_extremite;
	
	// CONSTRUCTEURS
	public Segment2D (int a)
	{		
		super(0,0);
		this.point_extremite = new Point2D (a,0);
	}
	public Segment2D (int a, int b)
	{
		super(0,0);
		this.point_extremite = new Point2D (a,b);
	}
	public Segment2D (int a, int b, int c, int d)
	{
		super(a,b);
		this.point_extremite = new Point2D (c,d);
	}
	
	// GETTERS ET SETTERS
	public Point2D getPoint_extremite ()                     { return point_extremite; }
	public void setPoint_extremite (Point2D point_extremite) { this.point_extremite = point_extremite; }
	
	// METHODES
	public String toString ()
	{
		return (super.toString() +"[" +point_extremite.getX() +"," +point_extremite.getY() +"] ");
	}
	
	public void afficher_fenetre (Graphics g)
	{
		g.drawString("Triangle " +toString(),0, 0);
	}
	
	public void afficher ()
	{
		System.out.println ("Segment " +toString());
	}
	
	public void deplacer (int x, int y)
	{
		super.deplacer(x, y);
		point_extremite.setX(point_extremite.getX() + x); point_extremite.setY(point_extremite.getY()+y);
	}
	
	public void dessiner (Graphics g)
	{
		g.drawLine(super.getX(), super.getY(), point_extremite.getX(), point_extremite.getY());
	}
}
