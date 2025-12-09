package metier;

public class Attribut
{
	private final String SOULIGNER     = "\033[4m";
	private final String GRAS          = "\033[1m";
	private final String REINITIALISER = "\033[0m";
	
	// private static final char[] tabVisibilite = new char[] {'+', '-', '#', '~'};
	
	private String  visibilite;        //Public, private ou protected
	private boolean statique;          //Static ou non 
	private boolean lectureUnique;     //Final ou non
	private String  type;
	private String  nom;

	public Attribut( String visibilite, boolean statique, boolean lectureUnique, String type, String nom )
	{
		this.visibilite    = visibilite;
		this.statique      = statique;
		this.lectureUnique = lectureUnique;
		this.type          = type;
		this.nom           = nom;
	}

	public Attribut()
	{
		this( "", false, false, null, null );
	}

	public String  getVisibilite   () { return this.visibilite;    }
	public boolean estStatique     () { return this.statique;      }
	public boolean estLectureUnique() { return this.lectureUnique; }
	public String  getType         () { return this.type;          }
	public String  getNom          () { return this.nom;           }

	public void setVisibilite   (String visibilite    ) { this.visibilite    = visibilite;    }
	public void setStatique     (boolean statique     ) { this.statique      = statique;      }
	public void setLectureUnique(boolean lectureUnique) { this.lectureUnique = lectureUnique; }
	public void setType         (String type          ) { this.type          = type;          }
	public void setNom          (String nom           ) { this.nom           = nom;           }
	
	public char getSymbole()
	{
		return switch (this.visibilite)
		{
			case "public"    -> '+';
			case "private"   -> '-';
			case "protected" -> '#';
			case "package"   -> '~';
			default          -> ' ';
		};
	}

	public String toString()
	{
		return this.nom + " :" + this.type;
	}
}