package retroconception.ihm;

import javax.swing.*;

import retroconception.Controleur;


/**
 * Frame pour l'ajout des role d'association
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */

public class FrameAjout extends JFrame
{
	private Controleur ctrl;
	private PanelAjout panel;
	
	public FrameAjout(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.panel = new PanelAjout(this.ctrl);

		this.setTitle("Ajouter Role Association");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(500, 350);

		this.add( this.panel );
		this.setVisible( true );
	}
}