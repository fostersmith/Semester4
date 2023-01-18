package programs;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class StopGate extends JFrame implements MouseListener {

	public static final int ROWS = 8, COLS = 8;
	public static final Color playerColor = Color.BLUE, computerColor = Color.BLACK;
	public static final Color bgTileA = Color.GRAY, bgTileB = Color.WHITE;
	
	private static final int EMPTY = 0, PLAYER = 1, COMPUTER = 2;
	
	Map<JPanel, int[]> rowColState = new HashMap<>();
	JPanel panelOfPanels = new JPanel();
	
	public StopGate() {
		panelOfPanels.setLayout(new GridLayout(ROWS, COLS));
		
		for(int r = 0; r < ROWS; ++r)
			for(int c = 0; c < COLS; ++c) {
				JPanel panel = new JPanel();
				panel.setBackground(r%2 == c%2 ? bgTileA : bgTileB);
				panel.addMouseListener(this);
				rowColState.put(panel, new int[] {r, c, EMPTY});
				panelOfPanels.add(panel);
		}
		
		add(panelOfPanels);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
		setVisible(true);
		
	}
	
	void checkComputerWin() {
		boolean playerHasMove = false;
		for(int r = 0; r < ROWS; ++r) {
			for(int c = 0; c < COLS-1; ++c) {
				int i = r*COLS+c;
				JPanel a = (JPanel) panelOfPanels.getComponent(i);
				JPanel b = (JPanel) panelOfPanels.getComponent(i+1);
				if(rowColState.get(a)[2] == EMPTY && rowColState.get(b)[2] == EMPTY) {
					c = COLS;
					r = ROWS;
					playerHasMove = true;
				}
			}
		}
		if(!playerHasMove) {
			JOptionPane.showMessageDialog(null, "Compooter wins");
			System.exit(0);
		}
	}
	
	void checkPlayerWin() {
		boolean computerHasMove = false;
		for(int r = 0; r < ROWS-1; ++r) {
			for(int c = 0; c < COLS; ++c) {
				int i = r*COLS+c;
				JPanel a = (JPanel) panelOfPanels.getComponent(i);
				JPanel b = (JPanel) panelOfPanels.getComponent(i+COLS);
				if(rowColState.get(a)[2] == EMPTY && rowColState.get(b)[2] == EMPTY) {
					c = COLS;
					r = ROWS;
					computerHasMove = true;
				}
			}
		}
		if(!computerHasMove) {
			JOptionPane.showMessageDialog(null, "Hooman wins");
			System.exit(0);
		}
		
	}
	
	void computerTurn() {
		List<Integer> available = new ArrayList<Integer>();
		for(int i = 0; i < panelOfPanels.getComponentCount()-COLS; ++i) {
			if(rowColState.get(panelOfPanels.getComponent(i))[2] == EMPTY &&
				rowColState.get(panelOfPanels.getComponent(i+COLS))[2] == EMPTY) {
				available.add(i);
			}
		}
		int choiceII = (int) (Math.random()*available.size());
		int choiceI = available.get(choiceII);
		JPanel choiceA = (JPanel) panelOfPanels.getComponent(choiceI);
		JPanel choiceB = (JPanel) panelOfPanels.getComponent(choiceI+COLS);
		rowColState.get(choiceA)[2] = COMPUTER;
		rowColState.get(choiceB)[2] = COMPUTER;
		choiceA.setBackground(computerColor);
		choiceB.setBackground(computerColor);
		checkComputerWin();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JPanel panel = (JPanel) e.getSource();
		int[] pRowColState = rowColState.get(panel);
		if(pRowColState[2] == EMPTY && pRowColState[1] < COLS-1) {
			JPanel otherPanel = (JPanel) panelOfPanels.getComponent(pRowColState[0]*COLS+pRowColState[1]+1);
			if(rowColState.get(otherPanel)[2] == EMPTY) {
				rowColState.get(panel)[2] = PLAYER;
				rowColState.get(otherPanel)[2] = PLAYER;
				panel.setBackground(playerColor);
				otherPanel.setBackground(playerColor);
				checkPlayerWin();
				computerTurn();
			}
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
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new StopGate();
	}
}
