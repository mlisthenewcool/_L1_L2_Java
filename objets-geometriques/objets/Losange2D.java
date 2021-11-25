package Objets;

import java.awt.Graphics;

public class Losange2D extends Forme_Geometrique2D
{
	private Point2D point2;
	
	// CONSTRUCTEURS
	public Losange2D(int a, int b, int c, int d)
	{
		super(a,b);
		this.point2 = new Point2D (c,d);
	}
	
	// GETTERS ET SETTERS
	public Point2D getPoint2() { return point2;}
	public void setPoint2(Point2D point2) {this.point2 = point2;}
	
	// METHODES
	public String toString ()
	{
		return (super.toString() +"[" +point2.getX() +"," +point2.getY() +"]");
	}
	
	public void afficher_fenetre (Graphics g)
	{
		g.drawString("Losange " +toString(),0, 0);
	}
	
	
	public void afficher ()
	{
		System.out.println ("Losange " +toString());
	}
	
	public void dessiner (Graphics g)
	{
	    int x2 = (super.getX()+point2.getX())/2;
	    int y2 = (super.getY()+point2.getY())/2;
	    g.drawLine(super.getX(), y2, x2,super.getY());
	    g.drawLine(x2, super.getY(), point2.getX(), y2);
	    g.drawLine(point2.getX(), y2, x2, point2.getY());
	    g.drawLine(x2, point2.getY(), super.getX(), y2);
	}
	
	public void deplacer (int x, int y)
	{
		super.deplacer(x,y);
		point2.deplacer(x, y);
	}
}