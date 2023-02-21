package programs;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JSmileFacePanel2 extends JPanel implements ActionListener {
	
	int faceWidth = 300;
	int eyeMarginX = 80;
	int eyeMarginY = 100;
	
	int mouthWidth = 200;
	
	boolean smiling = false;
	
	JButton button = new JButton("Click Me");
	
	public JSmileFacePanel2() {
		setLayout(null);
		add(button);
		button.setBounds(300, 50, 100, 50);
		button.addActionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int m = 80;
		int w = 20;
		g.drawOval(0, 0, faceWidth, faceWidth);
		g.drawOval(m, eyeMarginY, w, w);
		g.drawOval(faceWidth-m-w, eyeMarginY, w, w);
		
		if(smiling) {
		g.drawArc((faceWidth-mouthWidth)/2, (faceWidth-mouthWidth)/2, mouthWidth, mouthWidth, 180, 180);
		} else {
		g.drawArc((faceWidth-mouthWidth)/2, faceWidth/2, mouthWidth, mouthWidth, 0, 180);			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		smiling = !smiling;
		repaint();
	}

	public static void main(String[] args) {
		JFrame f1 = new JFrame();
		f1.add(new JSmileFacePanel2());
		f1.setSize(500,500);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}

}
