package metier;

public class Attribut
{
	// private static final char[] tabVisibilite = new char[] {'+', '-', '#', '~'};
	
	private String    visibilite;        //Public, private ou protected
	private boolean portee;            //Static ou non 
	private boolean lectureUnique;     //Final ou non
	private String  type;
	private String  nom;

	public Attribut()
	{
		this.visibilite    = "";
		this.portee        = false;
		this.lectureUnique = false;
		this.type          = null;
		this.nom           = null;
	}

	public String  getVisibilite   () {return this.visibilite;   }
	public boolean getPortee       () {return this.portee;       }
	public boolean getLectureUnique() {return this.lectureUnique;}
	public String  getType         () {return this.type;         }
	public String  getNom          () {return this.nom;          }

	public void setVisibilite   (String visibilite    ) { this.visibilite    = visibilite;    }
	public void setPortee       (boolean portee       ) { this.portee        = portee;        }
	public void setLectureUnique(boolean lectureUnique) { this.lectureUnique = lectureUnique; }
	public void setType         (String type          ) { this.type          = type;          }
	public void setNom          (String nom           ) { this.nom           = nom;           }
	
	public char getSymbole()
	{
		return switch (this.visibilite) {
			case "public" -> '+';
			case "private" -> '-';
			case "protected" -> '#';
			default -> ' ';
		};
	}
}