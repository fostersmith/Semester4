package duckJousting;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

public class PathDesigner extends JFrame implements ActionListener, ItemListener {
	
	private static final File DIR = new File("paths\\");
	
	PathDesignerPanel designer = new PathDesignerPanel();
	JMenuBar mainBar = new JMenuBar();

	JMenu editingPathMenu = new JMenu("Editing Path:");
	ButtonGroup pathToEditGroup = new ButtonGroup();
	
	JMenu showPathsMenu = new JMenu("Show Paths:");
	
	JMenu fileMenu = new JMenu("File");
	JMenuItem save = new JMenuItem("Save");
	JMenuItem addPath = new JMenuItem("Add Path");
	JMenuItem deletePath = new JMenuItem("Delete Path");
	
	JMenu modeMenu = new JMenu("Mode");
	JMenuItem append = new JRadioButtonMenuItem("Append");
	JMenuItem delete = new JRadioButtonMenuItem("Delete");
	JMenuItem move = new JRadioButtonMenuItem("Move");
	ButtonGroup modeGroup = new ButtonGroup();
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(pathToEditGroup.getSelection() == e.getSource()) {};//TODO
		
		if(e.getSource() instanceof JRadioButtonMenuItem) {
			
		} else if (e.getSource() instanceof JCheckBoxMenuItem) {
			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public PathDesigner() {
		for(File f : DIR.listFiles()) {
			designer.addPath(PathDesignerPanel.loadPoints(f));
			JRadioButtonMenuItem radio = new JRadioButtonMenuItem(f.getName());
			JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem(f.getName());
			editingPathMenu.add(radio);
			pathToEditGroup.add(radio);
			showPathsMenu.add(checkBox);						
		}
		
		fileMenu.add(save);
		fileMenu.add(addPath);
		fileMenu.add(deletePath);
		
		modeGroup.add(append);
		modeGroup.add(delete);
		modeGroup.add(move);
		
		append.addItemListener(this);
		delete.addItemListener(this);
		move.addItemListener(this);
		
		modeMenu.add(append);
		modeMenu.add(delete);
		modeMenu.add(move);
		
		mainBar.add(editingPathMenu);
		mainBar.add(showPathsMenu);
		mainBar.add(fileMenu);
		mainBar.add(modeMenu);
		
		this.setJMenuBar(mainBar);
		setLayout(new FlowLayout());
	}
	
	public static void main(String[] args) {
		JFrame f1 = new PathDesigner();
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(new Dimension(600, 100));
		f1.setResizable(true);
		f1.setVisible(true);
	}


}
