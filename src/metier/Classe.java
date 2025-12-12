package src.metier;

import java.util.ArrayList;
import java.util.List;


public class Classe 
{
	private static final String SOULIGNER     = "\033[4m";
	private static final String REINITIALISER = "\033[0m";
	
	private String           visibilite;           //public, private, protected ou package
	private boolean          statique;             //Static ou non 
	private boolean          lectureUnique;        //Final ou non
	private boolean          abstraite;
	private String           nom;

	private String           stereotype;
	private List<Attribut>   attributs;
	private List<Methode>    methodes;
	private List<Classe>     ClassesInterne;   //Classes dans la classe
	private String           mere;
	private List<String>     lstImplementations;

	

	/**
	 * Représentation d'une classe Java
	 * En plus des paramètres, elle comprends les attributs de la classe, les méthodes, et les sous classes
	 * @param visi Visibilité de la classe (public, private)
	 * @param statique Si la classe est statique ou non 
	 * @param lectureUnique Final ou non
	 * @param abstraite Abstraite ou non
	 * @param type Type de la classe
	 * @param nom Nom de la classe
	 */
	public Classe(String visi, boolean statique, boolean lectureUnique, boolean abstraite, String stereotype, String nom, String mere)
	{
		this.visibilite         = visi;
		this.statique           = statique;
		this.lectureUnique      = lectureUnique;
		this.abstraite          = abstraite;
		this.stereotype         = stereotype;
		this.nom                = nom;
		this.attributs          = new ArrayList<Attribut>  ();
		this.methodes           = new ArrayList<Methode>   ();
		this.ClassesInterne     = new ArrayList<Classe>();
		this.mere               = mere;
		this.lstImplementations = new ArrayList<String>();

	}

	// Getter
	public String           getVisibilite        () { return this.visibilite        ; }
	public boolean          estStatique          () { return this.statique          ; }
	public boolean          estLectureUnique     () { return this.lectureUnique     ; }
	public boolean          estAbstraite         () { return this.abstraite         ; }
	public String           getStereotype        () { return this.stereotype        ; }
	public String           getNom               () { return this.nom               ; }
	public List<Attribut>   getAttributs         () { return this.attributs         ; }
	public List<Methode>    getMethodes          () { return this.methodes          ; }
	public List<Classe>     getClassesInterne    () { return this.ClassesInterne    ; }
	public String           getMere              () { return this.mere              ; }
	public List<String>     getLstImplementations() { return this.lstImplementations; }

	// Setter
	public void setVisibilite        (String           visibilite         ) { this.visibilite         = visibilite         ; }
	public void setStatique          (boolean          statique           ) { this.statique           = statique           ; }
	public void setLectureUnique     (boolean          lectureUnique      ) { this.lectureUnique      = lectureUnique      ; }
	public void setAbstraite         (boolean          abstraite          ) { this.abstraite          = abstraite          ; }
	public void setStereotype        (String           stereotype         ) { this.stereotype         = stereotype         ; }
	public void setNom               (String           nom                ) { this.nom                = nom                ; }
	public void setAttributs         (List<Attribut>   attributs          ) { this.attributs          = attributs          ; }
	public void setMethodes          (List<Methode>    methodes           ) { this.methodes           = methodes           ; }
	public void setClassesInterne    (List<Classe>     ClassesInternes    ) { this.ClassesInterne     = ClassesInternes    ; }
	public void setMere              (String           mere               ) { this.mere               = mere               ; }
	public void setLstImplementations(List<String>     lstImplementations ) { this.lstImplementations = lstImplementations ; }

	public void ajouterAttribut         (Attribut   attribut         ) { this.attributs         .add(attribut      );}
	public void ajouterMethode          (Methode    methode          ) { this.methodes          .add(methode       );}
	public void ajouterClasseInterne    (Classe     ClasseInterne    ) { this.ClassesInterne    .add(ClasseInterne );}
	public void ajouterImplementations  (String     implementation   ) { this.lstImplementations.add(implementation);}


