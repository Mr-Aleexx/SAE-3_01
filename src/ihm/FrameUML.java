package src.ihm;

import java.awt.*;
import javax.swing.*;

import src.Controleur;

public class FrameUML extends JFrame
{
	private Controleur ctrl;
	private PanelUML panelUML;

	public FrameRetro(Controleur ctrl)
	{
		
		this.setTitle("UML Generator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		this.setLayout(new BorderLayout());
		
		//this.ctrl = ctrl;
		this.panelUML = new PanelUML(this.ctrl);
		this.add(this.panelUML, BorderLayout.CENTER);

		JMenuBar menubMaBarre = new BarreMenu(this.ctrl);
		this.setJMenuBar(menubMaBarre);

		this.setVisible(true);
	}
}
