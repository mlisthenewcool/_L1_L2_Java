 package Dessin_technique;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Objets.Arc_de_Cercle2D;
import Objets.Carre2D;
import Objets.Cercle2D;
import Objets.Collection_Forme2D;
import Objets.Ellipse2D;
import Objets.Forme_Geometrique2D;
import Objets.Losange2D;
import Objets.Multisegment2D;
import Objets.Quadrangle2D;
import Objets.Rectangle2D;
import Objets.Segment2D;
import Objets.Triangle2D;

public class Fenetre extends JFrame implements ActionListener, MouseListener
{
	// On declare une collection initiale
	Collection_Forme2D col = new Collection_Forme2D ();	
	// Variables correspondant aux positions de la fenetre de dessin pour tracer des figures
	private int currentX, currentY, oldX, oldY, compteur;
	private boolean seg, rect, multiseg, deplacer, carre_bool, cer, ell, tri;

	// PANELS
		// GENERAUX
	JPanel panel_menu_haut = new JPanel();
	JPanel panel_dessin = new JPanel();
	JPanel panel_menu_bas = new JPanel();
		// Du panel_menu_haut
	JPanel panel_figures = new JPanel();
	JPanel panel_saisie = new JPanel();
	JPanel panel_deplacer = new JPanel();
		// Du panel_menu_bas
	JPanel panel_composition = new JPanel();
	JPanel panel_dessiner_reenit = new JPanel();
	JPanel panel_aide = new JPanel();
	
	// BUTTONS
	JButton segment = new JButton ("Segment2D");
	JButton multisegment = new JButton ("Multisegment2D");
	JButton triangle = new JButton ("Triangle2D");
	JButton losange = new JButton ("Losange2D");
	JButton rectangle = new JButton ("Rectangle2D");
	JButton carre = new JButton ("Carre2D");
	JButton quadrangle = new JButton ("Quadrangle2D");
	JButton cercle = new JButton ("Cercle2D");
	JButton arc_de_cercle = new JButton ("Arc de cercle2D");
	JButton ellipse = new JButton ("Ellipse2D");
	
	JButton tracer_seg = new JButton ("Tracer un segment");
	JButton tracer_multiseg = new JButton ("Tracer un multisegment");
	JButton tracer_tri = new JButton ("Tracer un triangle");
	JButton tracer_los = new JButton ("Tracer un losange");
	JButton tracer_rect = new JButton ("Tracer un rectangle");
	JButton tracer_carre = new JButton ("Tracer un carre");
	JButton tracer_quad = new JButton ("Tracer un quadrangle");
	JButton tracer_cercle = new JButton ("Tracer un cercle");
	JButton tracer_arc = new JButton ("Tracer un arc de cercle");
	JButton tracer_ellipse = new JButton ("Tracer une ellispe");
	
	JButton ajouter_seg = new JButton ("Ajouter un segment");
	JButton ajouter_multiseg = new JButton ("Ajouter un multisegment");
	JButton ajouter_tri = new JButton ("Ajouter un triangle");
	JButton ajouter_los = new JButton ("Ajouter un losange");
	JButton ajouter_rect = new JButton ("Ajouter un rectangle");
	JButton ajouter_carre = new JButton ("Ajouter un carre");
	JButton ajouter_quad = new JButton ("Ajouter un quadrangle");
	JButton ajouter_cercle = new JButton ("Ajouter un cercle");
	JButton ajouter_arc = new JButton ("Ajouter un arc de cercle");
	JButton ajouter_ellipse = new JButton ("Ajouter une ellipse");
	
	JButton dessiner = new JButton ("Dessiner ");
	
	JButton composition = new JButton ("Composition ");
	JButton deplacer_tout = new JButton ("Deplacer ");
	JButton deplacer_souris = new JButton ("Deplacer a la souris");
	JButton deplacer_tout_effectuer= new JButton ("Effectuer le deplacement");
	
	JButton reenit = new JButton ("Reinitialiser");
	JButton aide = new JButton ("Aide");

	// ZONES DE SAISIE
	JTextField z1 = new JTextField (4);	JTextField z2 = new JTextField (4);
	JTextField z3 = new JTextField (4);	JTextField z4 = new JTextField (4);
	JTextField z5 = new JTextField (4);	JTextField z6 = new JTextField (4);
	JTextField z7 = new JTextField (4);	JTextField z8 = new JTextField (4);
	JTextField z9 = new JTextField (4);	JTextField z10 = new JTextField (4);
	JTextField z11 = new JTextField (4); JTextField z12 = new JTextField (4);
	JTextField z13 = new JTextField (4); JTextArea z_aide = new JTextArea (30,30);
	JTextField z_angle_deb = new JTextField (4); JTextField z_angle_fin = new JTextField (4);
	JTextField z_deplacement_bas = new JTextField (4); JTextField z_deplacement_haut = new JTextField (4);
	JTextField z_deplacement_gauche = new JTextField (4); JTextField z_deplacement_droite = new JTextField (4);
	JTextField z_nbre_segments = new JTextField(4);

	// COMBO BOX
	JComboBox liste = new JComboBox();
	
	// LABELS
	JLabel label_p1 = new JLabel ("Point 1");
	JLabel label_p2 = new JLabel ("Point 2");
	JLabel label_p3 = new JLabel ("Point 3");
	JLabel label_p4 = new JLabel ("Point 4");
	