	public String toString()
	{
		int tmp;

		int    nbCharPlusLong;
		int    typeAttributPlusLong = 0;
		int    nomAttributPlusLong  = 0;
		int    typeMethodePlusLong  = 0;
		int    nomMethodePlusLong   = 0;
		
		String separation;
		String sMeth;
		String sRet;

		//Prévérification de taille
		for (Attribut attrib : this.attributs)
		{
			tmp = attrib.getNom().length();        //Taille du nom et du symbole
			
			if( tmp > nomAttributPlusLong )
				nomAttributPlusLong = tmp;

			if( typeAttributPlusLong < attrib.getType().length() )
			{
				typeAttributPlusLong = attrib.getType().length();
				if(attrib.estLectureUnique()) typeAttributPlusLong += 9;           //Taille du {Gelé} + espace
			}
		}

		for (Methode meth : this.methodes)
		{
			tmp = meth.getNom().length() + 2;         //Taille du nom + espace + parenthèse

			if( ! meth.getParametre().isEmpty() )
			{
				for (Parametre param : meth.getParametre())
					tmp += param.nom().length() + param.type().length() + 5;   //Taille du nom + type + espaces + deux points
				
				tmp -= 2; //Enleve les espaces et deux points de la derniere itéartion
			}
			tmp += 1; //Parenthèse fermante

			if( tmp > nomMethodePlusLong )
				nomMethodePlusLong = tmp;

			// Type
			tmp = meth.estLectureUnique() ? +7 : +0;                      //Taille du {Gelé} + espaces
	
			if( meth.getType() != null && ! meth.getType().equals( "void" ) )
			{
				if ( typeMethodePlusLong < meth.getType().length() + tmp )
					typeMethodePlusLong = meth.getType().length() + tmp;
			}

			if( tmp > typeMethodePlusLong )
				typeMethodePlusLong = tmp;
		}

		// Test du ncCharPlusLong
		if ( typeAttributPlusLong + nomAttributPlusLong > typeMethodePlusLong + nomMethodePlusLong )
			nbCharPlusLong = typeAttributPlusLong + nomAttributPlusLong; // Symbole + : + espaces
		else
			nbCharPlusLong = typeMethodePlusLong + nomMethodePlusLong;
		
		nbCharPlusLong += 4;

		if ( typeMethodePlusLong == 0 ) nbCharPlusLong -= 2;

		separation = String.format ( "%" + (nbCharPlusLong+1) + "s", "" ).replace ( " ", "-" );

		sRet = separation + "\n";

		if ( this.stereotype != null )
		{
			int taille = ( nbCharPlusLong - this.stereotype.length() - 4 ) /2;
			sRet += String.format( "%" + taille + "s" , " " ) + "<<" + this.stereotype + ">>" + "\n";
		}

		if(this.lectureUnique)
			sRet += String.format ( "%"  + nbCharPlusLong/2 + "s", this.nom ) + ((this.lectureUnique) ? " {Gelé}" : "");
		else
			sRet += String.format ( "%" + (nbCharPlusLong-this.nom.length())/2 + "s", " "  ) + this.nom;

		sRet += "\n" + separation + "\n";

		for (Attribut attrib : this.attributs)
		{
			if(attrib.estStatique())
				sRet += Classe.SOULIGNER;
			else
				sRet += Classe.REINITIALISER;

			sRet += attrib.toString( nomAttributPlusLong ) + Classe.REINITIALISER + "\n" ;
		}

		sRet += separation + "\n";


		for (Methode meth : this.methodes)
		{
			if(meth.estStatique())
				sRet += Classe.SOULIGNER;
			else
				sRet += Classe.REINITIALISER;

			sMeth = meth.toString( nomMethodePlusLong );

			sMeth += Classe.REINITIALISER;
			
			sRet += sMeth + "\n";
		}

		sRet += separation + "\n";

		return sRet;
	}

}