package src;

import src.metier.Retroconception;

public class Controleur
{
	private Retroconception metier;

	public Controleur()
	{
		this.metier = new Retroconception();
	}

	public void ouvrirDossier(String cheminDossier)
	{
		this.metier.ouvrirDossier(cheminDossier);
		System.out.println(metier.toString());
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

		//ctrl.ouvrirFichier("./tests/Abstract.java");

		ctrl.ouvrirDossier( "./tests/" );
	}
	
}