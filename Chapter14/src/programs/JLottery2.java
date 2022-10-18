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
import javax.swing.JScrollPane;

public class JLottery2 extends JFrame implements ActionListener, ItemListener {
	JCheckBox[] boxes;
	JCheckBox[] selections; // the order of previous clicks - used to remove oldest click when selected > 6
	int mostRecent = -1; // index of most recent selection
	JButton playButton;
	
	JScrollPane resultsField;
	
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
		
		resultsField = new JScrollPane(); //TODO
		
		setLayout(new FlowLayout());
		for(JCheckBox b : boxes)
			add(b);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setResizable(true);
		setVisible(true);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		JCheckBox b = (JCheckBox)e.getSource();
		int index = Integer.parseInt(b.getText())-1;
		if(e.getStateChange() == 1) {
			System.out.println("Most Recent: "+mostRecent);
			mostRecent++;
			mostRecent %= selections.length;
			System.out.println("Most Recent Now: "+mostRecent);
			if(selections[mostRecent] != null)
				selections[mostRecent].setSelected(false);
			selections[mostRecent] = b;
		}
		System.out.print("[");
		for(JCheckBox s:selections) {
			if(s == null) 
				System.out.print("null,");
			else
				System.out.print(s.getText()+",");
		}
		System.out.println("]");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Random rand = new Random();
		Set<Integer> nums = new HashSet<>();
		for(int i = 0; i < selections.length; ++i)
			nums.add(rand.nextInt(31)+1);
		
	}

	public static void main(String[] args) {
		new JLottery2();
	}
	
}
