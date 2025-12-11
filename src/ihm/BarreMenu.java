package ihm;

import java.awt.event.*;
import javax.swing.*;

public class BarreMenu extends JMenuBar implements ActionListener
{
	private JMenuItem menuiOuvrir;
	private JMenuItem menuiSauveg;
	
	
	public BarreMenu(/*Controleur ctrl*/) 
	{
		this.initMenu();
		this.ajouterEcouteurs();
	}

	private void initMenu()
	{
		JMenu menuFichier = new JMenu("Fichier");

		this.menuiOuvrir = new JMenuItem("Ouvrir");
		this.menuiSauveg = new JMenuItem("Sauvegarder");

		menuFichier.add(this.menuiOuvrir);
		menuFichier.add(this.menuiSauveg);

		this.add(menuFichier);
	}

	private void ajouterEcouteurs()
	{
		this.menuiOuvrir.addActionListener(this);
		this.menuiSauveg.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.menuiOuvrir){}
		if (e.getSource() == this.menuiSauveg){}
	}
}