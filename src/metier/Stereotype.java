package src.metier;

import java.util.ArrayList;
import java.util.List;

public class Stereotype
{
	private static final String SOULIGNER     = "\033[4m";
	private static final String REINITIALISER = "\033[0m";
	
	private String           visibilite;           //public, private, protected ou package
	private boolean          statique;             //Static ou non 
	private boolean          lectureUnique;        //Final ou non
	private boolean          abstraite;
	private String           type;                 //class, interface, enum, record...
	private String           nom;
	private List<Attribut>   attributs;
	private List<Methode>    methodes;
	private List<Stereotype> stereotypesInterne;   //Classes dans la classe
	private String           mere;
	private List<String>     lstImplementations;

	private Position         position;

	

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
	public Stereotype(String visi, boolean statique, boolean lectureUnique, boolean abstraite, String type, String nom, String mere)
	{
		this.visibilite         = visi;
		this.statique           = statique;
		this.lectureUnique      = lectureUnique;
		this.abstraite          = abstraite;
		this.type               = type;
		this.nom                = nom;
		this.attributs          = new ArrayList<Attribut>  ();
		this.methodes           = new ArrayList<Methode>   ();
		this.stereotypesInterne = new ArrayList<Stereotype>();
		this.mere               = mere;
		this.lstImplementations = new ArrayList<String>();

		this.position           = new Position();
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
	public String           getMere              () { return this.mere              ; }
	public List<String>     getLstImplementations() { return this.lstImplementations; }
	public Position         getPos               () { return this.position          ; }

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
	public void setMere              (String           mere               ) { this.mere               = mere               ; }
	public void setLstImplementations(List<String>     lstImplementations ) { this.lstImplementations = lstImplementations ; }

	public void ajouterAttribut         (Attribut   attribut         ) { this.attributs         .add(attribut         );}
	public void ajouterMethode          (Methode    methode          ) { this.methodes          .add(methode          );}
	public void ajouterStereotypeInterne(Stereotype stereotypeInterne) { this.stereotypesInterne.add(stereotypeInterne);}
	public void ajouterImplementations  (String     implementation   ) { this.lstImplementations.add(implementation   );}

	public boolean possede(int x, int y)
	{
		return x >= this.position.getCentreX() - ( this.position.getTailleX() / 2                         ) &&
		       x <= this.position.getCentreX() + ( this.position.getTailleX() / 2                         ) &&

		       y >= this.position.getCentreY() - ( this.position.getTailleY() / 2                         ) &&
		       y <= this.position.getCentreY() + ( this.position.getTailleY() + this.position.getTailleY() / 2 );
	}
	// public String toString()
	// {
		
	// 	int    nbCharTempo;
	// 	int    nbCharPlusLong = this.nom.length() + ((this.lectureUnique) ? +7 : +0);  //Taille du nom de la classe + gelé si elle l'est
		
	// 	String separation     = "";
	// 	String sMeth          = "";
	// 	String sRet           = "";

	// 	//Prévérification de taille
	// 	for (Attribut attrib : this.attributs)
	// 	{
	// 		nbCharTempo = 2;                                    //Symbole de visibilité + espace
	// 		nbCharTempo += attrib.getNom().length() + 1;        //Taille du nom + espace
			
	// 		if(attrib.getType() != null)
	// 			nbCharTempo += attrib.getType().length() + 1;   //Taille du type + deux points

	// 		if(attrib.estLectureUnique())
	// 			nbCharTempo += 7;                               //Taille du {Gelé} + espace

	// 		if(nbCharTempo > nbCharPlusLong)
	// 			nbCharPlusLong = nbCharTempo;
	// 	}

	// 	for (Methode meth : this.methodes)
	// 	{
	// 		nbCharTempo = 2;                                   //Symbole de visibilité + espace
	// 		nbCharTempo += meth.getNom().length() + 2;         //Taille du nom + espace + parenthèse

	// 		if(meth.getParametre().size() != 0)
	// 			for (Parametre param : meth.getParametre())
	// 				nbCharTempo += param.nom().length() + param.type().length() + 4;   //Taille du nom + type + espaces + deux points

	// 		nbCharTempo += 2; //Parenthèse fermante + espace

	// 		if(meth.estLectureUnique())
	// 			nbCharTempo += 7;                             //Taille du {Gelé} + espace

	// 		if(meth.getType() != null)
	// 			nbCharTempo += meth.getType().length() + 1;   //Taille du type + deux points

	// 		if(nbCharTempo > nbCharPlusLong)
	// 			nbCharPlusLong = nbCharTempo;

	// 	}

	// 	separation = String.format ( "%" + (nbCharPlusLong+1) + "s", "" ).replace ( " ", "-" );

	// 	sRet = separation + "\n";

	// 	if(this.lectureUnique)
	// 		sRet += String.format ( "%"  + nbCharPlusLong/2 + "s", this.nom ) + ((this.lectureUnique) ? " {Gelé}" : "");
	// 	else
	// 		sRet += String.format ( "%-" + (nbCharPlusLong-this.nom.length())/2 + "s", " "  ) + this.nom;

	// 	sRet += "\n" + separation + "\n";

	// 	for (Attribut attrib : this.attributs)
	// 	{
			
	// 		if(attrib.estStatique())
	// 			sRet += Stereotype.SOULIGNER;
	// 		else
	// 			sRet += Stereotype.REINITIALISER;

	// 		sRet += attrib.getSymbole() + " " + attrib.toString() + Stereotype.REINITIALISER + "\n" ;
	// 	}

	// 	sRet += separation + "\n";


	// 	for (Methode meth : this.methodes)
	// 	{
	// 		sMeth = "";
			
	// 		if(meth.estStatique())
	// 			sRet += Stereotype.SOULIGNER;
	// 		else
	// 			sRet += Stereotype.REINITIALISER;
			
	// 		sMeth += meth.toString();

	// 		sMeth += Stereotype.REINITIALISER;


	// 		if (meth.getType() != null)
	// 		{
	// 			sMeth = String.format("%-" + (nbCharPlusLong-3) + "s %1s", sMeth, ":" + meth.getType() );
	// 		}
			
	// 		sRet += sMeth + "\n";
	// 	}

	// 	sRet += separation + "\n";

	// 	return sRet;
	// }

	public String toString()
	{
		int tmp;

		int    nbCharPlusLong;
		int    typeAttributPlusLong = 0;
		int    nomAttributPlusLong  = 0;
		int    typeMethodePlusLong  = 0;
		int    nomMethodePlusLong   = 0;
		
		String separation     = "";
		String sMeth          = "";
		String sRet           = "";

		//Prévérification de taille
		for (Attribut attrib : this.attributs)
		{
			tmp = attrib.getNom().length();        //Taille du nom
			
			if( tmp > nomAttributPlusLong )
				nomAttributPlusLong = tmp;

			if(attrib.getType() != null && typeAttributPlusLong < attrib.getType().length() )
			{
				typeAttributPlusLong = attrib.getType().length();
				if(attrib.estLectureUnique()) typeAttributPlusLong += 7;           //Taille du {Gelé} + espace
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
			tmp = meth.estLectureUnique() ? +7 : +0;                      //Taille du {Gelé} + espace
			
			// if(meth.estLectureUnique()) tmp += 7;                      //Taille du {Gelé} + espace
	
			if( meth.getType() != null )
			{
				if ( typeMethodePlusLong < meth.getType().length() + tmp )
					typeMethodePlusLong = meth.getType().length();
			}
			else //Si c'est le constructeur
			{
				if ( typeMethodePlusLong < meth.getNom().length() + tmp )
					typeMethodePlusLong = meth.getNom().length();
			}

			if( tmp > typeMethodePlusLong )
				typeMethodePlusLong = tmp;
		}

		// Test du ncCharPlusLong
		if ( typeAttributPlusLong + nomAttributPlusLong > typeMethodePlusLong + nomMethodePlusLong )
			nbCharPlusLong = typeAttributPlusLong + nomAttributPlusLong +2;
		else
			nbCharPlusLong = typeMethodePlusLong + nomMethodePlusLong;
		
		System.out.println( nbCharPlusLong );
		
		separation = String.format ( "%" + (nbCharPlusLong+1) + "s", "" ).replace ( " ", "-" );

		sRet = separation + "\n";

		if(this.lectureUnique)
			sRet += String.format ( "%"  + nbCharPlusLong/2 + "s", this.nom ) + ((this.lectureUnique) ? " {Gelé}" : "");
		else
			sRet += String.format ( "%-" + (nbCharPlusLong-this.nom.length())/2 + "s", " "  ) + this.nom;

		sRet += "\n" + separation + "\n";

		for (Attribut attrib : this.attributs)
		{
			sRet += attrib.toString( nomAttributPlusLong ) + Stereotype.REINITIALISER + "\n" ;

			if(attrib.estStatique())
				sRet += Stereotype.SOULIGNER;
			else
				sRet += Stereotype.REINITIALISER;
		}

		sRet += separation + "\n";


		for (Methode meth : this.methodes)
		{
			sMeth = meth.toString( nomMethodePlusLong );
			
			if(meth.estStatique())
				sRet += Stereotype.SOULIGNER;
			else
				sRet += Stereotype.REINITIALISER;

			sMeth += Stereotype.REINITIALISER;
			
			sRet += sMeth + "\n";
		}

		sRet += separation + "\n";

		return sRet;
	}

}