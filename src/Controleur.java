package src;

import src.metier.Retroconception;

public class Controleur
{
	private Retroconception metier;
	//private UneIHI ihm;

	public Controleur()
	{
		this.metier = new Retroconception();
		metier.printLstAssociation();
	}

	//public Integer              getIndiceStereotpe(int x, int y) { return this.metier.getIndiceStereotpe(x, y); }
	//public int                  getNbStereotype   ()             { return this.ensStereotype.size       ();     }
	//public Stereotype           getStereotype     (int id)       { return this.metier.getStereotype     (id);   }
	//public int                  getNbStereotype   ()             { return this.metier.getNbStereotype   ();     }

	public void deplacerStereo(int id, int x, int y)
	{
		//this.metier.deplacerStereo(id, x, y);
	}

	public void ouvrirDossier(String cheminDossier)
	{
		this.metier.ouvrirDossier(cheminDossier);
	}

	public void ouvrirFichier(String cheminFichier)
	{
		this.metier.ouvrirFichier(cheminFichier);
	}

	public String getErreur()
	{ 
		return "";
		//return this.metier.getErreur();
		
	}

	public String toString()
	{
		return this.metier.toString();
	}

	public static void main(String[] args)
	{
		Controleur ctrl = new Controleur();

		ctrl.ouvrirFichier("./tests/Commentaires.java");
		
		//ctrl.ouvrirDossier("./tests");
	}
	
}