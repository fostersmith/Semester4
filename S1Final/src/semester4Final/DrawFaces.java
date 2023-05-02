package semester4Final;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DrawFaces extends JFrame implements ActionListener {
	
	JPanel bottomBar = new JPanel(new GridLayout(1,3));
	JButton happyButton = new JButton("Happy"),
			sadButton = new JButton("Sad"),
			kookyButton = new JButton("Kooky");
	
	JPanel drawingPanel;
	
	JLabel noiseLabel = new JLabel(" ");
	
	BufferedImage faceImage = new BufferedImage(300,450,BufferedImage.TYPE_INT_ARGB);
	
	Face face = null;
	
	public DrawFaces() {
		super("Faces");

		drawingPanel = new JPanel(new BorderLayout()) {
			@Override
			public void paintComponent(Graphics g) { 
				super.paintComponent(g);
				if(face != null) { // when face is not null, paint a face using the fields in face
					// face
					g.setColor(face.getFaceC());
					g.fillOval(100, 150, face.getFaceSize(), face.getFaceSize());
					// eye 1
					g.setColor(face.getEyeC());
					g.fillOval(170, 200, face.getEyeSize(), face.getEyeSize());
					// eye 2
					g.fillOval(210, 200, face.getEyeSize(), face.getEyeSize());
					// mouth
					g.setColor(face.getMouthC());
					g.fillArc(150, 250, 100, 50, face.getMouthStartArc(), face.getMouthEndArc());
				}
			}
		};
		
		happyButton.addActionListener(this);
		sadButton.addActionListener(this);
		kookyButton.addActionListener(this);
		
		bottomBar.add(happyButton);
		bottomBar.add(sadButton);
		bottomBar.add(kookyButton);
		
		drawingPanel.add(noiseLabel, BorderLayout.NORTH);
		noiseLabel.setHorizontalAlignment(JLabel.CENTER);
		
		drawingPanel.add(bottomBar, BorderLayout.SOUTH);

		add(drawingPanel);
		drawingPanel.setBackground(Color.ORANGE);
		
		repaint();
		
		setSize(400, 520);
	}	
	
	public static void main(String[] args) {
		DrawFaces f1 = new DrawFaces();
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == happyButton) {
			face = new HappyFace();
		} else if(src == sadButton) {
			face = new SadFace();
		} else if(src == kookyButton) {
			boolean gotValidFace = false;
			while(!gotValidFace) {
				try {
					face = new KookyFace();
					gotValidFace = true;
				} catch(InvalidInputException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		}
		repaint();
		noiseLabel.setText(face.makeNoise());		
	}
}	`
