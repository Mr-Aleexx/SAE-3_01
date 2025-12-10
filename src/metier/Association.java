package metier;

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
	
	public Association( Stereotype stereotype1, Stereotype stereotype2, String typeAsso, String multiplicite1)
	{
		this.stereotype1   = stereotype1;
		this.stereotype2   = stereotype2;
		this.typeAsso      = typeAsso;
		this.multiplicite1 = multiplicite1;
	}
	
	public Association( Stereotype stereotype1, Stereotype stereotype2, String typeAsso, String multiplicite1, String multiplicite2)
	{
		this.stereotype1   = stereotype1;
		this.stereotype2   = stereotype2;
		this.typeAsso      = typeAsso;
		this.multiplicite1 = multiplicite1;
		this.multiplicite2 = multiplicite2;
	}

	public String     getTypeAsso   () { return this.typeAsso;    }
	public Stereotype getStereotype1() { return this.stereotype1; }
	public Stereotype getStereotype2() { return this.stereotype2; }

	public void setTypeAsso   ( String     typeAsso    ) { this.typeAsso    = typeAsso;    }
	public void setStereotype1( Stereotype stereotype1 ) { this.stereotype1 = stereotype1; }
	public void setStereotype2( Stereotype stereotype2 ) { this.stereotype2 = stereotype2; }

	
	
	//Faire les tests pour les set parce que oui
	//Faire la Classe "" qui va creer des associations 
	//Faire la verrification des extends et implements (mettre dans Lien)
}