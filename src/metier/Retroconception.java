package src.metier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Mets en place la rétroconception des associations
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Martha, CONSTANTIN Alexis
 * @version Etape 4
 * @since 08-12-2025
 */
public class Retroconception
{
	private List<Classe>      lstClasses;
	private List<Association> lstAssociations;
	
	public Retroconception()
	{
		this.lstClasses      = new ArrayList<Classe>     ();
		this.lstAssociations = new ArrayList<Association>();
	}
	
	public List<Classe>      getLstClasses     () { return this.lstClasses;      }
	public List<Association> getLstAssociations() { return this.lstAssociations; }
	
	/**
	 * Ouvre un dossier avec son chemin passé en paramètre
	 * @param cheminDossier Chemoin vers le dossier
	*/
	public void ouvrirDossier(String cheminDossier)
	{
		File dossier = new File(cheminDossier);
		
		File[] lstFichier = dossier.listFiles();
		
		for (File fichier : lstFichier)
			if (fichier.getName().contains(".java") )
				this.lstClasses.add(AnalyseurJava.analyserFichier(fichier.getAbsolutePath()));

			
		this.creationAssociation();
	}

	public void ouvrirFichier(String fichier)
	{
		AnalyseurJava.analyserFichier(fichier);
	}

	
	public void creationAssociation()
	{
		List<Association> lstAssociationTmp = creationMultiplicite();
		creationTypeAsso(lstAssociationTmp);
	}


	/**
	 * Créer les multiplicitées en fonctions des associations
	 * @return List des associations de la classe 
	 */
	private List<Association> creationMultiplicite()
	{
		List<Association> lstAssociationTmp = new ArrayList<>();
		
		for (Classe classe1 : this.lstClasses) 
		{
			for (Attribut attribut : classe1.getAttributs()) 
			{
				
				String type = attribut.getType();
           		boolean estEntre = type.contains("<") && type.contains(">");

				if (estEntre) 
				{
					type = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
				}
				
				for (Classe classe2 : this.lstClasses)
				{
					if (classe1 == classe2) continue;
					
					if (type.equals(classe2.getNom())) 
					{
						String mult = estEntre ? "0..*" : "1..1";
						lstAssociationTmp.add(new Association(classe1, classe2, mult));
            		}
				}
			}
		}

		return lstAssociationTmp;
	}


	
	private void creationTypeAsso(List<Association> lstAssociationTmp) 
	{
		boolean[] used = new boolean[lstAssociationTmp.size()];

		for (int i = 0; i < lstAssociationTmp.size(); i++) {
			if (used[i]) continue;

			Association association1 = lstAssociationTmp.get(i);
			Association association2 = null;

			for (int j = i + 1; j < lstAssociationTmp.size(); j++)
			{
				if (used[j]) continue;
				
				Association candidate = lstAssociationTmp.get(j);

				if (association1.getClasse1()== candidate.getClasse2() &&
					association1.getClasse2()== candidate.getClasse1()    )
				{

					association2 = candidate;
					used[j] = true;
					break;
				}
			}

			used[i] = true;

			if (association2 != null) 
			{
				this.lstAssociations.add( new Association(association1.getClasse1(),association1.getClasse2(),"bidirectionnelle",
									                      association1.getMultiplicite(),association2.getMultiplicite()));
			} 
			else 
			{
				lstAssociations.add (new Association(association1.getClasse1(),association1.getClasse2(),"unidirectionnelle",
				                                     association1.getMultiplicite(),"0..*"));
			}
		}
	}

	public void printLstAssociation() {
		for (int i = 0 ; i < this.lstAssociations.size() ; i++) {
			System.out.println(this.lstAssociations.get(i));
		}
	}
}