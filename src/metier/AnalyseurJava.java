package src.metier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Permet d'analyser un fichier java et d'en extraire :  Le nom de la classe, les attributs de la classe, les méthodes de la classe
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO Marta, CONSTANTIN Alexis 
 * @version Etape 4
 * @since 08-12-2025
 */

public class AnalyseurJava
{
	private static final String[] ENS_VISI = new String[] { "public", "private", "protected" };
	private static final String[] ENS_STER = new String[] { "interface", "enum", "record"    };

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

		for ( String ligne : AnalyseurJava.nettoyerFichier( fichier ) )
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
						CasParticuliers.gereRecord( classe, ligne );
					}
				}

				if ( sc.hasNext() ) CasParticuliers.gereExtendsImplements( classe, sc, ligne );
			}
			else
			{
				// Attributs
				if ( ligne.contains(";") )
				{
					ligne = ligne.substring( ligne.indexOf( mot ) );

					lstAttribut = AnalyseurJava.decomposeurType( ligne , ' ' );

					type = lstAttribut.get(0);
					nom  = lstAttribut.get(1).replace( ";" , "" );

					// gere le cas où on initialise l'attribut sans mettre d'espace au "="
					if ( nom.contains( "=" ) ) nom = nom.substring( 0 , nom.indexOf( "=" ) );

					attribut = new Attribut( visibilite, statique, lectureUnique, type, nom );
					classe.ajouterAttribut(attribut);
				}
				
				// Le else est obliger pour eviter les initialisation d'attribut par l'appel de methode
				else if ( ligne.contains("(") && ! CasParticuliers.estFonctionPredefinie(ligne) )
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

					methode = new Methode(visibilite, statique, lectureUnique, abstraite, type, nom );

					CasParticuliers.gereParametres(methode, ligne);

					classe.ajouterMethode(methode);
				}
				
			}
			sc.close();
		}

		return classe;
	}

	private static List<String> nettoyerFichier( String fichier )
	{
		/*
		 * Première Lecture : enleve les commentaire et les corps des méthodes
		 */
		Scanner      sc;
		String       ligne;
		List<String> fichierClean       = new ArrayList<String>();
		boolean      estDansCommentaire = false;
		boolean      estDansParametre   = false;
		int          niveauAcolade      = 0;
		try
		{
			sc = new Scanner( new FileInputStream( fichier ), "UTF8" );
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();

				/* ------------------------ */
				/* Gestion des commentaires */
				/* ------------------------ */

				// Le commentaire //
				if ( ligne.contains("//") ) ligne = ligne.substring( 0, ligne.indexOf("//") );

				// Verif de si on est a la fin commentaire */
				if ( estDansCommentaire && ligne.contains("*/") )
				{
					ligne = ligne.substring( ligne.indexOf("*/") + 2 ) ;
					estDansCommentaire = false;
				}

				// Verif si on est dans un commentaire /* */ et on passe a la
				// prochaine itération
				if ( estDansCommentaire ) continue;

				// Le commentaire /* */ sur la même ligne
				if ( ligne.contains("/*") && ligne.contains("*/") )
					ligne = ligne.substring( 0, ligne.indexOf("/*")     ) +
					        ligne.substring(    ligne.indexOf("*/") + 2 );

				// Verif de si on commence un commentaire /*
				if ( ligne.contains("/*") )
				{
					ligne = ligne.substring( 0, ligne.indexOf("/*") );
					estDansCommentaire = true;
				}

				// Enleve la documentation Java doc avec les overides...
				// en partant du principe qu'elle n'est pas sur la même ligne que le code
				if ( ligne.contains("@") ) continue;

				/* ------------------------------- */
				/* Gestion des imports et packages */
				/* ------------------------------- */

				if ( ligne.contains("import") ) ligne = ligne.substring( 0, ligne.indexOf("import")      ) +
				                                        ligne.substring(    ligne.indexOf(";"     ) + 1  );

				if ( ligne.contains("package") ) ligne = ligne.substring( 0, ligne.indexOf("package")      ) +
				                                         ligne.substring(    ligne.indexOf(";"      ) +  1 );

				/* ----------------------------- */
				/* Gestion des corps de méthodes */
				/* ----------------------------- */

				// Enleve les methode écrite sur 1 ligne
				if ( ligne.contains("{") && ligne.contains("}") )
					ligne = ligne.substring( 0, ligne.indexOf("{")     ) +
					        ligne.substring(    ligne.indexOf("}") + 1 );

				// On déclare une liste de boolean pour gérer le niveau d'acolade, soit si il y a
				// plusieurs classes ou une declaration de methode locale
				// ou les bloc d'initialisations d'attributs
				
				if ( ligne.contains("{") ) niveauAcolade++;

				if ( ligne.contains("}") )
				{
					niveauAcolade--;
					continue;
				}

				// Pour le format R&K
				if ( niveauAcolade >= 2 && !ligne.contains("{") ) continue;

				/* ------------------------------------------------------- */
				/* Gestion des parametres de methodes sur plusieurs lignes */
				/* ------------------------------------------------------- */

				if (estDansParametre)
				{
					if (ligne.contains(")"))
						estDansParametre = false;

					ligne = fichierClean.remove(fichierClean.size() - 1) + ligne;
				}

				if ( ligne.contains("(") && !ligne.contains(")") )
					estDansParametre = true;

				/* --------------- */
				/* Autres Gestions */
				/* --------------- */

				// Enleve les acolade en trop
				ligne = ligne.replace("{", "");

				// Enleve les ";" des methode abstract pour faciliter la lecture
				if ( ligne.contains( "(" ) && ligne.contains( ";" ) )
					ligne = ligne.replace( ";" , "" );

				// Enleve les lignes vides
				if ( ligne.trim().equals("") ) continue;

				/* ----------------- */
				/* Ajout de la ligne */
				/* ----------------- */
				
				fichierClean.add(ligne);
			}
			sc.close();
		}
		catch (FileNotFoundException e){}
		
		return fichierClean;
	}

	// Permet de gerer les types Hash avec plusieurs parametres dedanss
	public static List<String> decomposeurType( String ligne, char delimiteur )
	{
		List<String> lstRet = new ArrayList<String>();
		int          niveauChevron = 0;
		String       res = "";
		
		for (int i = 0 ; i < ligne.length() ; i++ )
		{
			// Caractère en cours
			char c = ligne.charAt(i);
			if ( c == '<' ) niveauChevron ++;
			if ( c == '>' ) niveauChevron --;
			
			res += c;
			
			if( niveauChevron == 0 && c == delimiteur )
			{
				res = res.trim();

				if ( ! res.equals("") )
				{
					// Gere le cas ou il y a des espace entre le type et le "<"
					if ( res.charAt(0) == '<' )
						lstRet.add( lstRet.remove( lstRet.size() - 1 ) + res );
					else
						lstRet.add( res );
				}
				res = "";
			}
		}
		// Ajoute la derniere iteration
		if ( ! res.equals("") ) lstRet.add(res);
		
		return lstRet;
	}
}