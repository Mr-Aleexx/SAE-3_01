package ressources;

public class Point
<<<<<<< HEAD
{
	private int x;
	private int y;
	private String nom;
=======
{ 
	private int x;
	private int y;

	public Point( int x, int y )
	{
		this.x = x;
		this.y = y;
	}

	// Getter
	public int getX() { return x; }
	public int getY() { return y; }

	// Setter
	public void setX( int x ) { this.x = x; }
	public void setY( int y ) { this.y = y; }
>>>>>>> 48d26dae1326a0ee1bbc9d9d13ca23b648a5c478
	
	public Point(int x, int y, String nom)
	{
		this.x = x;
		this.y = y;
		this.nom = nom;
	}
	
	public int    getX  () { return this.x;   }
	public int    getY  () { return this.y;   }
	public String getNom() { return this.nom; }
	
	public void setX  (int x)      { this.x = x;    }
	public void setY  (int y)	   { this.y = y;    }
	public void setNom(String nom) { this.nom = nom;}
}