package ressources;

public class Point
{
	private int x;
	private int y;
	private String nom;
	
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