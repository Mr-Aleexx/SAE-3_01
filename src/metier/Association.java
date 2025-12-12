package src.metier;

public class Association
{
	private Classe Classe1;
	private Classe Classe2;
	private String     typeAsso;
	private String     multiplicite1;
	private String     multiplicite2;
	
	
	public Association( Classe Classe1, Classe Classe2, String multiplicite1)
	{
		this(Classe1, Classe2, null, multiplicite1, null);
	}
	
	public Association( Classe Classe1, Classe Classe2, String typeAsso, String multiplicite1, String multiplicite2)
	{
		this.Classe1   = Classe1;
		this.Classe2   = Classe2;
		this.typeAsso      = typeAsso;
		this.multiplicite1 = multiplicite1;
		this.multiplicite2 = multiplicite2;
	}

	public String     getTypeAsso     () {return this.typeAsso;     }
	public Classe     getClasse1  () {return this.Classe1;  }
	public Classe     getClasse2  () {return this.Classe2;  }
	public String     getMultiplicite () {return this.multiplicite1;}
	public String     getMultiplicite1() {return this.multiplicite1;}
	public String     getMultiplicite2() {return this.multiplicite2;}

	public void setTypeAsso   ( String     typeAsso    ) { this.typeAsso    = typeAsso;    }
	public void setClasse1( Classe Classe1 ) { this.Classe1 = Classe1; }
	public void setClasse2( Classe Classe2 ) { this.Classe2 = Classe2; }

	
	public String toString()
	{
		return this.typeAsso +  ((this.typeAsso.equals("unidirectionnelle")) ? " de " : " entre ") + this.Classe1.getNom() + this.multiplicite1
		                     +  ((this.typeAsso.equals("unidirectionnelle")) ? " vers " : " et " ) + this.Classe2.getNom() + this.multiplicite2;
	}
}