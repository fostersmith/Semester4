package gears;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gears extends JFrame {
	
	JPanel picturePanel;
	List<GearBox> boxes = new ArrayList<>();
	
	public Gears() {
		
		List<Gear> gears = new ArrayList<Gear>();
		BasicGear gear = new BasicGear(200, 200, 50);
		gears.add(gear);
		BasicGear gear2 = new BasicGear(400, 200, 5);
		gear.connect(gear2);
		gear2.connect(gear);
		gears.add(gear2);
		GearBox box = new GearBox(gears);
		
		Thread rotateGear = new Thread() {
			@Override
			public void run() {
				while(true) {
					boxes.get(0).applyMovementToGear(gear, 1/100d);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					repaint();
				}
			}
		};
		rotateGear.start();

		
		boxes.add(box);
		
		picturePanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(GearBox box : boxes) {
					for(Gear gear : box.getGears()) {
						gear.drawGear(g);
					}
				}
			}
		};
		
		add(picturePanel);
		
		repaint();
	}
	
	public static void main(String[] args) {
		Gears f1 = new Gears();
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		f1.setSize(500, 500);
	}
}
