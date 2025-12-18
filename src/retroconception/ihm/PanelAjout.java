package retroconception.ihm;

import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import retroconception.Controleur;
import retroconception.metier.Association;


public class PanelAjout extends JPanel implements ActionListener
{
	private Controleur ctrl;
	
	private JComboBox<String> cmbAssociations;
	private JTextField txtRoleName1;
	private JTextField txtRoleName2;
	private JLabel lblAssociation;
	private JLabel lblRoleName;
	private JButton btnValider;
	private JButton btnAnnuler;
	
	public PanelAjout(Controleur ctrl)
	{
		this.ctrl = ctrl;
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		this.lblAssociation = new JLabel("Sélectionner l'association :");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		this.add(lblAssociation, gbc);
		
		this.cmbAssociations = new JComboBox<>();
		this.chargerAssociations();
		gbc.gridy = 1;
		this.add(cmbAssociations, gbc);
		
		this.lblRoleName = new JLabel("Nom du rôle 1 :");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		this.add(this.lblRoleName, gbc);

		this.txtRoleName1 = new JTextField(20);
		gbc.gridy = 3;
		this.add(this.txtRoleName1, gbc);

		JLabel lblRoleName2 = new JLabel("Nom du rôle 2 :");
		gbc.gridy = 4;
		this.add(lblRoleName2, gbc);

		this.txtRoleName2 = new JTextField(20);
		gbc.gridy = 5;
		this.add(this.txtRoleName2, gbc);
		
		JPanel panelBoutons = new JPanel(new FlowLayout());
		this.btnValider = new JButton("Valider");
		this.btnAnnuler = new JButton("Annuler");
		
		panelBoutons.add(btnValider);
		panelBoutons.add(btnAnnuler);
		
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		this.add(panelBoutons, gbc);

		this.txtRoleName1.setEditable(false);
		this.txtRoleName2.setEditable(false);
		
		this.btnValider.addActionListener(this);
		this.btnAnnuler.addActionListener(this);
		this.cmbAssociations.addActionListener(this);
	}
	
	private void chargerAssociations()
	{
		String desc;
		
		cmbAssociations.removeAllItems();
		for (Association asso : ctrl.getLstAssociations())
		{
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

	private void valider()
	{
		String roleName1 = this.txtRoleName1.getText().trim();
		String roleName2 = this.txtRoleName2.getText().trim();
		int selectedIndex = cmbAssociations.getSelectedIndex();
		
		if (selectedIndex >= 0)
		{
			this.ctrl.ajouterRoleAssociation(selectedIndex, roleName1, roleName2);
			
			this.txtRoleName1.setText("");
			this.txtRoleName2.setText("");
			SwingUtilities.getWindowAncestor(this).setVisible(false);
		}
	}
	
	private void annuler()
	{
		this.txtRoleName1.setText("");
		this.txtRoleName2.setText("");
		SwingUtilities.getWindowAncestor(this).setVisible(false);
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
			int indiceSelect = cmbAssociations.getSelectedIndex();
			if(indiceSelect >= 0 && indiceSelect < ctrl.getLstAssociations().size())
			{
				Association asso = ctrl.getLstAssociations().get(indiceSelect);
				this.txtRoleName1.setEditable(true);
				this.txtRoleName2.setEditable(asso.getTypeAsso().equals("bidirectionnelle"));
			}
		}
	}
}