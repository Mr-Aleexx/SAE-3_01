package metier;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnnalyseurJava
{
	private String nomFichier;

	public AnnalyseurJava(String fichier)
	{
		AnnalyseurJava.annalyser( nomFichier );
	}

	public static Stereotype annalyser(String fichier)
	{
		Scanner       sc, scLigne;
		int           cptLigne, cpt;
		boolean       estPremiereMethode = false;
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
			
			while ( sc.hasNextLine() )
			{
				ligne = sc.next();
				
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

			while ( sc.hasNextLine() )
			{
				ligne = sc.next();
				
				for ( int i = 0; i < plage1.size(); i++ )
				{
					// Création du stérétotype retour et des sous stéréotype ainsi que de l'ajout des sous stéretotype
					if ( cpt == plage1.get(i) )
					{
						if ( i == 0 ) stereotypeRet = AnnalyseurJava.initStereotype( ligne );
						else
						{
							if ( stereotypeInterne != null )
								stereotypeRet.ajouterStereotypeInterne( stereotypeInterne );
							
							stereotypeInterne = AnnalyseurJava.initStereotype( ligne );
						}
					}

					// Attributs
					if ( cpt > plage1.get(i) && cpt < plage2.get(i) )
					{
						attribut = AnnalyseurJava.initAttribut( ligne );

						if ( i == 0 ) stereotypeRet    .ajouterAttribut( attribut );
						else          stereotypeInterne.ajouterAttribut( attribut );
					}

					//Methodes
					if ( i == plage1.size() - 1 ) // Dernier i de la liste
					{
						if ( cpt >= plage2.get(i) )
						{
							methode = AnnalyseurJava.initMethode( ligne );
							
							if ( i == 0 ) stereotypeRet    .ajouterMethode( methode );
							else          stereotypeInterne.ajouterMethode( methode );
						}
					}
					else
					{
						if ( cpt >= plage2.get(i) && cpt < plage1.get(i+1) )
						{
							methode = AnnalyseurJava.initMethode( ligne );
							
							if ( i == 0 ) stereotypeRet    .ajouterMethode( methode );
							else          stereotypeInterne.ajouterMethode( methode );
						}
					}
					
				}

				cpt++;
			}
			sc.close();
		}
		catch (FileNotFoundException e) {}

		return null;
	}

	public static ArrayList<Stereotype> ouvrirDossier(String dossierChemin)
	{
		
		File dossier = new File(dossierChemin);
		ArrayList<Stereotype> arraySteo = new ArrayList<>();

		File[] lstFichier = dossier.listFiles();
		for (File fichier : lstFichier) 
		{
			arraySteo.add(AnnalyseurJava.annalyser(fichier.getAbsolutePath()));
		}

		return arraySteo;
	}

	private static Stereotype initStereotype(String ligne)
	{
		String[] ensVisi = new String[] { "public", "private", "protected"       };
		String[] ensMots = new String[] { "static", "final", "abstract"          };
		String[] ensType = new String[] { "class", "interface", "enum", "record" };
		
		Stereotype stereo;
		
		String mot;
		Scanner sc = new Scanner( ligne );
		sc.useDelimiter("\\s+");
		
		while ( sc.hasNext())
		{
			mot =  sc.next();
			for (String visi : ensVisi)
			{
				if( ligne.contains(visi) ) 
				
			}
		}
		
		

		return null;
	}

}
