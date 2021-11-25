package Objets;

import java.awt.Graphics;

public class Rectangle2D extends Forme_Geometrique2D
{
	private Point2D point_origine;
	private int longueur, hauteur;
	
	// CONSTRUCTEURS
	public Rectangle2D (int x, int y, int longueur,int hauteur)
	{
		super(x,y);
		this.longueur = longueur;
		this.hauteur = hauteur;
	}
	
	// GETTERS ET SETTERS
	public Point2D getPoint_origine() {	return point_origine;}
	public void setPoint_origine(Point2D point_origine) {this.point_origine = point_origine;}

	public int getLongueur() {return longueur;}
	public void setLongueur(int longueur) {this.longueur = longueur;}

	public int getHauteur() {return hauteur;}
	public void setHauteur(int hauteur) {this.hauteur = hauteur;}
	
	// METHODES
	public String toString ()
	{
		return ("d'origine " +super.toString() +"de longueur " +this.longueur +" et de hauteur " +this.hauteur );
	}
	
	public void afficher_fenetre (Graphics g)
	{
		g.drawString("Rectangle " +toString(),0, 0);
	}
	
	public void afficher ()
	{
		System.out.println ("Rectangle " +toString());
	}
	
	public void deplacer (int x, int y)
	{
		super.deplacer(x, y);
	}
	
	public void dessiner (Graphics g)
	{
		g.drawRect(super.getX(), super.getY(), this.longueur, this.hauteur);
	}
	
}
