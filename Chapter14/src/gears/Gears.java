package gears;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Gears extends JFrame implements MouseListener, ActionListener {
	
	public static final int MOVE_MODE = 0, ELECT_ROTATOR_MODE = 1, SPAWN_MODE = 2, FLIP_DIRECTION_MODE = 3, DELETE_MODE = 4;
	
	Map<Gear, Integer> preferredDirection = new HashMap<>();
	
	JPanel picturePanel;
	Gear heldGear = null;
	Gear spawnGear = new BasicGear(0,0,5);//null;
	int heldGearMouseOffsetX, heldGearMouseOffsetY;
	List<GearBox> boxes = new ArrayList<>();
	List<Gear> gears = new ArrayList<>();
	
	Thread autoRotate, moveGear, highlighter;
	
	int mode = MOVE_MODE;
	
	JMenuBar mainBar = new JMenuBar();
	JMenu toolMenu = new JMenu("Tools");
	JMenuItem moveTool = new JMenuItem("Move"),
			setRotatorTool = new JMenuItem("Set Rotator Gear"),
			reverseRotatorTool = new JMenuItem("Switch Rotator Direction"),
			spawnTool = new JMenuItem("Spawn Gear"),
			deleteTool = new JMenuItem("Delete Gear");
			
	
	public Gears() {
		super("Gears (Move Mode)");
		
		setJMenuBar(mainBar);
		mainBar.add(toolMenu);
		toolMenu.add(moveTool);
		toolMenu.add(setRotatorTool);
		toolMenu.add(reverseRotatorTool);
		toolMenu.add(spawnTool);
		toolMenu.add(deleteTool);
		
		moveTool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.ALT_MASK));
		setRotatorTool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
		reverseRotatorTool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		spawnTool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		deleteTool.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
		
		moveTool.addActionListener(this);
		setRotatorTool.addActionListener(this);
		reverseRotatorTool.addActionListener(this);
		spawnTool.addActionListener(this);
		deleteTool.addActionListener(this);
		
		Gear[] someGears = new Gear[10];
		for(int i = 0; i < someGears.length; ++i) {
			someGears[i] = new BasicGear((i+1)*40, 50, 5);
			((BasicGear)someGears[i]).setRotation(0);
			gears.add(someGears[i]);
		}
		someGears[0].y += 1;
		BasicGear.adjustGearRotation((BasicGear)someGears[0], (BasicGear)someGears[1]);
		
		autoRotate = new Thread() {
			@Override
			public void run() {
				while(true) {
					for(int i = 0; i < boxes.size(); ++i) {
						boxes.get(i).rotateGear(boxes.get(i).rotateGear, (Math.PI/2)/100d);
					}
						//boxes.get(i).applyMovementToGear(boxes.get(i).rotateGear, 1/100d);
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
		
		Thread.UncaughtExceptionHandler moveGearHandler = new Thread.UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("Uncaught exception: "+e);
				t.start();
			}
		};
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
		moveGear.setUncaughtExceptionHandler(moveGearHandler);
		moveGear.start();
		
		highlighter = new Thread() {
			Gear lastHighlighted;
			
			@Override
			public void run() {
				while(true) {
					if(mode == DELETE_MODE || mode == ELECT_ROTATOR_MODE) {
						Point mousePoint = MouseInfo.getPointerInfo().getLocation();
						Point onScreen =  picturePanel.getLocationOnScreen();
						int mouseX = mousePoint.x - onScreen.x;
						int mouseY = mousePoint.y - onScreen.y;
						Gear closest = findClosestGear(mouseX, mouseY);
						if(lastHighlighted != null)	
							lastHighlighted.unhighlight();
						closest.highlight();
						lastHighlighted = closest;
					}
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		highlighter.start();

		picturePanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				for(Gear gear : gears) {
					gear.drawGear(g);
				}
			}
		};
		picturePanel.setBackground(Color.black);
		picturePanel.addMouseListener(this);
		
		add(picturePanel);
		
		updateConnections();
		updateBoxes();
		
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
				for(Gear gear : box.getGears()) {
					if(preferredDirection.containsKey(gear))
						box.setRotateGear(gear, preferredDirection.get(gear));
				}
				boxes.add(box);
			}
		}
		preferredDirection.clear();
		for(GearBox box : boxes) {
			preferredDirection.put(box.rotateGear, box.getEvenOdd(box.rotateGear));
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
	
	public GearBox findGearBox(Gear g) {
		for(GearBox box : boxes)
			if(box.getGears().contains(g))
				return box;
		return null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(mode == ELECT_ROTATOR_MODE) {
			// find the gear closest to the click
			Gear closest = findClosestGear(e.getX(), e.getY());
			GearBox box = findGearBox(closest);
			preferredDirection.remove(box.rotateGear);
			
			box.setRotateGear(closest, 1);
			preferredDirection.put(box.rotateGear, box.getEvenOdd(box.rotateGear));
		} else if(mode == FLIP_DIRECTION_MODE) {
			Gear closest = findClosestGear(e.getX(), e.getY());
			GearBox box = findGearBox(closest);
			
			if(box.rotateGear == closest) {
				box.setRotateGear(closest, box.getEvenOdd(closest)*-1);
				preferredDirection.put(closest, box.getEvenOdd(closest));
			}
		} else if(mode == DELETE_MODE) {
			Gear closest = findClosestGear(e.getX(), e.getY());
			gears.remove(closest);
			updateConnections();
			updateBoxes();
		} else if(mode == SPAWN_MODE) {
			spawnGear.setX(e.getX());
			spawnGear.setY(e.getY());
			gears.add(spawnGear);
			updateConnections();
			updateBoxes();
			mode = MOVE_MODE;
			this.setTitle("Gears (Move Mode)");
		}
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
		if(mode == MOVE_MODE) {
			// find the gear closest to the click
			Gear closest = findClosestGear(e.getX(), e.getY());
			
			heldGear = closest;
			closest.highlight();
			heldGearMouseOffsetX = heldGear.getX() - e.getX();
			heldGearMouseOffsetY = heldGear.getY() - e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(mode == MOVE_MODE) {
			heldGear.unhighlight();
			heldGear = null;
			updateConnections();
			updateBoxes();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == moveTool) {
			mode = MOVE_MODE;
			this.setTitle("Gears (Move Mode)");
		} else if(src == setRotatorTool) {
			mode = ELECT_ROTATOR_MODE;
			this.setTitle("Gears (Set Rotator Mode)");
		} else if(src == reverseRotatorTool) {
			mode = FLIP_DIRECTION_MODE;
			this.setTitle("Gears (Flip Direction Mode)");
		} else if(src == spawnTool) {
			int toothCount = 0;
			do {
				try {
					toothCount = Integer.parseInt(JOptionPane.showInputDialog("Tooth Count:").trim());
				} catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
			} while(toothCount <= 0);
			spawnGear = new BasicGear(0,0,toothCount);
			mode = SPAWN_MODE;
			this.setTitle("Gears (Spawn Mode)");
		} else if(src == deleteTool) {
			mode = DELETE_MODE;
			this.setTitle("Gears (Delete Mode)");
		}
		
		for(Gear g : gears)
			g.unhighlight();
	}
}
