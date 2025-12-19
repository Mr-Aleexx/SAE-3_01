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
	
	public Classe            getClasse  (int id) { return this.lstClasses.get(id); }
	public int               getNbClasse()       { return this.lstClasses.size();  }

	public List<Classe>      getLstClasses     () { return this.lstClasses;      }
	public List<Association> getLstAssociations() { return this.lstAssociations; }
	
	/**
	 * Récupère l'indice d'un classe à partir de ses coordonnées
	 * @param x coordonnée x
	 * @param y coordonnée y
	 * @return
	 */
	public Integer getIndiceClasse(int x, int y)
	{
		for (int cpt = 0; cpt < this.lstClasses.size(); cpt++)
			if (this.lstClasses.get(cpt).possede(x, y))
				return cpt;
		
		return null;
	}

	/**
	 * Déplace une classe à partir de son id et de ses coordonnées
	 * @param idClasse numero de la classe
	 * @param x coordonnée x
	 * @param y coordonnée y
	 * @return
	 */
	public void deplacerClasse(int idClasse, int x, int y)
	{
		if (idClasse >= 0 && idClasse < this.lstClasses.size())
		{
			this.lstClasses.get(idClasse).getPos().deplacerX(x);
			this.lstClasses.get(idClasse).getPos().deplacerY(y);
		}
	}


	/**
	 * Déplace une classe à partir de son id et de ses coordonnées
	 * @param c La classe à modifier
	 * @param longueur nouvelle largeur de la classe
	 * @return
	 */
	public void definirLargeur(Classe c, int longueur)
	{
		c.getPos().setTailleX(longueur);
	}

	/**
	 * Ouvre un dossier avec son chemin passé en paramètre
	 * @param cheminDossier Chemoin vers le dossier
	*/
	public void ouvrirDossier( String cheminDossier )
	{
		File dossier = new File( cheminDossier );
		
		File[] lstFichier = dossier.listFiles();
		
		this.lstClasses.clear();
		this.lstAssociations.clear();

		// Ouvre chaque fichiers .java
		for ( File fichier : lstFichier )
			if ( fichier.getName().contains(".java") )
				this.lstClasses.add( AnalyseurJava.analyserFichier(fichier.getAbsolutePath()) );

		this.creationAssociation();
		this.initPosition();
	}


	/**
	 * Initialize la position d'une classe ( la taille, les coordonnees )
	 * @return
	 */
	public void initPosition()
	{
		int posX,      tailleX;
		int yClasse=0,   tailleYClasse;
		int yAttribut, tailleYAttribut;
		int yMethode,  tailleYMethode;
		
		
		for ( Classe classe : this.lstClasses )
		{
			posX    = 400;
			tailleX = 0;

			tailleYClasse = 18;
			if(   classe.estAbstraite()             ) tailleYClasse += 16;
			if( ! classe.getStereotype().equals("") ) tailleYClasse += 16;
		
			tailleYAttribut = 18;
			if( ! classe.getAttributs().isEmpty() ) tailleYAttribut += ( classe.getAttributs().size()-1 ) * 16;

			tailleYMethode = 18;
			if( ! classe.getMethodes() .isEmpty() ) tailleYMethode += ( classe.getMethodes ().size()-1 ) * 16;

			if( classe.getAttributs().size() > 3 )
				tailleYAttribut -= ( classe.getAttributs().size() - 4 ) * 16;

			if(classe.getMethodes().size() > 3)
				tailleYMethode  -= (classe.getMethodes ().size() - 4) * 16;

			yClasse   += tailleYClasse + tailleYAttribut + tailleYMethode + 25;
			yAttribut = yClasse   + tailleYClasse   /2 + tailleYAttribut /2;
			yMethode  = yAttribut + tailleYAttribut /2 + tailleYMethode  /2;

			classe.setPosition( new PositionClasse( posX,    yClasse,       yAttribut,       yMethode,
			                                        tailleX, tailleYClasse, tailleYAttribut, tailleYMethode ));
		}
	}

	/**
	 * Recupère la taille maximale d'une ligne dans une classe
	 * @param classe Numero classe
	 * @return
	 */
	public String getLigneMax( Classe classe )
	{
		String ligne;
		String maxAttribut  = "";
		String nomAttribut  = "";
		String maxMethode   = "";
		String typeAttribut = "";
		String typeMethode  = "";
		String nomMethode   = "";
		String methodeSansT = "";
		String specificite  = classe.estLectureUnique() ? " {Gelé}" : "";

		// Gere la taille du nom de la classe
		ligne = classe.getNom() + specificite;

		if( ligne.length() < classe.getStereotype().length()+2 && !classe.getStereotype().equals("") )
			ligne += "«"+classe.getStereotype()+"»";

		// Gere la taille des attributs
		for (int cptAtt = 0; cptAtt < 3 && cptAtt < classe.getAttributs().size(); cptAtt++)
		{
			Attribut attrib = classe.getAttributs().get(cptAtt);

			specificite = attrib.estLectureUnique() ? " {Gelé}" : "";

			maxAttribut = attrib.getSymbole() + " " + attrib.getNom();

			// Gere si la methode possede une valeur par defaut ( static et final )
			if(!attrib.getValeurConstante().equals(""))
			{
				maxAttribut += " = ";
				List<String> valeurs = AnalyseurJava.decomposeurType(attrib.getValeurConstante(), ',');
				for ( int cptVal = 0; cptVal < 3 && cptVal < valeurs.size(); cptVal++ )
					maxAttribut += valeurs.get(cptVal);
				maxAttribut += (( valeurs.size() > 3 ) ? " ..." : "");
			}

			if( nomAttribut.length() < maxAttribut.length())
				nomAttribut = maxAttribut;

			// Gere la specificite d'une classe abstract, default, gelée
			if( typeAttribut.length() < attrib.getType().length() + specificite.length() )
				typeAttribut = attrib.getType() + specificite;

			// Comparaison finale
			if( ligne.length() < (nomAttribut + " : " + typeAttribut).length())
				ligne = nomAttribut + " : " + typeAttribut;
		}

		// Gere la taille des methodes
		for ( int cptMeth = 0; cptMeth < 3 && cptMeth < classe.getMethodes().size(); cptMeth++ )
		{
			Methode meth = classe.getMethodes().get(cptMeth);

			String type = meth.getType();

			// Parcours les 3 premiers parametre pour definir la longueur max car sinon ...
			String para = "";
			for (int cptPara = 0; cptPara < 3 && cptPara < meth.getParametre().size(); cptPara++)
			{
				Parametre param = meth.getParametre().get(cptPara);

				if( cptPara > 1 )
				{
					para += " … ";
					break;
				}
				para += param.nom() + " : " + param.type();
				if(meth.getParametre().indexOf(param) != meth.getParametre().size() - 1)
					para += ", ";
			}

			maxMethode = (meth.getSymbole() + " " + meth.getNom() + "(" + para + ")");

			// Gere la specificite d'une classe abstract, default, gelée
			specificite = "";
			specificite += (meth.estLectureUnique() ? " {Gelé}" : "");
			specificite += (meth.estAbstraite() ? " {abstract}" : "");
			specificite += (!meth.getStereotype().equals("") ? " {" + meth.getStereotype()+ "}" : "");

			// Enleve l'affichage des methode renvoyant void ou étant un constructeur ( null )
			if( type != null && ! type.equals( "void" ) )
			{
				if (maxMethode.length() > nomMethode.length())
					nomMethode = maxMethode;

				if( typeMethode.length() < type.length() )
					typeMethode =  " : " + type;
			}
			else
				if( methodeSansT.length() < (maxMethode + specificite).length())
					methodeSansT = maxMethode + specificite;

			// Comparaison finale
			if( methodeSansT.length() < (nomMethode + typeMethode + specificite).length())
			{
				if( ligne.length() < (nomMethode + typeMethode + specificite).length())
					ligne = nomMethode + typeMethode + specificite;
			}
			else
				if( ligne.length() < (methodeSansT).length())
					ligne = methodeSansT;
		}

		return ligne;
	}

	/**
	 * Permet de sauvegarder un fichier au format xml
	 * @param fichier fichier de sauvegarde
	 * @return
	 */
	public void sauvegarderFichier(File fichier)
	{
		try
		{
			DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
			DocumentBuilder        builder  = factory.newDocumentBuilder();
			Document               document = builder.newDocument();
		
			Element racine = document.createElement("ihm");
			document.appendChild(racine);

			Element classes = document.createElement("classes");
			racine.appendChild(classes);

			for (Classe c : this.lstClasses)
				document = this.sauvegardeClasse(c, document, classes);

			for (Association a : this.lstAssociations)
				document = this.sauvegarderAssociation(a, document, racine);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer        transformer        = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			DOMSource    source   = new DOMSource(document);
			StreamResult resultat = new StreamResult(new File(fichier + ".xml"));
			
			transformer.transform(source, resultat);
		}
		catch (ParserConfigurationException      e) {e.printStackTrace();}
		catch (TransformerConfigurationException e) {e.printStackTrace();}
		catch (TransformerException              e) {e.printStackTrace();}
		
	}

	/**
	 * Charge la sauvegarde d'un fichier xml dans l'IHM
	 * @param fichier fichier de sauvergarde
	 * @return
	 */
	public void chargerSauvegarde(File fichier)
	{
		try
		{
			this.lstClasses.clear();
			this.lstAssociations.clear();
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document document = builder.parse(fichier);

			Element  racine  = document.getDocumentElement();
			NodeList classes = racine.getElementsByTagName("classes");

			Element classesElem = (Element) classes.item(0);
			NodeList bloc = classesElem.getElementsByTagName("bloc");

			for (int i = 0; i < bloc.getLength(); i++)
			{
				Element classe = (Element) bloc.item(i);
				
				this.lstClasses.add(this.chargerClasse(classe));
			}

			NodeList liens = racine.getElementsByTagName("lien");

			for (int i = 0; i < liens.getLength(); i++)
			{
				Element asso = (Element) liens.item(i);
				
				this.lstAssociations.add(this.chargerAssociation(asso));
			}

		}
		catch (ParserConfigurationException | SAXException | IOException e)
		{e.printStackTrace();}

	}

	/**
	 * Créer les associations des différents codes du dossier
	 */
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

	/**
	 * Supprime les attributs d'une association entrée en parametre
	 * @param lstAssociationTmp coordonnée x
	 * @return
	 */
	private void suppressionAttributAsso( List<Association> lstAssociationTmp )
	{
		for (Classe classe : this.lstClasses)
		{
			List<Attribut> attributsASupprimer = new ArrayList<>();
			
			for ( Attribut attribut : classe.getAttributs() )
			{
				String type = attribut.getType();
				
				// Gere le cas où la multiplicité est multiple ( tableau et tous types de listes.. )
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

	/**
	 * Création du type des associations
	 * @param lstAssociationTmp liste des associations
	 * @return
	 */
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
						"unidirectionnelle", association1.getMultiplicite1(), "0..*"));
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
				         "bidirectionnelle", association1.getMultiplicite1(), association2.getMultiplicite1()));
			}
			else
			{
				this.lstAssociations.add(new Association(association1.getClasse1(), association1.getClasse2(),
				        "unidirectionnelle", association1.getMultiplicite1(), "0..*"));
			}
		}
	}

	/**
	 * Transforme chaque element de la classe en element xml
	 * @param c Classe à enregistrer
	 * @param doc Document d'enregistrement
	 * @param e Element racine
	 * @return Le Document avec la classe écrite dedans en xml
	 */
	private Document sauvegardeClasse(Classe c, Document doc, Element e)
	{
		Element bloc = doc.createElement("bloc");
		e.appendChild(bloc);

		bloc.appendChild(Retroconception.creationElement(doc, "visibilite"   ,                c.getVisibilite   () ));
		bloc.appendChild(Retroconception.creationElement(doc, "statique"     , String.valueOf(c.estStatique     ())));
		bloc.appendChild(Retroconception.creationElement(doc, "lectureUnique", String.valueOf(c.estLectureUnique())));
		bloc.appendChild(Retroconception.creationElement(doc, "abstraite"    , String.valueOf(c.estAbstraite    ())));
		bloc.appendChild(Retroconception.creationElement(doc, "nom"          ,                c.getNom          () ));
		bloc.appendChild(Retroconception.creationElement(doc, "stereotype"   ,                c.getStereotype   () ));
		bloc.appendChild(Retroconception.creationElement(doc, "mere"         ,                c.getMere         () ));

		for (Attribut a : c.getAttributs())
		{
			Element attribut = doc.createElement("attribut");
			bloc.appendChild(attribut);
			
			attribut.appendChild(Retroconception.creationElement(doc, "visibiliteA"   ,                a.getVisibilite     () ));
			attribut.appendChild(Retroconception.creationElement(doc, "statiqueA"     , String.valueOf(a.estStatique       ())));
			attribut.appendChild(Retroconception.creationElement(doc, "lectureUniqueA", String.valueOf(a.estLectureUnique  ())));
			attribut.appendChild(Retroconception.creationElement(doc, "typeA"         ,                a.getType           () ));
			attribut.appendChild(Retroconception.creationElement(doc, "nomA"          ,                a.getNom            () ));
			attribut.appendChild(Retroconception.creationElement(doc, "constante"     ,                a.getValeurConstante() ));
		}

		for (Methode m : c.getMethodes())
		{
			Element methode = doc.createElement("methode");
			bloc.appendChild(methode);
			
			methode.appendChild(Retroconception.creationElement(doc, "visibiliteM"   ,                m.getVisibilite   () ));
			methode.appendChild(Retroconception.creationElement(doc, "statiqueM"     , String.valueOf(m.estStatique     ())));
			methode.appendChild(Retroconception.creationElement(doc, "lectureUniqueM", String.valueOf(m.estLectureUnique())));
			methode.appendChild(Retroconception.creationElement(doc, "abstraiteM"    , String.valueOf(m.estAbstraite    ())));
			methode.appendChild(Retroconception.creationElement(doc, "stereotypeM"   ,                m.getStereotype   () ));
			methode.appendChild(Retroconception.creationElement(doc, "typeM"         ,                m.getType         () ));
			methode.appendChild(Retroconception.creationElement(doc, "nomM"          ,                m.getNom          () ));

			for (Parametre p : m.getParametre())
			{
				Element param = doc.createElement("parametre");
				methode.appendChild(param);

				param.appendChild(Retroconception.creationElement(doc, "typeP", p.type()));
				param.appendChild(Retroconception.creationElement(doc, "nomP" , p.nom ()));
			}
		}

		//for (Classe ci : c.getClassesInterne())
		//	sauvegardeClasse(ci, doc, bloc);

		for (String imp : c.getLstImplementations())
		{
			Element implementation = doc.createElement("implementation");
			bloc.appendChild(implementation);

			implementation.appendChild(Retroconception.creationElement(doc, "implementation" , imp));
		}

		Element position = doc.createElement("position");
		bloc.appendChild(position);

		position.appendChild(Retroconception.creationElement(doc, "centreX"        , String.valueOf(c.getPos().getCentreX        ())));
		position.appendChild(Retroconception.creationElement(doc, "centreYClasse"  , String.valueOf(c.getPos().getCentreYClasse  ())));
		position.appendChild(Retroconception.creationElement(doc, "centreYAttribut", String.valueOf(c.getPos().getCentreYAttribut())));
		position.appendChild(Retroconception.creationElement(doc, "centreYMethode" , String.valueOf(c.getPos().getCentreYMethode ())));
		position.appendChild(Retroconception.creationElement(doc, "tailleX"        , String.valueOf(c.getPos().getTailleX        ())));
		position.appendChild(Retroconception.creationElement(doc, "tailleYClasse"  , String.valueOf(c.getPos().getTailleYClasse  ())));
		position.appendChild(Retroconception.creationElement(doc, "tailleYAttribut", String.valueOf(c.getPos().getTailleYAttribut())));
		position.appendChild(Retroconception.creationElement(doc, "tailleYMethode" , String.valueOf(c.getPos().getTailleYMethode ())));
		
		return doc;
	}

	/**
	 * Transforme chaque element de l'association en element xml
	 * @param a Association à enregistrer
	 * @param doc Document d'enregistrement
	 * @param e Element racine
	 * @return Le Document avec l'association écrite dedans en xml
	 */
	public Document sauvegarderAssociation(Association a, Document doc, Element e)
	{
		Element liens = doc.createElement("lien");
		e.appendChild(liens);

		doc = sauvegardeClasse(a.getClasse1(), doc, liens);
		doc = sauvegardeClasse(a.getClasse2(), doc, liens);

		liens.appendChild(Retroconception.creationElement(doc, "typeAsso"     , a.getTypeAsso     ()));
		liens.appendChild(Retroconception.creationElement(doc, "multiplicite1", a.getMultiplicite1()));
		liens.appendChild(Retroconception.creationElement(doc, "multiplicite2", a.getMultiplicite2()));
		liens.appendChild(Retroconception.creationElement(doc, "role1"        , a.getRole1        ()));
		liens.appendChild(Retroconception.creationElement(doc, "role2"        , a.getRole2        ()));

		return doc;
	}


	/**
	 * Crée un nouvel élément dans le document
	 * @param doc Document où créer l'élément
	 * @param nom Nom donné à la borne créée
	 * @param val Valeur mise entre les bornes
	 * @return L'élément créé à ajouter au document
	 */
	private static Element creationElement(Document doc, String nom, String val)
	{
		Element element = doc.createElement(nom);
		element.appendChild(doc.createTextNode(val));
		return element;
	}


	/**
	 * Crer les classes a partir du xml
	 * @param classeXML Element racine
	 * @return La Classe créée à partir des éléments du fichier xml
	 */
	private Classe chargerClasse(Element classeXML)
	{
		String  visibilite    =                      classeXML.getElementsByTagName("visibilite"   ).item(0).getTextContent() ;
		boolean statique      = Boolean.parseBoolean(classeXML.getElementsByTagName("statique"     ).item(0).getTextContent());
		boolean lectureUnique = Boolean.parseBoolean(classeXML.getElementsByTagName("lectureUnique").item(0).getTextContent());
		boolean abstraite     = Boolean.parseBoolean(classeXML.getElementsByTagName("abstraite"    ).item(0).getTextContent());
		String  nom           =                      classeXML.getElementsByTagName("nom"          ).item(0).getTextContent() ;
		String  stereotype    =                      classeXML.getElementsByTagName("stereotype"   ).item(0).getTextContent() ;
		String  mere          =                      classeXML.getElementsByTagName("mere"         ).item(0).getTextContent() ;

		Classe classe = new Classe(visibilite, statique, lectureUnique, abstraite, stereotype, nom);
		classe.setMere(mere);

		NodeList attributs = classeXML.getElementsByTagName("attribut");
		
		for (int i = 0; i < attributs.getLength(); i++)
		{
			Element attributsXML = (Element) attributs.item(i);
			
			String  visibiliteA    =                      attributsXML.getElementsByTagName("visibiliteA"   ).item(0).getTextContent() ;
			boolean statiqueA      = Boolean.parseBoolean(attributsXML.getElementsByTagName("statiqueA"     ).item(0).getTextContent());
			boolean lectureUniqueA = Boolean.parseBoolean(attributsXML.getElementsByTagName("lectureUniqueA").item(0).getTextContent());
			String  typeA          =                      attributsXML.getElementsByTagName("typeA"         ).item(0).getTextContent() ;
			String  nomA           =                      attributsXML.getElementsByTagName("nomA"          ).item(0).getTextContent() ;
			String  constante      =                      attributsXML.getElementsByTagName("constante"     ).item(0).getTextContent() ;

			Attribut att = new Attribut(visibiliteA, statiqueA, lectureUniqueA, typeA, nomA, constante);

			classe.ajouterAttribut(att);
		}

		NodeList methodes = classeXML.getElementsByTagName("methode");
		
		for (int i = 0; i < methodes.getLength(); i++)
		{
			Element methodesXML = (Element) methodes.item(i);
			
			String  visibiliteM    =                      methodesXML.getElementsByTagName("visibiliteM"   ).item(0).getTextContent() ;
			boolean statiqueM      = Boolean.parseBoolean(methodesXML.getElementsByTagName("statiqueM"     ).item(0).getTextContent());
			boolean lectureUniqueM = Boolean.parseBoolean(methodesXML.getElementsByTagName("lectureUniqueM").item(0).getTextContent());
			boolean abstraiteM     = Boolean.parseBoolean(methodesXML.getElementsByTagName("abstraiteM"    ).item(0).getTextContent());
			String  stereotypeM    =                      methodesXML.getElementsByTagName("stereotypeM"   ).item(0).getTextContent() ;
			String  typeM          =                      methodesXML.getElementsByTagName("typeM"         ).item(0).getTextContent() ;
			String  nomM           =                      methodesXML.getElementsByTagName("nomM"          ).item(0).getTextContent() ;

			Methode met = new Methode(visibiliteM, statiqueM, lectureUniqueM, abstraiteM, stereotypeM, typeM, nomM);

			NodeList parametres = methodesXML.getElementsByTagName("parametre");

			for (int j = 0; j < parametres.getLength(); j++)
			{
				Element parametresXML = (Element) parametres.item(j);
				
				String  typeP = parametresXML.getElementsByTagName("typeP").item(0).getTextContent();
				String  nomP  = parametresXML.getElementsByTagName("nomP" ).item(0).getTextContent();

				Parametre param = new Parametre(typeP, nomP);

				met.ajouterParametres(param);
			}

			classe.ajouterMethode(met);
		}

		NodeList positions = classeXML.getElementsByTagName("position");

		for (int i = 0; i < positions.getLength(); i++)
		{
			Element positionsXML = (Element) positions.item(i);

			int centreX         = Integer.parseInt(positionsXML.getElementsByTagName("centreX"        ).item(0).getTextContent());
			int centreYClasse   = Integer.parseInt(positionsXML.getElementsByTagName("centreYClasse"  ).item(0).getTextContent());
			int centreYAttribut = Integer.parseInt(positionsXML.getElementsByTagName("centreYAttribut").item(0).getTextContent());
			int centreYMethode  = Integer.parseInt(positionsXML.getElementsByTagName("centreYMethode" ).item(0).getTextContent());
			int tailleX         = Integer.parseInt(positionsXML.getElementsByTagName("tailleX"        ).item(0).getTextContent());
			int tailleYClasse   = Integer.parseInt(positionsXML.getElementsByTagName("tailleYClasse"  ).item(0).getTextContent());
			int tailleYAttribut = Integer.parseInt(positionsXML.getElementsByTagName("tailleYAttribut").item(0).getTextContent());
			int tailleYMethode  = Integer.parseInt(positionsXML.getElementsByTagName("tailleYMethode" ).item(0).getTextContent());

			PositionClasse pos = new PositionClasse(centreX, centreYClasse, centreYAttribut, centreYMethode, tailleX, tailleYClasse, tailleYAttribut, tailleYMethode);

			classe.setPosition(pos);
		}

		return classe;
	}

	/**
	 * Crée une les associations à partir du xml
	 * @param assoXML Element racine
	 * @return L'association créée à partir des éléments du fichier xml
	 */
	public Association chargerAssociation(Element assoXML)
	{

		NodeList blocs = assoXML.getElementsByTagName("bloc");
		String nomClasse1 = ((Element)blocs.item(0)).getElementsByTagName("nom").item(0).getTextContent();
		String nomClasse2 = ((Element)blocs.item(1)).getElementsByTagName("nom").item(0).getTextContent();
		
		Classe classe1 = trouverClasseParNom(nomClasse1);
		Classe classe2 = trouverClasseParNom(nomClasse2);

		String typeAsso      = assoXML.getElementsByTagName("typeAsso"     ).item(0).getTextContent() ;
		String multiplicite1 = assoXML.getElementsByTagName("multiplicite1").item(0).getTextContent() ;
		String multiplicite2 = assoXML.getElementsByTagName("multiplicite2").item(0).getTextContent() ;
		String role1         = assoXML.getElementsByTagName("role1"        ).item(0).getTextContent() ;
		String role2         = assoXML.getElementsByTagName("role2"        ).item(0).getTextContent() ;

		Association asso = new Association(classe1, classe2, typeAsso, multiplicite1, multiplicite2);
		
		asso.setRole1(role1);
		asso.setRole2(role2);

		return asso;
	}

	private Classe trouverClasseParNom(String nom)
	{
		for (Classe classe : this.lstClasses)
			if (classe.getNom().equals(nom))
				return classe;

		return null;
	}

	public void reset()
	{
		this.lstClasses.clear();
		this.lstAssociations.clear();
	}
}