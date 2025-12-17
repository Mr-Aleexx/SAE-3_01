package retroconception.metier;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import retroconception.metier.lecture.AnalyseurJava;
/**
 * Mets en place la rétroconception des associations
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
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
	
	public Classe            getClasse     (int id) { return this.lstClasses.get(id); }
	public int               getNbClasse   ()       { return this.lstClasses.size();  }

	public List<Classe>      getLstClasses     () { return this.lstClasses;      }
	public List<Association> getLstAssociations() { return this.lstAssociations; }
	
	public Integer getIndiceClasse(int x, int y)
	{
		for (int cpt = 0; cpt < this.lstClasses.size(); cpt++)
			if (this.lstClasses.get(cpt).possede(x, y))
				return cpt;
		
		return null;
	}

	public void deplacerClasse(int idTache, int x, int y)
	{
		if (idTache >= 0 && idTache < this.lstClasses.size())
		{
			this.lstClasses.get(idTache).getPos().deplacerX(x);
			this.lstClasses.get(idTache).getPos().deplacerY(y);
		}
	}

	public void definirDimension(Classe c, int ligne)
	{
		c.getPos().setTailleX(ligne);
	}

	/**
	 * Ouvre un dossier avec son chemin passé en paramètre
	 * @param cheminDossier Chemoin vers le dossier
	*/
	public void ouvrirDossier(String cheminDossier)
	{
		File dossier = new File(cheminDossier);
		
		File[] lstFichier = dossier.listFiles();
		
		this.lstClasses.clear();

		for (File fichier : lstFichier)
			if (fichier.getName().contains(".java") )
				this.lstClasses.add(AnalyseurJava.analyserFichier(fichier.getAbsolutePath()));

		this.initPosition();
		this.creationAssociation();
	}

	public void ouvrirFichier(String fichier)
	{
		AnalyseurJava.analyserFichier(fichier);
	}

	public void initPosition()
	{
		int posX,      tailleX;
		int yClasse,   tailleYClasse;
		int yAttribut, tailleYAttribut;
		int yMethode,  tailleYMethode;

		for (Classe classe : this.lstClasses)
		{
			posX    = 100;
			tailleX = 0;

			tailleYClasse = 18;
			if( classe.estAbstraite()            ) tailleYClasse += 16;
			if(!classe.getStereotype().equals("")) tailleYClasse += 16;
		
			tailleYAttribut = 18;
			if( !classe.getAttributs().isEmpty() ) tailleYAttribut += (classe.getAttributs().size()-1) * 16;

			tailleYMethode = 18;
			if( !classe.getMethodes() .isEmpty() ) tailleYMethode += (classe.getMethodes ().size()-1) * 16;

			if(classe.getAttributs().size() > 3)
				tailleYAttribut -= (classe.getAttributs().size() - 4) * 16;

			if(classe.getMethodes().size() > 3)
				tailleYMethode  -= (classe.getMethodes ().size() - 4) * 16;

			yClasse   = 75;
			yAttribut = yClasse   + tailleYClasse   /2 + tailleYAttribut /2;
			yMethode  = yAttribut + tailleYAttribut /2 + tailleYMethode  /2;

			classe.setPosition( new PositionClasse( posX,    yClasse,       yAttribut,       yMethode,
			                                        tailleX, tailleYClasse, tailleYAttribut, tailleYMethode ));
		}
	}

	public String getLigneMax(Classe classe)
	{
		String ligne;
		String maxAttribut  = "";
		String nomAttribut  = "";
		String maxMethode   = "";
		String typeAttribut = "";
		String typeMethode  = "";
		String nomMethode   = "";
			
		ligne = classe.getNom() + (classe.estLectureUnique() ? " {Gelé}" : "");

		if( ligne.length() < classe.getStereotype().length()+2 && !classe.getStereotype().equals("") )
			ligne += "«"+classe.getStereotype()+"»";

		for (Attribut attrib : classe.getAttributs())
		{
			maxAttribut = attrib.getSymbole() + " " + attrib.getNom() + (classe.estLectureUnique() ? " {Gelé}" : "");
			
			if( nomAttribut.length() < maxAttribut.length())
				nomAttribut = maxAttribut;
			if( typeAttribut.length() < attrib.getType().length() )
				typeAttribut = attrib.getType();

			if( ligne.length() < (nomAttribut + " : " + typeAttribut).length())
				ligne = nomAttribut + " : " + typeAttribut;
		}

		for (Methode meth : classe.getMethodes())
		{
			String type = meth.getType();

			String para = "";
			for (int cptPara = 0; cptPara < meth.getParametre().size(); cptPara++)
			{
				Parametre param = meth.getParametre().get(cptPara);

				if( cptPara > 1 )
				{
					para += "… ";
					break;
				}
				para += param.nom() + " : " + param.type();
				if(meth.getParametre().indexOf(param) != meth.getParametre().size() - 1)
					para += ", ";
			}
			maxMethode = (meth.getSymbole() + " " + meth.getNom() + "(" + para + ")");
			if (maxMethode.length() > nomMethode.length())
				nomMethode = maxMethode;

			if( meth.getType() != null && ! type.equals( "void" ) )
				if( typeMethode.length() < type.length() )
					typeMethode = meth.getType();

			if( ligne.length() < (nomMethode + " : " + typeMethode).length())
				ligne = nomMethode + " : " + typeMethode;
		}
		
		return ligne;
	}

	public void sauvegarderFichier(File fichier)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.newDocument();
		
			Element racine = document.createElement("ihm");
			document.appendChild(racine);

			for (Classe c : this.lstClasses)
			{
				document = this.sauvegardeClasse(c, document, racine);
			}

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource    source   = new DOMSource(document);
			StreamResult resultat = new StreamResult(new File(fichier + ".xml"));
			
			transformer.transform(source, resultat);
		}
		catch (ParserConfigurationException      e) {e.printStackTrace();}
		catch (TransformerConfigurationException e) {e.printStackTrace();}
		catch (TransformerException              e) {e.printStackTrace();}
		
	}


	public void chargerSauvegarde(File fichier)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(fichier);

			Element racine = document.getDocumentElement();
			NodeList noeuds = racine.getElementsByTagName("bloc");

			for (int i = 0; i < noeuds.getLength(); i++)
			{
				
			}
		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{e.printStackTrace();}

	}

	
	public void creationAssociation()
	{
		List<Association> lstAssociationTmp = creationMultiplicite();
		this.suppressionAttributAsso(lstAssociationTmp);
		this.creationTypeAsso(lstAssociationTmp);
	}


	/**
	 * Créer les multiplicitées en fonctions des associations
	 * @return List des associations de la classe 
	 */
	private List<Association> creationMultiplicite()
	{
		List<Association> lstAssociationTmp = new ArrayList<>();
		String            type;
		boolean estTableau = false;

		for (Classe classe1 : this.lstClasses)
		{
			for (Attribut attribut : classe1.getAttributs())
			{
				type = attribut.getType();
				
				// Gere le cas où la multipliciter est multiple ( tableau et tous types de listes.. )
				if ( type.contains( "<" ) || type.contains( "[" ) )
				{
					if ( type.contains( "<" ) ) type = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
					else                        type = type.substring( 0                   , type.indexOf("["));

					estTableau = true;
				}
				
				if ( type.equals( classe1.getNom() ) )
				{
					String mult = estTableau ? "0..*" : "1..1";
					lstAssociationTmp.add(new Association(classe1, classe1, mult));
				}
				
				for (Classe classe2 : this.lstClasses)
				{
					if (classe1 == classe2) continue;
					
					if (type.equals(classe2.getNom())) 
					{
						String mult = estTableau ? "0..*" : "1..1";
						lstAssociationTmp.add(new Association(classe1, classe2, mult));
					}
				}
			}
		}

		return lstAssociationTmp;
	}


	private void suppressionAttributAsso( List<Association> lstAssociationTmp )
	{
		for (Classe classe : this.lstClasses)
		{
			List<Attribut> attributsASupprimer = new ArrayList<>();
			
			for ( Attribut attribut : classe.getAttributs() )
			{
				String type = attribut.getType();
				
				// Gere le cas où la multipliciter est multiple ( tableau et tous types de listes.. )
				if (type.contains("<")) type = type.substring(type.indexOf("<") + 1, type.indexOf(">"));
				if (type.contains("[")) type = type.substring(0, type.indexOf("["));
				
				// Vérifier si ce type correspond à une classe connue
				for (Association association : lstAssociationTmp)
				{
					// L'attribut est une association si son type correspond à classe2
					// ET que classe1 de l'association est la classe courante
					if (association.getClasse1() == classe && 
						type.equals(association.getClasse2().getNom()))
					{
						attributsASupprimer.add(attribut);
						break; // Un attribut ne peut correspondre qu'à une seule association
					}
				}
			}
			
			// Supprimer les attributs après l'itération
			for (Attribut attr : attributsASupprimer)
			{
				classe.enleverAttribut(attr);
			}
		}
	}

	private void creationTypeAsso( List<Association> lstAssociationTmp )
	{
		boolean[] utilise = new boolean[lstAssociationTmp.size()];

		for (int i = 0; i < lstAssociationTmp.size(); i++)
		{
			if (utilise[i])
				continue;

			Association association1 = lstAssociationTmp.get(i);
			Association association2 = null;

			// Si c'est une association réflexive, la traiter directement
			if (association1.getClasse1() == association1.getClasse2())
			{
				utilise[i] = true;
				this.lstAssociations.add(new Association(association1.getClasse1(), association1.getClasse2(),
						"unidirectionnelle", association1.getMultiplicite(), "0..*"));
				continue;
			}

			// Associtiation non reflexive
			for (int j = i + 1; j < lstAssociationTmp.size(); j++)
			{
				if (utilise[j])
					continue;

				Association candidate = lstAssociationTmp.get(j);

				if (association1.getClasse1() == candidate.getClasse2()
						&& association1.getClasse2() == candidate.getClasse1())
				{
					association2 = candidate;
					utilise[j] = true;
					break;
				}
			}

			utilise[i] = true;

			if (association2 != null)
			{
				this.lstAssociations.add(new Association(association1.getClasse1(), association1.getClasse2(),
						"bidirectionnelle", association1.getMultiplicite(), association2.getMultiplicite()));
			}
			else
			{
				this.lstAssociations.add(new Association(association1.getClasse1(), association1.getClasse2(),
						"unidirectionnelle", association1.getMultiplicite(), "0..*"));
			}
		}
	}

	public void printLstAssociation()
	{
		for (int i = 0 ; i < this.lstAssociations.size() ; i++) {
			System.out.println(this.lstAssociations.get(i));
		}
	}

	private Document sauvegardeClasse(Classe c, Document doc, Element e)
	{
		Element bloc = doc.createElement("bloc");
		e.appendChild(bloc);

		bloc.appendChild(Retroconception.createElement(doc, "visibilite"   ,                c.getVisibilite   () ));
		bloc.appendChild(Retroconception.createElement(doc, "statique"     , String.valueOf(c.estStatique     ())));
		bloc.appendChild(Retroconception.createElement(doc, "lectureUnique", String.valueOf(c.estLectureUnique())));
		bloc.appendChild(Retroconception.createElement(doc, "abstraite"    , String.valueOf(c.estAbstraite    ())));
		bloc.appendChild(Retroconception.createElement(doc, "nom"          , String.valueOf(c.getNom          ())));
		bloc.appendChild(Retroconception.createElement(doc, "stereotype"   , String.valueOf(c.getStereotype   ())));
		bloc.appendChild(Retroconception.createElement(doc, "mere"         , String.valueOf(c.getMere         ())));

		bloc.appendChild(Retroconception.createElement(doc, "attributs", String.valueOf(c.getAttributs())));
		
		return doc;
	}

	private static Element createElement(Document doc, String nom, String val)
	{
		Element element = doc.createElement(nom);
		element.appendChild(doc.createTextNode(val));
		return element;
	}

}