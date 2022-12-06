package programs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class JFlexiblePanel extends JPanel {

	JLabel text;
	
	public JFlexiblePanel(Color bg, Color fg, Font f, String msg) {
		super();
		setBackground(bg);
		setForeground(fg);
		text = new JLabel(msg);
		text.setFont(f);
		add(text);
	}
	
}
