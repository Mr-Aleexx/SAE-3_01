import java.util.HashMap;

public final class Classe
{
	/* --------- */
	/* Attributs */
	/* --------- */

	// Attributs finaux
	private static final Double VALEUR_PI = 3.141592653;
	private static final String[] TAB_SAISONS = { "Hiver", "Printemps",
	                                              "Été", "Automne" };
	private static final String[][] TAB = { {"A", "B"},
	                                        {"C", "D"}
	                                      };
	
	// Test de visibilites
	          int    entier;
	private   String chaine;
	protected double unDouble;
	public    char   caractere;

	// Test particulier
	private HashMap< String, HashMap< String, Integer > > hmap;

	/* -------- */
	/* Methodes */
	/* -------- */

	// Test Visibilites
	void meth1 ( HashMap< String, HashMap< String, String > > hash )
	{
		System.out.println();
	}
	public    int meth2 ( int a, int b, int c, int d, int e ) {
		return 0;
	}
	private   Double meth3 ( Double un_double )
	{
		return un_double;
	}
	protected HashMap< String, HashMap< String, String > > meth4 ( String string ) { return null; }

	// Test final static et abstract

	public static final void meth5( int a ) {}

}