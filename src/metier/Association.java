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
	public void setStereotype1(Stereotype s){}
	public void setStereotype2(){}
}