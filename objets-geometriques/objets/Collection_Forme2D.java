package Objets;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

public class Collection_Forme2D extends JPanel
{
	private ArrayList <Forme_Geometrique2D> collection;
	
	// CONSTRUCTEUR
	public Collection_Forme2D ()
	{
		collection = new ArrayList <Forme_Geometrique2D>();
	}
	
	// METHODES
	public void ajouter (Forme_Geometrique2D f) 
	{
		collection.add(f);
	}
	
	public void supprimer (Forme_Geometrique2D f)
	{
		collection.remove(f);
	}
	
	public void supprimer_tout ()
	{
		collection.clear();
	}
	
	public void afficher ()
	{
		Iterator <Forme_Geometrique2D> it = collection.iterator();
		while (it.hasNext())
		{
			(it.next()).afficher();
		}
	}
	
	public void afficher_fenetre (Graphics g)
	{
		Iterator <Forme_Geometrique2D> it = collection.iterator();
		while (it.hasNext())
		{
			afficher_fenetre(g);
		}
		g.drawString("BONJOUR MIREILLE ", 300, 300);
	}
	
	public void deplacer (int x, int y)
	{
		Iterator <Forme_Geometrique2D> it = collection.iterator();
		while (it.hasNext())
		{
			(it.next()).deplacer(x, y);
		}
	}
	
	public void dessiner (Graphics g)
	{
		Iterator <Forme_Geometrique2D> it = collection.iterator();
		while (it.hasNext())
		{
			(it.next()).dessiner(g);
		}
	}
}	
