package ressources;

public class Disque
{ 
	private Point  centre;
	private double rayon;

	public Disque( Point centre, double rayon )
	{
		this.centre = centre;
		this.rayon  = rayon;
	}

	public double calculerAire()
	{
		return Math.PI * Math.pow(this.rayon, 2);
	}

	public double calculerPerimetre()
	{
		return 2 * Math.PI * this.rayon;
	}

	// Getter
	public Point  getPoint() { return this.centre; }
	public double getRayon() { return this.rayon ; }

	// Setter
	public void setPoint() { this.centre = centre; }
	public void setRayon() { this.rayon  = rayon ; }
	
}
