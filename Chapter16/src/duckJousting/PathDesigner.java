package duckJousting;

import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

public class PathDesigner extends JFrame {
	
	private static final File DIR = new File("paths\\");
	
	JPanel designer = new PathDesignerPanel();
	JMenuBar mainBar = new JMenuBar();

	JMenu editingPathMenu = new JMenu("Editing Path:");
	ArrayList<JRadioButtonMenuItem> pathToEdit = new ArrayList<>();
	ButtonGroup pathToEditGroup = new ButtonGroup();
	
	JMenu showPathsMenu = new JMenu("Show Paths:");
	ArrayList<JCheckBoxMenuItem> showPaths = new ArrayList<>();
	
	JMenu fileMenu = new JMenu("File");
	JMenuItem save = new JMenuItem("Save");
	JMenuItem addPath = new JMenuItem("Add Path");
	JMenuItem deletePath = new JMenuItem("Delete Path");
	
	JMenu modeMenu = new JMenu("Mode");
	JMenuItem append = new JMenuItem("Append");
	JMenuItem delete = new JMenuItem("Delete");
	JMenuItem move = new JMenuItem("Move");
	
	public PathDesigner() {
		for(File f : DIR.listFiles()) {
			
		}
		setLayout(new FlowLayout());
	}
	
	public static void main(String[] args) {
		JFrame f1 = new PathDesigner();
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setResizable(true);
		f1.setVisible(true);
	}
}
