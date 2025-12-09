package metier;

public class Association
{
	private Stereotype stereotype1, stereotype2;
	private String typeAsso;
	
	public Association(Stereotype stereotype1, Stereotype stereotype2, String typeAsso)
	{
		this.stereotype1 = stereotype1;
		this.stereotype2 = stereotype2;
		this.typeAsso = typeAsso;
	}

	public String     getTypeAsso   (){return this.typeAsso;   }
	public Stereotype getStereotype1(){return this.stereotype1;}
	public Stereotype getStereotype2(){return this.stereotype2;}

	public void setTypeAsso(String typeAsso){this.typeAsso = typeAsso;}
	public void setStereotype1(Stereotype stereotype1){this.stereotype1 = stereotype1;}
	public void setStereotype2(Stereotype stereotype2){this.stereotype2 = stereotype2;}

	
	
	//Faire les tests pour les set parce que oui
	//Faire la Classe "" qui va creer des associations 
	//Faire la verrification des extends et implements (mettre dans Lien)
}