	JLabel label_centre = new JLabel("Centre :");
	JLabel label_rayon = new JLabel("Rayon :");
	
	JLabel label_angle_deb = new JLabel ("Angle debut :");
	JLabel label_angle_fin = new JLabel ("Angle fin :");
	
	JLabel label_hauteur = new JLabel ("Hauteur :");
	JLabel label_longueur = new JLabel ("Longueur :");
	
	JLabel label_deplacement_bas = new JLabel ("De combien souhaitez-vous deplacer vers le bas");
	JLabel label_deplacement_haut = new JLabel ("vers le haut");
	JLabel label_deplacement_droite = new JLabel ("vers la droite");
	JLabel label_deplacement_gauche = new JLabel ("vers la gauche");
	
	JLabel label_nbre_segments = new JLabel("Nombre de segments");
	
	public void invisible ()
	{	// ON REND INVISIBLE LES ELEMENTS POUR RAFRAICHIR LA FENETRE A CHAQUE ACTION
		// LES ZONES DE SAISIE
		z1.setVisible(false); z2.setVisible(false);	z3.setVisible(false); z4.setVisible(false);
		z5.setVisible(false); z6.setVisible(false);	z7.setVisible(false); z8.setVisible(false);	
		z9.setVisible(false); z10.setVisible(false); z11.setVisible(false); z12.setVisible(false); 
		z13.setVisible(false); z_aide.setVisible(false);
		z_deplacement_bas.setVisible(false); z_deplacement_haut.setVisible(false);
		z_deplacement_gauche.setVisible(false); z_deplacement_droite.setVisible(false);
		z_nbre_segments.setVisible(false);
		z_angle_deb.setVisible(false); z_angle_fin.setVisible(false);
		// LES LABELS
		label_p1.setVisible(false);	label_p2.setVisible(false);
		label_p3.setVisible(false);	label_p4.setVisible(false);
		label_centre.setVisible(false); label_rayon.setVisible(false);
		label_hauteur.setVisible(false); label_longueur.setVisible(false);
		label_deplacement_bas.setVisible(false); label_deplacement_haut.setVisible(false);
		label_deplacement_droite.setVisible(false);	label_deplacement_gauche.setVisible(false);
		label_nbre_segments.setVisible(false);
		label_angle_deb.setVisible(false); label_angle_fin.setVisible(false);
		// LES BOUTONS
		ajouter_seg.setVisible(false);ajouter_multiseg.setVisible(false);
		ajouter_tri.setVisible(false);ajouter_los.setVisible(false);
		ajouter_rect.setVisible(false);ajouter_carre.setVisible(false);
		ajouter_quad.setVisible(false);ajouter_cercle.setVisible(false);
		ajouter_ellipse.setVisible(false); ajouter_arc.setVisible(false);
		
		tracer_seg.setVisible(false); tracer_multiseg.setVisible(false);
		tracer_tri.setVisible(false); tracer_los.setVisible(false);
		tracer_rect.setVisible(false); tracer_carre.setVisible(false);
		tracer_quad.setVisible(false); tracer_cercle.setVisible(false);
		tracer_ellipse.setVisible(false); tracer_arc.setVisible(false);
		 
		deplacer_tout_effectuer.setVisible(false);
		
		// ON LAISSE VISIBLE TOUT LE TEMPS LES DEPLACEMENTS
		deplacer_tout.setVisible(true); deplacer_souris.setVisible(true);
	}
	
	public void zone_edit ()
	{	// INITIALISATION DES ZONES DE TEXTE
		z1.setText("0"); z2.setText("0");z3.setText("0");z4.setText("0");
		z5.setText("0"); z6.setText("0");z7.setText("0");z8.setText("0");
		z9.setText("100"); z10.setText("100");z11.setText("100");z12.setText("0");z13.setText("0");
		z_deplacement_bas.setText("0");z_deplacement_haut.setText("0");
		z_deplacement_droite.setText("0");z_deplacement_gauche.setText("0");
		z_nbre_segments.setText("2"); z_angle_deb.setText("0"); z_angle_fin.setText("0");
	}
	
	public void rafraichir_CB ()
	{
		liste.removeAllItems();
		//Iterator <Forme_Geometrique2D> it = col.iterator();
		//while (it.hasNext())
		{
		//	liste.addItem(col);
		}
	}
	
	public void bloquer_tracer ()
	{
		seg=false; rect=false; multiseg=false; deplacer=false; ell=false; cer=false; carre_bool=false;tri=false;
	}
	
