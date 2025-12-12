package src.ihm;

import src.Controleur;
import src.metier.Association;
import src.metier.Classe;

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
