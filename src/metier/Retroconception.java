package src.metier;

import java.util.ArrayList;
import java.util.List;

public class Retroconception
{
	private List<Stereotype>  lstStereotype;
	private List<Association> lstAssociations;
	
	public Retroconception()
	{
		this.lstStereotype   = new ArrayList<Stereotype> ();
		this.lstAssociations = new ArrayList<Association>();
		creationAssociation();
	}
	
	/**
	 * Ouvre un dossier avec son chemin passé en paramètre
	 * @param cheminDossier Chemoin vers le dossier
	 */
	public void ouvrirDossier(String cheminDossier)
	{
		//this.lstStereotype = AnalyseurJava.analyserDossier(cheminDossier);

		for ( Stereotype s : this.lstStereotype )
			System.out.println( s.toString() );
	}

	/**
	 * I have no idea of what this is supposed to do
	 * @param fichier Contenu du fichier
	 */
	public void ouvrirFichier(String fichier)
	{
		System.out.println( AnalyseurJava.analyserFichier(fichier).toString() );
	}
	
	public Integer getIndiceStereotype(int x, int y)
	{
		for (int cpt = 0; cpt < this.lstStereotype.size(); cpt++)
			if (this.lstStereotype.get(cpt).possede(x, y))
				return cpt;
		
		return null;
	}


	public void deplacerStereotype(int idTache, int x, int y)
	{
		if (idTache >= 0 && idTache < this.lstStereotype.size())
		{
			this.lstStereotype.get(idTache).getPos().deplacerX(x);
			this.lstStereotype.get(idTache).getPos().deplacerY(y);
		}
	}
	
	
	public void creationAssociation()
	{
		List<Association> lstAssociationTmp = new ArrayList<>();
		List<Integer> lstIndex = new ArrayList<>();
		
		for (Stereotype stereotype1 : this.lstStereotype) 
		{
			for (Attribut attribut : stereotype1.getAttributs()) 
			{
				for (Stereotype stereotype2 : this.lstStereotype)
				{
					if (attribut.getType().equals(stereotype2.getNom()))
					{
						lstAssociationTmp.add(new Association(stereotype1, stereotype2, "1..1"));
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


		for (int i = 0; i < lstAssociationTmp.size(); i++) 
    	{
			if (lstIndex.contains(i)) continue;
			
			Association association1 = lstAssociationTmp.get(i);
			String lien = "unidirectionnelle";
			String mult1 = association1.getMultiplicite();
			String mult2 = mult1; 
			
			for (int j = i + 1; j < lstAssociationTmp.size(); j++) 
			{
				Association association2 = lstAssociationTmp.get(j);
				
				if (verifAssociation(association1, association2))
				{
					lien = "bidirectionnelle";
					mult2 = association2.getMultiplicite();
					lstIndex.add(j);
					break;
				}
			}
			this.lstAssociations.add(new Association(association1.getStereotype1(), association1.getStereotype2(), lien, mult1, mult2));
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

	private boolean verifAssociation(Association association1, Association association2){
		if (association1.getStereotype1().getNom().equals(association1.getStereotype2().getNom()))
			return false;
		
		return association1.getStereotype1().getNom().equals(association2.getStereotype2().getNom()) &&
			   association2.getStereotype1().getNom().equals(association1.getStereotype2().getNom());
	}

	public List<Association> getLsAssociations() {
		return getLsAssociations();
	}

	public void printLstAssociation() {
		for (int i = 0 ; i < this.lstAssociations.size() ; i++) {
			System.out.println(this.lstAssociations.get(i));
		}
	}
}