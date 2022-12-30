package programs;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Concentration extends JFrame implements MouseListener {

	private static final Color borderColor = Color.BLACK;
	private static final Color fillColor = Color.RED;
	private static final Color showColor = Color.CYAN;
	
	private static final Border border = BorderFactory.createLineBorder(borderColor);
	
	JLabel[] labels = new JLabel[20];
	Map<JLabel, Integer> numbers = new HashMap<>();
	Map<JPanel, Boolean> isRevealed = new HashMap<>();
	
	JPanel firstSelected = null;
	JPanel secondSelected = null;
	
	boolean playing = false;
	
	public Concentration() {
		setLayout(new GridLayout(4,5));
		for(int i = 0; i < labels.length; ++i) {
			labels[i] = new JLabel();
			labels[i].setForeground(Color.BLACK);
			JPanel panel = new JPanel();
			isRevealed.put(panel, false);
			panel.add(labels[i]);
			panel.addMouseListener(this);
			panel.setBorder(border);
			panel.setBackground(fillColor);
			add(panel);
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,700);
		setVisible(true);
		
		start();
	}
	
	public void start() {
		firstSelected = null;
		secondSelected = null;
		
		for(JLabel lbl : labels) {
			JPanel parent = (JPanel) lbl.getParent();
			isRevealed.put(parent, false);
			parent.setBackground(fillColor);
			parent.setBorder(border);
		}
		
		firstSelected = null;
		secondSelected = null;
		
		//make a list of numbers (these act like the faces of the cards)
		//the index of a number corresponds to its index in labels
		int[] nums = new int[20];
		for(int i = 0; i < nums.length; ++i)
			nums[i] = i%10;
		//shuffle the numbers
		for(int i = 0; i < nums.length; ++i) {
			int randInd = (int) (Math.random()*20);
			int temp = nums[i];
			nums[i] = nums[randInd];
			nums[randInd] = temp;
		}
		
		numbers.clear();
		for(int i = 0; i < nums.length; ++i) {
			numbers.put(labels[i], nums[i]);
		}
		
		playing = true;
	}
	
	public void end() {
		JOptionPane.showMessageDialog(null, "A congratulatory message");
		playing = false;
		
		int opt = JOptionPane.showConfirmDialog(null, "Play again?", "Play Again?", JOptionPane.YES_NO_OPTION);
		if(opt == JOptionPane.YES_OPTION) {
			start();
		} else {
			System.exit(0);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!isRevealed.get(e.getSource())) {
			JPanel source = (JPanel)e.getSource();
			JLabel lbl = (JLabel) source.getComponent(0);
			if(playing) {
				if(firstSelected == null) {
					lbl.setText(Integer.toString(numbers.get(lbl)));
					source.setBorder(null);
					source.setBackground(showColor);
					firstSelected = source;
				} else if(secondSelected == null) {
					lbl.setText(Integer.toString(numbers.get(lbl)));
					source.setBorder(null);
					source.setBackground(showColor);
					secondSelected = source;
					JLabel firstLBL = (JLabel) firstSelected.getComponent(0);
					if(numbers.get(firstLBL) == numbers.get(lbl)) {
						firstSelected.setBorder(null);
						firstSelected.setBackground(Color.WHITE);
						secondSelected.setBorder(null);
						secondSelected.setBackground(Color.WHITE);
						isRevealed.put(firstSelected, true);
						isRevealed.put(secondSelected, true);
						firstSelected = null;
						secondSelected = null;
					}
				}
			}
		}
	}

	public void checkWin() {
		boolean allRevealed = true;
		for(JLabel lbl : labels) {
			JPanel parent = (JPanel) lbl.getParent();
			allRevealed = allRevealed && isRevealed.get(parent);
		}
		if(allRevealed) {
			end();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(secondSelected != null) {
			JLabel firstLbl = (JLabel) firstSelected.getComponent(0);
			JLabel secondLbl = (JLabel) secondSelected.getComponent(0);
			firstLbl.setText("");
			secondLbl.setText("");
			firstSelected.setBackground(fillColor);
			secondSelected.setBackground(fillColor);
			firstSelected.setBorder(border);
			secondSelected.setBorder(border);
			firstSelected = null;
			secondSelected = null;
		}
	}

	public static void main(String[] args) {
		new Concentration();
	}


}
