package retroconception.ihm;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import retroconception.Controleur;


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
		
		/* ----------------------------- */
		/* Création des Composants      */
		/* ----------------------------- */
		this.panelUML  = new PanelUML    (ctrl);
		this.panelInfo = new PanelClasses(ctrl);

		JScrollPane scrollFrame = new JScrollPane( this.panelUML,
		                                           JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		                                           JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );

		menubMaBarre = new BarreMenu( this.ctrl );
		
		this.add( this.panelInfo, BorderLayout.WEST   );
		this.add( scrollFrame   , BorderLayout.CENTER );
		this.setJMenuBar( menubMaBarre );

		this.setVisible( true );
	}

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

	public void lancerSauvegarde()
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Enregistrer");

		int resultat = fileChooser.showSaveDialog(null);

		if (resultat == JFileChooser.APPROVE_OPTION)
			this.ctrl.sauvegarderFichier(fileChooser.getSelectedFile().getAbsoluteFile());
	}


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



	public void majIHM()
	{
		this.panelUML.majIHM();
		this.panelInfo.actualiser();
	}

}