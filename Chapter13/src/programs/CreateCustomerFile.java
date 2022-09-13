/*
 * Foster Smith
 * 9/12/22
 * CreateCustomerFile.java
 * 10a
 */

package programs;

import static java.nio.file.StandardOpenOption.*;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CreateCustomerFile {
	
	static final String DEFAULT_RECORD = "000,XXXXXX,00000\n";
	static final String DEFAULT_NAME = "XXXXXX";
	static final int NUMRECS = 1000;
	
	public static void main(String[] args) throws IOException {
		
		Path file = Paths.get("customerfile.txt");
		FileChannel fc;
		if(Files.exists(file)) {
			fc = (FileChannel)Files.newByteChannel(file, READ, WRITE);
		} else {
			fc = (FileChannel)Files.newByteChannel(file, READ, WRITE, CREATE);
			createEmpty(fc);
		}
		
		boolean done = false;
		Scanner in = new Scanner(System.in);
		int id;
		String input, name, zip;
		Set<Integer> usedIDs = new HashSet<Integer>();
		while(!done) {
			input = null;
			System.out.print("Enter an ID, or QUIT to quit >> ");
			input = in.nextLine().trim().toUpperCase();
			if(!input.equals("QUIT")) {
				id = Integer.parseInt(input);
				if(!usedIDs.contains(id)) {
					if(getEntry(fc, id)[1].equals(DEFAULT_NAME)) {
						System.out.print("Enter a Name >> ");
						name = String.format("%-6s",in.nextLine().trim()).substring(0,6); //TODO format to 6 characters
						System.out.print("Enter a Zip Code >> ");
						zip = in.nextLine().trim();
						writeEntry(fc, id, name, zip);
					usedIDs.add(id);
					} else {
						System.out.println("That ID already exists in the file.");
						usedIDs.add(id);
					}
				} else {
					System.out.println("That ID has already been entered.");
				}
			} else {
				done = true;
			}
		}
		
		
		fc.close();
	}
	
	public static void createEmpty(FileChannel fc) throws IOException {
		byte[] defaultbytes = DEFAULT_RECORD.getBytes();
		ByteBuffer out = ByteBuffer.wrap(defaultbytes);
		for(int i = 0; i < NUMRECS; ++i) {
			while(out.hasRemaining())
				fc.write(out);
			out.rewind();
		}
	}
	public static long getPosition(int id) {
		return id*DEFAULT_RECORD.length();
	}
	
	public static String[] getEntry(FileChannel fc, int id) throws IOException {
		fc.position(getPosition(id));
		byte[] bytes = DEFAULT_RECORD.substring(0, DEFAULT_RECORD.length()-1).getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		fc.read(buffer);
		return new String(bytes).split(",");
	}
	
	public static void writeEntry(FileChannel fc, int id, String name, String zip) throws IOException {
		fc.position(getPosition(id));
		String entryStr = String.format("%3s", Integer.toString(id)).replace(" ", "0")+
				","+name+","+zip+"\n";
		ByteBuffer out = ByteBuffer.wrap(entryStr.getBytes());
		while(out.hasRemaining())
			fc.write(out);
	}

}
