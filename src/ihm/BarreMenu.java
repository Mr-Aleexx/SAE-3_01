package ihm;

import javax.swing.*;

import java.awt.event.*;

public class BarreMenu extends JMenuBar implements ActionListener
{
	private JMenuItem menuiOuvrir;
	
	
	public BarreMenu(/*Controleur ctrl*/) 
	{
		this.initMenu();

		this.ajouterEcouteurs();
	}

	private void initMenu()
	{
		JMenu menuFichier = new JMenu("Fichier");

		this.menuiOuvrir = new JMenuItem("Ouvrir");

		menuFichier.add(this.menuiOuvrir);

		this.add(menuFichier);
	}

	private void ajouterEcouteurs()
	{
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
	}
}