package metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnalyseurJava
{
	private String nomFichier;

	private static final String[] ENS_VISI = new String[] { "public", "private", "protected"       };
	private static final String[] ENS_STER = new String[] { "class", "interface", "enum", "record" };

	public static Stereotype analyserFichier(String fichier)
	{
		Scanner       sc, scLigne;
		int           cptLigne, cpt;
		List<Boolean> dansMethode;
		boolean       estPremiereMethode;
		Stereotype    stereotypeRet, stereotypeInterne;
		Attribut      attribut;
		Methode       methode;
		String        visibilite, ligne;
		
		List<Integer> plage1 = new ArrayList<Integer>();
		List<Integer> plage2 = new ArrayList<Integer>();
		
		cptLigne = cpt = 0;
		// Lecture 1
		try 
		{
			sc = new Scanner( new FileInputStream ( fichier ), "UTF8" );

			estPremiereMethode = false;
			
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();
				
				if ( ligne.contains( "class" ) )
				{
					plage1.add( cptLigne );
					estPremiereMethode = false;
				}
					
				
				if (   ligne.contains( "(" ) &&
				     ! ligne.contains( ";" ) &&
				     ! estPremiereMethode     )
				{
					plage2.add( cptLigne );
					estPremiereMethode = true;
				}
				
				cptLigne++;
			}
			sc.close();
		}
		catch (FileNotFoundException e) {}

		try
		{
			sc = new Scanner( new FileInputStream ( fichier ), "UTF8" );
			
			stereotypeInterne = stereotypeRet = null;
			dansMethode = new ArrayList<Boolean>();

			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();
				
				for ( int i = 0; i < plage1.size(); i++ )
				{
					// Création du stérétotype retour et des sous stéréotype ainsi que de l'ajout des sous stéretotype
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

					// Attributs
					if ( cpt > plage1.get(i) && cpt < plage2.get(i) )
					{
						if ( ligne.contains( ";" ) )
						{
							attribut = AnalyseurJava.initAttribut( ligne );

							if ( i == 0 ) stereotypeRet    .ajouterAttribut( attribut );
							else          stereotypeInterne.ajouterAttribut( attribut );
						}
					}

					//Methodes
					if ( i == plage1.size() - 1 && cpt >= plage2.get(i) ||   //Si dernier stereotype
					     cpt >= plage2.get(i) && cpt < plage1.get(i+1)    )  //Si ce n'est pas le dernier
					{
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

						// Gestion des declaration de classes en local
						if ( ligne.contains( "{" ) ) dansMethode.add( true );
						if ( ligne.contains( "}" ) && ! dansMethode.isEmpty() )
							dansMethode.remove(0);
						
					}
					
				}

				cpt++;
			}
			sc.close();
		}
		catch (FileNotFoundException e) {}

		return null;
	}

	public static ArrayList<Stereotype> analyserDossier(String dossierChemin)
	{
		
		File dossier = new File(dossierChemin);
		ArrayList<Stereotype> arraySteo = new ArrayList<Stereotype>();

		File[] lstFichier = dossier.listFiles();
		for (File fichier : lstFichier) 
		{
			if (fichier.getName().contains(".java"))
				arraySteo.add(AnalyseurJava.analyserFichier(fichier.getAbsolutePath()));
		}

		return arraySteo;
	}

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
				if( mot.equals(ster) ) type = ster;  //ligne de base : mot.equals(type)
		}
		
		nom = mot;

		System.out.println( visibilite +","+ statique +","+ lectureUnique +","+ abstraite + "," + type +","+ nom );
		
		return new Stereotype( visibilite, statique, lectureUnique, abstraite, type, nom );
	}

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

		System.out.println( visibilite +","+ statique +","+ lectureUnique +","+ type +","+ nom );
		
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
		
		// Gestion du constructeur
		index = mot.indexOf( "(" );
		if ( index != -1 ) mot = mot.substring( 0, index );

		if ( nomStereotype.equals( mot ) )
		{
			type = "Constructeur";

			nom = mot.replace( "" , "" );
		}
		else
		{
			type = mot;
			
			mot  = sc.next();

			index = mot.indexOf( "(" );
			if ( index != -1 ) nom = mot.substring( 0, index );
		}
		

		Methode m =  new Methode( visibilite, statique, lectureUnique, abstraite, type, nom );

		//Partie Parametres

		System.out.println( visibilite +","+ statique +","+ lectureUnique +","+ abstraite + "," + type +","+ nom );
		
		return m ;
	}

}
