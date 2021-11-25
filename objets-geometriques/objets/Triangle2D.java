package Objets;

import java.awt.Graphics;

public class Triangle2D extends Forme_Geometrique2D
{
	private Point2D point2;
	private Point2D point3;
	
	// CONSTRUCTEURS
	public Triangle2D (int a, int b, int c, int d)
	{
		super (0,0);
		this.setPoint2 (new Point2D (a, b));
		this.setPoint3 (new Point2D(c,d));
	}
	
	public Triangle2D (int a, int b, int c, int d, int e, int f)
	{
		super (a,b);
		this.setPoint2 (new Point2D (c, d));
		this.setPoint3 (new Point2D (e,f));
	}

	// GETTERS ET SETTERS	
	public Point2D getPoint2()             { return point2; }
	public void setPoint2(Point2D point2)  { this.point2 = point2; }

	public Point2D getPoint3 ()            { return point3; }
	public void setPoint3 (Point2D point3) { this.point3 = point3; }
	
	// METHODES
	public String toString ()
	{
		return (super.toString() +"[" +point2.getX() +"," +point2.getY() +"] [" +point3.getX() +"," +point3.getY() +"]");
	}
	
	public void afficher_fenetre (Graphics g)
	{
		g.drawString("Triangle " +toString(), 0, 0);
	}
	
	public void afficher ()
	{
		System.out.println ("Triangle   : " +toString());
	}
	
	public void deplacer (int x, int y)
	{
		super.deplacer(x, y);
		point2.setX(point2.getX() + x); point2.setY(point2.getY()+y);
		point3.setX(point3.getX() + x); point3.setY(point3.getY()+y);
	}
	
	public void dessiner (Graphics g)
	{
		g.drawLine(super.getX(), super.getY(), point2.getX(), point2.getY());
		g.drawLine(point2.getX(), point2.getY(), point3.getX(),point3.getY());
		g.drawLine(point3.getX(), point3.getY(), super.getX(), super.getY());
	}
}
