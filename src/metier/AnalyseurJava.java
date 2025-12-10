package metier;

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

	public static Stereotype analyserFichier(String fichier)
	{
		Scanner       sc;
		int           nbLigne, cpt;
		List<Boolean> dansMethode;
		boolean       estPremiereMethode, estDansCommentaire;
		Stereotype    stereotypeRet, stereotypeInterne;
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
				debutLigne = ligne.trim();
				
				if ( debutLigne.length() >= 2 )
				{
					System.out.println( debutLigne );
					if ( debutLigne.substring(0, 2).equals( "/*" ) ) estDansCommentaire = true;

					if ( debutLigne.contains( "*/" ) && estDansCommentaire )
					{
						// On enleve les */
						ligne = ligne.substring( ligne.indexOf( "*/" ) +2, ligne.length() );
						estDansCommentaire = false;
					}

					estDansCommentaire = estDansCommentaire || debutLigne.substring(0, 2).equals( "//" );
				}

				if ( ! estDansCommentaire )
				{
					//Définie la/les lignes du/des nom stéréotype(s) du fichier
					for ( String ster : AnalyseurJava.ENS_STER )
						if ( ligne.contains( ster )  )
						{
							plage1.add( nbLigne );
							estPremiereMethode = false;
						}
					
					// Vérifie si la ligne est la première méthode du stéreotype
					if ( ligne.contains( "(" ) && ( ligne.contains( "abstract" ) || ! ligne.contains( ";" ) )
						&& ! estPremiereMethode )
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
		cpt = 0;

		try
		{
			sc = new Scanner( new FileInputStream ( fichier ), "UTF8" );
			
			stereotypeInterne = stereotypeRet = null;
			dansMethode = new ArrayList<Boolean>();

			// Parcours ligne a ligne en comptant le nombre de ligne pour lié les plages à la ligne en cour
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();
				
				// Parcours des différents stereotype du fichier
				for ( int i = 0; i < plage1.size(); i++ )
				{
					// Création du stérétotype retour et des stéréotypes Internes et de l'ajout et du liens entre eux
					if ( cpt == plage1.get(i) )
					{
						if ( i == 0 ) stereotypeRet = AnalyseurJava.initStereotype( ligne );
						else
						{
							if ( stereotypeInterne != null )
								stereotypeRet.ajouterStereotypeInterne( stereotypeInterne );
							
							stereotypeInterne = AnalyseurJava.initStereotype( ligne );
						}
					}

					// Création des attributs
					if ( cpt > plage1.get(i) && cpt < plage2.get(i) )
					{
						if ( ligne.contains( ";" ) )
						{
							attribut = AnalyseurJava.initAttribut( ligne );

							if ( i == 0 ) stereotypeRet    .ajouterAttribut( attribut );
							else          stereotypeInterne.ajouterAttribut( attribut );
						}
					}

					// Création des Methodes
					//    - Adaptation de la plage des méthodes
					if ( i   == plage1.size() - 1 && cpt >= plage2.get(i)   ||   //Si dernier stereotype
					     cpt >= plage2.get(i)     && cpt <  plage1.get(i+1)   )  //Si ce n'est pas le dernier
					{
						// Verifie si il y a pas une methode locale dans la methode en cour
						if ( ligne.contains( "(" ) && dansMethode.isEmpty() )
						{
							if ( i == 0 )
							{
								methode = AnalyseurJava.initMethode( ligne, stereotypeRet.getNom() );
								stereotypeRet.ajouterMethode( methode );
							}
							else
							{
								methode = AnalyseurJava.initMethode( ligne, stereotypeInterne.getNom() );
								stereotypeInterne.ajouterMethode( methode );
							}
						}

						// Permet de gerer les méthodes locales
						if ( ligne.contains( "{" )                            ) dansMethode.add( true );
						if ( ligne.contains( "}" ) && ! dansMethode.isEmpty() ) dansMethode.remove(0);
					}
				}
				//Compte le nombre de ligne parcourue
				cpt++;
			}
			sc.close();
		}
		catch (FileNotFoundException e) {}

		return null;
	}

	// Permet d'annalyser tous les fichiers .java d'un dossier
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

	// Creer le stereotype
	private static Stereotype initStereotype( String ligne )
	{
		String  visibilite    = "package";
		boolean statique      = false;
		boolean lectureUnique = false;
		boolean abstraite     = false;
		String  type          = "";
		String  nom;

		Scanner sc = new Scanner( ligne );
		sc.useDelimiter("\\s+");
		
		String  mot = "";
		while ( sc.hasNext() )
		{
			mot = sc.next();
			
			for ( String visi : AnalyseurJava.ENS_VISI )
				if( mot.equals(visi) ) visibilite = visi;

			if( mot.equals( "static"  ) ) statique      = true;
			if( mot.equals( "final"   ) ) lectureUnique = true;
			if( mot.equals( "abstract") ) abstraite     = true;

			for ( String ster : AnalyseurJava.ENS_STER )
				if( mot.equals(ster) ) type = ster;
		}
		nom = mot.replace( "{", "");

		sc.close();
		
		return new Stereotype( visibilite, statique, lectureUnique, abstraite, type, nom );
	}

	// Creer un attribut
	private static Attribut initAttribut(String ligne)
	{
		String  visibilite       = "package";
		boolean statique         = false;
		boolean lectureUnique    = false;
		String  type             = "";
		String  nom;
		
		boolean traiteStatiqueFinal;

		Scanner sc = new Scanner( ligne );
		sc.useDelimiter("\\s+");

		String  mot = sc.next();

		for ( String visi : AnalyseurJava.ENS_VISI )
			if( mot.equals(visi) ) visibilite = visi;
		
		
		while ( sc.hasNext() )
		{
			traiteStatiqueFinal = false;
			
			mot = sc.next();

			if( mot.equals( "static"  ) )
			{
				statique            = true;
				traiteStatiqueFinal = true;
			}
			if( mot.equals( "final"   ) )
			{
				lectureUnique       = true;
				traiteStatiqueFinal = true;
			}

			if( ! traiteStatiqueFinal ) break;
		}

		type = mot;
		nom  = sc.next().replace( ";" , "" );
		
		sc.close();

		return new Attribut( visibilite, statique, lectureUnique, type, nom );
	}

	private static Methode initMethode( String ligne, String nomStereotype )
	{
		String  visibilite    = "package";
		boolean statique      = false;
		boolean lectureUnique = false;
		boolean abstraite     = false;
		String  type;
		String  nom           = "";
		
		boolean traiteStatiqueFinalAbstract;
		int     index;

		Scanner sc = new Scanner( ligne );
		sc.useDelimiter("\\s+");

		String  mot = sc.next();

		for ( String visi : AnalyseurJava.ENS_VISI )
			if( mot.equals(visi) ) visibilite = visi;
		
		
		while ( sc.hasNext() )
		{
			traiteStatiqueFinalAbstract = false;
			
			mot = sc.next();

			if( mot.equals( "static"   ) )
			{
				statique                    = true;
				traiteStatiqueFinalAbstract = true;
			}
			if( mot.equals( "final"    ) )
			{
				lectureUnique               = true;
				traiteStatiqueFinalAbstract = true;
			}

			if( mot.equals( "abstract" ) )
			{
				abstraite                   = true;
				traiteStatiqueFinalAbstract = true;
			}

			if( ! traiteStatiqueFinalAbstract ) break;
		}
		
		// Recuperation du nom
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

		// Si sans paramètre
		if ( ligne.contains( "()" ) ) return m;

		//Partie Parametres

		index = ligne.indexOf( "(" ) + 1; //On enleve la premiere parenthèse
		String parametre = ligne.substring( index , ligne.length() -1 ); // On enleve la deuxieme parenthèse

		//Cas ou 1 seul parametre
		if ( ! ligne.contains( "," ) )
		{
			sc = new Scanner( parametre );
			sc.useDelimiter("\\s+");

			type = sc.next();
			nom  = sc.next().replace( ")", "" );

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
			nom  = sc.next().replace( ")", "" );

			m.ajouterParametres( new Parametre(type, nom) );
		}
		
		return m ;
	}

}
