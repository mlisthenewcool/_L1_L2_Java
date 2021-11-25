package Objets;

import java.awt.Graphics;
import java.util.Random;

public class Quadrangle2D extends Cercle2D
{
	private Point2D point1;
	private Point2D point2;
	private Point2D point3;
	private Point2D point4;
	
	static Random rand = new Random ();
	
	// CONSTRUCTEURS
	public Quadrangle2D (int a, int b, int rayon)
	{
		super (a, b, rayon);
		this.point1 = new Point2D (a+rayon*Math.cos(rand.nextDouble()*360), b+rayon*Math.sin(rand.nextDouble()*360));		
		this.point2 = new Point2D (a+rayon*Math.cos(rand.nextDouble()*360), b+rayon*Math.sin(rand.nextDouble()*360));
		this.point3 = new Point2D (a+rayon*Math.cos(rand.nextDouble()*360), b+rayon*Math.sin(rand.nextDouble()*360));
		this.point4 = new Point2D (a+rayon*Math.cos(rand.nextDouble()*360), b+rayon*Math.sin(rand.nextDouble()*360));		
	}	
	
	// GETTERS ET SETTERS

	// METHODES
	public String toString ()
	{
		return (point1.getX() +point1.getY() +"[" +Math.round(point2.getX()) +"," +Math.round(point2.getY()) +"] [" +Math.round(point3.getX()) +"," +Math.round(point3.getY()) +"] [" +Math.round(point4.getX()) +"," +Math.round(point4.getY()) +"] ");
	}
	
	public void afficher_fenetre (Graphics g)
	{
		g.drawString("Quadrangle " +toString(),0, 0);
	}
	
	public void afficher ()
	{
		System.out.println ("Quadrangle : " +toString());
	}
	
	public void dessiner (Graphics g)
	{
		g.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
	    g.drawLine(point2.getX(), point2.getY(), point3.getX(), point3.getY());
	    g.drawLine(point3.getX(), point3.getY(), point4.getX(), point4.getY());
	    g.drawLine(point4.getX(), point4.getY(), point1.getX(), point1.getY());
	}
	
	public void deplacer (int x, int y)
	{
		point1.deplacer(point1.getX()+x, point1.getY()+y);
		point2.deplacer(point2.getX()+x, point2.getY()+y);
		point3.deplacer(point3.getX()+x, point3.getY()+y);
		point4.deplacer(point4.getX()+x, point4.getY()+y);
	}

}