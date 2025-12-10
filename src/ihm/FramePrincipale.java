package ihm;

import javax.swing.*;
import java.awt.*;
//import src.Controleur;

public class FramePrincipale extends JFrame
{
	//private Controleur ctrl;

	public FramePrincipale(/*Controleur ctrl*/)
	{
		//this.ctrl = ctrl;

		this.setTitle("UML Generator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		this.setLayout(new BorderLayout());

		JMenuBar menubMaBarre = new BarreMenu(/*this.ctrl*/);
		this.setJMenuBar(menubMaBarre);
	}
}
