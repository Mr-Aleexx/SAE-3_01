package retroconception.ihm;

import javax.swing.*;

import retroconception.Controleur;

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