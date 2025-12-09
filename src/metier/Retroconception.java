package metier;

import java.util.ArrayList;
import java.util.List;

public class Retroconception
{
	private List<Stereotype> lstStereotype;
	
	public Retroconception()
	{
		this.lstStereotype = new ArrayList<Stereotype>();
	}
	
	public void ouvrirDossier(String cheminDossier)
	{
		this.lstStereotype = AnalyseurJava.analyserDossier(cheminDossier);
	}

	public void ouvrirFichier(String fichier)
	{
		
		this.lstStereotype.add( AnalyseurJava.analyserFichier(fichier) );
	}

	public Stereotype getSter() { return this.lstStereotype.get(0); } ///~~~~~~~~~~~~~
	
	
	
	
	public static void creationAssociation()
	{

	}

	public String toString()
	{
		String sRet = "";

		for ( Stereotype s : this.lstStereotype )
		{
			sRet += s.toString();
		}

		return sRet;
	}
}