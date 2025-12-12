package src.metier;
public record Parametre ( String type, String nom )
{
	public String toString()
	{
		return this.nom + " : " + this.type;
	}
}