package metier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AnnalyseurJava
{
	private String nomFichier;

	private String contructor;

	public AnnalyseurJava(String fichier)
	{
		this.readFile( fichier);
	}

	public void readFile(String fichier)
	{
		String     sRet;
		
		Scanner    sc, scLigne;
		String     visibilite = "";
		Stereotype stereotype;

		try 
		{
			sc = new Scanner( new FileInputStream ( fichier ), "UTF8" );

			stereotype = new Stereotype();
			
			while ( sc.hasNextLine() )
			{
				scLigne = new Scanner( sc.nextLine() );
				scLigne.useDelimiter(  "\\s+" );

				do
				{
					if ( scLigne.hasNext() )
						visibilite = scLigne.next();
					else
						break;
				}
				while( ! visibilite.equals( "private"   ) &&
				       ! visibilite.equals( "public"    ) &&
				       ! visibilite.equals( "protected" ) );

				

				this.construire( scLigne );
				

			}
		}
		catch (FileNotFoundException e) { e.printStackTrace(); }
	}

	public void construire( Scanner scLigne )
	{
		this.construire( 0, scLigne );
	}

	private void construire( int cpt , Scanner scLigne )
	{
	 	String motCourent;


	 	switch ( cpt )
		{
			case 0 ->
			{
				if( scLigne.next().equals( "static" ) )

			}
			case 1 ->
			{
				if( scLigne.next().equals( "final" ) )
			}
			case 2 ->
			{
				
			}
			case 3 ->
			{
				
			}
			case 4 ->
			{
				
			}

		}


	}

	/*
		public static final int nom()
		{
		
		}


	*/
}
