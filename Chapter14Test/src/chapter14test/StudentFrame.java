package chapter14test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class StudentFrame extends ThreeFieldAddFrame {

	public StudentFrame(CSFrame mainMenu) {
		super("Add a Student", "Add Student", "Student First Name", "Student Last Name","Student Grade",  mainMenu);

	}

	@Override
	void onAddButton(String a, String b, String c) {
		
	}

}
