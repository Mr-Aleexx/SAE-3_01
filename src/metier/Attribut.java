package metier;

public class Attribut
{
	private String  visibilite;
	private boolean statique;
	private boolean lectureUnique;
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