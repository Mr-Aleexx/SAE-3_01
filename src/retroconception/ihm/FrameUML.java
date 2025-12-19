package retroconception.ihm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import retroconception.Controleur;

/**
 * Frame Pour l'affichage de la classe avec les détails
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO
 *         Marta, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */

public class FrameUML extends JFrame
{
	private Controleur   ctrl;
	private PanelUML     panelUML;
	private PanelClasses panelInfo;

	private String      fichierEnCours;

	public FrameUML(Controleur ctrl)
	{
		JMenuBar menubMaBarre;

		this.ctrl = ctrl;
		this.fichierEnCours = "";

		this.setTitle("UML Generator");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000, 700);
		this.setLayout(new BorderLayout());
		
		/* ----------------------- */
		/* Création des Composants */
		/* ----------------------- */
		this.panelUML  = new PanelUML    (ctrl);
		this.panelInfo = new PanelClasses(ctrl);

		JScrollPane scrollFrame = new JScrollPane( this.panelUML,
		                                           JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		                                           JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

		menubMaBarre = new BarreMenu( this.ctrl );
		
		/* ----------------------------- */
		/* Positionnement des Composants */
		/* ----------------------------- */

		this.add( this.panelInfo, BorderLayout.WEST   );
		this.add( scrollFrame   , BorderLayout.CENTER );
		this.setJMenuBar( menubMaBarre );

		this.setVisible( true );
	}

	/**
	 * Prend le panel et l'exporte en format image
	 * @return
	 */
	public void exporterImage()
	{
		BufferedImage image = new BufferedImage
		(
			this.panelUML.getWidth(),
			this.panelUML.getHeight(),
			BufferedImage.TYPE_INT_RGB
		);
		
		Graphics2D g2d = image.createGraphics();
		this.panelUML.paint(g2d);
		g2d.dispose();
		
		try
		{
			JFileChooser selection = new JFileChooser();
			selection.setDialogTitle("Enregistrer en Image");

			int resultat = selection.showSaveDialog(null);

			if (resultat == JFileChooser.APPROVE_OPTION)
				ImageIO.write(image, "PNG", new File(selection.getSelectedFile().getAbsolutePath()));
		}
		catch (IOException e) { e.printStackTrace(); }
	}


	/**
	 * Permet l'ouverture du dossier en IHM et le le donne au Controleur
	 * @return
	 */
	public void ouvrirDossier()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Ouvrir  : ");
		fileChooser.setCurrentDirectory(new File("./data"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int result = fileChooser.showOpenDialog(this.getParent());

		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			if (selectedFile.isDirectory()) 
			{
				this.ctrl.ouvrirDossier(selectedFile.getAbsolutePath());
				this.majIHM();
			}
			else 
			{
				JOptionPane.showMessageDialog( this.getParent(), "Veuillez sélectionner un dossier",
				                               "Sélection invalide", JOptionPane.ERROR_MESSAGE );
			}
		}
	}

	/**
	 * Permet la sauvegarde de l'ihm en xml
	 * @return
	 */
	public void lancerSauvegarde()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Enregistrer");

		int resultat = fileChooser.showSaveDialog(null);

		if (resultat == JFileChooser.APPROVE_OPTION)
			this.ctrl.sauvegarderFichier(fileChooser.getSelectedFile().getAbsoluteFile());
	}

	/**
	 * Permet de charger un fichier xml
	 * @return
	 */
	public void lancerChargement()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Charger");
		fileChooser.setCurrentDirectory(new File("./data"));
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

		int result = fileChooser.showOpenDialog(this.getParent());

		if (result == JFileChooser.APPROVE_OPTION)
		{
			File selectedFile = fileChooser.getSelectedFile();
			if(selectedFile.getName().contains(".xml"))
				this.ctrl.chargerSauvegarde(fileChooser.getSelectedFile().getAbsoluteFile());
		}
	}

	/**
	 * Change la couleur entre le panelUML et le panelClasse
	 * @param panel le panel d'où vient l'action
	 * @param index l'index de du bouton ou de la classe
	 * @return
	 */
	public void changerCouleur(String panel, int index)
	{
		if (panel.equals("classe"))
		{
			this.panelUML.changerCouleur(index);
		}

		if(panel.equals("uml"))
		{
			this.panelInfo.changerCouleur(index);
		}
	}

	/**
	 * Permet la mise a jour de l'ihm
	 * @return
	 */
	public void majIHM()
	{
		this.panelUML.majIHM();
		this.panelInfo.actualiser();
	}

}