package retroconception.metier.lecture;

import java.util.ArrayList;
import java.util.List;
import retroconception.metier.Attribut;
import retroconception.metier.Classe;
import retroconception.metier.Methode;
import retroconception.metier.Parametre;

final class GereStereotype
{
	private GereStereotype() {}

	/**
	 * Gère la création d'une énumération à partir d'une ligne de code.
	 * Extrait les valeurs de l'énumération et les ajoute comme attributs à la classe.
	 * @param c     La classe représentant l'énumération
	 * @param ligne La ligne contenant la déclaration de l'énumération
	 */
	public static void gereEnum(Classe c, String ligne) {
		
	}

	/**
	 * Gère la création d'un record à partir d'une ligne de code. Extrait
	 * les valeurs de l'énumération et les ajoute comme attributs à la classe.
	 * @param c La classe représentant le record
	 * @param ligne La ligne contenant la déclaration de l'énumération
	 */
	public static void gereRecord(Classe c, String ligne)
	{
		/* --------------------- */
		/* Gestion des attributs */
		/* --------------------- */

		String          type, nom;
		Methode         methode;
		List<String>    lstType;
		List<Parametre> lstParametres = new ArrayList<Parametre>();

		String attributs = ligne.substring(ligne.indexOf("(") + 1, ligne.indexOf(")"));
		List<String> lstAttributs = AnalyseurJava.decomposeurType(attributs, ',');

		if (lstAttributs.isEmpty())
			return;

		for (String s : lstAttributs)
		{
			lstType = AnalyseurJava.decomposeurType(s, ' ');

			type = lstType.get(0);
			nom = lstType.get(1).replace(",", "");

			c.ajouterAttribut(new Attribut("private", false, true, type, nom, ""));
			lstParametres.add(new Parametre(type, nom));
		}

		/* -------------------- */
		/* Gestion des methodes */
		/* -------------------- */

		// Constructeur

		if (!lstParametres.isEmpty())
		{
			methode = new Methode("public", false, false, false, null, null, c.getNom());
			methode.setParametres(lstParametres);

			c.ajouterMethode(methode);
		}

		// Getters

		List<Attribut> ensAttributs = c.getAttributs();

		if (!ensAttributs.isEmpty())
		{
			for (Attribut a : ensAttributs)
			{
				c.ajouterMethode(new Methode("public", false, false, false, null, a.getType(), a.getNom()));
			}
		}

	}

	public static boolean estMethodeStandardRecord(Classe c, String ligne)
	{
		return c.getNom().equals("record") && ( ligne.contains("equals"  ) ||
		                                        ligne.contains("hashCode") ||
		                                        ligne.contains("toString") );
	}

	public static boolean estMethodeAbstraiteInterface( Classe c, String ligne )
	{
		return c.getStereotype().equals("interface") && ligne.contains("(") && ! ligne.contains("=") ;
	}
}
	