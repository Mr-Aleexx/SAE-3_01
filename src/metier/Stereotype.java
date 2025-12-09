package metier;

import java.util.ArrayList;
import java.util.List;

public class Stereotype
{
	private static final char[] tabVisibilite = new char[] {'~', '+', '-', '#'};
	
	private String         visibilite;
	private String         type;
	private String         nom;
	private List<Attribut> attributs;
	private List<Methode>  methodes;

	public Stereotype()
	{
		this.visibilite = "";
		this.type       = null;
		this.nom        = null;
		this.attributs  = new ArrayList<Attribut>();
		this.methodes   = new ArrayList<Methode> ();
	}

	// Getter
	public String         getVisibilite() { return this.visibilite; }
	public String         getType      () { return this.type      ; }
	public String         getNom       () { return this.nom       ; }
	public List<Attribut> getAttributs () { return this.attributs ; }
	public List<Methode>  getMethodes  () { return this.methodes  ; }

	// Setter
	public void setVisibilite(String         visibilite) { this.visibilite = visibilite; }
	public void setType      (String         type      ) { this.type       = type      ; }
	public void setNom       (String         nom       ) { this.nom        = nom       ; }
	public void setAttributs (List<Attribut> attributs ) { this.attributs  = attributs ; }
	public void setMethodes  (List<Methode>  methodes  ) { this.methodes   = methodes  ; }

	public void ajouterAttribut(Attribut attribut){this.attributs.add(attribut);}
	public void ajouterMethode (Methode  methode ){this.methodes.add (methode); }

	public String toString()
	{
		String sRet = "----------------------------------------\n" +
		              "              " + this.nom + "          \n" +
		              "----------------------------------------\n";
		
		for (Attribut attrib : this.attributs)
		{
			sRet += attrib.getSymbole() + " " + attrib.getNom() + "  :" + attrib.getType() + "\n";
		}

		sRet += "----------------------------------------\n";


		return sRet;
	}

	public static void main(String[] args)
	{
		Stereotype classe = new Stereotype();
		classe.setNom("classe");

		System.out.println(classe);
	}


}