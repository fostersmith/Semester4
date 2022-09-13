/*
 * Foster Smith
 * 9/12/22
 * CreateCustomerFile.java
 * 10a
 */

package programs;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateCustomerFile {
	
	static final String DEFAULT_STR = "000,XXXXXX,00000\n";
	static final int NUMRECS = 1000;
	
	public static void main(String[] args) {
		
		Map<Integer,String[]> entries = new HashMap<>();
		boolean done = false;
		Scanner in = new Scanner(System.in);
		int id;
		String input, name, zip;
		while(!done) {
			System.out.print("Enter an ID, or QUIT to quit >> ");
			input = in.nextLine().trim().toUpperCase();
			if(!input.equals("QUIT")) {
				id = Integer.parseInt(input);
				if(!entries.containsKey(id)) {
					System.out.print("Enter a Name >> ");
					name = in.nextLine().trim(); //TODO format to 6 characters
					System.out.print("Enter a Zip Code >> ");
					zip = in.nextLine().trim();
				} else {
					System.out.println("That ID has already been entered.");
				}
			} else {
				done = true;
			}
		}
		
		try {
			Path file = Paths.get("customerfile.txt");
			FileChannel fc;
			if(!Files.exists(file)) {
				fc = (FileChannel)Files.newByteChannel(file, WRITE, CREATE);
				createEmpty(fc);
			} else {
				fc = (FileChannel)Files.newByteChannel(file, WRITE);			
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createEmpty(FileChannel fc) throws IOException {
		byte[] defaultbytes = DEFAULT_STR.getBytes();
		ByteBuffer out = ByteBuffer.wrap(defaultbytes);
		for(int i = 0; i < NUMRECS; ++i) {
			while(out.hasRemaining())
				fc.write(out);
			out.rewind();
		}
	}
}
