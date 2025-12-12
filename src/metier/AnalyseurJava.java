package src.metier;

import  java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnalyseurJava
{
	private static final String[] ENS_VISI = new String[] { "public", "private", "protected"       };
	private static final String[] ENS_STER = new String[] { "class", "interface", "enum", "record" };

	/**
	 * Retourne un Objet Stereotype à partir d'un fichier java
	 * @param fichier nom du fichier
	 * @return
	 */
	public static Stereotype analyserFichier(String fichier)
	{
		Scanner       sc;
		String        fichierClean, ligne;
		boolean       estDansCommentaire;
		List<Boolean> niveauAcolade;

		/* Première Lecture : enleve les commentaire et les corps des méthodes
		*/
		fichierClean       = "";
		estDansCommentaire = false;
		niveauAcolade      = new ArrayList<Boolean>();
		try
		{
			sc = new Scanner( new FileInputStream ( fichier ), "UTF8" );
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine().trim();
				
				/* ------------------------ */
				/* Gestion des commentaires */
				/* ------------------------ */

				// Le commentaire //
				if ( ligne.contains( "//" ) )
				{
					ligne = ligne.substring( 0 , ligne.indexOf( "//" ) );
				}

				// Verif de si on est a la fin commentaire */
				if ( estDansCommentaire && ligne.contains( "*/" ) )
				{
					ligne = ligne.substring( ligne.indexOf( "*/" ) + 2 );
					estDansCommentaire = false;
				}

				// Verif si on est dans un commentaire /* */ et on passe a la prochaine itération
				if ( estDansCommentaire ) continue;
				
				// Le commentaire /* */ sur la même ligne
				if ( ligne.contains( "/*" ) && ligne.contains( "*/" ) )
					ligne = ligne.substring( 0, ligne.indexOf( "/*" ) ) + ligne.substring( ligne.indexOf( "*/" ) + 2 );

				// Verif de si on commence un commentaire /* 
				if ( ligne.contains( "/*" ) )
				{
					ligne = ligne.substring( 0, ligne.indexOf( "/*" ) );
					estDansCommentaire = true;
				}

				// Enleve la documentation Java doc avec les overides...
				// en partant du principe qu'elle n'est pas sur la même ligne
				if( ligne.contains( "@" ) )
					continue;

				/* ------------------------------- */
				/* Gestion des imports et packages */
				/* ------------------------------- */

				if ( ligne.contains( "import" ) )
					ligne = ligne.substring( 0, ligne.indexOf( "import" ) ) + ligne.substring( ligne.indexOf(";") + 1 );

				if ( ligne.contains( "package" ) )
					ligne = ligne.substring( 0, ligne.indexOf( "package" ) ) + ligne.substring( ligne.indexOf(";") + 1 );

				/* ----------------------------- */
				/* Gestion des corps de méthodes */
				/* ----------------------------- */
				
				// On déclare une liste de boolean pour gérer si il y a plusieurs classes et la declaration de methode locale
				// et les bloc d'initialisations d'attributs

				// Enleve les methode écrite sur 1 ligne
				if ( ligne.contains( "{" ) && ligne.contains( "}" ) )
					ligne = ligne.substring( 0, ligne.indexOf( "{" ) ) + ligne.substring( ligne.indexOf( "}" ) + 1 );

				
				if ( ligne.contains( "{" ) ) niveauAcolade.add( true );

				if ( ligne.contains( "}" ) )
				{
					niveauAcolade.remove(0);
					ligne = "";
				}

				// Pour le format R&K
				if ( niveauAcolade.size() >= 2 && !ligne.contains( "{" ) ) ligne = "";
				
				// Enleve les acolade en trop
				ligne = ligne.replace( "{", "" );

				// Enleve les lignes vides
				if ( !ligne.equals("") )
					fichierClean += ligne + "\n";
			}
			sc.close();
		}
		catch (FileNotFoundException e) {}

		/* ---------------------- */
		/* Creation du stereotype */
		/* --------- ------------ */
		Scanner    scLigne, scLstParametre, scParametre;
		Stereotype stereotype = null;
		Attribut   attribut;
		Methode    methode;
		String     mot, ligneParametre, typeParam, nomParam;
		boolean    aGeneralization;
		int        index;

		sc = new Scanner( fichierClean );
		sc.useDelimiter( "\\n" );

		while ( sc.hasNext() )
		{
			ligne = sc.next();

				
			// Stereotype
			for ( String ster : AnalyseurJava.ENS_STER )
				if( ligne.contains(ster) )
				{
					stereotype = ( Stereotype )AnalyseurJava.initAll("Stereotype", "", ligne);
				}

			// Attributs
			if ( ligne.contains( ";" ) && ( ! ligne.contains( "(" ) ||
			                                  ligne.contains( "(" ) && ligne.contains( "=" ) ) )
			{
				attribut = ( Attribut )AnalyseurJava.initAll( "Attribut", stereotype.getNom(), ligne );
				stereotype.ajouterAttribut( attribut );
			}

			// Methode ( gestion des methodes abstract )
			else if ( ligne.contains( "(" )  )
			{
				methode = ( Methode )AnalyseurJava.initAll( "Methode", stereotype.getNom(), ligne );
				stereotype.ajouterMethode( methode );
			}
		}
		sc.close();

		return stereotype;
	}

	private static Object initAll( String typeInit, String nomStereotype, String ligne )
	{
		/* Caractéristiques des stereotypes, attributs et methodes */
		String  visibilite    = "package";
		boolean statique      = false;
		boolean lectureUnique = false;
		boolean abstraite     = false;
		String  type          = "";
		String  nom           = "";
		String  mere          = null;

		boolean    aGeneralization;
		int        index, indexAvt, indexAps;
		String     parametre = "";
		Stereotype s = null;

		Scanner sc = new Scanner( ligne );
		sc.useDelimiter("\\s+");

		String  mot = sc.next();

		for ( String visi : AnalyseurJava.ENS_VISI )
			if( mot.equals(visi) )visibilite = visi;

		aGeneralization = false;
		while ( sc.hasNext() )
		{
			if ( aGeneralization || ! visibilite.equals("package") )
				mot = sc.next();
			
			aGeneralization = false;

			if( mot.equals( "static"   ) ) statique      = aGeneralization = true;
			if( mot.equals( "final"    ) ) lectureUnique = aGeneralization = true;
			if( mot.equals( "abstract" ) ) abstraite     = aGeneralization = true;

			if( ! aGeneralization ) break;
		}

		switch ( typeInit )
		{
			case "Stereotype" ->
			{
				for ( String ster : AnalyseurJava.ENS_STER )
					if( mot.equals(ster) )type = ster;
				
				mot = sc.next().replace( "{", "");
				
				nom = mot;

				while ( sc.hasNext() )
				{
			
					mot = sc.next();

					if( mot.equals( "extends"   ) )
					{
						mot = sc.next();
						mere = mot;
					}

					s = new Stereotype( visibilite, statique, lectureUnique, abstraite, type, nom, mere );

					if( mot.equals( "implements") )
					{
						int indexImplement = ligne.indexOf( "implements" ) + "implements".length();

						String detecteImplements = ligne.substring( indexImplement ,ligne.length() ).trim();
						Scanner scImplements = new Scanner( detecteImplements );
						scImplements.useDelimiter("\\,");

						System.out.println( detecteImplements );
						
						while ( scImplements.hasNext() )
						{
							s.ajouterImplementations( scImplements.next() );
						}
						break;
					}
				}
				
				sc.close();

				return s;
			}
			case "Attribut" ->
			{
				type = mot;
				nom  = sc.next().replace( ";" , "" );

				sc.close();

				return new Attribut( visibilite, statique, lectureUnique, type, nom );
			}
			case "Methode" ->
			{
				// Recuperation du nom de la methode
				index = mot.indexOf( "(" );
				if ( index != -1 ) mot = mot.substring( 0, index );
				
				// Si c'est le controleur
				if ( nomStereotype.equals( mot ) )
				{
					type = null;
					nom  = mot;
				}
				else
				{
					type = mot;
					
					mot  = sc.next();

					index = mot.indexOf( "(" );
					if ( index != -1 ) nom = mot.substring( 0, index );
				}
				

				Methode m =  new Methode( visibilite, statique, lectureUnique, abstraite, type, nom );

				indexAvt = ligne.indexOf( "(" );
				indexAps = ligne.indexOf( ")" );
				if ( indexAvt != -1 && indexAps != -1 )
					parametre = ligne.substring( indexAvt+1, indexAps );

				// Si sans paramètre
				if ( parametre.equals("") ) return m;

				//Cas ou 1 seul parametre
				if ( ! parametre.contains( "," ) )
				{
					sc = new Scanner( parametre );
					sc.useDelimiter("\\s+");

					type = sc.next();
					nom  = sc.next();

					m.ajouterParametres( new Parametre(type, nom) );

					return m;
				}

				//Cas ou plusieurs parametres

				Scanner scParametre = new Scanner( parametre );
				scParametre.useDelimiter("\\,");

				while ( scParametre.hasNext() )
				{
					mot = scParametre.next();

					sc = new Scanner( mot );
					sc.useDelimiter("\\s+");

					type = sc.next();
					nom  = sc.next();

					m.ajouterParametres( new Parametre(type, nom) );
				}
				
				return m ;
			}
			default -> { return null; }
		}
	}
}