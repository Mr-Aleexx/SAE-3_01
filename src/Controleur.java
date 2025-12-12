package src;

import java.util.List;
import src.metier.Association;
import src.metier.Classe;
import src.metier.Retroconception;
import src.ihm.IHMCUI;

/**
 * Controleur de la classe qui lance le CUI pour l'instant
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Martha, CONSTANTIN Alexis
 * @version Etape 4
 * @since 08-12-2025
 */
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

		ctrl.ouvrirDossier( "./data/" );

		System.out.println( ctrl.afficher() );
	}
	
}