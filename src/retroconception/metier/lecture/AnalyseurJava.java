package retroconception.metier.lecture;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import retroconception.metier.Attribut;
import retroconception.metier.Classe;
import retroconception.metier.Methode;
import retroconception.metier.Parametre;

/**
 * Permet d'analyser un fichier java et d'en extraire :  Le nom de la classe, les attributs de la classe, les méthodes de la classe
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO Marta, CONSTANTIN Alexis 
 * @version Etape 4
 * @since 08-12-2025
 */

public final class AnalyseurJava
{
	private static final String[] ENS_VISI = new String[] { "public", "private", "protected", "default" };
	private static final String[] ENS_STER = new String[] { "interface", "enum", "record"               };

	private AnalyseurJava() {}

	/**
	 * Retourne un Objet Classe à partir d'un fichier java
	 * @param fichier nom du fichier
	 * @return
	 */
	public static Classe analyserFichier(String fichier)
	{
		Classe   classe = null;
		Attribut attribut;
		Methode  methode;

		/* Caractéristiques des Classes, attributs et methodes */
		String  visibilite;
		boolean statique;
		boolean lectureUnique;
		boolean abstraite;
		String  stereotype;
		String  nom;
		String  type;

		/* Variables Utilitaires */
		Scanner sc;
		boolean aGeneralization;
		int     index;
		List<String> lstAttribut;

		for ( String ligne : NettoyerFichier.nettoyerFichier( fichier ) )
		{
			visibilite    = "package";
			statique      = false;
			lectureUnique = false;
			abstraite     = false;
			stereotype    = "";
			nom           = "";

			sc = new Scanner(ligne);
			sc.useDelimiter("\\s+");

			String mot = sc.next();

			// Gere la visibilite de la ligne qui par default est a package
			for (String visi : AnalyseurJava.ENS_VISI)
				if ( mot.equals(visi) ) visibilite = visi;

			// Gere les combinaisons des static, final et abstract possibles
			aGeneralization = false;
			while ( sc.hasNext() )
			{
				if ( aGeneralization || ! visibilite.equals("package") ) mot = sc.next();

				aGeneralization = false;

				if ( mot.equals( "static"   ) ) statique      = aGeneralization = true;
				if ( mot.equals( "final"    ) ) lectureUnique = aGeneralization = true;
				if ( mot.equals( "abstract" ) ) abstraite     = aGeneralization = true;

				if ( ! aGeneralization ) break;
			}
			
			if ( classe == null )
			{
				// Stereotypes
				for (String ster : AnalyseurJava.ENS_STER)
					if ( mot.equals( ster ) ) stereotype = ster;

				// Gere si les parametre sont collé au record
				mot = sc.next();

				index =  mot.indexOf( "(" );
				if ( index != -1 ) mot = mot.substring( 0, index );

				nom = mot;

				classe = new Classe( visibilite, statique, lectureUnique, abstraite, stereotype, nom );

				switch( stereotype )
				{
					case "record" ->
					{
						GereStereotype.gereRecord( classe, ligne );
					}
					case "enum" ->
					{

					}
					default ->
					{

					}
				}

				if ( sc.hasNext() ) AnalyseurJava.gereExtendsImplements( classe, sc, ligne );
			}
			else
			{
				// Attributs
				// System.out.println("ligne  :" + ligne);
				if ( ligne.contains(";") )
				{
					ligne = ligne.substring( ligne.indexOf( mot ) );

					lstAttribut = AnalyseurJava.decomposeurType( ligne , ' ' );

					type = lstAttribut.get(0);
					nom  = lstAttribut.get(1).replace( ";" , "" );

					// gere le cas où on initialise l'attribut sans mettre d'espace au "="
					if ( nom.contains( "=" ) ) nom = nom.substring( 0 , nom.indexOf( "=" ) );
					
					if ( classe.getStereotype().equals( "interface" ) )
					{
						attribut = new Attribut( "public", true, true, type, nom );
					}
					else
					{
						attribut = new Attribut( visibilite, statique, lectureUnique, type, nom );
					}

					classe.ajouterAttribut( attribut ) ;
					
				}
				
				// Le else est obliger pour eviter les initialisation d'attribut par l'appel de methode
				else if ( ligne.contains("(") && ! GereStereotype.estMethodeStandardRecord(classe, ligne) )
				{
					// Recuperation du nom de la methode
					index = mot.indexOf("(");
					if (index != -1)
						mot = mot.substring(0, index);

					// Si c'est le controleur
					if ( classe.getNom().equals(mot))
					{
						type = null;
						nom = mot;
					}
					else
					{
						type = mot;

						mot = sc.next();

						index = mot.indexOf("(");
						if (index != -1)
							nom = mot.substring(0, index);
					}

					if ( visibilite.equals( "default" ) )
					{
						visibilite = "public";
						stereotype = "default";
					}
					
					if ( classe.getStereotype().equals( "interface" ) )
					{
						if ( visibilite.equals( "package" ) ) visibilite = "public";
						
						if ( visibilite.equals( "package" ) && ! statique )
						{
							abstraite = true;
						}
					}

					methode = new Methode(visibilite, statique, lectureUnique, abstraite, stereotype, type, nom );

					AnalyseurJava.gereParametres(methode, ligne);
					classe.ajouterMethode(methode);
					
				}
				
			}
			sc.close();
		}

		return classe;
	}

	private static void gereParametres(Methode m, String ligne)
	{
		String       parametre;
		String       type, nom;
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

			type = lstType.get(0);
			nom = lstType.get(1).replace(",", "");

			m.ajouterParametres(new Parametre(type, nom));
		}
	}

	private static void gereExtendsImplements(Classe c, Scanner sc, String ligne)
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

	// Permet de gerer les types Hash avec plusieurs parametres dedanss
	public static List<String> decomposeurType(String ligne, char delimiteur)
	{
		List<String> lstRet = new ArrayList<String>();
		int niveauChevron = 0;
		String res = "";

		for (int i = 0; i < ligne.length(); i++)
		{
			// Caractère en cours
			char c = ligne.charAt(i);
			if (c == '<') niveauChevron++;
			if (c == '>') niveauChevron--;

			res += c;

			if (niveauChevron == 0 && c == delimiteur)
			{
				res = res.trim();

				if (!res.equals(""))
				{
					// Gere le cas ou il y a des espace entre le type et le "<"
					if (res.charAt(0) == '<')
						lstRet.add(lstRet.remove(lstRet.size() - 1) + res);
					else
						lstRet.add(res);
				}
				res = "";
			}
		}
		// Ajoute la derniere iteration
		if (!res.equals(""))
			lstRet.add(res);

		return lstRet;
	}
}