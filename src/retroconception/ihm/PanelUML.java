package retroconception.ihm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.*;
import javax.swing.*;

import retroconception.Controleur;
import retroconception.metier.Association;
import retroconception.metier.Attribut;
import retroconception.metier.Classe;
import retroconception.metier.Methode;
import retroconception.metier.Parametre;

public class PanelUML extends JPanel
{
	private Controleur ctrl;
	private Integer    selectedClassIndex;

	public PanelUML(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.selectedClassIndex = 0;

		this.setBackground( new Color(247, 247, 246) );

		GereSouris gs = new GereSouris();

		this.addMouseListener      (gs);
		this.addMouseMotionListener(gs);
	}

	public void paintComponent(Graphics g)
	{
		Classe c;
		int posX,      tailleX;
		int yClasse,   tailleYClass;
		int yAttribut, tailleYAttribut;
		int yMethode,  tailleYMethode;

		int textClasseX, textMembreX;
		int textClasseY, textMembreY;

		int nbEspace;
		String para;
		int largeurStr;
		int sautDeLigne;

		Graphics2D g2 = (Graphics2D) g;

		super.paintComponent(g);

		g2.setFont( new Font("Monospaced", Font.PLAIN, 12 ));
		FontMetrics fm = g2.getFontMetrics();

		largeurStr  = fm.stringWidth(" ");
		sautDeLigne = (int)(fm.getAscent()*1.2);
		
		for (int cpt = 0; cpt < this.ctrl.getNbClasse(); cpt++)
		{
			c = this.ctrl.getClasse(cpt);
			
			this.ctrl.definirDimension(c, fm.stringWidth(this.ctrl.getLigneMax(c)+"  "));

			posX            = c.getPos().getCentreX()         - c.getPos().getTailleX() / 2;
			tailleX         = c.getPos().getTailleX();


			// affectation des coordonnees
			yClasse         = c.getPos().getCentreYClasse  () - c.getPos().getTailleYClasse  () / 2 ;
			yAttribut       = c.getPos().getCentreYAttribut() - c.getPos().getTailleYAttribut() / 2 ;
			yMethode        = c.getPos().getCentreYMethode () - c.getPos().getTailleYMethode () / 2 ;
			tailleYClass    = c.getPos().getTailleYClasse  ();
			tailleYAttribut = c.getPos().getTailleYAttribut();
			tailleYMethode  = c.getPos().getTailleYMethode ();
			

			if (cpt == this.selectedClassIndex)
			{
				g2.setColor(new Color(0, 107, 87));
			}
			else
			{
				g2.setColor(Color.BLACK);
			}

			// Construction de la Classe graphiquement:
			g2.drawRect( posX, yClasse,   tailleX, tailleYClass    );
			g2.drawRect( posX, yAttribut, tailleX, tailleYAttribut );
			g2.drawRect( posX, yMethode,  tailleX, tailleYMethode  );
			
			
			textClasseY = (int)( yClasse + sautDeLigne);

			// ligne <<abstract>>
			if( c.estAbstraite())
			{
				textClasseX = posX + ( tailleX - fm.stringWidth( "«abstract»")) / 2;
				g2.drawString("«abstract»", textClasseX, textClasseY);
				textClasseY += sautDeLigne;
			}

			// ligne <<stereotype>>
			if( !c.getStereotype().equals("") )
			{
				textClasseX = posX + ( tailleX - fm.stringWidth( "«"+c.getStereotype()+"»" )) / 2;
				g2.drawString("«"+c.getStereotype()+"»", textClasseX, textClasseY);
				textClasseY += sautDeLigne;
			}
			String classe = c.getNom()+ ((c.estLectureUnique()) ? " {Gelé}" : "");
			
			// ligne Classe
			textClasseX = posX + (tailleX - fm.stringWidth( c.getNom()+ ((c.estLectureUnique()) ? " {Gelé}" : ""))) /2;
			g2.drawString( classe, textClasseX, textClasseY );
			
			// determiner longueur max attribut
			int tailleMax    = 0;
			int maxNomTaille = 0;
			for ( int cptAtt = 0; cptAtt < 3 && cptAtt < c.getAttributs().size(); cptAtt++ )
			{
				Attribut att = c.getAttributs().get(cptAtt);
				
				tailleMax = (att.getSymbole() + " " + att.getNom()).length();
				
				if(!att.getValeurConstante().equals(""))
					tailleMax += (" = " + att.getValeurConstante()).length();

		
				if (tailleMax > maxNomTaille) maxNomTaille = tailleMax;

			}
			
			// dessiner Attribut
			for (int cptAtt = 0; cptAtt < 3 && cptAtt < c.getAttributs().size(); cptAtt++)
			{
				Attribut att = c.getAttributs().get(cptAtt);

				String attribut = att.getSymbole() + " " + att.getNom();

				if(!att.getValeurConstante().equals(""))
					attribut += " = " + att.getValeurConstante();

				nbEspace = maxNomTaille - attribut.length();

				attribut += " ".repeat(nbEspace+1) + ": " + att.getType() +
				           (att.estLectureUnique() ? " {Gelé}" : "");
				
				textMembreX = posX      + largeurStr;
				textMembreY = yAttribut + sautDeLigne;

				
		
				if( att.estStatique()) g2.drawLine(textMembreX, textMembreY+sautDeLigne/6, textMembreX + fm.stringWidth( attribut ), textMembreY+sautDeLigne/6);
				
				g2.drawString( attribut, textMembreX, textMembreY );

				yAttribut += sautDeLigne;

				if( cptAtt == 2 && c.getAttributs().size() > 3 )
				{
					textMembreX = posX      + largeurStr;
					textMembreY = yAttribut + sautDeLigne;
					
					g2.drawString( "…", textMembreX, textMembreY );
				}
			}
			
			// determiner longueur max methode
			maxNomTaille = 0;
			tailleMax    = 0;
			for ( int cptMeth = 0; cptMeth < 3 && cptMeth < c.getMethodes().size(); cptMeth++ )
			{
				Methode meth = c.getMethodes().get(cptMeth);
				
				String type = meth.getType();
				if( type != null )
				{
					para = "";
					for (int cptPara = 0; cptPara < meth.getParametre().size(); cptPara++)
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

					tailleMax = (meth.getSymbole() + " " + meth.getNom() + "(" + para + ")").length();

					if (tailleMax > maxNomTaille) maxNomTaille = tailleMax;
						tailleMax += (meth.estLectureUnique() ? 7 : 0) + (meth.estAbstraite() ? 10 : 0);
					
				}
			}
			
			// dessiner methode
			for (int cptMeth = 0; cptMeth < 3 && cptMeth < c.getMethodes().size(); cptMeth++)
			{
				Methode meth = c.getMethodes().get(cptMeth);
		
				String type = meth.getType();
				String methode;
				para = "";
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
				
				methode  = meth.getSymbole() + " " + meth.getNom() + "(" + para + ")";

				if( type != null && !type.equals("void") )
				{
					nbEspace = maxNomTaille - methode.length();
					methode += " ".repeat(nbEspace+1) + ": " + meth.getType();
				}

				methode += (meth.estLectureUnique() ? " {Gelé}" : "");
				methode += (meth.estAbstraite() ? " {abstract}" : "");
				
				textMembreX = posX     + largeurStr;
				textMembreY = yMethode + sautDeLigne;
				
				if( meth.estStatique()) g2.drawLine(textMembreX, textMembreY+sautDeLigne/6, textMembreX + fm.stringWidth( methode), textMembreY+sautDeLigne/6);

				g2.drawString( methode, textMembreX, textMembreY );
				
				yMethode += sautDeLigne;

				if( cptMeth == 2 && c.getMethodes().size() > 3 )
				{ 
					textMembreX = posX     + largeurStr;
					textMembreY = yMethode + sautDeLigne;
					
					g2.drawString( "…", textMembreX, textMembreY );
				}
			}

			// Construction des liens entre les tâches:
			this.construireLiens( g2 );
		}
	}

