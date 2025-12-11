package src.ihm;

import java.awt.event.*;
import java.io.File;
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
		if (e.getSource() == this.menuiOuvrir)
		{
			ouvrirDossier();
		}

		if (e.getSource() == this.menuiSauveg){}
	}

	public void ouvrirDossier()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Ouvrir un graphe Mpm : ");
		fileChooser.setCurrentDirectory(new File("./test"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int result = fileChooser.showOpenDialog(this.getParent());

		/*if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile.getName().endsWith(".java")) 
			{
				this.ctrl.ouvrirDossier(selectedFile.getAbsolutePath());

			}
			else
				JOptionPane.showMessageDialog(frame, Erreur.FORMAT_FICHIER_INVALIDE.getMessage(), "Format de fichier invalide", JOptionPane.ERROR_MESSAGE);
		}*/
	}
}