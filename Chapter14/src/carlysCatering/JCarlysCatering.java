package carlysCatering;

import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.function.Consumer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JCarlysCatering extends JFrame implements ItemListener, ActionListener {

	TextField guestsField;
	
	JCheckBox[] sideDishes;
	JCheckBox[] entrees;
	JCheckBox[] desserts;
	
	int sidesSelected;
	int guests = -1;
	
	ButtonGroup entreesGroup;
	ButtonGroup dessertsGroup;
	
	JPanel sideDishPanel;
	JPanel entreePanel;
	JPanel dessertPanel;
	
	JButton submitButton;
	JButton resetButton;
	JButton quitButton;
	JButton guestsButton;
	
	JLabel sideLabel;
	JLabel guestsLabel;
	JLabel entreeLabel;
	JLabel dessertLabel;
	
	Consumer<Event> doOnEntry;
	
	/**
	 * Creates a new JFrame which takes arguments for an Event object
	 * The JFrame will accept new Events until the "Quit" button is pressed, calling doOnEntry each time a new Event is entered
	 * @param doOnEntry - method to be called when an Event is entered by the user
	 */
	public JCarlysCatering(Consumer<Event> doOnEntry) {
		this.doOnEntry = doOnEntry;
		
		sideDishes = new JCheckBox[Event.sides.length];
		entrees = new JCheckBox[Event.entrees.length];
		desserts = new JCheckBox[Event.desserts.length];
		
		sideDishPanel = new JPanel(new FlowLayout());
		entreePanel = new JPanel(new FlowLayout());
		dessertPanel = new JPanel(new FlowLayout());

		for(int i = 0; i < sideDishes.length; ++i) {
			sideDishes[i] = new JCheckBox(Event.sides[i]);
			sideDishes[i].addItemListener(this);
			sideDishPanel.add(sideDishes[i]);
		}
		for(int i = 0; i < entrees.length; ++i) {
			entrees[i] = new JCheckBox(Event.entrees[i]);
			entreesGroup.add(entrees[i]);
			entreePanel.add(entrees[i]);
		}
		for(int i = 0; i < desserts.length; ++i) {
			desserts[i] = new JCheckBox(Event.desserts[i]);
			dessertsGroup.add(desserts[i]);
			dessertPanel.add(desserts[i]);
		}
		entreesGroup.setSelected(entrees[0].getModel(), true);
		dessertsGroup.setSelected(desserts[0].getModel(), true);
		
		sidesSelected = 0;
		
		sideLabel = new JLabel("Select up to 2 sides");
		guestsLabel = new JLabel("Enter number of Guests");
		entreeLabel = new JLabel("Select an Entree");
		dessertLabel = new JLabel("Select a Dessert");
		
		submitButton = new JButton("Submit Event");
		resetButton = new JButton("Reset");
		quitButton = new JButton("Quit");
		guestsButton = new JButton("Enter Guests");

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		if(source == submitButton) {
			if(guests != -1) {
				JCheckBox[] selectedSides = new JCheckBox[sidesSelected];
			} else {
				JOptionPane.showMessageDialog(null, "Please enter guest number");
			}
		} else if(source == resetButton) {
			resetFields();
		} else if(source == quitButton) {
			System.exit(0);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED /*&& entrees contains e.getSource()*/) {
			++sidesSelected;
		} else if(e.getStateChange() == ItemEvent.DESELECTED) {
			--sidesSelected;
		}
	}
	
	public void resetFields() {
		guestsField.setText("");
		for(JCheckBox b : sideDishes)
			b.setSelected(false);
		guests = -1;
		entreesGroup.setSelected(entrees[0].getModel(), true);
		dessertsGroup.setSelected(desserts[0].getModel(), true);
	}

}
