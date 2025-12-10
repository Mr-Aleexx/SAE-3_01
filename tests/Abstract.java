public abstract class Abstract {
	protected String nom;

	public Abstract(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public abstract double getAire();
	public abstract double getPerimetre();
}
