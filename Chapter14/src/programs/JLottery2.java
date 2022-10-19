package programs;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JLottery2 extends JFrame implements ActionListener, ItemListener {
	JCheckBox[] boxes;
	JCheckBox[] selections; // the order of previous clicks - used to remove oldest click when selected > 6
	int mostRecent = -1; // index of most recent selection
	JButton playButton;
	
	JScrollPane resultsPane;
	JTextArea resultsField;
	
	JPanel boxPanel;
	
	public JLottery2() {
		boxes = new JCheckBox[30];
		for(int i = 1; i < 31; ++i) {
			boxes[i-1] = new JCheckBox(Integer.toString(i));
			boxes[i-1].addItemListener(this);
		}
		selections = new JCheckBox[6];
		for(int i = 0; i < selections.length; ++i)
			selections[i] = null;
		
		playButton = new JButton("Play");
		playButton.addActionListener(this);
		
		resultsField = new JTextArea();
		resultsPane = new JScrollPane(resultsField);
		
		
		setLayout(null);
		boxPanel = new JPanel(new FlowLayout());
		for(JCheckBox b : boxes)
			boxPanel.add(b);
		
		boxPanel.add(playButton);
		
		add(boxPanel);
		boxPanel.setBounds(0,0,500,200);
		add(resultsPane);
		resultsPane.setBounds(0,200,500,300);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setResizable(true);
		setVisible(true);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox b = (JCheckBox)e.getSource();
		if(e.getStateChange() == 1) {
			System.out.println("Most Recent: "+mostRecent);
			mostRecent++;
			mostRecent %= selections.length;
			System.out.println("Most Recent Now: "+mostRecent);
			if(selections[mostRecent] != null)
				selections[mostRecent].setSelected(false);
			selections[mostRecent] = b;
		}
		/*System.out.print("[");
		for(JCheckBox s:selections) {
			if(s == null) 
				System.out.print("null,");
			else
				System.out.print(s.getText()+",");
		}
		System.out.println("]");*/
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean sixSelections = true;
		for(JCheckBox b:selections)
			sixSelections = sixSelections || b != null;
		if(sixSelections) {
			Random rand = new Random();
			Set<Integer> nums = new HashSet<>();
			resultsField.setText("");
			resultsField.append("Numbers: ");
			System.out.print("Numbers: ");
			for(int i = 0; i < selections.length; ++i) {
				int choice;
				do
					choice = rand.nextInt(31)+1;
				while(nums.contains(choice));
					
				nums.add(choice);
				resultsField.append(((i!=0)?", ":"")+choice);
				System.out.print(((i!=0)?", ":"")+choice);
			}
			int correct = 0;
			for(int i = 0; i < selections.length; ++i)
				if(nums.contains(Integer.parseInt(selections[i].getText()))) {
					resultsField.append("\n"+selections[i].getText()+" - YES!");
					System.out.print("\n"+selections[i].getText()+" - YES!");
					correct++;
				}
				else {
					resultsField.append("\n"+selections[i].getText()+" - NO :(");
					System.out.print("\n"+selections[i].getText()+" - NO :(");
				}
			int reward;
			switch(correct) {
			case 3:
				reward = 100;
				break;
			case 4:
				reward = 10000;
				break;
			case 5:
				reward = 50000;
				break;
			case 6:
				reward = 1000000;
				break;
			default:
				reward = 0;
			}
			if(reward > 0) {
				resultsField.append("\nYou matched "+correct+" and earned $"+reward+"!");
			} else {
				resultsField.append("\nBetter Luck Next Time");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please Make 6 Selections Before Proceeding");
		}
	}

	public static void main(String[] args) {
		new JLottery2();
	}
	
}