	public Fenetre ()
	{
		this.setTitle("Logiciel de geometrie 2D");
		this.setSize(1200,800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		this.setVisible(true);
		panel_dessin.setBackground(Color.white);
		
		// LAYOUT DE LA FENETRE
		this.setLayout(new BorderLayout ());
		this.add("North", panel_menu_haut); this.add(panel_dessin); this.add("South", panel_menu_bas);
		// LAYOUT panel_menu_haut
		panel_menu_haut.setLayout(new GridLayout(3,1));
		panel_menu_haut.add(panel_figures);panel_menu_haut.add(panel_saisie); panel_menu_haut.add(panel_deplacer);
		// LAYOUT panel_menu_bas
		panel_menu_bas.setLayout (new GridLayout (1,3));
		panel_menu_bas.add(panel_composition); panel_menu_bas.add(panel_dessiner_reenit); panel_menu_bas.add(panel_aide);
		
		// AJOUT DES ELEMENTS AUX PANELS
		// PANEL_FIGURES
		panel_figures.add(segment); panel_figures.add(multisegment);
		panel_figures.add(triangle); panel_figures.add(losange);
		panel_figures.add(rectangle); panel_figures.add(carre);
		panel_figures.add(quadrangle); panel_figures.add(cercle); 
		panel_figures.add(arc_de_cercle); panel_figures.add(ellipse);
		// PANEL_SAISIE
			// BOUTONS_TRACER
		panel_saisie.add(tracer_seg); panel_saisie.add(tracer_multiseg);
		panel_saisie.add(tracer_tri); panel_saisie.add(tracer_los);
		panel_saisie.add(tracer_rect); panel_saisie.add(tracer_carre);
		panel_saisie.add(tracer_quad); panel_saisie.add(tracer_cercle);
		panel_saisie.add(tracer_ellipse);
			// LABELS ET ZONES EDIT
		panel_saisie.add(label_p1); panel_saisie.add(z1); panel_saisie.add(z2);
		panel_saisie.add(label_p2);	panel_saisie.add(z3); panel_saisie.add(z4);
		panel_saisie.add(label_p3);	panel_saisie.add(z5); panel_saisie.add(z6);
		panel_saisie.add(label_p4);	panel_saisie.add(z7); panel_saisie.add(z8);
		panel_saisie.add(label_centre); panel_saisie.add(z9); panel_saisie.add(z10);
		panel_saisie.add(label_rayon);	panel_saisie.add(z11);
		panel_saisie.add(label_longueur); panel_saisie.add(z12);
		panel_saisie.add(label_hauteur); panel_saisie.add(z13);
		panel_saisie.add(label_deplacement_bas); panel_saisie.add(z_deplacement_bas);
		panel_saisie.add(label_deplacement_haut); panel_saisie.add(z_deplacement_haut);
		panel_saisie.add(label_deplacement_droite);	panel_saisie.add(z_deplacement_droite);
		panel_saisie.add(label_deplacement_gauche);	panel_saisie.add(z_deplacement_gauche);
		panel_saisie.add(label_nbre_segments); panel_saisie.add(z_nbre_segments);
		panel_saisie.add(label_angle_deb); panel_saisie.add(z_angle_deb);
		panel_saisie.add(label_angle_fin); panel_saisie.add(z_angle_fin);
			// BOUTONS_AJOUTER
		panel_saisie.add(ajouter_seg); panel_saisie.add(ajouter_multiseg);
		panel_saisie.add(ajouter_tri); panel_saisie.add(ajouter_los); 
		panel_saisie.add(ajouter_rect);	panel_saisie.add(ajouter_carre);
		panel_saisie.add(ajouter_quad);	panel_saisie.add(ajouter_cercle); 
		panel_saisie.add(ajouter_arc); panel_saisie.add(ajouter_ellipse);
		// PANEL_DESSINER_DEPLACER
		panel_deplacer.add(deplacer_tout); panel_deplacer.add(deplacer_souris); panel_deplacer.add(deplacer_tout_effectuer); panel_deplacer.add(liste);
		// PANEL_MENU_BAS
		panel_composition.add(composition); 
		panel_dessiner_reenit.add(dessiner);panel_dessiner_reenit.add(reenit); 
		panel_aide.add(aide);
			
		// ACTIVATION DES BOUTONS
		segment.addActionListener(this); multisegment.addActionListener(this);
		triangle.addActionListener(this); losange.addActionListener(this);
		carre.addActionListener(this); rectangle.addActionListener(this);
		quadrangle.addActionListener(this); cercle.addActionListener(this); 
		ellipse.addActionListener(this); arc_de_cercle.addActionListener(this);
		tracer_seg.addActionListener(this); tracer_multiseg.addActionListener(this);
		tracer_tri.addActionListener(this); tracer_los.addActionListener(this);
		tracer_rect.addActionListener(this); tracer_carre.addActionListener(this);
		tracer_quad.addActionListener(this); tracer_cercle.addActionListener(this);
		tracer_ellipse.addActionListener(this);
		ajouter_seg.addActionListener(this); ajouter_multiseg.addActionListener(this);
		ajouter_tri.addActionListener(this); ajouter_los.addActionListener(this);
		ajouter_rect.addActionListener(this); ajouter_carre.addActionListener(this);
		ajouter_quad.addActionListener(this); ajouter_cercle.addActionListener(this);
		ajouter_ellipse.addActionListener(this); ajouter_arc.addActionListener(this);
		dessiner.addActionListener(this);
		composition.addActionListener(this); deplacer_tout.addActionListener(this);
		deplacer_tout_effectuer.addActionListener(this);  deplacer_souris.addActionListener(this);
		reenit.addActionListener(this); aide.addActionListener(this);

		// ON INITIALISE L'AFFICHAGE ET LES ZONES D EDIT
		invisible(); zone_edit(); bloquer_tracer();rafraichir_CB ();
	} 
	
	

	public void actionPerformed (ActionEvent e)
	{

		if (e.getSource() == segment)
		{
			bloquer_tracer();
			seg=true;
			invisible();
			// ON REND VISIBLE 
				// POINT 1
			label_p1.setVisible(true);
			z1.setVisible(true); 
			z2.setVisible(true);
				// POINT 2
			label_p2.setVisible(true);
			z3.setVisible(true); 
			z4.setVisible(true);
				// LES BOUTONS AJOUTER/TRACER
			ajouter_seg.setVisible(true); tracer_seg.setVisible(true);	
		}
		
		if (e.getSource() == multisegment)
		{
			bloquer_tracer();
			multiseg=false;
			invisible();
			// ON REND VISIBLE
				// NBRE DE SEGMENTS
			label_nbre_segments.setVisible(true); z_nbre_segments.setVisible(true);
				// LES BOUTONS AJOUTER/TRACER
			ajouter_multiseg.setVisible(true); tracer_multiseg.setVisible(true);
		}
		
		if (e.getSource() == triangle)
		{
			bloquer_tracer();
			invisible();
			// ON REND VISIBLE
				// POINT 1
			label_p1.setVisible(true);
			z1.setVisible(true); z2.setVisible(true);
				// POINT 2
			label_p2.setVisible(true);
			z3.setVisible(true); z4.setVisible(true);
				// POINT 3
			label_p3.setVisible(true);
			z5.setVisible(true); z6.setVisible(true);
				// LES BOUTONS AJOUTER/TRACER
			ajouter_tri.setVisible(true); tracer_tri.setVisible(true);
		}
		
		if (e.getSource() == losange)
		{
			bloquer_tracer();
			invisible();
			// ON REND VISIBLE
				// POINT 1
			label_p1.setVisible(true);z1.setVisible(true); z2.setVisible(true);
				// POINT 2
			label_p2.setVisible(true);z3.setVisible(true); z4.setVisible(true);
				// LES BOUTONS AJOUTER/TRACER
			ajouter_los.setVisible(true); tracer_los.setVisible(true);
		}
		
		if (e.getSource() == rectangle)
		{
			bloquer_tracer();
			rect=true;
			invisible();
			// ON REND VISIBLE
				// POINT 1
			label_p1.setVisible(true);
			z1.setVisible(true); z2.setVisible(true);
				// LA LONGUEUR
			label_longueur.setVisible(true);
			z12.setVisible(true);
				// LA HAUTEUR
			label_hauteur.setVisible(true);
			z13.setVisible(true);
				// LES BOUTONS AJOUTER/TRACER
			ajouter_rect.setVisible(true); tracer_rect.setVisible(true);
		}
		
		if (e.getSource() == carre)
		{
			bloquer_tracer();
			invisible();
			// ON REND VISIBLE
				// POINT 1
			label_p1.setVisible(true);
			z1.setVisible(true); z2.setVisible(true);
				// LA LONGUEUR
			label_longueur.setVisible(true);
			z12.setVisible(true);
				// LES BOUTONS AJOUTER/TRACER
			ajouter_carre.setVisible(true);	tracer_carre.setVisible(true);
		}
		
		if (e.getSource() == quadrangle)
		{
			bloquer_tracer();
			invisible();
			// ON REND VISIBLE 
				// CENTRE
			label_centre.setVisible(true);
			z10.setVisible(true); z11.setVisible(true);
				// RAYON
			label_rayon.setVisible(true);
			z9.setVisible(true);
				// LES BOUTONS AJOUTER/TRACER
			ajouter_quad.setVisible(true); tracer_quad.setVisible(true);
		}
		
		if (e.getSource() == cercle)
		{
			bloquer_tracer();
			invisible();
			// ON REND VISIBLE 
				// CENTRE
			label_centre.setVisible(true);
			z10.setVisible(true); z11.setVisible(true);
				// RAYON
			label_rayon.setVisible(true);
			z9.setVisible(true);
			// LES BOUTONS AJOUTER/TRACER
			ajouter_cercle.setVisible(true); tracer_cercle.setVisible(true);
		}	
		
		if (e.getSource() == arc_de_cercle)
		{
			bloquer_tracer();
			invisible();
			// ON REND VISIBLE 
				// CENTRE
			label_centre.setVisible(true);
			z10.setVisible(true); z11.setVisible(true);
				// RAYON
			label_rayon.setVisible(true);
			z9.setVisible(true);
				// ANGLES
			label_angle_deb.setVisible(true); z_angle_deb.setVisible(true);
			label_angle_fin.setVisible(true); z_angle_fin.setVisible(true);
			// LES BOUTONS AJOUTER/TRACER
			ajouter_arc.setVisible(true); tracer_arc.setVisible(true);
		}	
		
		if (e.getSource() == ellipse)
		{
			bloquer_tracer();
			invisible();
			// ON REND VISIBLE
				// CENTRE
			label_centre.setVisible(true);
			z10.setVisible(true); z11.setVisible(true);
				// LES DEUX DIAMETRES
			label_hauteur.setVisible(true); label_longueur.setVisible(true);
			z12.setVisible(true); z13.setVisible(true);
			// LES BOUTONS AJOUTER/TRACER
			ajouter_ellipse.setVisible(true); tracer_ellipse.setVisible(true);			
		}
		
		if (e.getSource() == ajouter_seg)
		{
			String s1 = z1.getText(); String s2 = z2.getText(); String s3 = z3.getText(); String s4 = z4.getText();
			Segment2D seg = new Segment2D (Integer.parseInt(s1),Integer.parseInt(s2),Integer.parseInt(s3),Integer.parseInt(s4));
			col.ajouter(seg);
			//zone_edit();
		}
		
		if (e.getSource() == ajouter_multiseg)
		{
			//zone_edit();
		}
			
		if (e.getSource() == ajouter_tri)
		{
			String s1 = z1.getText(); String s2 = z2.getText(); String s3 = z3.getText(); String s4 = z4.getText();
			String s5 = z5.getText(); String s6 = z6.getText();
			Triangle2D tri = new Triangle2D (Integer.parseInt(s1),Integer.parseInt(s2),Integer.parseInt(s3),Integer.parseInt(s4),Integer.parseInt(s5),Integer.parseInt(s6));
			col.ajouter(tri);
			rafraichir_CB ();
			//zone_edit();
		}
		
		if (e.getSource() == ajouter_los)
		{
			String s1 = z1.getText(); String s2 = z2.getText(); String s3 = z3.getText(); String s4 = z4.getText();
			Losange2D los = new Losange2D (Integer.parseInt(s1),Integer.parseInt(s2),Integer.parseInt(s3),Integer.parseInt(s4));
			col.ajouter(los);
			rafraichir_CB ();
			//zone_edit();
		}
		
		if (e.getSource() == ajouter_rect)
		{
			String s1 = z1.getText(); String s2 = z2.getText(); String s12 = z12.getText(); String s13 = z13.getText();
			Rectangle2D rect = new Rectangle2D (Integer.parseInt(s1),Integer.parseInt(s2),Integer.parseInt(s12),Integer.parseInt(s13));
			col.ajouter(rect);
			rafraichir_CB ();
			//zone_edit();
		}
		
		if (e.getSource() == ajouter_carre)
		{
			String s1 = z1.getText(); String s2 = z2.getText(); String s12 = z12.getText();
			Carre2D carre = new Carre2D (Integer.parseInt(s1),Integer.parseInt(s2),Integer.parseInt(s12));
			col.ajouter(carre);
			rafraichir_CB ();
			//zone_edit();
		}
		
		if (e.getSource() == ajouter_quad)
		{
			String s10 = z10.getText(); String s11 = z11.getText(); String s9 = z9.getText();
			Quadrangle2D quad = new Quadrangle2D (Integer.parseInt(s9),Integer.parseInt(s10),Integer.parseInt(s11));
			col.ajouter(quad);
			rafraichir_CB ();
			//zone_edit();
		}
		
		if (e.getSource() == ajouter_cercle)
		{
			String s10 = z10.getText(); String s11 = z11.getText(); String s9 = z9.getText();
			Cercle2D cercle = new Cercle2D (Integer.parseInt(s9)-Integer.parseInt(s11)/2,Integer.parseInt(s10)-Integer.parseInt(s11)/2,Integer.parseInt(s11));
			col.ajouter(cercle);
			rafraichir_CB ();
			//zone_edit();
		}
		
		if (e.getSource() == ajouter_arc)
		{
			/*
			String s10 = z10.getText(); String s11 = z11.getText(); String s9 = z9.getText(); String s1 = z_angle_deb.getText(); String s2 = z_angle_fin.getText();
			Arc_de_Cercle2D arc = new Arc_de_Cercle2D (Integer.parseInt(s9)-Integer.parseInt(s11)/2,Integer.parseInt(s10)-Integer.parseInt(s11)/2,Integer.parseInt(s11),Integer.parseInt(s1),Integer.parseInt(s2));
			col.ajouter(arc);
			rafraichir_CB ();
			//zone_edit();
			 */
		}
		
		if (e.getSource() == ajouter_ellipse)
		{
			String s1 = z10.getText(); String s2 = z11.getText(); String s12= z12.getText();String s13= z13.getText();
			Ellipse2D ellipse = new Ellipse2D (Integer.parseInt(s1)-Integer.parseInt(s12)/2,Integer.parseInt(s2)-Integer.parseInt(s13)/2,Integer.parseInt(s12), Integer.parseInt(s13));
			col.ajouter(ellipse);
			rafraichir_CB ();
			//zone_edit();
		}
		
		if (e.getSource() == tracer_seg)
		{
			bloquer_tracer();
			seg=true;
			panel_dessin.addMouseListener(new MouseAdapter() 
			{
				public void mousePressed(MouseEvent e) 
				{
					currentX = oldX = e.getX();
					currentY =oldY = e.getY();
				}
			});
			panel_dessin.addMouseListener(new MouseAdapter()
			{
				public void mouseReleased(MouseEvent e) 
				{
					currentX = e.getX();
					currentY = e.getY();
					Graphics g = panel_dessin.getGraphics();
					g.setColor(Color.black);
					if (seg==true)
					{
						rect=false;
						multiseg=false;
						Segment2D seg = new Segment2D (oldX, oldY, currentX, currentY);
						col.ajouter(seg);
					}
					col.dessiner(g);
				}
			});
		}
		
		if (e.getSource() == tracer_multiseg)
		{
			compteur = 0;
			bloquer_tracer();
			multiseg=true;
			panel_dessin.addMouseListener(new MouseAdapter() 
			{
				public void mousePressed(MouseEvent e) 
				{
					String s1 = z_nbre_segments.getText();
					if (multiseg==true)
					{
						do
						{
							/*
							if (compteur == 0){oldX=e.getX(); oldY= e.getY();compteur++;}
							else if (compteur ==1){ currentX= e.getX(); currentY= e.getY(); Multisegment2D seg = new Multisegment2D (oldX, oldY, currentX, currentY,Integer.parseInt(s1)); col.ajouter(seg);compteur++; }
							else if(compteur > 1){oldX= currentX; oldY= currentY; currentX=e.getX(); currentY= e.getY();Multisegment2D seg =new Multisegment2D (oldX, oldY, currentX, currentY, Integer.parseInt(s1)); col.ajouter(seg); compteur++; }
							*/
						}
						while(compteur +1 < Integer.parseInt(s1));
						
						Graphics g = panel_dessin.getGraphics();
						g.setColor(Color.black);
						col.dessiner(g);
					}
				}
			});
		}
		
		if (e.getSource() == tracer_rect)
		{
			bloquer_tracer();
			rect=true;
			panel_dessin.addMouseListener(new MouseAdapter() 
			{
				public void mousePressed(MouseEvent e)
				{
					currentX = oldX = e.getX();
					currentY =oldY = e.getY();
				}
			});
			panel_dessin.addMouseListener(new MouseAdapter()
			{
				public void mouseReleased(MouseEvent e) 
				{
					currentX = e.getX();
					currentY = e.getY();
					Graphics g = panel_dessin.getGraphics();
					g.setColor(Color.black);
					
					if(rect==true)
					{
						if ((currentY-oldY)<0 && (currentX-oldX)<0) 
						{ 
							Rectangle2D rect = new Rectangle2D (oldX+(currentX-oldX),oldY+(currentY-oldY), Math.abs(currentX-oldX), Math.abs(currentY-oldY));
							col.ajouter(rect);
						}
						else if(currentX-oldX <0) 
						{
							Rectangle2D rect = new Rectangle2D (oldX+(currentX-oldX),oldY,Math.abs(currentX-oldX),currentY-oldY);
							col.ajouter(rect);
						}
						else if(currentY-oldY<0) 
						{
							Rectangle2D rect = new Rectangle2D (oldX,oldY+(currentY-oldY), currentX-oldX, Math.abs(currentY-oldY));
							col.ajouter(rect);
						}
						else
						{
							Rectangle2D rect = new Rectangle2D (oldX,oldY,currentX-oldX,currentY-oldY);
							col.ajouter(rect);
						}
					col.dessiner(g);
					}
				}
			});
		}
		
		if (e.getSource() == tracer_tri)
        {
                compteur = 0;
                bloquer_tracer();
                tri=true;
                panel_dessin.addMouseListener(new MouseAdapter() 
                {
                        public void mousePressed(MouseEvent e) 
                        {
                                if (tri==true)
                                {
                                	int triX, triY;
                                    if (compteur == 0){oldX=e.getX(); oldY= e.getY();compteur++;}
                                    else if (compteur ==1){ currentX= e.getX(); currentY= e.getY();compteur++; }
                                    else if(compteur == 2){ triX=e.getX(); triY= e.getY();Triangle2D triangle =new Triangle2D (oldX, oldY, currentX, currentY, triX, triY); col.ajouter(triangle); compteur = 0; }
                                        
                                    Graphics g = panel_dessin.getGraphics();
                                    g.setColor(Color.black);
                                    col.dessiner(g);
                                }
                        }
                });
        }

		
		if (e.getSource() == tracer_ellipse)
        {
                bloquer_tracer();
                ell =true;
                panel_dessin.addMouseListener(new MouseAdapter() 
                {
                        public void mousePressed(MouseEvent e) 
                        {
                                oldX = e.getX();
                                oldY = e.getY();
                        }
                });
                
                panel_dessin.addMouseListener(new MouseAdapter()
                {
                        public void mouseReleased(MouseEvent e) 
                        {
                                currentX = e.getX();
                                currentY = e.getY();
                                Graphics g = panel_dessin.getGraphics();
                                g.setColor(Color.black);
                                if (ell==true)
                                {
                                        if ((currentY-oldY)<0 && (currentX-oldX)<0) 
                                        { 
                                                Ellipse2D ellipse = new Ellipse2D (oldX+(currentX-oldX)+Math.abs(currentX-oldX)/2,oldY+(currentY-oldY)+Math.abs(currentY-oldY)/2, Math.abs(currentX-oldX), Math.abs(currentY-oldY));
                                                col.ajouter(ellipse);
                                        }
                                        else if(currentX-oldX <0) 
                                        {
                                                Ellipse2D ellipse = new Ellipse2D (oldX+(currentX-oldX)+Math.abs(currentX-oldX)/2,oldY-(currentY-oldY)/2,Math.abs(currentX-oldX),currentY-oldY);
                                                col.ajouter(ellipse);
                                        }
                                        else if(currentY-oldY<0) 
                                        {
                                                Ellipse2D ellipse = new Ellipse2D (oldX-(currentX-oldX)/2,oldY+(currentY-oldY)+Math.abs(currentY-oldY)/2, currentX-oldX, Math.abs(currentY-oldY));
                                                col.ajouter(ellipse);
                                        }
                                        else
                                        {
                                                Ellipse2D ellipse = new Ellipse2D (oldX-(currentX-oldX)/2, oldY-(currentY-oldY)/2, currentX-oldX, currentY-oldY);
                                                col.ajouter(ellipse);
                                        }
                                                
                                        
                                }
                                col.dessiner(g);
                        }
                });
        }
        
        if (e.getSource() == tracer_carre)
        {
                bloquer_tracer();
                carre_bool=true;
                panel_dessin.addMouseListener(new MouseAdapter() 
                {
                        public void mousePressed(MouseEvent e)
                        {
                                 oldX = e.getX();
                                 oldY = e.getY();
                        }
                });
                panel_dessin.addMouseListener(new MouseAdapter()
                {
                        public void mouseReleased(MouseEvent e) 
                        {
                                currentX = e.getX();
                                currentY = e.getY();
                                Graphics g = panel_dessin.getGraphics();
                                g.setColor(Color.black);
                                
                                if(carre_bool==true)
                                {
                                if( Math.abs(currentY-oldY)< Math.abs(currentX-oldX))
                                {

                                        if ((currentX-oldX)<0 && (currentY-oldY)<0) 
                                        { 
                                                Carre2D car = new Carre2D (currentX,currentY, Math.abs(oldX-currentX));
                                                col.ajouter(car);
                                        }
                                        else if(currentX-oldX <0) 
                                        {
                                                Carre2D car = new Carre2D (currentX,currentY-Math.abs(currentY-oldY),Math.abs(oldX-currentX));
                                                col.ajouter(car);
                                        }
                                        else if(currentY-oldY<0) 
                                        {
                                                Carre2D car = new Carre2D (oldX,oldY-(oldY-currentY), Math.abs(currentX-oldX));
                                                col.ajouter(car);
                                        }
                                        else
                                        {
                                                Carre2D car = new Carre2D (oldX,oldY,currentX-oldX);
                                                col.ajouter(car);
                                        }
                                col.dessiner(g);
                                }
                                else
                                {if ((currentX-oldX)<0 && (currentY-oldY)<0) 
                                { 
                                        Carre2D car = new Carre2D (currentX,currentY-Math.abs(oldX-currentX), Math.abs(oldY-currentY));
                                        col.ajouter(car);
                                }
                                else if(currentX-oldX <0) 
                                {
                                        Carre2D car = new Carre2D (currentX,currentY-Math.abs(currentY-oldY),Math.abs(oldY-currentY));
                                        col.ajouter(car);
                                }
                                else if(currentY-oldY<0) 
                                {
                                        Carre2D car = new Carre2D (oldX,oldY-(oldY-currentY), Math.abs(currentY-oldY));
                                        col.ajouter(car);
                                }
                                else
                                {
                                        Carre2D car = new Carre2D (oldX,oldY,currentY-oldY);
                                        col.ajouter(car);
                                }
                        col.dessiner(g); 
                        }
                                
                                }
                        }
                });
        }
        
        if (e.getSource() == tracer_cercle)
        {
                bloquer_tracer();
                cer=true;
                panel_dessin.addMouseListener(new MouseAdapter() 
                {
                        public void mousePressed(MouseEvent e)
                        {
                                 oldX = e.getX();
                                 oldY = e.getY();
                        }
                });
                panel_dessin.addMouseListener(new MouseAdapter()
                {
                        public void mouseReleased(MouseEvent e) 
                        {
                                currentX = e.getX();
                                currentY = e.getY();
                                Graphics g = panel_dessin.getGraphics();
                                g.setColor(Color.black);
                                
                                if(cer==true)
                                {
                                	if( Math.abs(currentY-oldY)< Math.abs(currentX-oldX))
                                	{

                                        if ((currentX-oldX)<0 && (currentY-oldY)<0) 
                                        { 
                                                Ellipse2D cer = new Ellipse2D (currentX+Math.abs(currentX-oldX)/2,currentY+Math.abs(currentY-oldY)/2, Math.abs(oldX-currentX), Math.abs(oldX-currentX));
                                                col.ajouter(cer);
                                        }
                                        else if(currentX-oldX <0) 
                                        {
                                                Ellipse2D cer = new Ellipse2D (currentX+Math.abs(currentX-oldX)/2,currentY-Math.abs(currentY-oldY)-(currentY-oldY)/2,Math.abs(oldX-currentX),Math.abs(oldX-currentX));
                                                col.ajouter(cer);
                                        }
                                        else if(currentY-oldY<0) 
                                        {
                                                Ellipse2D cer = new Ellipse2D (oldX-Math.abs(currentX-oldX)/2,oldY-(oldY-currentY)+Math.abs(currentY-oldY)/2, Math.abs(currentX-oldX),Math.abs(currentX-oldX));
                                                col.ajouter(cer);
                                        }
                                        else
                                        {
                                                Ellipse2D cer = new Ellipse2D (oldX-Math.abs(currentX-oldX)/2,oldY-Math.abs(currentY-oldY)/2,currentX-oldX,currentX-oldX);
                                                col.ajouter(cer);
                                        }
                                        col.dessiner(g);
                                	}
                                	else
                                	{
                                        if ((currentX-oldX)<0 && (currentY-oldY)<0) 
                                        { 
                                        	Ellipse2D cer = new Ellipse2D (currentX+Math.abs(currentX-oldX)/2,currentY+Math.abs(oldY-currentY)/2, Math.abs(oldY-currentY), Math.abs(oldY-currentY));
                                            col.ajouter(cer);
                                        }
                                        else if(currentX-oldX <0) 
                                        {
                                        	Ellipse2D cer = new Ellipse2D (currentX+Math.abs(currentX-oldX)/2,currentY-Math.abs(currentY-oldY)-Math.abs(currentY-oldY)/2,Math.abs(oldY-currentY),Math.abs(oldY-currentY));
                                        	col.ajouter(cer);
                                        }
                                        else if(currentY-oldY<0) 
                                        {
                                        	Ellipse2D cer = new Ellipse2D (oldX-Math.abs(currentX-oldX)/2,oldY-(oldY-currentY)+Math.abs(currentY-oldY)/2, Math.abs(currentY-oldY),Math.abs(currentY-oldY));
                                        	col.ajouter(cer);
                                        }
                                        else
                                        {
                                        	Ellipse2D cer = new Ellipse2D (oldX-Math.abs(currentX-oldX)/2,oldY-Math.abs(currentY-oldY)/2,currentY-oldY,currentY-oldY);
                                        	col.ajouter(cer);
                                        }
                                        col.dessiner(g);
                                	}
                                
                                }
                        }
                });
        }


		
		if (e.getSource() == deplacer_souris)
		{
			bloquer_tracer();
			deplacer=true;
			panel_dessin.addMouseListener(new MouseAdapter() 
			{
				public void mousePressed(MouseEvent e) 
				{
					oldX = e.getX();
					oldY = e.getY();
				}
			});
			panel_dessin.addMouseListener(new MouseAdapter()
			{
				public void mouseReleased(MouseEvent e) 
				{
					currentX = e.getX();
					currentY = e.getY();
					Graphics g = panel_dessin.getGraphics();
					g.setColor(Color.white);
					col.dessiner(g);
					g.setColor(Color.black);
					
					if(deplacer==true)
					{
						col.deplacer(currentX-oldX,currentY-oldY);
						col.dessiner(g);
					}

				}
			});
		}
		
		if (e.getSource() == dessiner)
		{
			Graphics g = panel_dessin.getGraphics();
			g.setColor(Color.black); 
			col.dessiner(g); 
		}
		
		if (e.getSource() == composition)
		{
			//On ouvre une nouvelle fenetre
			//JFrame fen = new JFrame();
			//fen.setTitle("Composition de votre collection de geometrie 2D");
			//fen.setSize(700,500);
			//fen.setLocationRelativeTo(null);
			//fen.setVisible(true);
			// On lui implemente un JPanel dans lequel on met un JComboBox
			//JPanel panel_affichage = new JPanel ();
			//fen.add(panel_affichage);
			//panel_affichage.setBackground(Color.white);

			//Graphics g1 = panel_affichage.getGraphics();
			//g1.setColor(Color.black);
			//col.dessiner(g1);
			//col.afficher_fenetre(g1);
			col.afficher();
		}
		
		if (e.getSource() == deplacer_tout)
		{
			invisible();
			//ON REND VISIBLE
				// LABELS ET ZONES EDIT DEPLACEMENT
			label_deplacement_bas.setVisible(true); z_deplacement_bas.setVisible(true); 
			label_deplacement_droite.setVisible(true); z_deplacement_droite.setVisible(true);
			label_deplacement_gauche.setVisible(true); label_deplacement_haut.setVisible(true);
			z_deplacement_gauche.setVisible(true); z_deplacement_haut.setVisible(true);
				// on remplace le bouton pour effectuer le deplacement
			deplacer_tout_effectuer.setVisible(true); deplacer_tout.setVisible(false);

		}
		
		if (e.getSource() == deplacer_tout_effectuer)
		{
			// On récupère les valeurs saisies, on les affecte à la collection et on repeint sur l'ancienne figure
			String s1 = z_deplacement_bas.getText(); String s2 = z_deplacement_droite.getText();
			String s3 = z_deplacement_haut.getText(); String s4 = z_deplacement_gauche.getText();
			Graphics g = panel_dessin.getGraphics();
			g.setColor(Color.white);
			col.dessiner(g);
			g.setColor(Color.black);
			col.deplacer(Integer.parseInt(s2)-Integer.parseInt(s4), Integer.parseInt(s1)-Integer.parseInt(s3));
			col.dessiner(g);
		}
		
		if (e.getSource() == reenit)
		{
			for(int i=0;i<10;i++)
		        System.out.println("\n" ); 
		    
			Graphics g = panel_dessin.getGraphics();
			g.setColor(Color.white);
			col.dessiner(g);
			col.supprimer_tout();
		}
		
		if (e.getSource() == aide)
		{
			JFrame fen = new JFrame ();
			fen.setTitle("Aide du logiciel de geometrie 2D");
			fen.setSize(600,300);
			fen.setLocation(600,230);
			fen.setVisible(true);
			fen.add(z_aide);
			z_aide.setBackground(Color.LIGHT_GRAY);
			z_aide.setVisible(true);
			z_aide.setText("\n\n\tBIENVENUE SUR LE LOGICIEL DE DESSIN D'OBJETS GEOMETRIQUES 2D\n\n\n" +
					"Veuillez choisir une figure a creer, rentrer ses coordonnees, puis appuyer sur 'Ajouter'.\n" +
					"Vous pouvez aussi appuyer sur 'Tracer' pour tracer une figure a l'interieur de la zone blanche a la souris.\n" +
					"Le bouton 'Dessiner' fera apparaitre l'ensemble des formes que vous avez creees.\n" +
					"Le bouton 'Deplacer' deplacera l'ensemble des formes en fonction des coordonnees saisies.\n" +
					"Le bouton 'Deplacer a la souris' deplacer l'ensemble des formes en fonction des clics de depart et d'arrivee.\n" +
					"Le bouton 'Composition' vous donnera les types ainsi que les attributs de chacun des objets crees.\n\n\n" +
					"Le point origine [0,0] se trouve en haut a gauche de la zone blanche.");			
		}
			
	}
	
	public static void main(String[] args) 
	{
		Fenetre f = new Fenetre();
		//Quadrangle2D quad2 = new Quadrangle2D (0,0,2);
		//quad2.afficher();
		//quad2.rand();
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}