package retroconception.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.text.*;
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
		
		/* ------------------ */
		/* Titre de la classe */
		/* ------------------ */
		JLabel titre = new JLabel(classe.getNom());
		titre.setFont(new Font("Arial", Font.BOLD, 16));
		titre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		/* ---------------- */
		/* Zone de contenu  */
		/* ---------------- */
		JTextPane pane = new JTextPane();
		pane.setEditable(false);
		pane.setFont(new Font("Monospaced", Font.PLAIN, 12));
		pane.setBackground(new Color(240, 245, 242));
		pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		StyledDocument doc = pane.getStyledDocument();

		/* ------- */
		/* Styles  */
		/* ------- */

		Style normal = pane.addStyle("normal", null);

		Style bold = pane.addStyle("bold", null);
		StyleConstants.setBold(bold, true);

		Style underline = pane.addStyle("underline", null);
		StyleConstants.setUnderline(underline, true);
		
		try
		{
			/* ----------- */
			/* ATTRIBUTS   */
			/* ----------- */

			doc.insertString( doc.getLength(), "ATTRIBUTS\n"                    , bold );
			doc.insertString( doc.getLength(), "─────────────────────────────\n", bold );

			int maxNomLength = 0;
			for (Attribut attr : classe.getAttributs())
			{
				int length = (attr.getSymbole() + " " + attr.getNom()).length();
				if (length > maxNomLength) maxNomLength = length;
			}

			for (Attribut attr : classe.getAttributs())
			{
				String prefix = String.format("%s %s", attr.getSymbole(), attr.getNom());
				int padding = maxNomLength - prefix.length() + 2; // +2 pour le symbole

				String line = prefix + " ".repeat(padding) + ": " + attr.getType() + "\n";

				if (attr.estStatique()) doc.insertString( doc.getLength(), line, underline );
				else                    doc.insertString( doc.getLength(), line, normal    );
			}

			/* ----------- */
			/* MÉTHODES    */
			/* ----------- */

			doc.insertString( doc.getLength(), "\nMÉTHODES\n"                   , bold );
			doc.insertString( doc.getLength(), "─────────────────────────────\n", bold );

			int maxMethodLength = 0;
			for ( Methode method : classe.getMethodes() )
			{
				String params = "";
				for ( Parametre p : method.getParametre() )
				{
					params += p.nom() + " : " + p.type();
					if ( method.getParametre().indexOf(p) != method.getParametre().size() - 1 )
						params += ", ";
				}

				int length = (method.getVisibilite() + " " + method.getNom() + "(" + params + ")").length();
				if (length > maxMethodLength) maxMethodLength = length;
			}

			for (Methode method : classe.getMethodes())
			{
				String params = "";
				for (Parametre p : method.getParametre())
				{
					params += p.nom() + " : " + p.type();
					if (method.getParametre().indexOf(p) != method.getParametre().size() - 1)
						params += ", ";
				}

				String signature = String.format(
					"%s %s(%s)",
					method.getSymbole(),
					method.getNom(),
					params
				);

				if (method.getType() != null && !method.getType().equals("void"))
				{
					int padding = maxMethodLength - signature.length() + 2;
					signature += " ".repeat(padding) + ": " + method.getType();
				}

				if (method.estStatique())
					doc.insertString(doc.getLength(), signature + "\n", underline);
				else
					doc.insertString(doc.getLength(), signature + "\n", normal);
			}
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}

		JScrollPane scrollPane = new JScrollPane(pane);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		this.add(scrollPane, BorderLayout.CENTER);
	}
}