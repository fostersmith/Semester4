/*
 * Foster Smith
 * 09/13/22
 * CustomerItemOrder.java
 * 10c
 */

package programs;

import static java.nio.file.StandardOpenOption.READ;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class CustomerItemOrder {
	
	public static void main(String[] args) throws IOException {
		Path custFile = Paths.get("customerfile.txt"),
				itemFile = Paths.get("itemfile.txt");
		FileChannel custFC = (FileChannel)Files.newByteChannel(custFile, READ),
				itemFC = (FileChannel)Files.newByteChannel(itemFile, READ);
		
		boolean done = false;
		Scanner in = new Scanner(System.in);
		String input;
		int id, itemnum;
		while(!done) {
			System.out.print("Enter Customer ID or QUIT to quit >> ");
			input = in.nextLine().trim().toUpperCase();
			if(!input.equals("QUIT")) {
				id = Integer.parseInt(input);
				String[] custData = CreateCustomerFile.getEntry(custFC, id);
				System.out.print("Enter Item # >> ");
				itemnum = Integer.parseInt(in.nextLine().trim());
				String[] itemData = CreateItemFile.getEntry(itemFC, itemnum);
				System.out.println("----- Customer Data -----");
				System.out.println("ID: "+custData[0]);
				System.out.println("Name: "+custData[1].trim());
				System.out.println("Zip Code: "+custData[2]);
				System.out.println("----- Item Data -----");
				System.out.println("Item #: "+itemData[0]);
				System.out.println("Description: "+itemData[1]);
				System.out.println("-------------------------");
			} else {
				done = true;
			}
		}
	}
	
}
