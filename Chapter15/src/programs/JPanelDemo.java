package programs;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JFrame;

public class JPanelDemo extends JFrame {
	
	static Random rand = new Random();
	
	public JPanelDemo() {
		setLayout(new GridLayout(2,2));
		JFlexiblePanel[] panels = new JFlexiblePanel[4];
		String[] fontList = {"Jokerman","Broadway", "Brush Script MT", "Edwardian Script ITC"};
		for(int i = 0; i < 4; ++i) {
			panels[i] = new JFlexiblePanel(randColor(), randColor(), new Font(fontList[i], Font.PLAIN, 10), "Test String");
			add(panels[i]);
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,400);
	}
	
	public static Color randColor() {
		return new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
	}
	
	public static void main(String[] args) {
		JPanelDemo f = new JPanelDemo();
		f.setVisible(true);
	}
}
