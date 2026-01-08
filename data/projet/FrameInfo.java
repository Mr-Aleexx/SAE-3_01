package retroconception.ihm;

import javax.swing.*;
import retroconception.Controleur;
import retroconception.metier.Classe;

/**
 * Frame d'information sur les classes
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */

public class FrameInfo extends JFrame
{
	private PanelInfo  panel;
	private Controleur ctrl;
	
	public FrameInfo( Controleur ctrl, Classe classe )
	{
		this.ctrl = ctrl;
		this.panel = new PanelInfo(ctrl, classe);
		
		this.setTitle( "Generateur UML" );
		this.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE );
		this.setSize( 1000, 700 );

		this.add( this.panel );
		this.setVisible( true );
	}
}