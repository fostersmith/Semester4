package programs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JLuckySeven extends JPanel implements ActionListener {

	int[] values = new int[7];
	JButton[] buttons = new JButton[7];
	
	int nextToClick = -1;
	
	public JLuckySeven() {
		setLayout(null);
		for(int i = 0; i < buttons.length; ++i) {
			buttons[i] = new JButton(Integer.toString(i+1));
			buttons[i].addActionListener(this);
			buttons[i].setBounds(i*100+10, 10, 95, 200);
			add(buttons[i]);
		}
		shuffle();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int buttonIndex = 0; 
		for(int i = 0; i < buttons.length; ++i) {
			if(buttons[i] == e.getSource()) {
				buttonIndex = i;
				i = buttons.length;
			}
		}
		if(buttonIndex == nextToClick || nextToClick == -1) {
			
			buttons[buttonIndex].setText(Integer.toString(values[buttonIndex]+1));
			nextToClick = values[buttonIndex];
			buttons[buttonIndex].setEnabled(false);
			
			boolean won = true;
			for(int i = 0; i < buttons.length; ++i) {
				won = !buttons[i].isEnabled();
				if(!won)
					i = buttons.length;
			}
			if(won) {
				JOptionPane.showMessageDialog(null, "You Win!");
				nextToClick = -2;
			}
			else if(!buttons[nextToClick].isEnabled()) {
				JOptionPane.showMessageDialog(null, "Better Luck Next Time");
			}
		}
	}
		
	public void shuffle() {
		for(int i = 0; i < values.length; ++i) {
			values[i] = i;
		}
		
		for(int i = 0; i < values.length; ++i) {
			int randInd = (int) (Math.random()*7);
			int temp = values[i];
			values[i] = values[randInd];
			values[randInd] = temp;
		}
	}
	
	public static void main(String[] args) {
		JFrame f1 = new JFrame("Lucky Seven");
		f1.add(new JLuckySeven());
		f1.setSize(720,250);
		f1.setResizable(false);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
	}


}
