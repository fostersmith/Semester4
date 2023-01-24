package gears;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Gears extends JFrame implements MouseListener {
	
	JPanel picturePanel;
	Gear heldGear = null;
	int heldGearMouseOffsetX, heldGearMouseOffsetY;
	List<GearBox> boxes = new ArrayList<>();
	List<Gear> gears = new ArrayList<>();
	
	Thread autoRotate, moveGear;
	
	public Gears() {
		
		Gear[] someGears = new Gear[10];
		for(int i = 0; i < someGears.length; ++i) {
			someGears[i] = new BasicGear((i+1)*40, 50, 6);
			gears.add(someGears[i]);
		}
		
		autoRotate = new Thread() {
			@Override
			public void run() {
				while(true) {
					for(int i = 0; i < boxes.size(); ++i)
						boxes.get(i).applyMovementToGear(boxes.get(i).rotateGear, 1/100d);
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
		autoRotate.start();
		
		moveGear = new Thread() {
			@Override
			public void run() {
				while(true) {
					if(heldGear != null) {
						Point mousePoint = MouseInfo.getPointerInfo().getLocation();
						Point onScreen =  picturePanel.getLocationOnScreen();
						int mouseX = mousePoint.x - onScreen.x;
						int mouseY = mousePoint.y - onScreen.y;
						heldGear.setX(heldGearMouseOffsetX+mouseX);
						heldGear.setY(heldGearMouseOffsetY+mouseY);
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		moveGear.start();

		picturePanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(Gear gear : gears) {
					gear.drawGear(g);
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
		for(Gear g : gears) {
			g.disconnectAll();
			for(Gear g2 : gears) {
				if(g.isConnected(g2))
					g.connect(g2);
			}
		}
	}
	
	public void updateBoxes() {
		boxes.clear();

		Set<Gear> discovered = new HashSet<>();
		for(Gear g : gears) {
			if(!discovered.contains(g)) {
				GearBox box = new GearBox(g);
				discovered.add(g);
				updateBox(g, box, discovered);
				boxes.add(box);
			}
		}
	}
	
	public void updateBox(Gear g, GearBox box, Set<Gear> excluded){
		for(Gear child : g.getDirectConnections()) {
			if(!excluded.contains(child)) {
				excluded.add(child);
				box.addGear(child);
				updateBox(child, box, excluded);
			}
		}
	}

	public Gear findClosestGear(int x, int y) {
		Gear closest = null;
		double closestDist = -1;
		for(Gear g : gears) {
			double dist = Math.sqrt(Math.pow(g.x-x,2) + Math.pow(g.y-y,2));
			if(dist < closestDist || closestDist == -1) {
				closestDist = dist;
				closest = g;
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
		
		heldGear = closest;
		closest.highlight();
		heldGearMouseOffsetX = heldGear.getX() - e.getX();
		heldGearMouseOffsetY = heldGear.getY() - e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		heldGear.unhighlight();
		heldGear = null;
		updateConnections();
		updateBoxes();
	}
}
