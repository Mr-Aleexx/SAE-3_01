package src;

import java.util.List;
import src.metier.Association;
import src.metier.Classe;
import src.metier.Retroconception;
import src.ihm.IHMCUI;

public class Controleur
{
	private Retroconception metier;
	private IHMCUI          ihm;

	public Controleur()
	{
		this.metier = new Retroconception();
		this.ihm    = new IHMCUI( this );
	}


	public List<Classe>      getLstClasses     () { return this.metier.getLstClasses();      }
	public List<Association> getLstAssociations() { return this.metier.getLstAssociations(); }
	

	public void ouvrirDossier(String cheminDossier)
	{
		this.metier.ouvrirDossier(cheminDossier);
	}

	public void ouvrirFichier(String cheminFichier)
	{
		this.metier.ouvrirFichier(cheminFichier);
	}

	public String afficher()
	{
		return this.ihm.afficher();
	}

	public static void main(String[] args)
	{
		Controleur ctrl = new Controleur();

		//ctrl.ouvrirFichier("./tests/Abstract.java");

		ctrl.ouvrirDossier( "./tests/" );

		System.out.println( ctrl.afficher() );
	}
	
}