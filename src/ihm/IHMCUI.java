package src.ihm;

import src.Controleur;
import src.metier.Association;
import src.metier.Attribut;
import src.metier.Classe;
import src.metier.Methode;

public class IHMCUI
{
	private Controleur ctrl;

	public IHMCUI(Controleur ctrl)
	{
		this.ctrl = ctrl;
		
	}

	public static String afficher()
	{
		String sRet = "";

		int tmp;

		int    nbCharPlusLong;
		int    typeAttributPlusLong = 0;
		int    nomAttributPlusLong  = 0;
		int    typeMethodePlusLong  = 0;
		int    nomMethodePlusLong   = 0;
		
		String separation;
		String sMeth;

		//Prévérification de taille
		for (Attribut attrib : )
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
		
		////////////////////////////////////////////

		for ( Classe classe : this.ctrl.getLstClasses() )
			sRet += classe.toString() + "\n";

		sRet += "Associations:\n";

		for (int i = 0 ; i < this.ctrl.getLstAssociations().size() ; i++)
			sRet += "Association " + (i + 1) + ": " + this.ctrl.getLstAssociations().get(i).afficherAssociations() + "\n";

		sRet += "Heritage:\n";
		for ( Classe classe : this.ctrl.getLstClasses() )
			if( classe.getMere() != null ) sRet += classe.getNom() + " hérite de " + classe.getMere();

		sRet += "\nImplements:\n";
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

	private String afficherAssociations(Association asso)
	{
		return asso.getTypeAsso() +  ((asso.getTypeAsso().equals("unidirectionnelle")) ? " de " : " entre ") + asso.getClasse1().getNom() + asso.getMultiplicite1()
		                     +  ((asso.getTypeAsso().equals("unidirectionnelle")) ? " vers " : " et " ) + asso.getClasse2().getNom() + asso.getMultiplicite2();
	}

}
