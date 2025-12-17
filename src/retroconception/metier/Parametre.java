package retroconception.metier;

/**
 * Représentation des paramètres d'une méthode
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */

public record Parametre ( String type, String nom )
{
	public String toString()
	{
		return this.nom + " : " + this.type;
	}
}