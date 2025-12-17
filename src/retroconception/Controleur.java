package retroconception;

import java.io.File;
import java.util.List;
import retroconception.ihm.FrameUML;
import retroconception.ihm.IHMCUI;
import retroconception.metier.Association;
import retroconception.metier.Classe;
import retroconception.metier.Retroconception;

/**
 * Controleur de la classe qui lance le CUI pour l'instant
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Martha, CONSTANTIN Alexis
 * @version Etape 4
 * @since 08-12-2025
 */
public class Controleur
{
	private Retroconception metier;
	private IHMCUI          ihmCUI;
	private FrameUML         ihm;

	public Controleur()
	{
		this.metier = new Retroconception();
		this.ihmCUI    = new IHMCUI( this );
		this.ihm = new FrameUML(this);
	}

	public List<Classe>      getLstClasses     () { return this.metier.getLstClasses();      }
	public List<Association> getLstAssociations() { return this.metier.getLstAssociations(); }
	
	public Classe            getClasse      (int id)       { return this.metier.getClasse      (id);   }
	public Integer           getIndiceClasse(int x, int y) { return this.metier.getIndiceClasse(x, y); }
	public int               getNbClasse    ()             { return this.metier.getNbClasse    ();     }


	public void   ouvrirDossier	    ()                        { this.ihm   .ouvrirDossier();          }
	public void   exporterImage	    ()                        { this.ihm   .exporterImage();         }

	public void   definirDimension  (Classe c , int ligne   ) { this.metier.definirDimension(c, ligne);}
	public void   deplacerClasse    (int    id, int x, int y) { this.metier.deplacerClasse(id, x, y); }
	public void   ouvrirDossier     (String cheminDossier   ) { this.metier.ouvrirDossier(cheminDossier); }
	public void   ouvrirFichier     (String cheminFichier   ) { this.metier.ouvrirFichier(cheminFichier); }
	public void   sauvegarderFichier(File   fichier         ) { this.metier.sauvegarderFichier(fichier); }
	public void   chargerSauvegarde (File   fichier         ) { this.metier.chargerSauvegarde(fichier); }
	public String getLigneMax       (Classe classe          ) { return this.metier.getLigneMax(classe); }

	public String afficher()
	{
		return this.ihmCUI.afficher();
	}

	public static void main(String[] args)
	{
		Controleur ctr = new Controleur();
		ctr.ouvrirDossier( "./data/testAtt" );
		System.out.println( ctr.afficher() );
	}
	
}