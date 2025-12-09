package metier;

import java.util.ArrayList;
import java.util.List;

public class Methode
{
	private final String SOULIGNER     = "\033[4m";
	private final String GRAS          = "\033[1m";
	private final String REINITIALISER = "\033[0m";
	
	// private static final char[] tabVisibilite = new char[] {'+', '-', '#','~'};
	
	private String          visibilite;      //Public, private ou protected
	private boolean         statique;        //Static ou non 
	private boolean         lectureUnique;   //Final ou non
	private boolean         abstraite;
	private boolean         syncronize;
	private String          type;
	private String          nom;
	private List<Parametre> parametres;      //Parametres de la methode avec un type et un nom

	public Methode( String visibilite, boolean statique, boolean lectureUnique, boolean abstraite, String type, String nom )
	{
		this.visibilite    = visibilite;
		this.statique      = statique;
		this.lectureUnique = lectureUnique;
		this.abstraite     = abstraite;
		this.type          = type;
		this.nom           = nom;
		this.parametres    = new ArrayList<Parametre>();
	}

	public Methode()
	{
		this ( "", false, false, false, null, null );
	}

	public String          getVisibilite   () { return this.visibilite;    }
	public boolean         estStatique     () { return this.statique;      }
	public boolean         estLectureUnique() { return this.lectureUnique; }
	public boolean         estAbstraite    () { return this.lectureUnique; }
	public String          getType         () { return this.type;          }
	public String          getNom          () { return this.nom;           }
	public List<Parametre> getParametre    () { return this.parametres;    }

	public void setVisibilite   ( String          visibilite    ) { this.visibilite    = visibilite;    }
	public void setStatique     ( boolean         statique      ) { this.statique      = statique;      }
	public void setLectureUnique( boolean         lectureUnique ) { this.lectureUnique = lectureUnique; }
	public void setAbstraite    ( boolean         abstraite     ) { this.abstraite     = abstraite;     }
	public void setType         ( String          type          ) { this.type          = type;          }
	public void setNom          ( String          nom           ) { this.nom           = nom;           }
	public void setParametres   ( List<Parametre> parametres    ) { this.parametres    = parametres;    }

	public void ajouterParametres(Parametre param) { this.parametres.add(param); }

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
}