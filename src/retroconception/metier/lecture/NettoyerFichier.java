package retroconception.metier.lecture;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

final class NettoyerFichier
{
	private NettoyerFichier() {}

	public static List<String> nettoyerFichier( String fichier )
	{
		/*
		 * Première Lecture : enleve les commentaire et les corps des méthodes
		 */
		Scanner      sc;
		String       ligne;
		List<String> fichierClean       = new ArrayList<String>();
		boolean      estDansCommentaire = false;
		boolean      estDansParametre   = false;
		boolean      ignorerLigne, estTableau;
		int          niveauTableau      = 0;
		int          niveauAcolade      = 0;
		try
		{
			sc = new Scanner( new FileInputStream( fichier ), "UTF8" );
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine().trim();
				
				estTableau = false;
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

				/* --------------------- */
				/* Gestion des accolades */
				/* --------------------- */

				// Gere les tableaux d'attributs static final
				if ( ligne.indexOf("=") != -1 && ligne.indexOf("{") != -1 &&
				     ligne.indexOf("=") < ligne.indexOf("{"))
				{
					niveauTableau++;
					estTableau = true;
				}
				else if( niveauTableau > 0 )
				{
					if ( ligne.contains( "}" ) ) niveauTableau--;
					ligne = fichierClean.remove(fichierClean.size() - 1) + ligne;

					estTableau = true;
				}
				/* ----------------------------- */
				/* Gestion des corps de méthodes */
				/* ----------------------------- */
				else
				{
					// Enleve les methode écrite sur 1 ligne
					if ( ligne.contains("{") && ligne.contains("}") )
						ligne = ligne.substring( 0, ligne.indexOf("{")     ) +
								ligne.substring(    ligne.indexOf("}") + 1 );

					// On déclare une liste de int pour gérer le niveau d'acolade, soit si il y a
					// plusieurs classes ou une declaration de methode locale ou les bloc d'instances
					
					// Vérifier si on doit ignorer cette ligne avant de modifier niveauAcolade
					ignorerLigne = (niveauAcolade >= 2);

					if ( ligne.contains("{") ) niveauAcolade++;

					if ( ligne.contains("}") )
					{
						niveauAcolade--;
						continue;
					}

					// Pour le format R&K : ignorer les lignes dans les corps de méthode
					if ( ignorerLigne ) continue;
				}

				

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
				if ( ! estTableau ) ligne = ligne.replace("{", "");

				// Enleve les ";" des methode abstract pour faciliter la lecture
				if ( ligne.contains( "abstract" ) && ligne.contains( ";" ))
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

		// for ( String s : fichierClean )
		// 	System.out.println( s );

		return fichierClean;
	}
}
