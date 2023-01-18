package programs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LightsOut extends JFrame implements MouseListener {

	
	public static final Color dark = Color.BLACK;
	public static final Color light = Color.WHITE;
	
	JPanel panelOfPanels = new JPanel();
	JPanel messagePanel = new JPanel();
	
	JLabel messageLabel = new JLabel();
	
	public LightsOut() {
		panelOfPanels.setLayout(new GridLayout(5,5));
		for(int i = 0; i < 25; ++i) {
			JPanel panel = new JPanel();
			panel.setBackground(light);
			panel.addMouseListener(this);
			panelOfPanels.add(panel);
		}
				
		messagePanel.add(messageLabel);
		
		setLayout(new BorderLayout());
		add(panelOfPanels, BorderLayout.CENTER);
		add(messagePanel, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,600);
		setVisible(true);
	}
	
	public JPanel getPanel(int r, int c) {
		return (JPanel) panelOfPanels.getComponent(c+r*5);
	}
	
	public int[] getRowAndCol(JPanel panel) {
		int index = getPanelIndex(panel);
		if(index != -1) {
			return new int[] {index / 5, index % 5};
		} else {
			return null;
		}
	}
	
	public int getPanelIndex(JPanel panel) {
		for(int i = 0; i < panelOfPanels.getComponentCount(); ++i) {
			if(panelOfPanels.getComponent(i) == panel) {
				return i;
			}
		}
		return -1;
	}
	
	public void togglePanel(JPanel panel) {
		panel.setBackground(panel.getBackground() == light ? dark : light);
	}
	
	public void checkWin() {
		boolean win = true;
		for(int i = 0; i < panelOfPanels.getComponentCount(); ++i) {
			if(panelOfPanels.getComponent(i).getBackground() != dark) {
				win = false;
				i = panelOfPanels.getComponentCount();
			}
		}
		if(win) {
			for(Component c : panelOfPanels.getComponents())
				c.removeMouseListener(this);
			messageLabel.setText("a congratulatory message");
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		JPanel src = (JPanel) e.getSource();
		int[] rAndC = getRowAndCol(src);
		for(int r = 0; r < 5; ++r) {
			togglePanel(getPanel(r, rAndC[1]));
		}
		for(int c = 0; c < 5; ++c) {
			togglePanel(getPanel(rAndC[0], c));
		}
		togglePanel(getPanel(rAndC[0],rAndC[1]));
		checkWin();
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
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new LightsOut();
	}

}
