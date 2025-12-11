package src.metier;

public class Position
{
	private int centreX;
	private int centreY;
	private int tailleX;
	private int tailleY;
 
	public Position(int centreX, int centreY, int tailleX, int tailleY)
	{
		this.centreX = centreX;
		this.centreY = centreY;
		this.tailleX = tailleX;
		this.tailleY = tailleY;
	}
 
	public Position()
	{
		this( 50, 25, 80 , 40 );
	}

	public int getCentreX() { return this.centreX; }
	public int getCentreY() { return this.centreY; }
	public int getTailleX() { return this.tailleX; }
	public int getTailleY() { return this.tailleY; }

	public void deplacerX (int x)       { this.centreX+= x;       }
	public void deplacerY (int y)       { this.centreY+= y;       }
	public void setTailleX(int tailleX) { this.tailleX = tailleX; }
	public void setTailleY(int tailleY) { this.tailleY = tailleY; }
	public void setCentreX(int centreX) { this.centreX = centreX; }
	public void setCentreY(int centreY) { this.centreY = centreY; }
 
	public String toString()
	{
		return this.centreX + ":" + this.centreY;
	}
 }