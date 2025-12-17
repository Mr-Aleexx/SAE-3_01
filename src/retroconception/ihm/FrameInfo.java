package retroconception.ihm;

import javax.swing.JFrame;

import retroconception.Controleur;
import retroconception.metier.Classe;

public class FrameInfo extends JFrame
{
	private PanelInfo panel;
	private Controleur ctrl;
	
	public FrameInfo(Controleur ctrl, Classe classe)
	{
		this.ctrl = ctrl;
		this.panel = new PanelInfo(ctrl, classe);
		
		this.setTitle("UML Generator");
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setSize(1000, 700);

		this.add(this.panel);
		this.setVisible(true);
	}

	public void nouvPanel()
	{

	}
}