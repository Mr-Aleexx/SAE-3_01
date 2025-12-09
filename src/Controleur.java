import metier.Retroconception;
import metier.Stereotype;
import metier.Attribut;
import metier.Methode;
public class Controleur
{
	private Retroconception metier;
	//private UneIHI ihm;

	public Controleur()
	{
		this.metier = new Retroconception();
	}

	public void ouvrirDossier(String cheminDossier)
	{
		this.metier.ouvrirDossier(cheminDossier);
	}

	public void ouvrirFichier(String cheminFichier)
	{
		this.metier.ouvrirFichier(cheminFichier);
	}
	

	public String toString()
	{
		return this.metier.toString();
	}

	public static void main(String[] args)
	{
		Controleur ctrl = new Controleur();

		// Stereotype s = new Stereotype("public", true, false, false, "class", "Caca");
		// s.ajouterAttribut( new Attribut( "private", true, false, "int", "pipi" ) );
		// s.ajouterAttribut( new Attribut( "private", false, true, "String", "prout" ) );
		// s.ajouterMethode( new Methode( "package", true, false, false, "boolean", "fairepipi" ) );
		// System.out.println( s.toString() );

		ctrl.ouvrirFichier("./ressources/Point.java");

		ctrl.ouvrirDossier("./ressource");

		System.out.println(ctrl.toString());
	}
}