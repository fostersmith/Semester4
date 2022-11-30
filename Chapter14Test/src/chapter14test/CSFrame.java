package chapter14test;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class CSFrame extends ThreeButtonFrame {
	
	static ArrayList<Equipment> equipmentList = new ArrayList<>();
	static ArrayList<Student> studentList = new ArrayList<>();
	
	JButton addStudentButton, addEquipmentButton, viewDatabaseButton;
	EquipmentFrame addEquipment;
	StudentFrame addStudent;
	
	public CSFrame() {
		super("Add Student", "Add Equipment", "View Database");
		addEquipment = new EquipmentFrame(this);
		addStudent = new StudentFrame(this);
	}
	
	@Override
	public void onA() {
		addStudent.setVisible(true);
		setVisible(false);
	}
	
	@Override
	public void onB() {
		addEquipment.setVisible(true);
		setVisible(false);
	}
	
	@Override
	public void onC() {
		System.out.println("Students: ");
		for(Student s : studentList) {
			System.out.println();
			System.out.println("Name = "+s.getFirst()+" "+s.getLast());
			System.out.println("Grade = "+s.getGrade());
		}
	}
	
	public static void addEquipment(Equipment e) {
		equipmentList.add(e);
	}
	
	public static void addStudent(Student e) {
		studentList.add(e);
	}
	
	public static void main(String[] args) {
		CSFrame f1 = new CSFrame();
		f1.setSize(500,225);
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f1.setVisible(true);
		f1.repaint();

	}

}
