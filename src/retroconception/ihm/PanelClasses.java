package retroconception.ihm;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import retroconception.Controleur;
import retroconception.metier.Classe;

public class PanelClasses extends JPanel implements ActionListener
{
	private Controleur    ctrl;
	private FrameUML      frame;
	private List<JButton> lstButtons;
	private FrameInfo     frameInfo;

	public PanelClasses(Controleur ctrl, FrameUML frame)
	{
		this.ctrl = ctrl;
		this.frame = frame;
		this.lstButtons = new ArrayList<>();

		this.setBackground(new Color(228, 240, 232));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.setPreferredSize(new Dimension(220, 0));

		this.actualiser();
	}

	public void actualiser()
	{
		this.removeAll();
		this.lstButtons.clear();

		List<Classe> lstClasse = ctrl.getLstClasses();

		this.add(Box.createVerticalStrut(15));

		for (Classe classe : lstClasse)
		{
			JButton btn = new JButton(classe.getNom());

			btn.setBorderPainted(false);
			btn.setFocusPainted(false);
			btn.setContentAreaFilled(false);
			btn.setOpaque(true);

			btn.setBackground(new Color(128, 170, 141));
			btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

			btn.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));

			btn.setMaximumSize( new Dimension(Integer.MAX_VALUE, btn.getPreferredSize().height));

			btn.setAlignmentX(LEFT_ALIGNMENT);
			btn.addActionListener(this);

			this.add(btn);
			this.add(Box.createVerticalStrut(10));


			this.lstButtons.add(btn);
		}

		this.revalidate();
		this.repaint();
	}

	public void actionPerformed(ActionEvent e) 
	{
		for (JButton btn : this.lstButtons)
		{
			if(e.getSource() == btn)
			{
				this.resetBtnColor();
				btn.setBackground(new Color(153, 213, 144));
				this.frameInfo = new FrameInfo(ctrl, ctrl.getLstClasses().get(this.lstButtons.indexOf(btn)));
			}
		}
	}

	private void resetBtnColor()
	{
		for (JButton btn : this.lstButtons)
		{
			if (btn.getBackground() != new Color(128, 170, 141))
			{
				btn.setBackground(new Color(128, 170, 141));
			}
		}
	}
	
}