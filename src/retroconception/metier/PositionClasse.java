package retroconception.metier;

public class PositionClasse
{
	private int centreX;
	private int centreYClasse;
	private int centreYAttribut;
	private int centreYMethode;
	private int tailleX;
	private int tailleYClasse;
	private int tailleYAttribut;
	private int tailleYMethode;
 
	public PositionClasse(int centreX, int centreYClasse, int centreYAttribut, int centreYMethode,
	                      int tailleX, int tailleYClasse, int tailleYAttribut, int tailleYMethode )
	{
		this.centreX         = centreX;
		this.centreYClasse   = centreYClasse;
		this.centreYAttribut = centreYAttribut;
		this.centreYMethode  = centreYMethode;
		this.tailleX         = tailleX;
		this.tailleYClasse   = tailleYClasse;
		this.tailleYAttribut = tailleYAttribut;
		this.tailleYMethode  = tailleYMethode;
	}
 
	public PositionClasse()
	{
		this( 50, 100, 150, 200, 200, 50, 50, 50 );
	}

	public int getCentreX        () { return this.centreX;         }
	public int getCentreYClasse  () { return this.centreYClasse;   }
	public int getCentreYAttribut() { return this.centreYAttribut; }
	public int getCentreYMethode () { return this.centreYMethode;  }
	
	public int getTailleX        () { return this.tailleX;         }
	public int getTailleYClasse  () { return this.tailleYClasse;   }
	public int getTailleYAttribut() { return this.tailleYAttribut; }
	public int getTailleYMethode () { return this.tailleYMethode;  }

	public void setCentreX        (int centreX        ) { this.centreX         = centreX;         }
	public void setCentreYClasse  (int centreYClasse  ) { this.centreYClasse   = centreYClasse;   }
	public void setCentreYAttribut(int centreYAttribut) { this.centreYAttribut = centreYAttribut; }
	public void setCentreYMethode (int centreYMethode ) { this.centreYMethode  = centreYMethode;  }
	public void setTailleX        (int tailleX        ) { this.tailleX         = tailleX;         }
	public void setTailleYClasse  (int tailleYClasse  ) { this.tailleYClasse   = tailleYClasse;   }
	public void setTailleYAttribut(int tailleYAttribut) { this.tailleYAttribut = tailleYAttribut; }
	public void setTailleYMethode (int tailleYMethode ) { this.tailleYMethode  = tailleYMethode;  }

	public void deplacerX (int x ) { this.centreX += x; }
	public void deplacerY (int y )
	{
		this.centreYClasse   += y;
		this.centreYAttribut += y;
		this.centreYMethode  += y;
	}
 
	public String toString()
	{
		return this.centreX        + "|" + this.tailleX       + "|" + this.centreYClasse   + "|" + this.centreYAttribut + "|" +
		       this.centreYMethode + "|" + this.tailleYClasse + "|" + this.tailleYAttribut + "|" + this.tailleYMethode;
	}
}