public abstract class Abstract {
	protected String nom;

	public Abstract(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}
	public abstract double getPerimetre();

	public final String abstrait()
	{
		return "immagine";
	}

	public abstract double getAire();


	// private final class Reel extends Abstract
	// {
	// 	public Reel()
	// 	{
	// 		super("world");
	// 	}
	// }
}
