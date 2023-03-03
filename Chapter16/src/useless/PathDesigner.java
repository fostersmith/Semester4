package useless;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
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

	JMenu editingPathMenu = new JMenu("Editing Path");
	ButtonGroup pathToEditGroup = new ButtonGroup();
	
	JMenu showPathsMenu = new JMenu("Show Paths");
	
	JMenu fileMenu = new JMenu("File");
	JMenuItem save = new JMenuItem("Save");
	JMenuItem addPath = new JMenuItem("Add Path");
	JMenuItem deletePath = new JMenuItem("Delete Path");
	
	JMenu modeMenu = new JMenu("Mode");
	JRadioButtonMenuItem append = new JRadioButtonMenuItem("Append");
	JRadioButtonMenuItem delete = new JRadioButtonMenuItem("Delete");
	JRadioButtonMenuItem move = new JRadioButtonMenuItem("Move");
	ButtonGroup modeGroup = new ButtonGroup();
	
	Map<ButtonModel, Integer> modeMap = new HashMap<>();
	Map<ButtonModel, File> editingPathButtons = new HashMap<>();
	Map<ButtonModel, File> showPathButtons = new HashMap<>();
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if(modeGroup.getSelection() != null)
			designer.setMode(modeMap.get(modeGroup.getSelection()));
		if(pathToEditGroup.getSelection() != null)
			designer.setSelectedFile(editingPathButtons.get(pathToEditGroup.getSelection()));
		
		File show = showPathButtons.get(e.getSource());
		if(show != null) {
			JCheckBoxMenuItem check = (JCheckBoxMenuItem)e.getSource();
			designer.setVisible(show, check.isSelected());
		}
		
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			designer.saveSelected();
			System.out.println("saved");
		}
	}
	
	public PathDesigner() {
		
		designer.setPreferredSize(new Dimension(600,600));

		modeMap.put(append.getModel(), PathDesignerPanel.APPEND);
		modeMap.put(delete.getModel(), PathDesignerPanel.DELETE);
		modeMap.put(move.getModel(), PathDesignerPanel.MOVE);
		
		for(File f : DIR.listFiles()) {
			designer.addPath(PathDesignerPanel.loadPoints(f), f);
			JRadioButtonMenuItem radio = new JRadioButtonMenuItem(f.getName());
			JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem(f.getName());
			editingPathButtons.put(radio.getModel(), f);
			showPathButtons.put(checkBox.getModel(), f);
			radio.addItemListener(this);
			checkBox.addItemListener(this);
			editingPathMenu.add(radio);
			pathToEditGroup.add(radio);
			showPathsMenu.add(checkBox);				

			radio.setSelected(true);
			checkBox.setSelected(true);
		}
		
		fileMenu.add(save);
		fileMenu.add(addPath);
		fileMenu.add(deletePath);
		
		modeGroup.add(append);
		modeGroup.add(delete);
		modeGroup.add(move);
		
		append.addItemListener(this);
		append.setSelected(true);
		delete.addItemListener(this);
		move.addItemListener(this);
		
		modeMenu.add(append);
		modeMenu.add(delete);
		modeMenu.add(move);
		
		mainBar.add(editingPathMenu);
		mainBar.add(showPathsMenu);
		mainBar.add(fileMenu);
		mainBar.add(modeMenu);
		
		save.addActionListener(this);
		
		add(designer);
		addMouseListener(designer);
		
		this.setJMenuBar(mainBar);
		setLayout(new FlowLayout());
	}
	
	public static void main(String[] args) {
		JFrame f1 = new PathDesigner();
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setSize(new Dimension(600, 600));
		f1.setResizable(true);
		f1.setVisible(true);
	}


}
