package retroconception.ihm;

import java.awt.event.*;
import javax.swing.*;
import retroconception.Controleur;

public class BarreMenu extends JMenuBar implements ActionListener
{
	private Controleur ctrl;

	private JMenuItem menuiOuvrir;
	private JMenuItem menuiSauveg;
	private JMenuItem menuiExpImg;
	private JMenuItem menuiQuitte;
	private JMenuItem menuiCharge;
	private JMenuItem menuiReset;

	public BarreMenu(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.initMenu();
		this.ajouterEcouteurs();
	}

	private void initMenu()
	{
		JMenu menuFichier = new JMenu("Fichier");

		this.menuiOuvrir = new JMenuItem("Ouvrir");
		this.menuiCharge = new JMenuItem("Charger Sauvegarde");
		this.menuiSauveg = new JMenuItem("Sauvegarder");
		this.menuiExpImg = new JMenuItem("Exporter en Image");
		this.menuiReset  = new JMenuItem("Réinitialiser");
		this.menuiQuitte = new JMenuItem("Quitter");

		menuFichier.add(this.menuiOuvrir);
		menuFichier.add(this.menuiCharge);
		menuFichier.add(this.menuiSauveg);
		menuFichier.add(this.menuiExpImg);
		menuFichier.addSeparator();
		menuFichier.add(this.menuiReset );
		menuFichier.add(this.menuiQuitte);

		this.add( menuFichier );
	}

	private void ajouterEcouteurs()
	{
		this.menuiOuvrir.addActionListener(this);
		this.menuiCharge.addActionListener(this);
		this.menuiSauveg.addActionListener(this);
		this.menuiExpImg.addActionListener(this);
		this.menuiReset .addActionListener(this);
		this.menuiQuitte.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.menuiOuvrir)
		{
			this.ctrl.ouvrirDossier();
		}

		if (e.getSource() == this.menuiCharge)
		{
			JFileChooser selection = new JFileChooser();
			selection.setDialogTitle("Enregistrer");

			int resultat = selection.showSaveDialog(null);

			if (resultat == JFileChooser.APPROVE_OPTION)
				this.ctrl.chargerSauvegarde(selection.getSelectedFile().getAbsoluteFile());
		}

		if ( e.getSource() == this.menuiSauveg )
		{
			JFileChooser selection = new JFileChooser();
			selection.setDialogTitle("Enregistrer");

			int resultat = selection.showSaveDialog(null);

			if (resultat == JFileChooser.APPROVE_OPTION)
				this.ctrl.sauvegarderFichier(selection.getSelectedFile().getAbsoluteFile());
		}

		if ( e.getSource() == this.menuiExpImg )
		{
			this.ctrl.exporterImage();
		}

		if ( e.getSource() == this.menuiReset )
		{
			
		}

		if ( e.getSource() == this.menuiQuitte )
		{
			System.exit(0);
		}
	}
}