package src.metier;

public class Association
{
	private Stereotype stereotype1;
	private Stereotype stereotype2;
	private String     typeAsso;
	private String     multiplicite1;
	private String     multiplicite2;
	
	
	public Association( Stereotype stereotype1, Stereotype stereotype2, String multiplicite1)
	{
		this.stereotype1   = stereotype1;
		this.stereotype2   = stereotype2;
		this.multiplicite1 = multiplicite1;
	}
	
	public Association( Stereotype stereotype1, Stereotype stereotype2, String typeAsso, String multiplicite)
	{
		this.stereotype1   = stereotype1;
		this.stereotype2   = stereotype2;
		this.typeAsso      = typeAsso;
		this.multiplicite1 = multiplicite;
	}
	
	public Association( Stereotype stereotype1, Stereotype stereotype2, String typeAsso, String multiplicite1, String multiplicite2)
	{
		this.stereotype1   = stereotype1;
		this.stereotype2   = stereotype2;
		this.typeAsso      = typeAsso;
		this.multiplicite1 = multiplicite1;
		this.multiplicite2 = multiplicite2;
	}

	public String     getTypeAsso     () {return this.typeAsso;     }
	public Stereotype getStereotype1  () {return this.stereotype1;  }
	public Stereotype getStereotype2  () {return this.stereotype2;  }
	public String     getMultiplicite () {return this.multiplicite1;}
	public String     getMultiplicite1() {return this.multiplicite1;}
	public String     getMultiplicite2() {return this.multiplicite2;}

	public void setTypeAsso   ( String     typeAsso    ) { this.typeAsso    = typeAsso;    }
	public void setStereotype1( Stereotype stereotype1 ) { this.stereotype1 = stereotype1; }
	public void setStereotype2( Stereotype stereotype2 ) { this.stereotype2 = stereotype2; }

	
	public String toString() {
		String res = "";
		res += String.format("%s de %s%s vers %s%s", this.typeAsso, this.stereotype1, this.multiplicite1, this.stereotype2, this.multiplicite2);
		return res;
	}
}