package src.ihm;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;
import static java.lang.Math.atan2;

import src.Controleur;
import src.metier.Stereotype;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;

import javax.swing.*;
import java.awt.event.*;

public class PanelUML extends JPanel
{
	private static final Color BLEU = new Color(0, 128, 128);

	private Controleur ctrl;
	private FrameRetro frameMere;

	public PanelUML(Controleur ctrl)
	{
		this.ctrl = ctrl;
		
		this.setBackground( new Color(247, 247, 247) );

		GereSouris gs = new GereSouris();

		this.addMouseListener      (gs);
		this.addMouseMotionListener(gs);



	}

	public void paintComponent(Graphics g)
	{
		Stereotype s;
		int xRect, yRect, tailleXRect, tailleYRect;
		int tailleXCarre, xCarre, yCarre;
		int textRectX, textRectY, textCarreX, textCarreY;

		Graphics2D g2 = (Graphics2D) g;

		super.paintComponent(g);

		// Dessiner l'ensemble des Stereotype
		for (int cpt = 0; cpt < this.ctrl.getNbStereotype(); cpt++)
		{
			s = this.ctrl.getTache(cpt);

			// Création du positionnement des Stereotypes:
			xRect       = s.getPos().getCentreX() - s.getPos().getTailleX() / 2;
			yRect       = s.getPos().getCentreY() - s.getPos().getTailleY() / 2;
			tailleXRect = s.getPos().getTailleX();
			tailleYRect = s.getPos().getTailleY();

			tailleXCarre = tailleXRect / 2;
			xCarre       = xRect + tailleXCarre;
			yCarre       = yRect + tailleYRect;

			// Création du positionnement des dates (au plut tôt et au plus
			// tard):
			FontMetrics fm = g2.getFontMetrics();

			textRectX  = xRect  + ( tailleXRect  - fm.stringWidth( s.getNom() )            ) / 2;
			textRectY  = yRect  + ( tailleYRect  + fm.getAscent  ()                        ) / 2;
			textCarreX = xCarre + ( tailleXCarre - fm.stringWidth("" + s.affichageMax() )  ) / 2;
			textCarreY = yCarre + ( tailleYRect  + fm.getAscent  ()                        ) / 2;

			// Construction de la tâche graphiquement:
			g2.drawRect( xRect , yRect , tailleXRect , tailleYRect );
			g2.drawRect( xRect , yCarre, tailleXCarre, tailleYRect );
			g2.drawRect( xCarre, yCarre, tailleXCarre, tailleYRect );

			// Construction des informations des tâches quand on clique sur "+
			// tot" ou sur "+ tard":
			g2.drawString( s.getNom(), textRectX, textRectY );
			this.construireInformations( g2, s, textCarreX, textCarreY, tailleXCarre );

			// Construction des liens entre les tâches:
			this.construireLiens( g2, s, xRect, yRect, tailleXRect, tailleYRect );
		}
	}

	public void construireInformations(Graphics2D g2, Stereotype s, int textX, int textY, int tailleX)
	{
		
	}


	private class GereSouris extends MouseAdapter
	{
		Integer numStereoActive = 0;
		int     x, y;

		public void mousePressed(MouseEvent e)
		{
			this.numStereoActive = PanelUML.this.ctrl.getIndiceStereotpe(e.getX(), e.getY());
			this.x = e.getX();
			this.y = e.getY();
		}

		public void mouseClicked(MouseEvent e)
		{
			this.numStereoActive = PanelUML.this.ctrl.getIndiceStereotpe(e.getX(), e.getY());

			if (this.numStereoActive != null)
			{
				if (e.getClickCount() == 2)
					if ( this.numStereoActive != 0 && this.numStereoActive != PanelUML.this.ctrl.getNbTaches() - 1 )
						PanelUML.this.frameMere.majLabel(PanelUML.this.ctrl.getStereotype(this.numStereoActive));

				if (SwingUtilities.isRightMouseButton(e))
					if ( this.numStereoActive != 0 && this.numStereoActive != PanelUML.this.ctrl.getNbTaches() - 1 )
						PanelUML.this.ctrl.creerFrameTache(PanelUML.this.ctrl.getStereotype(this.numStereoActive) );
			}
		}

		public void mouseDragged(MouseEvent e)
		{
			if ( this.numStereoActive != null )
				PanelUML.this.ctrl.deplacerStereotype( this.numStereoActive, e.getX() - x, e.getY() - y );

			this.x = e.getX();
			this.y = e.getY();

			PanelUML.this.repaint();
		}
	}
}