package programs;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JFontSelector extends JFrame implements ActionListener {
	
	private static final int largeFont = 20, smallFont = 10;
	
	JButton jokerman = new JButton("Jokerman");
	JButton dreamingOutloud = new JButton("Edwardian Script ITC");
	JButton amazone = new JButton("Brush Script MT");
	JButton cezanne = new JButton("Broadway");
	JButton calligrapher = new JButton("Kunstler Script");
	JButton sizeButton = new JButton("Enlarge");
	
	JLabel sampleText = new JLabel("The quick brown fox jumps over the lazy dog");
	
	private int fontSize = smallFont;
	private String fontName;
	
	public JFontSelector() {
		super("J Font Selector");
		
		jokerman.addActionListener(this);
		dreamingOutloud.addActionListener(this);
		amazone.addActionListener(this);
		cezanne.addActionListener(this);
		calligrapher.addActionListener(this);
		sizeButton.addActionListener(this);
		
		setLayout(new GridLayout(7, 1));
		add(sampleText);
		add(jokerman);
		add(dreamingOutloud);
		add(amazone);
		add(cezanne);
		add(calligrapher);
		add(sizeButton);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500,500);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == sizeButton) {
			if(fontSize == smallFont) {
				fontSize = largeFont;
				sizeButton.setText("Un-Enlarge");
			}
			else {
				fontSize = smallFont;
				sizeButton.setText("Enlarge");
			}
		} else {
			fontName = ((JButton)src).getText();
		}
		Font f = new Font(fontName, Font.PLAIN, fontSize);  
		sampleText.setFont(f);
	}
	
	public static void main(String[] args) {
		JFrame f = new JFontSelector();
		f.setVisible(true);
	}
	
}
