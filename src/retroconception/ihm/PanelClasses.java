package retroconception.ihm;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import retroconception.Controleur;
import retroconception.metier.Classe;

/**
 * Panel sur le cote avec toutes les classes
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */

public class PanelClasses extends JPanel
{
	private Controleur    ctrl;
	private FrameUML      frame;
	private List<JButton> lstButtons;

	public PanelClasses(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.lstButtons = new ArrayList<>();

		this.setBackground(new Color(228, 240, 232));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setPreferredSize(new Dimension(220, 0));

		this.actualiser();
	}

	/**
	 * Crée un bouton pour chaque classe dans la liste du controleur
	 * @return
	 */
	public void actualiser()
	{
		this.removeAll();
		this.lstButtons.clear();

		List<Classe> lstClasse = ctrl.getLstClasses();

		this.add(Box.createVerticalStrut(15));

		for (Classe classe : lstClasse)
		{
			JButton btn = new JButton(classe.getNom());

			btn.setBorderPainted    (false);
			btn.setFocusPainted     (false);
			btn.setContentAreaFilled(false);
			btn.setOpaque           (true );

			btn.setBackground(new Color(128, 170, 141));
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

			btn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

			btn.setMaximumSize( new Dimension(Integer.MAX_VALUE, btn.getPreferredSize().height));

			btn.setAlignmentX(LEFT_ALIGNMENT);
			btn.addMouseListener(new GereSouris());

			this.add(btn);
			this.add(Box.createVerticalStrut(10));


			this.lstButtons.add(btn);
		}

		this.revalidate();
		this.repaint();
	}

	/**
	 * Change la couleur du bouton donné en paramettre
	 * @param index numéro du bouton dans la liste de bouton
	 * @return
	 */
	public void changerCouleur(int index)
	{
		this.resetBtnColor();
		this.lstButtons.get(index).setBackground(new Color(153, 213, 144));
	}

	private void resetBtnColor()
	{
		for (JButton btn : this.lstButtons)
		{
			btn.setBackground(new Color(128, 170, 141));
		}
	}

	private class GereSouris extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			//Ouverture de la page d'information de la classe avec un clique droit sur son bouton respectif
			if (SwingUtilities.isRightMouseButton(e))
			{
				for (int i = 0; i < PanelClasses.this.lstButtons.size(); i++)
				{
					if(e.getSource() == PanelClasses.this.lstButtons.get(i))
					{
						new FrameInfo(PanelClasses.this.ctrl, PanelClasses.this.ctrl.getClasse(i));
					}
				}
			}

			//Changement de la couleur du bouton et de la boite de la classe correspondante
			if(SwingUtilities.isLeftMouseButton(e))
			{
				for (int i = 0; i < PanelClasses.this.lstButtons.size(); i++)
				{
					if(e.getSource() == PanelClasses.this.lstButtons.get(i))
					{
						PanelClasses.this.changerCouleur(i);
						PanelClasses.this.ctrl.changerCouleur("classe", i);
					}
				}
			}
		}
	}
	
}