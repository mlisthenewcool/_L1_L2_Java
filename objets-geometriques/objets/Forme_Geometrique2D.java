package Objets;

public class Forme_Geometrique2D extends Point2D
{
	private Point2D point_origine;
	
	// CONSTRUCTEURS
	// Classe abstraite
	Forme_Geometrique2D(int a, int b)
	{
		super(a,b);
	}

	// GETTERS ET SETTERS
	public Point2D getPoint_origine() {	return point_origine; }
	public void setPoint_origine(Point2D point_origine) { this.point_origine = point_origine; }
	
	// METHODES
}
