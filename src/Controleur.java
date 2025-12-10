import metier.Retroconception;
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

		//ctrl.ouvrirFichier("./ressources/PointRetK.java");

		ctrl.ouvrirDossier("./ressources");
	}
}