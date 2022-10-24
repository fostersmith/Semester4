package programs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SmarterLastManStanding extends JFrame implements ActionListener, ItemListener {

	JCheckBox[] boxes;
	JPanel boxPanel;
	JButton submitButton;
	JCheckBox[] selected;
	int ptr = 0; //index of the next to replace
	ArrayList<JCheckBox> available;
	
	public SmarterLastManStanding() {
		boxPanel = new JPanel(new GridLayout(2, 5));
		
		available = new ArrayList<>();
		selected = new JCheckBox[3];
		
		boxes = new JCheckBox[10];
		for(int i = 0; i < boxes.length;++i) {
			boxes[i] = new JCheckBox();
			boxPanel.add(boxes[i]);
			available.add(boxes[i]);
			boxes[i].addItemListener(this);
		}
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		
		setLayout(null);
		add(boxPanel);
		boxPanel.setBounds(0,0,500,200);
		add(submitButton);
		submitButton.setBounds(200,200,100,50);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setResizable(false);
		setSize(500,300);
		setVisible(true);
	}
	
	
	public void doBotTurn() {
		int r = available.size();
		int numToSelect = 1;
		// if r <= 3, select r to win immediately
		if(r <= 3)
			numToSelect = r;
		// 'checkmate' is ending with 4 remaining
		// if r = 4, you've already lost, just choose something
		else if(r == 4)
			numToSelect = (int)(Math.random()*3)+1;
		// so if 5 <= r <= 7, select r-4
		else if(r>=5 && r <= 7)
			numToSelect = r-4;
		// if you start the turn w/ 8, you are forced into choosing on [5,7], i.e. the danger zone
		else if(r == 8)
			numToSelect = (int)(Math.random()*3)+1;
		// it's desirable to start turn w/ 5 <= r <= 7, so if 8 < r( <= 10), select r-8, forcing opponent to choose w/in interval
		else if(r > 8)
			numToSelect = r-8;
		for(int i = 0; i < numToSelect && available.size() > 0; ++i) {
			int selectedInd = (int) (Math.random()*available.size());
			JCheckBox choice = available.get(selectedInd);
			choice.removeItemListener(this);
			choice.setSelected(true);
			choice.setEnabled(false);
			choice.addItemListener(this);
			available.remove(choice);
		}
		if(checkWin()) {
			JOptionPane.showMessageDialog(null, "The computer wins!");
			submitButton.setEnabled(false);
		}
	}
	
	public boolean checkWin() {
		return available.size() == 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// if(e.getSource() == submitButton)
		if(!noneSelected()) {
			for(JCheckBox b : selected) {
				if(b != null) {
					available.remove(b);
					b.setEnabled(false);
				}
			}
			for(int i = 0; i < selected.length; ++i)
				selected[i] = null;
			ptr = 0;
			if(checkWin()) {
				JOptionPane.showMessageDialog(null, "You win!");
				submitButton.setEnabled(false);
			}		
			else
				doBotTurn();
		} else
			JOptionPane.showMessageDialog(null, "Select at least one box");
	}
	
	public boolean noneSelected() {
		for(int i = 0; i < selected.length; ++i)
			if(selected[i] != null)
				return false;
		return true;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox b = (JCheckBox) e.getSource();
		if(e.getStateChange() == ItemEvent.SELECTED) {
			if(selected[ptr] != null) {
				selected[ptr].removeItemListener(this);
				selected[ptr].setSelected(false);
				selected[ptr].addItemListener(this);
			}
			selected[ptr] = b;
			ptr += 1;
			ptr %= selected.length;
		} else if(e.getStateChange() == ItemEvent.DESELECTED) {
			if(selected[ptr] != b) {
				JCheckBox[] selectedCopy = selected.clone(); //this is bad
				int i = (ptr+1)%selected.length;
				int lastI = ptr;
				boolean done = false;
				while(!done) {
					done = selected[i] == b;
					selected[i] = selectedCopy[lastI];
					lastI = i;
					i = (i+1)%selected.length;
				}
			}
			selected[ptr] = null;
			
		}
		//printSelected();
	}
	
	public void printSelected() {
		for(JCheckBox b : selected) {
			if(b != null) {
				System.out.print(b.getText()+",");
			} else {
				System.out.print("X,");
			}
		}
		System.out.println();
		for(int i = 0; i < selected.length; ++i) {
			if(ptr == i)
				System.out.print("^ ");
			else
				System.out.print("  ");
		}
		System.out.println("\n");
	}
	
	public void printAvailable() {
		for(JCheckBox b : available) {
			System.out.print(b.getText()+",");
		}
		System.out.println();
	}
	
	public static void main(String[] args) {
		new SmarterLastManStanding();
	}
}
