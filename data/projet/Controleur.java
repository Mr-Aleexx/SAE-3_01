package retroconception;

import java.io.File;
import java.util.List;
import retroconception.ihm.FrameUML;
import retroconception.metier.Association;
import retroconception.metier.Classe;
import retroconception.metier.Retroconception;
import retroconception.metier.lecture.AnalyseurJava;

/**
 * Controleur de la classe qui lance le CUI pour l'instant
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
 * @version Etape 4
 * @since 08-12-2025
 */
public class Controleur
{
	private Retroconception metier;
	private FrameUML         ihm;

	public Controleur()
	{
		this.metier = new Retroconception();
		this.ihm    = new FrameUML(this);
	}

	public List<Classe>      getLstClasses     () { return this.metier.getLstClasses();      }
	public List<Association> getLstAssociations() { return this.metier.getLstAssociations(); }
	
	public Classe            getClasse      (int id)         { return this.metier.getClasse      (id);     }
	public Integer           getIndiceClasse(int x, int y)   { return this.metier.getIndiceClasse(x, y);   }
	public int               getNbClasse    ()               { return this.metier.getNbClasse    ();       }
	public String            getLigneMax    (Classe classe ) { return this.metier.getLigneMax    (classe); }


	public static List<String> decomposeurType(String ligne, char separateur)
	{
		return AnalyseurJava.decomposeurType( ligne, separateur );
	}

	public void   ouvrirDossier	    ()                        { this.ihm   .ouvrirDossier(); }
	public void   exporterImage	    ()                        { this.ihm   .exporterImage(); }

	public void   definirLargeur    (Classe c , int ligne   ) { this.metier.definirLargeur(c, ligne);     }
	public void   deplacerClasse    (int    id, int x, int y) { this.metier.deplacerClasse(id, x, y);     }
	public void   ouvrirDossier     (String cheminDossier   ) { this.metier.ouvrirDossier(cheminDossier); }
	public void   sauvegarderFichier(File   fichier         ) { this.metier.sauvegarderFichier(fichier);  }
	public void   chargerSauvegarde (File   fichier         ) { this.metier.chargerSauvegarde(fichier);   }

	public void majIHM() { this.ihm.majIHM(); }
	public void reset ()
	{
		this.metier.reset();
		this.ihm.majIHM();
	}
	public void lancerSauvegarde() { this.ihm.lancerSauvegarde(); }
	public void lancerChargement() { this.ihm.lancerChargement(); }

	public void ajouterRoleAssociationUni(int index, String nomRole)
	{
		this.metier.getLstAssociations().get(index).setRole1(nomRole);
	}

	public void ajouterRoleAssociation(int index, String nomRole1, String nomRole2)
	{
		this.metier.getLstAssociations().get(index).setRole1(nomRole1);
		this.metier.getLstAssociations().get(index).setRole2(nomRole2);
	}

	public void changerCouleur(String panel, int index)
	{
		this.ihm.changerCouleur(panel, index);
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
	
}