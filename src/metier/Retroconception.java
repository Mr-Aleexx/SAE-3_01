package metier;

import java.util.ArrayList;
import java.util.List;

public class Retroconception
{
	private List<Stereotype> lstStereotype;
	private List<Association> lstAssociations;
	
	public Retroconception()
	{
		this.lstStereotype   = new ArrayList<Stereotype> ();
		this.lstAssociations = new ArrayList<Association>();
	}
	
	/**
	 * Ouvre un dossier avec son chemin passé en paramètre
	 * @param cheminDossier Chemoin vers le dossier
	 */
	public void ouvrirDossier(String cheminDossier)
	{
		this.lstStereotype = AnalyseurJava.analyserDossier(cheminDossier);
	}

	/**
	 * I have no idea of what this is supposed to do
	 * @param fichier Contenu du fichier
	 */
	public void ouvrirFichier(String fichier)
	{
		this.lstStereotype.add( AnalyseurJava.analyserFichier(fichier) );
		
		for ( Stereotype s : this.lstStereotype )
			System.out.println( s.toString() );
	}

	public Stereotype getSter() { return this.lstStereotype.get(0); } ///~~~~~~~~~~~~~
	
	
	
	
	public void creationAssociation()
	{
		List<Association> lstAssociationTmp = new ArrayList<>();
		
		for (Stereotype stereotype1 : this.lstStereotype) 
		{
			for (Attribut attribut : stereotype1.getAttributs()) 
			{
				for (Stereotype stereotype2 : this.lstStereotype)
				{
					if (attribut.getType().equals(stereotype2.getNom()))
					{
						lstAssociationTmp.add(new Association(stereotype1, stereotype2, "1"));
					}
					else if(attribut.getType().contains("<") && attribut.getType().contains(">"))
					{
						String typeExtrait = extraireTypeGenerique(attribut.getType());

						if (typeExtrait.equals(stereotype2.getNom()))
						{
							lstAssociationTmp.add(new Association(stereotype1, stereotype2, "0..*"));
						}
					} 
				}
			}
		}

		for (Association association1 : lstAssociationTmp) 
		{
			for (Association association2 : lstAssociationTmp) 
			{
				String truc = "";
				
				if (association1.getStereotype1().getNom().equals(association2.getStereotype2().getNom()) &&
					association2.getStereotype1().getNom().equals(association1.getStereotype2().getNom()))
				{
					truc = "bi";
				}
				else
				{
					truc = "uni";
				}

				this.lstAssociations.add(new Association(association1.getStereotype1(), association1.getStereotype2(), truc, "", ""));


			}
		}


	}

	private String extraireTypeGenerique(String type) 
	{
		if (type.contains("<") && type.contains(">")) 
		{
			return type.substring(type.indexOf("<") + 1, type.indexOf(">"));
		}
		return type;  	
	}
}