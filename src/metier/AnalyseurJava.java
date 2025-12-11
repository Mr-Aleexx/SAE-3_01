package src.metier;

import  src.Controleur;

import java.io.File;
import java.io.FileInputStream;
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
		int           nbLigne, numLigne;
		List<Boolean> dansMethode;
		boolean       estPremiereMethode, estDansCommentaire, estCommentaire;
		Stereotype    stereotypeRet = null;
		Stereotype    stereotypeInterne;
		Attribut      attribut;
		Methode       methode;
		String        ligne, debutLigne;
		
		List<Integer> plage1 = new ArrayList<Integer>();
		List<Integer> plage2 = new ArrayList<Integer>();

		/* Première Lecture :
		    - Compte le nombre de ligne du fichier
		    - Compte le nombre de classe interne au fichier
		    - Définis les zones du fichiers ( stereotype, attributs, méthode )
		*/
		nbLigne = 0;
		estDansCommentaire = false;
		try
		{
			sc = new Scanner( new FileInputStream ( fichier ), "UTF8" );

			estPremiereMethode = false;
			
			while ( sc.hasNextLine() )
			{
				ligne      = sc.nextLine();
				
				// Gestion des commentaires // et /* */ au debut de la ligne
				debutLigne     = ligne.trim();
				estCommentaire = false;
				
				if ( debutLigne.length() >= 2 )
				{
					estDansCommentaire = AnalyseurJava.estDansCommentaire( ligne, debutLigne, estDansCommentaire);
					estCommentaire     = AnalyseurJava.estCommentaire( debutLigne );
				}

				if ( ! estDansCommentaire && ! estCommentaire )
				{
					//Définie la/les lignes du/des nom stéréotype(s) du fichier
					for ( String ster : AnalyseurJava.ENS_STER )
						if ( ligne.contains( ster )  )
						{
							plage1.add( nbLigne );
							estPremiereMethode = false;
						}
					
					// Définie le début des méthode du stéreotype
					if ( AnalyseurJava.premiereMethodeDetecte( ligne ) && ! estPremiereMethode )
					{
						plage2.add( nbLigne );
						estPremiereMethode = true;
					}
				}
				
				//Compte le nombre de ligne du fichier
				nbLigne++;
			}
			sc.close();
		}
		catch (FileNotFoundException e) {}

		/* Deuxième Lecture :
		    - Création du stereotype
		*/
		numLigne = 0;

		try
		{
			sc = new Scanner( new FileInputStream ( fichier ), "UTF8" );
			
			stereotypeInterne = null;
			dansMethode = new ArrayList<Boolean>();
			estDansCommentaire = false;

			// Parcours ligne a ligne en comptant le nombre de ligne pour lié les plages à la ligne en cour
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();

				// Gestion des commentaires // et /* */ au debut de la ligne
				debutLigne     = ligne.trim();
				estCommentaire = false;
				
				if ( debutLigne.length() >= 2 )
				{
					estDansCommentaire = AnalyseurJava.estDansCommentaire(ligne, debutLigne, estDansCommentaire);
					estCommentaire     = AnalyseurJava.estCommentaire( debutLigne );
				}
				
				if ( ! estDansCommentaire && ! estCommentaire )
				{
					// Parcours des différents stereotype du fichier
					for ( int i = 0; i < plage1.size(); i++ )
					{
						// Création du stérétotype retour et des stéréotypes Internes et de l'ajout et du liens entre eux
						if ( numLigne == plage1.get(i) )
						{
							if ( i == 0 )
								stereotypeRet = (Stereotype)AnalyseurJava.initAll( "Stereotype" , "" , ligne );
							else
							{
								if ( stereotypeInterne != null )
									stereotypeRet.ajouterStereotypeInterne( stereotypeInterne );
								
								stereotypeInterne = (Stereotype)AnalyseurJava.initAll( "Stereotype" , "" , ligne );
							}
						}

						// PROTECTION: Vérifier que plage2 a un élément pour cet index
						if ( i < plage2.size() )
						{
							// Création des attributs
							if ( numLigne > plage1.get(i) && numLigne < plage2.get(i) )
							{
								if ( ligne.contains( ";" ) )
								{
									attribut = (Attribut)AnalyseurJava.initAll( "Attribut" , "" , ligne );

									if ( i == 0 ) stereotypeRet    .ajouterAttribut( attribut );
									else          stereotypeInterne.ajouterAttribut( attribut );
								}
							}

							// Création des Methodes
							boolean dansZoneMethode = false;
							
							// Si c'est le dernier stereotype
							if ( i == plage1.size() - 1 )
								dansZoneMethode = ( numLigne >= plage2.get(i) );
							// Sinon, vérifier qu'on est avant le prochain stereotype
							else if ( i + 1 < plage1.size() )
								dansZoneMethode = ( numLigne >= plage2.get(i) && numLigne < plage1.get(i + 1) );
							
							if ( dansZoneMethode )
							{
								// Verifie si il y a pas une methode locale dans la methode en cour
								if ( ligne.contains( "(" ) && dansMethode.isEmpty() )
								{
									if ( i == 0 )
									{
										methode = (Methode)AnalyseurJava.initAll( "Methode" , stereotypeRet.getNom() , ligne );
										stereotypeRet.ajouterMethode( methode );
									}
									else
									{
										methode = (Methode)AnalyseurJava.initAll( "Methode" , stereotypeInterne.getNom() , ligne );
										stereotypeInterne.ajouterMethode( methode );
									}
								}

								// Permet de gerer les méthodes locales
								if ( ligne.contains( "{" ) ) dansMethode.add( true );
								if ( ligne.contains( "}" ) && ! dansMethode.isEmpty() ) dansMethode.remove(0);
							}
						}
					}
				}
				
				//Compte le nombre de ligne parcourue
				numLigne++;
			}
			
			// N'oubliez pas d'ajouter le dernier stereotype interne s'il existe
			if ( stereotypeInterne != null )
				stereotypeRet.ajouterStereotypeInterne( stereotypeInterne );
			
			sc.close();
		}
		catch (FileNotFoundException e) {}

		return stereotypeRet;
	}

	private static boolean estDansCommentaire( String ligne, String debutLigne, boolean estDansCommentaire )
	{
		
		if ( debutLigne.substring(0, 2).equals( "/*" ) ) estDansCommentaire = true;
		
		if ( debutLigne.contains( "*/" ) && estDansCommentaire )
		{
			// On enleve les */
			ligne = ligne.substring( ligne.indexOf( "*/" ) +2, ligne.length() );
			estDansCommentaire = false;
		}

		return estDansCommentaire;
	}

	private static boolean estCommentaire( String debutLigne )
	{
		return debutLigne.substring(0, 2).equals( "//" );
	}
	
	/**
	 * Retourne la liste des stéréotypes analysés dans un dossier
	 * @param dossierChemin Chemin vers le dossier
	 * @return
	 */
	public static ArrayList<Stereotype> analyserDossier(String dossierChemin)
	{
		File dossier = new File(dossierChemin);
		ArrayList<Stereotype> lstStereotypes = new ArrayList<Stereotype>();

		File[] lstFichier = dossier.listFiles();

		for ( File fichier : lstFichier )
			if ( fichier.getName().contains(".java") )
				lstStereotypes.add(AnalyseurJava.analyserFichier(fichier.getAbsolutePath()));

		return lstStereotypes;
	}

	private static Object initAll( String typeInit, String nomStereotype, String ligne )
	{
		String  visibilite    = "package";
		boolean statique      = false;
		boolean lectureUnique = false;
		boolean abstraite     = false;
		String  type          = "";
		String  nom           = "";
		String  mere          = null;

		boolean aGeneralization;
		int     index, indexAvt, indexAps;
		String  parametre = "";

		Scanner sc = new Scanner( ligne );
		sc.useDelimiter("\\s+");

		String  mot = sc.next();

		for ( String visi : AnalyseurJava.ENS_VISI )
			if( mot.equals(visi) ) visibilite = visi;

		while ( sc.hasNext() )
		{
			aGeneralization = false;
			
			mot = sc.next();

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
					if( mot.equals(ster) ) type = ster;
				
				mot = sc.next().replace( "{", "");
				
				nom = mot;

				while ( sc.hasNext() )
				{
					aGeneralization = false;
					
					mot = sc.next();

					if( mot.equals( "extends"   ) )
					{
						mot = sc.next();
						mere = mot;
					}
					if( mot.equals( "implements") ) 
					{
						String detecteImplements = ligne.substring( ligne.indexOf("implements"),ligne.length() );
						Scanner scImplements = new Scanner( detecteImplements );
						scImplements.useDelimiter("\\,");
						
						// while ( scImplements.hasNext() )
						// {

						// }
						// break;
					}

					if( ! aGeneralization ) break;
				}

				if ( sc.hasNext() ) mot = sc.next();

				if ( mot.equals( "extends" ) )
				{
					mere = sc.next().replace( "{", "");
					if ( sc.hasNext() ) mot = sc.next();
					
					if ( mot.equals( "implements" ) ) mot = "mabite";
				}
				
				sc.close();

				return new Stereotype( visibilite, statique, lectureUnique, abstraite, type, nom, mere );
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

	private static boolean premiereMethodeDetecte(String ligne)
	{
		return ligne.contains( "(" ) && ( ligne.contains( "abstract" ) || ( ! ligne.contains( ";" ) && ! ligne.contains("=") ) );
	}
}