	private int[] calculerPointsConnexion(Classe c1, Classe c2)
	{
		int c1CentreX = obtenirCentreX(c1);
		int c1CentreY = obtenirCentreY(c1);
		int c2CentreX = obtenirCentreX(c2);
		int c2CentreY = obtenirCentreY(c2);

		int c1Largeur = obtenirLargeur(c1);
		int c1Hauteur = obtenirHauteur(c1);
		int c2Largeur = obtenirLargeur(c2);
		int c2Hauteur = obtenirHauteur(c2);

		int[] point1 = calculerPointConnection(c1CentreX, c1CentreY, c1Largeur, c1Largeur, c2Largeur, c2Largeur);
		int[] point2 = calculerPointConnection(c1CentreX, c1CentreY, c1Largeur, c1Largeur, c2Largeur, c2Largeur);

		return new int[]{point1[0], point1[1], point2[0], point2[1]};
	}

	public void construireLiens(Graphics2D g2)
	{
		//Pour les Associations
		for (Association association : this.ctrl.getLstAssociations())
		{
			Classe classe1 = association.getClasse1();
			Classe classe2 = association.getClasse2();

			int[] points = calculerPointsConnexion(classe1, classe2);

			int x1 = classe1.getPos().getCentreX() - classe1.getPos().getTailleX() / 2;
			int y1 = classe1.getPos().getCentreYClasse() + 
			         classe1.getPos().getTailleYClasse() / 2 +
			         classe1.getPos().getTailleYAttribut() / 2;

			int x2 = classe2.getPos().getCentreX() - classe2.getPos().getTailleX() / 2;
			int y2 = classe2.getPos().getCentreYClasse() + 
			         classe2.getPos().getTailleYClasse() / 2 +
			         classe2.getPos().getTailleYAttribut() / 2;

			g2.drawLine(x1, y1, x2, y2);

			if (association.getTypeAsso() == "unidirectionnelle")
			{
				drawArrowHead(g2, x1, y1, x2, y2);
			}

			int mult1X = x1 + (int)((x2 - x1) * 0.1);
			int mult1Y = y1 + (int)((y2 - y1) * 0.1);

			int mult2X = x2 - (int)((x2 - x1) * 0.1);
			int mult2Y = y2 - (int)((y2 - y1) * 0.1);

			g2.drawString(String.valueOf(association.getMultiplicite1()), mult1X,  mult1Y );
			g2.drawString(String.valueOf(association.getMultiplicite2()),  mult2X , mult2Y);

			if (association.getRole1() != null)
			{
				g2.drawString(String.valueOf(association.getRole1()), mult1X, mult1Y + 15);
			}

			if (association.getRole2() != null)
			{
				g2.drawString(String.valueOf(association.getRole2()), mult2X, mult2Y + 15);
			}
		}

		//Pour l'héritage
		for (Classe classe : this.ctrl.getLstClasses())
		{
			String mere = classe.getMere();
			
			if(mere != null)
			{
				for(Classe classe2 : this.ctrl.getLstClasses())
				{
					if(classe2.getNom().equals(mere))
					{
						int x1 = classe.getPos().getCentreX() - classe.getPos().getTailleX() / 2;
						int y1 = classe.getPos().getCentreYClasse() + 
						         classe.getPos().getTailleYClasse() / 2 +
						         classe.getPos().getTailleYAttribut() / 2;

						int x2 = classe2.getPos().getCentreX() - classe2.getPos().getTailleX() / 2;
						int y2 = classe2.getPos().getCentreYClasse() + 
						         classe2.getPos().getTailleYClasse() / 2 +
						         classe2.getPos().getTailleYAttribut() / 2;

						g2.drawLine(x1, y1, x2, y2);
						drawArrowHeadFull(g2, x1, y1, x2, y2);
					}
				}
			}
		}

		//Pour les interfaces 
		for (Classe classe : this.ctrl.getLstClasses())
		{
			for (String interfaceName : classe.getLstImplementations())
			{
				for (Classe classe2 : this.ctrl.getLstClasses())
				{
					if (classe2.getNom().equals(interfaceName))
					{
						int x1 = classe.getPos().getCentreX() - classe.getPos().getTailleX() / 2;
						int y1 = classe.getPos().getCentreYClasse() + 
								classe.getPos().getTailleYClasse() / 2 +
								classe.getPos().getTailleYAttribut() / 2;

						int x2 = classe2.getPos().getCentreX() - classe2.getPos().getTailleX() / 2;
						int y2 = classe2.getPos().getCentreYClasse() + 
								 classe2.getPos().getTailleYClasse() / 2 +
								 classe2.getPos().getTailleYAttribut() / 2;

						Stroke originalStroke = g2.getStroke();
						
						float[] dashPattern = {5.0f, 5.0f};
						g2.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dashPattern, 0.0f));
						
						g2.drawLine(x1, y1, x2, y2);
						
						g2.setStroke(originalStroke);
						
						drawArrowHeadFull(g2, x1, y1, x2, y2);
					}
				}
			}
		}
	}

	private void drawArrowHead(Graphics2D g2, int x1, int y1, int x2, int y2)
	{
		double phi   = Math.toRadians(25);
		double dy    = y2 - y1, dx = x2 - x1;
		double theta = Math.atan2(dy, dx);

		int barb     = 10;
		
		for (int j = 0; j < 2; j++)
		{
			double rho = theta + (j == 0 ? phi : -phi);

			int    x   = (int) (x2 - barb * Math.cos(rho));
			int    y   = (int) (y2 - barb * Math.sin(rho));

			g2.drawLine(x2, y2, x, y);
		}
	}

	private void drawArrowHeadFull(Graphics2D g2, int x1, int y1, int x2, int y2)
	{
		double phi   = Math.toRadians(25);
		double dy    = y2 - y1, dx = x2 - x1;
		double theta = Math.atan2(dy, dx);

		int barb     = 10;

		double rho1 = theta + phi;
		double rho2 = theta - phi;

		int x3 = (int) (x2 - barb * Math.cos(rho1));
		int y3 = (int) (y2 - barb * Math.sin(rho1));

		int x4 = (int) (x2 - barb * Math.cos(rho2));
		int y4 = (int) (y2 - barb * Math.sin(rho2));

		int[] xPoints = {x2, x3, x4};
		int[] yPoints = {y2, y3, y4};

		g2.fillPolygon(xPoints, yPoints, 3);
	}


	private int obtenirCentreX(Classe c)
	{
		return c.getPos().getCentreX();
	}

	private int obtenirCentreY(Classe c)
	{
		return c.getPos().getCentreYClasse() + 
			   c.getPos().getTailleYClasse() / 2 +
			   c.getPos().getTailleYAttribut() / 2;
	}

	private int obtenirLargeur(Classe c) 
	{
		return c.getPos().getTailleX();
	}

	private int obtenirHauteur(Classe c) 
	{
		return c.getPos().getTailleYClasse  () + 
	    	   c.getPos().getTailleYAttribut() + 
	       	   c.getPos().getTailleYMethode ();
	}

	private int[] calculerPointConnection(int centreX, int centreY, int largueur, int hauteur, int versCentreX, int versCentreY) 
	{
		double dDepuisCentre;
	
		int x;
		int y;

		// Un tableau non instancié sera retourné
		double dx = versCentreX - centreX;
		double dy = versCentreY - centreY;
		if (largueur == 0) return new int[]{centreX, centreY};

		// Theoreme de Pythagore pour avoir la taille du vecteur de la ligne
		double hypothenuse = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

		// Normalisation des vecteur pour qu'il soit plus facile de calculer ensuite
		dx /= hypothenuse;
		dy /= hypothenuse;

		// Calcule des demiLargeur et demiHauteur pour les points d'ancrages
		double demiLargeur = largueur/2.0;
		double demiHauteur = hauteur/2.0;

		double pointHorizontale = demiLargeur/Math.abs(dx);
		double pointVerticale   = demiHauteur/Math.abs(dy);

		dDepuisCentre = Math.min(pointHorizontale, pointVerticale);

		x = centreX + (int)(dx * dDepuisCentre);
		y = centreY + (int)(dy * dDepuisCentre);

		return new int[]{0,0};
	}

	public void majIHM()
	{
		int maxX = 0;
		int maxY = 0;

		for (int cpt = 0; cpt < this.ctrl.getNbClasse(); cpt++) {
			Classe c = this.ctrl.getClasse(cpt);
			int rightEdge = c.getPos().getCentreX() + c.getPos().getTailleX() / 2;
			int bottomEdge = c.getPos().getCentreYMethode() + c.getPos().getTailleYMethode() / 2;
		
			if (rightEdge > maxX) maxX = rightEdge;
			if (bottomEdge > maxY) maxY = bottomEdge;
		}

		maxX += 50;
		maxY += 50;

		this.setPreferredSize(new Dimension(maxX, maxY));
		this.revalidate();
		
		this.repaint();
	}


	private class GereSouris extends MouseAdapter
	{
		Integer numClasseActive = 0;
		int     x, y;

		public void mousePressed(MouseEvent e)
		{
			this.numClasseActive = PanelUML.this.ctrl.getIndiceClasse(e.getX(), e.getY());
			this.x = e.getX();
			this.y = e.getY();
		}

		public void mouseClicked(MouseEvent e)
		{
			this.numClasseActive = PanelUML.this.ctrl.getIndiceClasse(e.getX(), e.getY());

			if (this.numClasseActive != null)
			{
				if (e.getClickCount() == 1)
				{
					PanelUML.this.selectedClassIndex = this.numClasseActive;
					PanelUML.this.repaint();
				}
				
				// affiche des info sur la Class
				//if (e.getClickCount() == 2)
				//	if ( this.numStereoActive != 0 && this.numStereoActive != PanelUML.this.ctrl.getNbClasse() - 1 )
				//		PanelUML.this.frameMere.majLabel(PanelUML.this.ctrl.getClasse(this.numStereoActive));

				// Frame de modification
				//if (SwingUtilities.isRightMouseButton(e))
				//	if ( this.numStereoActive != 0 && this.numStereoActive != PanelUML.this.ctrl.getNbClasse() - 1 )
				//		PanelUML.this.ctrl.creerFrameTache(PanelUML.this.ctrl.getClasse(this.numStereoActive) );
			}
		}

		public void mouseDragged(MouseEvent e)
		{
			if ( this.numClasseActive != null && e.getX() > 0 & e.getY() > 0 )
				PanelUML.this.ctrl.deplacerClasse( this.numClasseActive, e.getX() - x, e.getY() - y );

			this.x = e.getX();
			this.y = e.getY();


			PanelUML.this.majIHM();
		}
	}
}