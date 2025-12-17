package retroconception.metier;

import java.util.ArrayList;
import java.util.List;

public class Methode
{
	private String          visibilite;
	private boolean         statique;
	private boolean         lectureUnique;
	private boolean         abstraite;
	private String          stereotype;
	private String          type;
	private String          nom;
	private List<Parametre> parametres;

	public Methode( String visibilite, boolean statique, boolean lectureUnique, boolean abstraite,
	                String stereotype, String  type    , String  nom                               )
	{
		this.visibilite    = visibilite;
		this.statique      = statique;
		this.lectureUnique = lectureUnique;
		this.abstraite     = abstraite;
		this.stereotype    = stereotype;
		this.type          = type;
		this.nom           = nom;
		this.parametres    = new ArrayList<Parametre>();
	}

	public String          getVisibilite   () { return this.visibilite;    }
	public boolean         estStatique     () { return this.statique;      }
	public boolean         estLectureUnique() { return this.lectureUnique; }
	public boolean         estAbstraite    () { return this.abstraite;     }
	public String          getStereotype   () { return this.stereotype;    }
	public String          getType         () { return this.type;          }
	public String          getNom          () { return this.nom;           }
	public List<Parametre> getParametre    () { return this.parametres;    }

	public void setVisibilite   ( String          visibilite    ) { this.visibilite    = visibilite;    }
	public void setStatique     ( boolean         statique      ) { this.statique      = statique;      }
	public void setLectureUnique( boolean         lectureUnique ) { this.lectureUnique = lectureUnique; }
	public void setAbstraite    ( boolean         abstraite     ) { this.abstraite     = abstraite;     }
	public void setStereotype   ( String          stereotype    ) { this.stereotype    = stereotype;    }
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