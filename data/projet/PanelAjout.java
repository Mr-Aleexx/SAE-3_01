package retroconception.ihm;

import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import retroconception.Controleur;
import retroconception.metier.Association;

/**
 * Panel permettant l'ajout ou la modification des rôles d'une association.
 * Affiche une interface avec une liste déroulante pour sélectionner une association
 * et des champs de texte pour définir les noms des rôles.
 * 
 * @author HAZET Alex, LUCAS Alexandre, FRERET Alexandre, AZENHA NASCIMENTO Marta, CONSTANTIN Alexis
 * @version 1.0
 * @since 08-12-2025
 */

public class PanelAjout extends JPanel implements ActionListener
{
	private Controleur ctrl;
	
	private JComboBox<String> cmbAssociations;
	private JTextField txtRoleName1;
	private JTextField txtRoleName2;
	private JLabel lblAssociation;
	private JButton btnValider;
	private JButton btnAnnuler;
	

	/**
	 * Constructeur du panel d'ajout de rôles.
	 * Initialise tous les composants graphiques et leur positionnement.
	 * 
	 * @param ctrl Le contrôleur de l'application
	 */
	public PanelAjout(Controleur ctrl)
	{
		this.ctrl = ctrl;

		/* Création des composants */
		this.cmbAssociations = new JComboBox<>();
		this.chargerAssociations();

		this.txtRoleName1 = new JTextField(20);
		this.txtRoleName1.setEditable(false);

		this.txtRoleName2 = new JTextField(20);
		this.txtRoleName2.setEditable(false);

		this.btnValider = new JButton("Valider");
		this.btnAnnuler = new JButton("Annuler");


		/* Positionnement des composants */
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridwidth = 2;

		gbc.gridy = 0;
		this.add(new JLabel("Sélectionner l'association :"), gbc);

		gbc.gridy = 1;
		this.add(cmbAssociations, gbc);

		gbc.gridy = 2;
		this.add(new JLabel("Nom du rôle 1 :"), gbc);

		gbc.gridy = 3;
		this.add(this.txtRoleName1, gbc);

		gbc.gridy = 4;
		this.add(new JLabel("Nom du rôle 2 :"), gbc);

		gbc.gridy = 5;
		this.add(this.txtRoleName2, gbc);

		JPanel panelBoutons = new JPanel(new FlowLayout());
		panelBoutons.add(btnValider);
		panelBoutons.add(btnAnnuler);

		gbc.gridy = 6;
		this.add(panelBoutons, gbc);
		

		/* Activation des écouteurs d'événements */
		this.btnValider     .addActionListener(this);
		this.btnAnnuler     .addActionListener(this);
		this.cmbAssociations.addActionListener(this);
	}
	

	/**
	 * Charge toutes les associations disponibles dans la liste déroulante.
	 * Affiche les associations bidirectionnelles avec <-> et les autres avec ->.
	 */
	private void chargerAssociations()
	{
		String desc;
		
		cmbAssociations.removeAllItems();
		for (Association asso : ctrl.getLstAssociations())
		{
			// Différencie l'affichage selon le type d'association
			if(asso.getTypeAsso() != null && "bidirectionnelle".equals(asso.getTypeAsso()))
			{
				desc = asso.getClasse1().getNom() + " <-> " + asso.getClasse2().getNom();
			}
			else
			{
				desc = asso.getClasse1().getNom() + " -> " + asso.getClasse2().getNom();
			}
			
			cmbAssociations.addItem(desc);
		}
	}


	/**
	 * Valide et enregistre les noms de rôles saisis pour l'association sélectionnée.
	 * Réinitialise les champs et ferme la fenêtre après validation.
	 */
	private void valider()
	{
		String roleName1 = this.txtRoleName1.getText().trim();
		String roleName2 = this.txtRoleName2.getText().trim();
		int selectedIndex = cmbAssociations.getSelectedIndex();
		
		if (selectedIndex >= 0)
		{
			this.ctrl.ajouterRoleAssociation(selectedIndex, roleName1, roleName2);
			
			// Réinitialise les champs
			this.txtRoleName1.setText("");
			this.txtRoleName2.setText("");
			SwingUtilities.getWindowAncestor(this).setVisible(false);
		}

		this.ctrl.majIHM();
	}
	
	/**
	 * Annule l'opération en cours.
	 * Réinitialise les champs et ferme la fenêtre.
	 */
	private void annuler()
	{
		this.txtRoleName1.setText("");
		this.txtRoleName2.setText("");
		SwingUtilities.getWindowAncestor(this).setVisible(false);
	}


	/**
	 * Initialise les champs de texte selon l'association sélectionnée.
	 * Active ou désactive le second champ selon que l'association est bidirectionnelle ou non.
	 * Remplit les champs avec les rôles existants s'ils sont définis.
	 */
	private void initTextField()
	{
		int indiceSelect = cmbAssociations.getSelectedIndex();
		if(indiceSelect >= 0 && indiceSelect < ctrl.getLstAssociations().size())
		{
			Association asso = ctrl.getLstAssociations().get(indiceSelect);
			boolean estBidirectionnelle = asso.getTypeAsso().equals("bidirectionnelle");

			// Active le premier champ, le second uniquement si bidirectionnelle	
			this.txtRoleName1.setEditable(true);
			this.txtRoleName2.setEditable(estBidirectionnelle);

			// Remplit les champs avec les rôles existants
			if(asso.getRole1() != null)
			{
				this.txtRoleName1.setText(asso.getRole1());
			}

			if(asso.getRole2() != null && estBidirectionnelle)
			{
				this.txtRoleName2.setText(asso.getRole2());
			}

		}
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnValider)
		{
			this.valider();
		}

		if(e.getSource() == this.btnAnnuler)
		{
			this.annuler();
		}
		
		if(e.getSource() == this.cmbAssociations)
		{
			this.initTextField();
		}
	}
}