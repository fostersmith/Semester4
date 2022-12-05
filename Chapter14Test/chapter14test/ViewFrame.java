package chapter14test;

public class ViewFrame extends ThreeButtonFrame {

	public ViewFrame() {
		super("View Database", "View Students", "View Equipment", "Return to Main Menu");
	}
	
	@Override
	void onA() { // View Students
		System.out.println("Students:");
		for(Student s : CSFrame.studentList) {
			System.out.println("\nName = "+s.getFirst() +" "+ s.getLast());
			System.out.println("Grade = "+s.getGrade());			
		}
	}

	@Override
	void onB() { // View Equipment
		System.out.println("Equipment:");
		for(Equipment e : CSFrame.equipmentList) {
			System.out.println("\nItem = "+e.getItem());
			System.out.println("Description = "+e.getDescription());
			System.out.println("Cost = $"+e.getCost());
		}

	}

	@Override
	void onC() { // Return to Main Menu
		setVisible(false);
		CSFrame.f1.setVisible(true);
	}

}
