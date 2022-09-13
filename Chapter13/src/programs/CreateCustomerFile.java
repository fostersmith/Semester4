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
			input = null;
			System.out.print("Enter an ID, or QUIT to quit >> ");
			input = in.nextLine().trim().toUpperCase();
			if(!input.equals("QUIT")) {
				id = Integer.parseInt(input);
				if(!entries.containsKey(id)) {
					System.out.print("Enter a Name >> ");
					name = String.format("%-6s",in.nextLine().trim()).substring(0,6); //TODO format to 6 characters
					System.out.print("Enter a Zip Code >> ");
					zip = in.nextLine().trim();
					entries.put(id, new String[] {name,zip});
				} else {
					System.out.println("That ID has already been entered.");
				}
			} else {
				done = true;
			}
		}
		
		String entryStr;
		try {
			Path file = Paths.get("customerfile.txt");
			FileChannel fc;
			ByteBuffer out;
			if(!Files.exists(file)) {
				fc = (FileChannel)Files.newByteChannel(file, WRITE, CREATE);
				createEmpty(fc);
			} else {
				fc = (FileChannel)Files.newByteChannel(file, WRITE);			
			}
			for(Map.Entry<Integer,String[]> entry:entries.entrySet()) {
				id = entry.getKey();
				name = entry.getValue()[0];
				zip = entry.getValue()[1];
				fc.position(getPosition(id));
				entryStr = String.format("%3s", Integer.toString(id)).replace(" ", "0")+
						","+name+","+zip+"\n";
				System.out.println("writing '"+entryStr+"'");
				out = ByteBuffer.wrap(entryStr.getBytes());
				while(out.hasRemaining())
					fc.write(out);
			}
			fc.close();
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
	public static long getPosition(int id) {
		return id*DEFAULT_STR.length();
	}
}
