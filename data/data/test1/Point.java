package ressources;

public abstract class Point
{
	private int x;
	private int y;
	private String nom;

	public Point( int x, int y )
	{
		this.x = x;
		this.y = y;
	}

	public Point(int x, int y, String nom)
	{
		this.x = x;
		this.y = y;
		this.nom = nom;
	}

	// Getter
	public int getX() { return x; }
	public int getY() { return y; }
	public String getNom() { return this.nom; }

	// Setter
	public void setX( int x ) { this.x = x; }
	public void setY( int y ) { this.y = y; }
	public void setNom(String nom) { this.nom = nom;}
	
}