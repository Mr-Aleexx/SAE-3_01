package retroconception.metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Représentation d'une classe
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */
public class Classe
{
	private String         visibilite;
	private boolean        statique;
	private boolean        lectureUnique;
	private boolean        abstraite;
	private String         nom;

	private String         stereotype;
	private String         mere;
	private List<Attribut> attributs;
	private List<Methode>  methodes;
	private List<Classe>   ClassesInterne;
	private List<String>   lstImplementations;

	private PositionClasse position;

	public Classe( String visi, boolean statique, boolean lectureUnique, boolean abstraite, String stereotype,
	               String nom )
	{
		this.visibilite         = visi;
		this.statique           = statique;
		this.lectureUnique      = lectureUnique;
		this.abstraite          = abstraite;
		this.nom                = nom;

		this.stereotype         = stereotype;
		this.mere               = null;
		this.attributs          = new ArrayList<Attribut>();
		this.methodes           = new ArrayList<Methode> ();
		this.ClassesInterne     = new ArrayList<Classe>  ();
		this.lstImplementations = new ArrayList<String>  ();

		this.position           = new PositionClasse();
	}

	// Getter
	public String           getVisibilite        () { return this.visibilite        ; }
	public boolean          estStatique          () { return this.statique          ; }
	public boolean          estLectureUnique     () { return this.lectureUnique     ; }
	public boolean          estAbstraite         () { return this.abstraite         ; }
	public String           getStereotype        () { return this.stereotype        ; }
	public String           getNom               () { return this.nom               ; }
	public String           getMere              () { return this.mere              ; }
	public List<Attribut>   getAttributs         () { return this.attributs         ; }
	public List<Methode>    getMethodes          () { return this.methodes          ; }
	public List<Classe>     getClassesInterne    () { return this.ClassesInterne    ; }
	public List<String>     getLstImplementations() { return this.lstImplementations; }
	public PositionClasse   getPos               () { return this.position          ; }

	// Setter
	public void setVisibilite        ( String           visibilite         ) { this.visibilite         = visibilite         ; }
	public void setStatique          ( boolean          statique           ) { this.statique           = statique           ; }
	public void setLectureUnique     ( boolean          lectureUnique      ) { this.lectureUnique      = lectureUnique      ; }
	public void setAbstraite         ( boolean          abstraite          ) { this.abstraite          = abstraite          ; }
	public void setStereotype        ( String           stereotype         ) { this.stereotype         = stereotype         ; }
	public void setNom               ( String           nom                ) { this.nom                = nom                ; }
	public void setMere              ( String           mere               ) { this.mere               = mere               ; }
	public void setAttributs         ( List<Attribut>   attributs          ) { this.attributs          = attributs          ; }
	public void setMethodes          ( List<Methode>    methodes           ) { this.methodes           = methodes           ; }
	public void setClassesInterne    ( List<Classe>     ClassesInternes    ) { this.ClassesInterne     = ClassesInternes    ; }
	public void setLstImplementations( List<String>     lstImplementations ) { this.lstImplementations = lstImplementations ; }
	public void setPosition          ( PositionClasse   position           ) { this.position           = position           ; }

	public void ajouterAttribut         (Attribut   attribut         ) { this.attributs         .add(attribut      );}
	public void enleverAttribut         (Attribut   attribut         ) { this.attributs         .remove( attribut ); }
	public void ajouterMethode          (Methode    methode          ) { this.methodes          .add(methode       );}
	public void ajouterClasseInterne    (Classe     ClasseInterne    ) { this.ClassesInterne    .add(ClasseInterne );}
	public void ajouterImplementations  (String     implementation   ) { this.lstImplementations.add(implementation);}



	/**
	 * Permet de savoir si les coordonées sont dans l'objet classe
	 * @param x coordonnée x
	 * @param y coordonnée y
	 * @return
	 */
	public boolean possede(int x, int y)
	{
		int milieuY = ((this.position.getCentreYClasse () - this.position.getTailleYClasse () / 2) +
		               (this.position.getCentreYMethode() + this.position.getTailleYMethode() / 2)) /2;

		return x >= this.position.getCentreX       () - this.position.getTailleX       () / 2 &&
		       x <= this.position.getCentreX       () + this.position.getTailleX       () / 2 &&

		       y >= this.position.getCentreYClasse () - this.position.getTailleYClasse () / 2 &&
		       y <= this.position.getCentreYMethode() + this.position.getTailleYMethode() / 2;
	}

}