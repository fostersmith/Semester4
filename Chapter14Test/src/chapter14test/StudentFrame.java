package chapter14test;

import javax.swing.JOptionPane;

public class StudentFrame extends ThreeFieldAddFrame {

	public StudentFrame() {
		super("Add a Student", "Add Student", "Student First Name", "Student Last Name","Student Grade");

	}

	@Override
	void onAddButton(String first, String last, String gradeStr) {
		if(first.equals("")) {
			JOptionPane.showMessageDialog(null, "Enter a first name", "Bad Name",JOptionPane.ERROR_MESSAGE);	
		} else if(first.equals("")) {
			JOptionPane.showMessageDialog(null, "Enter a last name", "Bad Name",JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				double grade = Double.parseDouble(gradeStr);
				CSFrame.addStudent(new Student(first, last, grade));
				JOptionPane.showMessageDialog(null, "Successfully added student");
			} catch(Exception e) {
				JOptionPane.showMessageDialog(null, "Enter a valid grade", "Bad Grade",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

}
