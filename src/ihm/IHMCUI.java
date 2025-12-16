package src.ihm;

import src.Controleur;
import src.metier.Classe;
import src.metier.Attribut;
import src.metier.Methode;
import src.metier.Parametre;
import src.metier.Association;

/**
 * Représentation dans le terminal en UML d'une ou plusieurs clases java
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
 * @version Etape 4
 * @since 08-12-2025
 */
public class IHMCUI
{
	private static final String SOULIGNER     = "\033[4m";
	private static final String REINITIALISER = "\033[0m";

	private Controleur ctrl;

	public IHMCUI(Controleur ctrl)
	{
		this.ctrl = ctrl;
	}

	public String afficher()
	{
		String sRet = "";
		String strClasse = "";

		int    longeurMaxTmp;

		int    nbCharPlusLong;
		int    typeAttributPlusLong;
		int    nomAttributPlusLong;
		int    typeMethodePlusLong;
		int    nomMethodePlusLong;
		
		String separation;
		String sMeth;

		String  stereotype = null;
		String  nom; 
		boolean lectureUnique;

		
		for ( Classe classe : this.ctrl.getLstClasses() )
		{
		
			typeAttributPlusLong = 0;
			nomAttributPlusLong  = 0;
			typeMethodePlusLong  = 0;
			nomMethodePlusLong   = 0;

			stereotype    = classe.getStereotype();
			nom           = classe.getNom();
			lectureUnique = classe.estLectureUnique();

			nbCharPlusLong = nom.length();

			
			for (Attribut attrib : classe.getAttributs())
			{
				longeurMaxTmp = attrib.getNom().length();        //Taille du nom et du symbole
				
				if( longeurMaxTmp > nomAttributPlusLong )
					nomAttributPlusLong = longeurMaxTmp;

				if( typeAttributPlusLong < attrib.getType().length() )
				{
					typeAttributPlusLong = attrib.getType().length();
					if(attrib.estLectureUnique()) typeAttributPlusLong += 9;           //Taille du {Gelé} + espace
				}
			}

			for (Methode meth : classe.getMethodes())
			{
				longeurMaxTmp = meth.getNom().length() + 2;         //Taille du nom + espace + parenthèse

				if( ! meth.getParametre().isEmpty() )
				{
					for (Parametre param : meth.getParametre())
						longeurMaxTmp += param.nom().length() + param.type().length() + 5;   //Taille du nom + type + espaces + deux points
					
					longeurMaxTmp -= 2; //Enleve les espaces et deux points de la derniere itéartion
				}
				longeurMaxTmp += 1; //Parenthèse fermante

				if( longeurMaxTmp > nomMethodePlusLong )
					nomMethodePlusLong = longeurMaxTmp;

				// Type
				longeurMaxTmp = meth.estLectureUnique() ? +7 : +0;                      //Taille du {Gelé} + espaces
		
				if( meth.getType() != null && ! meth.getType().equals( "void" ) )
				{
					if ( typeMethodePlusLong < meth.getType().length() + longeurMaxTmp )
					  	typeMethodePlusLong = meth.getType().length() + longeurMaxTmp;
				}

				if( longeurMaxTmp > typeMethodePlusLong )
					typeMethodePlusLong = longeurMaxTmp;
			}

			// Test du ncCharPlusLong
			if ( typeAttributPlusLong + nomAttributPlusLong > typeMethodePlusLong + nomMethodePlusLong )
				nbCharPlusLong = typeAttributPlusLong + nomAttributPlusLong; // Symbole + : + espaces
			else
				nbCharPlusLong = typeMethodePlusLong + nomMethodePlusLong;

			if ( nbCharPlusLong == 0 )
			{
				nbCharPlusLong = classe.getNom().length();
			}

			
			nbCharPlusLong += 4;

			if ( typeMethodePlusLong == 0 ) nbCharPlusLong -= 2;

			separation = String.format ( "%" + (nbCharPlusLong+1) + "s", "" ).replace ( " ", "-" );

			strClasse = separation + "\n";

			if ( stereotype != null )
			{
				int taille = ( nbCharPlusLong - stereotype.length() - 4 ) /2;
				strClasse += String.format( "%" + taille + "s" , " " ) + "<<" + stereotype + ">>" + "\n";
			}

			if(lectureUnique)
				strClasse += String.format ( "%"  + nbCharPlusLong/2 + "s", nom ) + ((lectureUnique) ? " {Gelé}" : "");
			else
				strClasse += String.format ( "%" + (nbCharPlusLong-nom.length())/2 + "s", " "  ) + nom;

			strClasse += "\n" + separation + "\n";

			for (Attribut attrib : classe.getAttributs())
			{
				if(attrib.estStatique())
					strClasse += IHMCUI.SOULIGNER;
				else
					strClasse += IHMCUI.REINITIALISER;

				strClasse += this.afficherAttribut(attrib, nomAttributPlusLong ) + IHMCUI.REINITIALISER + "\n" ;
			}

		
			strClasse += separation + "\n";


			for (Methode meth : classe.getMethodes())
			{
				if(meth.estStatique())
					strClasse += IHMCUI.SOULIGNER;
				else
					strClasse += IHMCUI.REINITIALISER;

				sMeth = this.afficherMethode(meth, nomMethodePlusLong);

				sMeth += IHMCUI.REINITIALISER;
				
				strClasse += sMeth + "\n";
			}

			strClasse += separation + "\n";

			sRet += strClasse + "\n\n";
		}

		sRet += "Associations:\n";

		for (int i = 0 ; i < this.ctrl.getLstAssociations().size() ; i++)
			sRet += "Association " + (i + 1) + ": " + this.afficherAssociations(this.ctrl.getLstAssociations().get(i)) + "\n";

		sRet += "\nHeritage:\n";
		for ( Classe classe : this.ctrl.getLstClasses() )
			if( classe.getMere() != null ) sRet += classe.getNom() + " hérite de " + classe.getMere() + "\n";

		sRet += "\nImplementation:\n";
		for ( Classe classe : this.ctrl.getLstClasses() )
			if( ! classe.getLstImplementations().isEmpty() )
			{
				sRet += classe.getNom() + " implemente ";
				for ( String s : classe.getLstImplementations() )
					sRet += s + ", ";
				sRet = sRet.substring( 0, sRet.length()-2 ) + "\n";
			}

		return sRet;
	}

