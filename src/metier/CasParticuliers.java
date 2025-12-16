package src.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CasParticuliers {
	
	public static void gereParametres(Methode m, String ligne)
	{
		String parametre = "";
		Scanner sc;
		String mot, type, nom;
		List<String> lstType, lstParametre;

		parametre = ligne.substring(ligne.indexOf("(") + 1, ligne.indexOf(")"));

		// Si sans paramètre
		if (parametre.equals(""))
			return;

		lstParametre = AnalyseurJava.decomposeurType(parametre, ',');

		// Cas ou 1 seul parametre
		if (lstParametre.size() == 1)
		{

			lstType = AnalyseurJava.decomposeurType(parametre, ' ');

			type = lstType.get(0);
			nom = lstType.get(1);

			m.ajouterParametres(new Parametre(type, nom));

			return;
		}

		// Cas ou plusieurs parametres

		for (String s : lstParametre)
		{
			s = s.trim();

			lstType = AnalyseurJava.decomposeurType(s, ' ');

			type = lstType.get(0).replace(",", "");
			nom = lstType.get(1).replace(",", "");

			m.ajouterParametres(new Parametre(type, nom));
		}
	}

	public static void gereExtendsImplements(Classe c, Scanner sc, String ligne)
	{
		String mot, mere;

		mot = sc.next();

		if (mot.equals("extends"))
		{
			mere = sc.next();
			c.setMere(mere);

			mot = sc.next();
		}

		if (mot.equals("implements"))
		{
			int indexImplement = ligne.indexOf("implements") + "implements".length();

			String detecteImplements = ligne.substring(indexImplement, ligne.length()).trim();
			Scanner scImplements = new Scanner(detecteImplements);
			scImplements.useDelimiter("\\,");

			while (scImplements.hasNext())
			{
				c.ajouterImplementations(scImplements.next().trim());
			}
		}
	}

	public static void gereRecord(Classe c, String ligne)
	{
		/* --------------------- */
		/* Gestion des attributs */
		/* --------------------- */

		String type, nom;
		Methode methode;
		List<String> lstType;
		List<Parametre> lstParametres = new ArrayList<Parametre>();

		String attributs = ligne.substring(ligne.indexOf("(") + 1, ligne.indexOf(")"));
		List<String> lstAttributs = AnalyseurJava.decomposeurType(attributs, ',');

		System.out.println(lstAttributs.isEmpty());

		if (lstAttributs.isEmpty())
			return;

		for (String s : lstAttributs)
		{
			lstType = AnalyseurJava.decomposeurType(s, ' ');

			type = lstType.get(0);
			nom = lstType.get(1).replace(",", "");

			c.ajouterAttribut(new Attribut("private", false, true, type, nom));
			lstParametres.add(new Parametre(type, nom));
		}

		/* -------------------- */
		/* Gestion des methodes */
		/* -------------------- */

		// Constructeur

		if (!lstParametres.isEmpty())
		{
			methode = new Methode("public", false, false, false, null, c.getNom());
			methode.setParametres(lstParametres);

			c.ajouterMethode(methode);
		}

		// Getters

		List<Attribut> ensAttributs = c.getAttributs();

		if (!ensAttributs.isEmpty())
		{
			for (Attribut a : ensAttributs)
			{
				c.ajouterMethode(new Methode("public", false, false, false, a.getType(), a.getNom()));
			}
		}

	}
}
