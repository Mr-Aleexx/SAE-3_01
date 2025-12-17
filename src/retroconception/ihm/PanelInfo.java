package retroconception.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.Parameter;

import javax.swing.*;

import retroconception.Controleur;
import retroconception.metier.Attribut;
import retroconception.metier.Classe;
import retroconception.metier.Methode;
import retroconception.metier.Parametre;

public class PanelInfo extends JPanel
{
	private Controleur ctrl;
		
	public PanelInfo(Controleur ctrl, Classe classe)
	{
		this.ctrl = ctrl;
		
		
		this.setLayout(new BorderLayout());
		this.setBackground(new Color(228, 240, 232));
		
		// Titre
		JLabel titre = new JLabel(classe.getNom());
		titre.setFont(new Font("Arial", Font.BOLD, 16));
		titre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBackground(new Color(240, 245, 242));
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		StringBuilder sb = new StringBuilder();
		
		int maxNomLength = 0;
		for (Attribut attr : classe.getAttributs()) 
		{
			int length = (attr.getVisibilite() + " " + attr.getNom()).length();
			if (length > maxNomLength) maxNomLength = length;
		}
		
		sb.append("ATTRIBUTS\n");
		sb.append("─────────────────────────────\n");
		for (Attribut attr : classe.getAttributs()) 
		{
			String prefix = String.format("  %s %s", attr.getVisibilite(), attr.getNom());
			int padding = maxNomLength - prefix.length() + 2;
			sb.append(prefix);
			sb.append(" ".repeat(padding));
			sb.append(": " + attr.getType() + "\n");
		}
		
		sb.append("\n");
		
		int maxMethodLength = 0;
		for (Methode method : classe.getMethodes()) 
		{
			String para = "";
			for (Parametre parametre : method.getParametre()) 
			{
				para += parametre.nom() + " : " + parametre.type();
				if(method.getParametre().indexOf(parametre) != method.getParametre().size() - 1)
					para += ", ";
			}
			int length = (method.getVisibilite() + " " + method.getNom() + "(" + para + ")").length();
			if (length > maxMethodLength) maxMethodLength = length;
		}
		
		sb.append("MÉTHODES\n");
		sb.append("─────────────────────────────\n");
		for (Methode method : classe.getMethodes())
		{
			String para = "";
			String type = method.getType();
			
			for (Parametre parametre : method.getParametre()) 
			{
				para += parametre.nom() + " : " + parametre.type();
				if(method.getParametre().indexOf(parametre) != method.getParametre().size() - 1)
					para += ", ";
			}

			String signature = String.format("  %s %s(%s)", method.getVisibilite(), method.getNom(), para);
			
			if (type != null && !type.equals("void"))
			{
				int padding = maxMethodLength - signature.length() + 2;
				sb.append(signature);
				sb.append(" ".repeat(padding));
				sb.append(": " + type + "\n");
			}
			else
			{
				sb.append(signature + "\n");
			}
		}
		
		textArea.setText(sb.toString());
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		this.add(titre, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}
}