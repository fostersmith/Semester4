/*
 * Foster Smith
 * 07/04/22
 * StaffDinnerEvent.java
 */
package carlysCatering;

import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class StaffDinnerEventAndCreateFile {

	public static void main(String[] args) throws IOException {
		Path file = Paths.get("events.txt");
		OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));
		
		for(int i = 0; i < 3; ++i) {
			DinnerEvent e = createEvent();
			saveEvent(e,writer,i==0);
		}
		writer.flush();
		output.close();
	}
	
	public static DinnerEvent createEvent() {
		String eventNum = JOptionPane.showInputDialog("Enter an Event Number");
		String phoneNum = JOptionPane.showInputDialog("Enter a Phone Number");
		int guests = getIntegerDataEntry("Enter Number of Guests");
		
		int entr = JOptionPane.showOptionDialog(null, "Choose an entree", "Entree", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, DinnerEvent.entrees, DinnerEvent.entrees[0]);
		int s1 = JOptionPane.showOptionDialog(null, "Choose a side", "Side 1", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, DinnerEvent.sides, DinnerEvent.sides[0]);
		int s2 = JOptionPane.showOptionDialog(null, "Choose a side", "Side 2", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, DinnerEvent.sides, DinnerEvent.sides[0]);
		int dessert = JOptionPane.showOptionDialog(null, "Choose a side", "Side 2", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, DinnerEvent.desserts, DinnerEvent.desserts[0]);
		
		int numWaitstaff = guests/10+1;
		int numBartender = guests/25;
		
		Employee[] staff = new Employee[numWaitstaff+numBartender+1];
		for(int i = 0; i < numWaitstaff; ++i) {
			String fName = JOptionPane.showInputDialog("Enter Waitstaff "+(i+1)+"'s First Name");
			String lName = JOptionPane.showInputDialog("Enter Waitstaff "+(i+1)+"'s Last Name");
			staff[i] = new Waitstaff(i, fName, lName);
		}
		for(int i = numWaitstaff; i < numWaitstaff+numBartender; ++i) {
			String fName = JOptionPane.showInputDialog("Enter Bartender "+(i+1)+"'s First Name");
			String lName = JOptionPane.showInputDialog("Enter Bartender "+(i+1)+"'s Last Name");
			staff[i] = new Bartender(i, fName, lName);
		}
		staff[staff.length-1] = new Coordinator(staff.length-1,"Greg??!?"," :|");
		DinnerEvent event = new DinnerEvent(eventNum,guests,phoneNum,entr,s1,s2,dessert);
		event.setStaff(staff);
		
		return event;
	}
	
	public static void displayEvent(DinnerEvent e) {
		System.out.println("Event #: "+e.getEventNumber());
		System.out.println("Guests: "+e.getGuests());
		System.out.println("Phone #: "+e.getPhoneNumber());
		System.out.println("Entree: "+e.entrees[e.entreeChoice]);
		System.out.println("Side 1: "+e.sides[e.side1Choice]);
		System.out.println("Side 2: "+e.sides[e.side2Choice]);
		System.out.println("Dessert: "+e.desserts[e.dessertChoice]);
		System.out.println("Price: "+e.getPrice());
		System.out.println("Staff: ");
		for(Employee em:e.getStaff())
			System.out.println("\t"+em);
	}
	
	public static void saveEvent(DinnerEvent e, BufferedWriter writer, boolean isFirst) throws IOException {
		
		// Event #,Event Type,Guests,Price 
		
		String record = e.getEventNumber()+","
				+e.getEventType()+","
				+e.getGuests()+","
				+e.getPrice();
		if(!isFirst)
			record += "\n";
		writer.write(record);
	}
	
	//helpers
	private static int getIntegerDataEntry(String message) {
		int i;
		while(true) {
			try {
				String entryStr = JOptionPane.showInputDialog(message);
				i = Integer.parseInt(entryStr);
				break;
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Invalid Input. Please enter valid input","Invalid Input", JOptionPane.ERROR_MESSAGE);
			}
		}
		return i;
	}

}