	private String afficherAssociations(Association association)
	{
        if (association.getTypeAsso().equals("unidirectionnelle"))
		{
            return association.getTypeAsso() + " de " 
                + association.getClasse1().getNom() + "(" + association.getMultiplicite2() + ") vers "
                + association.getClasse2().getNom() + "(" + association.getMultiplicite1() + ")";
        } 
		else
		{
            return association.getTypeAsso() + " entre " 
                + association.getClasse1().getNom() + "(" + association.getMultiplicite1() + ") et "
                + association.getClasse2().getNom() + "(" + association.getMultiplicite2() + ")";
        }
		
	}

	private String afficherAttribut( Attribut attribut, int longueurNom )
	{
		return attribut.getSymbole() + " " + String.format( "%-" + longueurNom + "s" , attribut.getNom()) + " :" + attribut.getType() +
		       ((attribut.estLectureUnique()) ? " {Gelé}" : "");
	}

	private String afficherMethode( Methode methode, int longueurNom )
	{
		String parametre = "";

		if ( ! methode.getParametre().isEmpty() )
		{
			for (Parametre p : methode.getParametre())
			{
				parametre += p.toString() + ", ";
			}
			// Enleve le ", " en trop
			parametre = parametre.substring( 0, parametre.length() -2 );
		}
		
		String sRet = methode.getSymbole() + " ";

		if( methode.getType() == null || methode.getType().equals( "void" ) )
			sRet += methode.getNom() + " (" + parametre + ")";
		else
			sRet += String.format( "%-" + longueurNom + "s", methode.getNom() + " (" + parametre + ")" ) +
			        " :" + methode.getType() + ((methode.estLectureUnique()) ? " {Gelé}" : "");

		return sRet;
	}
}
