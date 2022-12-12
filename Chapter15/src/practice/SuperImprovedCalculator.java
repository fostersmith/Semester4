/*
 * SuperImprovedCalculator.java
 * Foster Smith
 * 12/7/22
 */

package practice;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SuperImprovedCalculator extends JFrame implements ActionListener {
	
	JButton[] keys = new JButton[17];
	String[] operatorKeys = {"/","*","-","+","=",".","CLEAR"};
	JPanel keypad = new JPanel(new GridLayout(4,4,10,10));
	JTextField lcd = new JTextField(20);
	Font bigFont = new Font("HoboSTD",Font.BOLD,25);
	Font funFont = new Font("Chiller",Font.BOLD,30);
	double op1 = 0d, op2 = 0d;
	boolean first = true, clearText = true, rightClickFlag = false;
	int lastOp = 0;
	DecimalFormat calcPattern = new DecimalFormat("########.########");
	JMenuBar mBar;
	JMenu mFile, mEdit, mAbout;
	static SuperImprovedCalculator f1;
		
	public SuperImprovedCalculator() {
		//creates an instance of the JMenuBar
		mBar = new JMenuBar();
		setJMenuBar(mBar);
		
		//construct and populate the File menu
		mFile = new JMenu("File", true);
		mBar.add(mFile);
		JMenuItem mFileExit = new JMenuItem("Exit");
		mFile.add(mFileExit);

		//construct and populate the Edit menu
		mEdit = new JMenu("Edit", true);
		mBar.add(mEdit);
		JMenuItem mEditClear = new JMenuItem("Clear");
		mEdit.add(mEditClear);
		mEdit.insertSeparator(1);
		JMenuItem mEditCopy = new JMenuItem("Copy");
		mEdit.add(mEditCopy);
		JMenuItem mEditPaste = new JMenuItem("Paste");
		mEdit.add(mEditPaste);
		mEdit.insertSeparator(4);
		JMenu mEditTextColor = new JMenu("Text Color");
		JMenuItem mEditTextColorRed = new JMenuItem("Red");
		mEditTextColor.add(mEditTextColorRed);
		JMenuItem mEditTextColorGreen = new JMenuItem("Green");
		mEditTextColor.add(mEditTextColorGreen);
		JMenuItem mEditTextColorMagenta = new JMenuItem("Magenta");
		mEditTextColor.add(mEditTextColorMagenta);
		mEdit.add(mEditTextColor);
		
		//construct and populate the About menu
		mAbout = new JMenu("About",true);
		mBar.add(mAbout);
		JMenuItem mAboutCalculator = new JMenuItem("About Calculator");
		mAbout.add(mAboutCalculator);
		
		//add the ActionListener to each menu item
		mFileExit.addActionListener(this);
		mEditClear.addActionListener(this);
		mEditCopy.addActionListener(this);
		mEditPaste.addActionListener(this);
		mEditTextColorRed.addActionListener(this);
		mEditTextColorGreen.addActionListener(this);
		mEditTextColorMagenta.addActionListener(this);
		mAboutCalculator.addActionListener(this);

		//set an ActionCommand for each menu item
		mFileExit.setActionCommand("Exit");
		mEditClear.setActionCommand("Clear");
		mEditCopy.setActionCommand("Copy");
		mEditPaste.setActionCommand("Paste");
		mEditTextColorRed.setActionCommand("Red");
		mEditTextColorGreen.setActionCommand("Green");
		mEditTextColorMagenta.setActionCommand("Magenta");
		mAboutCalculator.setActionCommand("About");

		//add Mnemonic for each menu & menu item
		mFile.setMnemonic('F');
		mFileExit.setMnemonic('X');
		mEdit.setMnemonic('E');
		mEditClear.setMnemonic('L');
		mEditCopy.setMnemonic('C');
		mEditPaste.setMnemonic('P');
		mEditTextColor.setMnemonic('O');
		mEditTextColorRed.setMnemonic('R');
		mEditTextColorGreen.setMnemonic('G');
		mEditTextColorMagenta.setMnemonic('M');
		mAbout.setMnemonic('A');
		mAboutCalculator.setMnemonic('R');

		
		lcd.setEditable(false);
		lcd.setBackground(Color.WHITE);
		
		//construct and assign captions to the JButtons
		for(int i = 0; i <= 9; ++i) {
			keys[i] = new JButton(String.valueOf(i));	
		}
		for(int i = 10;  i <= 16; ++i) {
			keys[i] = new JButton(operatorKeys[i-10]);
		}
		for(int i = 7;  i <= 10; ++i) { //7, 8, 9, /
			keypad.add(keys[i]);
		}
		for(int i = 4;  i <= 6; ++i) { //4, 5, 6
			keypad.add(keys[i]);
		}
		keypad.add(keys[11]); //*
		for(int i = 1;  i <= 3; ++i) { //1,2,3
			keypad.add(keys[i]);
		}
		keypad.add(keys[12]); //-
		keypad.add(keys[0]); //0
		for(int i = 15;  i >= 13; --i) { //7, 8, 9, divide
			keypad.add(keys[i]);
		}
		for(int i = 0;  i < keys.length; ++i) {
			keys[i].setBackground(new Color(135,14,233));
			keys[i].setForeground(Color.WHITE);
			keys[i].setFont(bigFont);			
			keys[i].addActionListener(this);
		}
		mBar.setBackground(new Color(135, 144, 233));
		lcd.setFocusable(false);
		keypad.setBackground(Color.BLACK);
		add(lcd, BorderLayout.NORTH);
		add(keypad, BorderLayout.CENTER);
		add(keys[16], BorderLayout.SOUTH);
		
		lcd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rightClickFlag = false;
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					JOptionPane.showMessageDialog(null, "You can not edit this with the mouse!");
				} else if(e.getButton() == MouseEvent.BUTTON2) {
					Image icon = Toolkit.getDefaultToolkit().getImage("cards\\sk.png"); //TODO
					f1.setIconImage(icon);
				} else if(e.getButton() == MouseEvent.BUTTON3) {
					changeFont(funFont);
					rightClickFlag = true;
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				changeColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(!rightClickFlag) {
					changeFont(bigFont);
				}
				else {
					rightClickFlag = false;
				}
			}
			
			//@Override
			//public void mousePressed(MouseEvent e) {
				
			//@Override
			//public void mouseReleased(MouseEvent e) {
					
		});
		
		changeFont(bigFont);
		
		addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				String keyString = new String();
				int key = -1;
				int keyCode = e.getKeyCode();
				int keyLocation = e.getKeyLocation();
				
				if(keyCode == KeyEvent.VK_ENTER) {
					key = 14;
				} else if(keyLocation == KeyEvent.KEY_LOCATION_NUMPAD) {
					if(keyCode >= KeyEvent.VK_NUMPAD0 && keyCode <= KeyEvent.VK_NUMPAD9) {
						key = Integer.parseInt(keyString + e.getKeyChar());
					} else {
						switch(keyCode) {
							case KeyEvent.VK_DIVIDE:
								key = 10;
								break;
							case KeyEvent.VK_MULTIPLY:
								key = 11;
								break;
							case KeyEvent.VK_SUBTRACT:
								key = 12;
								break;
							case KeyEvent.VK_ADD:
								key = 13;
								break;
							case KeyEvent.VK_DECIMAL:
								key = 15;
								break;
						}
					} 
				}
				if(key >= 0)
					keyAction(key);
				
			}
			
			//@Override
			//public void keyReleased

			//@Override
			//public void keyTyped
		});
		setFocusable(true);
	}
	
	private void changeFont(Font f) {
		lcd.setFont(f);
		for(int i = 0; i < keys.length; i++) {
			keys[i].setFont(f);
		}
		mFile.setFont(f);
		mEdit.setFont(f);
		mAbout.setFont(f);
		setFocusable(true);
	}
	
	private void changeColor(Color c) {
		Color menuTextColor;
		lcd.setForeground(c);
		for(int i = 0; i < keys.length; i++) {
			keys[i].setForeground(c);
		}
		mBar.setBackground(c);
		menuTextColor = new Color(255-c.getRed(), 255-c.getGreen(), 255-c.getBlue());
		mFile.setForeground(menuTextColor);
		mEdit.setForeground(menuTextColor);
		mAbout.setForeground(menuTextColor);
		setFocusable(true);
	}
	

	private void keyAction(int keyNum) {
		switch(keyNum) {
			//number and decimal point buttons
		case 0: case 1: case 2: case 3: case 4: 
		case 5: case 6: case 7: case 8: case 9:
		case 15:
			if(clearText) {
				lcd.setText("");
				clearText = false;
			}
			lcd.setText(lcd.getText() + keys[keyNum].getText());
			break;
		case 10: case 11: case 12: case 13: case 14: 
			if(lcd.getText().length() != 0) {	//to stop from pressing operators w/o numbers
				clearText = true;
				if(first) { //first operand
					op1 = Double.parseDouble(lcd.getText());
					first = false;
					lastOp = keyNum;
				}
				else { //second
					op2 = Double.parseDouble(lcd.getText());
					switch(lastOp) {
					case 10: //divide
						op1 /= op2;
						break;
					case 11: //multiply
						op1 *= op2;
						break;
					case 12: //subtract
						op1 -= op2;
						break;
					case 13: //addition
						op1 += op2;
						break;
					}
				}
				lcd.setText(calcPattern.format(op1));
				if(keyNum == 14) { // =
					first = true;
				} else {
					lastOp = keyNum;
				}
			} 
			break;
		case 16:
			lcd.setText("");
			first = true;
			break;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String arg = e.getActionCommand();
		if(arg == "Exit") {
			System.exit(0);
		} else if(arg == "Clear") {
			clearText = true;
			first = true;
			op1 = 0.0;
			op2 = 0.0;
			lcd.setText("");
			lcd.requestFocus();
		} else if(arg == "Red") {
			changeColor(Color.red);
		} else if(arg == "Green") {
			changeColor(Color.green);
		} else if(arg == "Magenta") {
			changeColor(Color.magenta);
		} else if(arg == "Copy") {
			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection contents = new StringSelection(lcd.getText());
			cb.setContents(contents, null);
		} else if(arg == "Paste") {
			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
			Transferable content = cb.getContents(this);
			try {
				String s = (String)content.getTransferData(DataFlavor.stringFlavor);
				lcd.setText(calcPattern.format(Double.parseDouble(s)));
			} catch(Throwable exc) {
				lcd.setText("");
			}
		} else if(arg == "About") {
			String message = "Calculator ver. 2.0.2\nHHS Software\nCopyright 2022\nAll rights reserved";
			JOptionPane.showMessageDialog(null, message, "About Calculator", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
					
			for(int i = 0; i < keys.length; ++i) {
				if(e.getSource() == keys[i]) {
					keyAction(i);
					i = keys.length;
				}
			}
			
		}
	}
			
	public static void main(String[] args) {
		f1 = new SuperImprovedCalculator();
		f1.setTitle("Calculator");
		f1.setBounds(200,200,300,350);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		// set image properties and add to the frame
		Image icon = Toolkit.getDefaultToolkit().getImage("cards\\sk.png"); //TODO
		f1.setIconImage(icon);
	}
}
