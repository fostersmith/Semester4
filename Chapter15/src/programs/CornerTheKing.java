package programs;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CornerTheKing extends JFrame implements ActionListener {

	public final static int ROWS = 10;
	public final static int COLS = 10;
	public JButton[][] board = new JButton[ROWS][COLS];
	int playerR, playerC, compR, compC;
	boolean isPlayerTurn = true;
	
	public CornerTheKing() {
		for(int r = 0; r < board.length; ++r) {
			for(int c = 0; c < board[r].length; ++c) {
				board[r][c] = new JButton();
				board[r][c].addActionListener(this);
			}
		}
		int playerInitialPos = (int)(Math.random()*(COLS));
		int computerInitialPos;
		do {
			computerInitialPos = (int)(Math.random()*(COLS));
		} while(playerInitialPos == computerInitialPos);
		board[ROWS-1][playerInitialPos].setText("P");
		board[ROWS-1][computerInitialPos].setText("C");
		playerR = ROWS-1;
		compR = ROWS-1;
		playerC = playerInitialPos;
		compC = computerInitialPos;
		
		setLayout(new GridLayout(ROWS, COLS));
		for(int r = 0; r < board.length; ++r) {
			for(int c = 0; c < board[r].length; ++c) {
				add(board[r][c]);
			}			
		}
		
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public int[] findPosition(JButton b) {
		for(int r = 0; r < board.length; ++r) {
			for(int c = 0; c < board[r].length; ++c) {
				if(board[r][c] == b)
					return new int[] {r, c};
			}
		}
		return null;
	}
	
	public void playerWin() {
		JOptionPane.showMessageDialog(null, "You win!");
		System.exit(0);
	}
	
	public void computerWin() {
		JOptionPane.showMessageDialog(null, "Better luck next time");
		System.exit(0);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		int[] clickPos = findPosition((JButton)e.getSource());
		int verticalChange = playerR - clickPos[0];
		int horizontalChange = playerC-clickPos[1];
		if(!(clickPos[0] == compR && clickPos[1] == compC)&&(verticalChange!=0) ^ (horizontalChange!=0) && (verticalChange<=2&&verticalChange>=0) && (horizontalChange<=2&&horizontalChange>=0)) {
			board[playerR][playerC].setText("");
			playerR = clickPos[0];
			playerC = clickPos[1];
			board[playerR][playerC].setText("P");
			if(playerR == 0 && playerC == 0) {
				playerWin();
			} else {
				isPlayerTurn = false;
				doComputerTurn();
			}
		}
	}
	
	public void doComputerTurn() {
		if(!isPlayerTurn) {
			board[compR][compC].setText("");
			boolean movingUp = Math.random() < 0.5 || compC == 0;
			if(compR == 0)
				movingUp = false;
			int movementAmount = (int) (Math.random()*2)+1;
			
			if(compC==playerC && movingUp) {
				if(compR - 1 == playerR)
					movementAmount = 2;
				else if(compR - 2 == playerR)
					movementAmount = 1;
			} else if(compR == playerR && !movingUp) {
				if(compC - 1 == playerC)
					movementAmount = 2;
				else if(compC -2 == playerC)
					movementAmount = 1;
			}
			
			if(movingUp)
				compR = Math.max(0,compR-movementAmount);
			else
				compC = Math.max(0,compC-movementAmount);

			board[compR][compC].setText("C");
			
			if(compR == 0 && compC == 0)
				computerWin();
			else
				isPlayerTurn = true;
		}
	}

	
	public static void main(String[] args) {
		new CornerTheKing();
	}
}
