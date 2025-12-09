package metier;

import java.util.ArrayList;
import java.util.List;

public class Stereotype
{
	private static final String SOULIGNER     = "\033[4m";
	private static final String GRAS          = "\033[1m";
	private static final String REINITIALISER = "\033[0m";

	
	private static final char[] tabVisibilite = new char[] {'+', '-', '#', '~'};
	
	private String           visibilite;           //public, private, protected ou package
	private boolean          statique;             //Static ou non 
	private boolean          lectureUnique;        //Final ou non
	private boolean          abstraite;
	private String           type;                 //class, interface, enum, record...
	private String           nom;
	private List<Attribut>   attributs;
	private List<Methode>    methodes;
	private List<Stereotype> stereotypesInterne;   //Classes dans la classe

	public Stereotype(String visi, boolean statique, boolean lectureUnique, boolean abstraite, String type, String nom)
	{
		this.visibilite    = visi;
		this.statique      = statique;
		this.lectureUnique = lectureUnique;
		this.abstraite     = abstraite;
		this.type          = type;
		this.nom           = nom;
	}


	public Stereotype()
	{
		this.visibilite         = "";
		this.statique           = false;
		this.lectureUnique      = false;
		this.abstraite          = false;
		this.type               = null;
		this.nom                = null;
		this.attributs          = new ArrayList<Attribut>  ();
		this.methodes           = new ArrayList<Methode>   ();
		this.stereotypesInterne = new ArrayList<Stereotype>();
	}

	// Getter
	public String           getVisibilite        () { return this.visibilite        ; }
	public boolean          estStatique          () { return this.statique          ; }
	public boolean          estLectureUnique     () { return this.lectureUnique     ; }
	public boolean          estAbstraite         () { return this.abstraite         ; }
	public String           getType              () { return this.type              ; }
	public String           getNom               () { return this.nom               ; }
	public List<Attribut>   getAttributs         () { return this.attributs         ; }
	public List<Methode>    getMethodes          () { return this.methodes          ; }
	public List<Stereotype> getStereotypesInterne() { return this.stereotypesInterne; }

	// Setter
	public void setVisibilite        (String           visibilite         ) { this.visibilite         = visibilite         ; }
	public void setStatique          (boolean          statique           ) { this.statique           = statique           ; }
	public void setLectureUnique     (boolean          lectureUnique      ) { this.lectureUnique      = lectureUnique      ; }
	public void setAbstraite         (boolean          abstraite          ) { this.abstraite          = abstraite          ; }
	public void setType              (String           type               ) { this.type               = type               ; }
	public void setNom               (String           nom                ) { this.nom                = nom                ; }
	public void setAttributs         (List<Attribut>   attributs          ) { this.attributs          = attributs          ; }
	public void setMethodes          (List<Methode>    methodes           ) { this.methodes           = methodes           ; }
	public void setStereotypesInterne(List<Stereotype> stereotypesInternes) { this.stereotypesInterne = stereotypesInternes; }

	public void ajouterAttribut         (Attribut   attribut         ) { this.attributs         .add(attribut         );}
	public void ajouterMethode          (Methode    methode          ) { this.methodes          .add(methode          );}
	public void ajouterStereotypeInterne(Stereotype stereotypeInterne) { this.stereotypesInterne.add(stereotypeInterne);}

	public String toString()
	{
		String sRet = "----------------------------------------\n";

		if(this.lectureUnique)
			sRet += Stereotype.GRAS;

		sRet += "              " + this.nom + "          \n"
		         + Stereotype.REINITIALISER +
		        "----------------------------------------\n";
		
		for (Attribut attrib : this.attributs)
		{
			if(attrib.estLectureUnique())
				sRet += Stereotype.GRAS;
			
			if(attrib.estStatique())
				sRet += Stereotype.SOULIGNER;

			sRet += attrib.getSymbole() + " " + attrib.toString() + Stereotype.REINITIALISER + "\n" ;
		}

		sRet += "----------------------------------------\n";

		for (Methode meth : this.methodes)
		{
			if(meth.estLectureUnique())
				sRet += Stereotype.GRAS;
			
			if(meth.estStatique())
				sRet += Stereotype.SOULIGNER;
			
			sRet += meth.getSymbole() + " " + meth.getNom() + " (";

			for (int cpt = 0; cpt < meth.getParametre().size(); cpt++)
			{
				sRet += " " + meth.getParametre().get(cpt).toString();

				if(cpt != meth.getParametre().size()-1)
					sRet += ", ";
			}

			sRet += " )";

			sRet += (meth.getType() != null) ? ":" + meth.getType() + "\n" : "\n"; 
		}


		return sRet;
	}

	public static void main(String[] args)
	{
		Stereotype classe = new Stereotype();
		Attribut att1 = new Attribut();
		Attribut att2 = new Attribut();
		Methode  methC = new Methode();
		Methode  meth1 = new Methode();
		Methode  meth2 = new Methode();

		classe.setNom("classe");
		classe.setLectureUnique(true);

		att1.setVisibilite("private");
		att1.setNom("attribut1");
		att1.setType("String");

		att2.setVisibilite("public");
		att2.setNom("attribut2");
		att2.setType("Point");
		att2.setStatique(true);
		att2.setLectureUnique(true);

		classe.ajouterAttribut(att1);
		classe.ajouterAttribut(att2);

		methC.setVisibilite("public");
		methC.setNom("Classe");
		methC.ajouterParametres(att2);
		methC.ajouterParametres(att1);

		meth1.setVisibilite("private");
		meth1.setNom("testerMethode");
		meth1.setType("Point");

		meth2.setVisibilite("public");
		meth2.ajouterParametres(att1);

		classe.ajouterMethode(methC);
		classe.ajouterMethode(meth2);
		classe.ajouterMethode(meth1);

		System.out.println(classe);
	}


}