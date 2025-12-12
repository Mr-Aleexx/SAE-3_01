package src.metier;


/**
 * Représentation des attributs d'une classe
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Martha, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */
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

	/**
	 * Représentation de la visibilité d'une classe par un charactère
	 * @return Caractère représenté
	 */
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