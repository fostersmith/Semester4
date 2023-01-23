package gears;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gears extends JFrame implements MouseListener {
	
	JPanel picturePanel;
	Gear heldGear = null;
	List<GearBox> boxes = new ArrayList<>();
	
	public Gears() {
		
		List<Gear> gears = new ArrayList<Gear>();
		BasicGear gear = new BasicGear(200, 200, 50);
		BasicGear gear2 = new BasicGear(400, 200, 5);
		gears.add(gear);
		gears.add(gear2);
		List<Gear> list1 = new ArrayList<Gear>();
		list1.add(gear);
		List<Gear> list2 = new ArrayList<Gear>();
		list2.add(gear2);
		GearBox box1 = new GearBox(list1);
		GearBox box2 = new GearBox(list2);
		boxes.add(box1);
		boxes.add(box2);
		
		
		Thread rotateGear = new Thread() {
			@Override
			public void run() {
				while(true) {
					for(int i = 0; i < boxes.size(); ++i)
						boxes.get(i).applyMovementToGear(boxes.get(i).getGears().get(0), 1/100d);
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
		picturePanel.addMouseListener(this);
		
		add(picturePanel);
		
		repaint();
	}
	
	public static void main(String[] args) {
		Gears f1 = new Gears();
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		f1.setSize(500, 500);
	}
	
	public void updateConnections() {
		
	}
	
	public void updateBoxes() {
		
	}

	public Gear findClosestGear(int x, int y) {
		Gear closest = null;
		double closestDist = -1;
		for(GearBox box : boxes) {
			for(Gear g : box.getGears()) {
				System.out.println("g.x: "+g.x);
				System.out.println("g.y: "+g.y);
				System.out.println("x: "+x);
				System.out.println("y: "+y);
				double dist = Math.sqrt((g.x-x)^2 + (g.y-y)^2);
				System.out.println("Dist: "+dist);
				if(dist < closestDist || closestDist == -1) {
					closestDist = dist;
					closest = g;
				}
			}
		}
		return closest;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		// find the gear closest to the click
		Gear closest = findClosestGear(e.getX(), e.getY());
		System.out.println("Closest: "+closest);
		
		heldGear = closest;
		closest.highlight();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		heldGear.unhighlight();
		heldGear = null;
	}
}
