package programs;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JChangeSizeAndColorPanel extends JPanel implements ActionListener {

	private static final String phrase = "a phrase";
	private int clickCount = 0;
	private int xPos = 5;
	private int fontSize = 15;
	private Color color = randColor();
	
	private JButton button = new JButton("Click me");
	
	public JChangeSizeAndColorPanel() {
		button.addActionListener(this);
		setLayout(new FlowLayout());
		add(button);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
		g.drawString(phrase, xPos, 300);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(clickCount < 3) {
			xPos += 40;
			fontSize -= 1;
			clickCount += 1;
			color = randColor();
		}
		repaint();
	}
	
	public static Color randColor() {
		return new Color((int) (Math.random()*Integer.MAX_VALUE));
	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		f1.add(new JChangeSizeAndColorPanel());
		f1.setSize(500,500);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}

}
