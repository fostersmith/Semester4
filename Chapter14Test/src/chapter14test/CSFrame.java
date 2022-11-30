package chapter14test;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CSFrame extends ThreeButtonFrame {
	
	static ArrayList<Equipment> equipmentList = new ArrayList<>();
	static ArrayList<Student> studentList = new ArrayList<>();
	static CSFrame f1 = new CSFrame();
	
	JButton addStudentButton, addEquipmentButton, viewDatabaseButton;
	EquipmentFrame addEquipment;
	StudentFrame addStudent;
	ViewFrame view;
	
	public CSFrame() {
		super("Computer Science Class", "Add Student", "Add Equipment", "View Database");
		addEquipment = new EquipmentFrame();
		addStudent = new StudentFrame();
		view = new ViewFrame();
	}
	
	@Override
	public void onA() { // Add Student
		addStudent.setVisible(true);
		setVisible(false);
	}
	
	@Override
	public void onB() { // Add Equipment
		addEquipment.setVisible(true);
		setVisible(false);
	}
	
	@Override
	public void onC() { // View Database
		view.setVisible(true);
		setVisible(false);
	}
	
	public static void addEquipment(Equipment e) {
		equipmentList.add(e);
	}
	
	public static void addStudent(Student e) {
		studentList.add(e);
	}
	
	public static void main(String[] args) {
		//CSFrame f1 = CSFrame.f1;
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		f1.repaint();
	}

}
