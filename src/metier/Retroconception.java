package src.metier;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Retroconception
{
	private List<Classe>  lstClasses;
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
		ArrayList<Classe> lstClasses = new ArrayList<Classe>();
		
		File[] lstFichier = dossier.listFiles();
		
		for (File fichier : lstFichier)
			if (fichier.getName().contains(".java"))
				lstClasses.add(AnalyseurJava.analyserFichier(fichier.getAbsolutePath()));
			
		this.creationAssociation();		
	}

	/**
	 * I have no idea of what this is supposed to do
	 * @param fichier Contenu du fichier
	 */
	public void ouvrirFichier(String fichier)
	{
		AnalyseurJava.analyserFichier(fichier);
	}

	
	public void creationAssociation()
	{
		List<Association> lstAssociationTmp = creationMultiplicite();
		creationTypeAsso(lstAssociationTmp);
	}

	private List<Association> creationMultiplicite()
	{
		List<Association> lstAssociationTmp = new ArrayList<>();
		
		for (Classe Classe1 : this.lstClasses) 
		{
			for (Attribut attribut : Classe1.getAttributs()) 
			{
				for (Classe Classe2 : this.lstClasses)
				{
					if(!Classe1.getNom().equals(Classe2.getNom()))
					{
						if (attribut.getType().equals(Classe2.getNom()))
						{
							lstAssociationTmp.add(new Association(Classe1, Classe2, "(1..1)"));
						}
						else if(attribut.getType().contains("<") && attribut.getType().contains(">"))
						{
							String typeExtrait = extraireTypeGenerique(attribut.getType());

							if (typeExtrait.equals(Classe2.getNom()))
							{
								lstAssociationTmp.add(new Association(Classe1, Classe2, "(0..*)"));
							}
						}
					}
				}
			}
		}

		return lstAssociationTmp;
	}

	private void creationTypeAsso(List<Association> lstAssociationTmp) 
	{
		List<Integer> lstIndex = new ArrayList<>();
		
		for (int i = 0; i < lstAssociationTmp.size(); i++) 
		{
			if (lstIndex.contains(i)) continue;
			
			Association association1 = lstAssociationTmp.get(i);
			String lien = "unidirectionnelle";
			String mult1 = association1.getMultiplicite();
			String mult2 = null;
			
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
			if (mult2 == null) 
				mult2 = "(1..1)";
				
			this.lstAssociations.add(new Association(association1.getClasse1(), association1.getClasse2(), lien, mult1, mult2));
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
		if (association1.getClasse1().getNom().equals(association1.getClasse2().getNom()))
			return false;
		
		return association1.getClasse1().getNom().equals(association2.getClasse2().getNom()) &&
			   association2.getClasse1().getNom().equals(association1.getClasse2().getNom());
	}

	public List<Association> getLsAssociations() {
		return getLsAssociations();
	}

	public void printLstAssociation() {
		for (int i = 0 ; i < this.lstAssociations.size() ; i++) {
			System.out.println(this.lstAssociations.get(i));
		}
	}

	public String toString()
	{
		String sRet = "";

		for ( Classe s : this.lstClasses )
			sRet += s.toString() + "\n";

		sRet += "Associations:\n";

		for (int i = 0 ; i < this.lstAssociations.size() ; i++)
			sRet +="Association " + (i + 1) + ": " + this.lstAssociations.get(i).toString() + "\n";

		sRet += "Heritage:\n";
		for ( Classe stereo : this.lstClasses )
			if( stereo.getMere() != null ) sRet += stereo.getNom() + " hérite de " + stereo.getMere();

		sRet += "\nImplements:\n";
		for ( Classe stereo : this.lstClasses )
			if( ! stereo.getLstImplementations().isEmpty() )
			{
				sRet += stereo.getNom() + " implemente ";
				for ( String s : stereo.getLstImplementations() )
					sRet += s + ", ";
				sRet = sRet.substring( 0, sRet.length()-2 ) + "\n";
			}

		return sRet;
	}
}