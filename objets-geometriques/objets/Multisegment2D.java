package Objets;

public class Multisegment2D extends Segment2D
{
	private Segment2D segment_origine;
	private Segment2D segment_final;
	
	public Multisegment2D (int a, int b, int c, int d, int e, int f)
	{
		super(a,b,c,d);
		segment_final = new Segment2D (c,d,e,f);
	}
}
