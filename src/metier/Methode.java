package metier;

import java.util.ArrayList;
import java.util.List;

public class Methode
{
	private final String SOULIGNER     = "\033[4m";
	private final String GRAS          = "\033[1m";
	private final String REINITIALISER = "\033[0m";
	
	// private static final char[] tabVisibilite = new char[] {'+', '-', '#','~'};
	
	private String                  visibilite;      //Public, private ou protected
	private boolean                 statique;        //Static ou non 
	private boolean                 lectureUnique;   //Final ou non
	private String                  type;
	private String                  nom;
	private List<Attribut>          parametres;      //Parametres de la methode avec un type et un nom

	public Methode()
	{
		this.visibilite    = "";
		this.statique      = false;
		this.lectureUnique = false;
		this.type          = null;
		this.nom           = null;
		this.parametres    = new ArrayList<Attribut>();
	}

	public String                  getVisibilite   (){return this.visibilite;   }
	public boolean                 estStatique     (){return this.statique;     }
	public boolean                 estLectureUnique(){return this.lectureUnique;}
	public String                  getType         (){return this.type;         }
	public String                  getNom          (){return this.nom;          }
	public List<Attribut>          getParametre    (){return this.parametres;   }

	public void setVisibilite   (String                  visibilite      ) { this.visibilite    = visibilite;    }
	public void setStatique     (boolean                 statique        ) { this.statique      = statique;      }
	public void setLectureUnique(boolean                 lectureUnique   ) { this.lectureUnique = lectureUnique; }
	public void setType         (String                  type            ) { this.type          = type;          }
	public void setNom          (String                  nom             ) { this.nom           = nom;           }
	public void setParametres   (List<Attribut>          parametres      ) { this.parametres    = parametres;    }

	public void ajouterParametres(Attribut attrib) { this.parametres.add(attrib); }

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