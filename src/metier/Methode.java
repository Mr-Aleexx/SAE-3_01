package metier;

import java.util.HashMap;

public class Methode
{
	// private static final char[] tabVisibilite = new char[] {'+', '-', '#'};
	
	private String                  visibilite;      //Public, private ou protected
	private boolean                 portee;          //Static ou non 
	private boolean                 lectureUnique;   //Final ou non
	private String                  type;
	private String                  nom;
	private HashMap<String, String> parametres;      //Parametres de la methode avec un type et un nom

	public Methode()
	{
		this.visibilite    = "";
		this.portee        = false;
		this.lectureUnique = false;
		this.type          = null;
		this.nom           = null;
		this.parametres    = new HashMap<String, String>();
	}

	public String                  getVisibilite(){return this.visibilite;   }
	public boolean                 getPorte     (){return this.portee;       }
	public boolean                 getLecteur   (){return this.lectureUnique;}
	public String                  getType      (){return this.type;         }
	public String                  getNom       (){return this.nom;          }
	public HashMap<String, String> getParametre (){return this.parametres;   }

	public void setVisibilite   (String                  visibilite      ) { this.visibilite    = visibilite;    }
	public void setPortee       (boolean                 portee          ) { this.portee        = portee;        }
	public void setLectureUnique(boolean                 lectureUnique   ) { this.lectureUnique = lectureUnique; }
	public void setType         (String                  type            ) { this.type          = type;          }
	public void setNom          (String                  nom             ) { this.nom           = nom;           }
	public void setParametres   (HashMap<String, String> parametres      ) { this.parametres    = parametres;    }

	public void ajouterParametres(String nom, String type) { this.parametres.put(nom, type); }

}