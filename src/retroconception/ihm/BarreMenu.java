package retroconception.ihm;

import java.awt.event.*;
import javax.swing.*;
import retroconception.Controleur;

/**
 * Barre menu en haut de l'ihm
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */

public class BarreMenu extends JMenuBar implements ActionListener
{
	private Controleur ctrl;

	private JMenuItem menuiOuvrir;
	private JMenuItem menuiSauveg;
	private JMenuItem menuiExpImg;
	private JMenuItem menuiQuitte;
	private JMenuItem menuiCharge;
	private JMenuItem menuiReset;
	private JMenuItem menuiAjoutRoleAsso;


	public BarreMenu( Controleur ctrl )
	{
		this.ctrl = ctrl;

		this.initMenu();
		this.ajouterEcouteurs();
	}

	private void initMenu()
	{
		JMenu menuFichier   = new JMenu("Fichier"  );
		JMenu menuAffichage = new JMenu("Affichage");

		this.menuiOuvrir = new JMenuItem("Ouvrir");
		this.menuiCharge = new JMenuItem("Charger Sauvegarde");
		this.menuiSauveg = new JMenuItem("Sauvegarder");
		this.menuiExpImg = new JMenuItem("Exporter en Image");
		this.menuiReset  = new JMenuItem("Réinitialiser");
		this.menuiQuitte = new JMenuItem("Quitter");

		this.menuiAjoutRoleAsso = new JMenuItem("Ajouter Role");

		menuFichier.add( this.menuiOuvrir );
		menuFichier.add( this.menuiCharge );
		menuFichier.add( this.menuiSauveg );
		menuFichier.add( this.menuiExpImg );

		menuFichier.addSeparator();
		
		menuFichier.add( this.menuiReset  );
		menuFichier.add( this.menuiQuitte );

		menuAffichage.add( this.menuiAjoutRoleAsso );

		this.add( menuFichier );
		this.add(menuAffichage);
	}

	private void ajouterEcouteurs()
	{
		this.menuiOuvrir.addActionListener(this);
		this.menuiCharge.addActionListener(this);
		this.menuiSauveg.addActionListener(this);
		this.menuiExpImg.addActionListener(this);
		this.menuiReset .addActionListener(this);
		this.menuiQuitte.addActionListener(this);

		this.menuiAjoutRoleAsso.addActionListener(this);
	}

	public void actionPerformed( ActionEvent e )
	{
		if ( e.getSource() == this.menuiOuvrir )
		{
			this.ctrl.ouvrirDossier();
		}

		if ( e.getSource() == this.menuiCharge )
		{
			this.ctrl.lancerChargement();
		}

		if ( e.getSource() == this.menuiSauveg )
		{
			this.ctrl.lancerSauvegarde();
		}

		if ( e.getSource() == this.menuiExpImg )
		{
			this.ctrl.exporterImage();
		}

		if ( e.getSource() == this.menuiReset )
		{
			this.ctrl.reset();
		}

		if ( e.getSource() == this.menuiQuitte )
		{
			System.exit(0);
		}

		if ( e.getSource() == this.menuiAjoutRoleAsso )
		{
			new FrameAjout( this.ctrl );
		}

		this.ctrl.majIHM();
	}
}