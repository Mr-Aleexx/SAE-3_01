package retroconception.ihm;

import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import retroconception.Controleur;

public class BarreMenu extends JMenuBar implements ActionListener
{
	private Controleur ctrl;
	private FrameUML   frame;
	
	private JMenuItem menuiOuvrir;
	private JMenuItem menuiSauveg;
	private JMenuItem menuiExpImg;
	private JMenuItem menuiQuitte;
	private JMenuItem menuiCharge;

	public BarreMenu(Controleur ctrl, FrameUML frame) 
	{
		this.ctrl = ctrl;
		this.frame = frame;
		
		this.initMenu();
		this.ajouterEcouteurs();
	}

	private void initMenu()
	{
		JMenu menuFichier = new JMenu("Fichier");

		this.menuiOuvrir = new JMenuItem("Ouvrir"           );
		this.menuiSauveg = new JMenuItem("Sauvegarder"      );
		this.menuiExpImg = new JMenuItem("Exporter en Image");
		this.menuiQuitte = new JMenuItem("Quitter"          );
		this.menuiCharge = new JMenuItem("Charger Sauvegarde");

		menuFichier.add(this.menuiOuvrir);
		menuFichier.add(this.menuiCharge);
		menuFichier.add(this.menuiSauveg);
		menuFichier.add(this.menuiExpImg);
		menuFichier.addSeparator();
		menuFichier.add(this.menuiQuitte);

		this.add(menuFichier);
	}

	private void ajouterEcouteurs()
	{
		this.menuiOuvrir.addActionListener(this);
		this.menuiCharge.addActionListener(this);
		this.menuiSauveg.addActionListener(this);
		this.menuiExpImg.addActionListener(this);
		this.menuiQuitte.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.menuiOuvrir)
		{
			this.frame.ouvrirDossier();
		}

		if (e.getSource() == this.menuiCharge)
		{
			JFileChooser selection = new JFileChooser();
			selection.setDialogTitle("Enregistrer");

			int resultat = selection.showSaveDialog(null);

			if (resultat == JFileChooser.APPROVE_OPTION)
				this.ctrl.chargerSauvegarde(selection.getSelectedFile().getAbsoluteFile());
		}

		if (e.getSource() == this.menuiSauveg)
		{
			JFileChooser selection = new JFileChooser();
			selection.setDialogTitle("Enregistrer");

			int resultat = selection.showSaveDialog(null);

			if (resultat == JFileChooser.APPROVE_OPTION)
				this.ctrl.sauvegarderFichier(selection.getSelectedFile().getAbsoluteFile());
		}

		if (e.getSource() == this.menuiExpImg)
		{
			this.frame.exporterImage();
		}

		if (e.getSource() == this.menuiQuitte)
		{
			System.exit(0);
		}
	}
}