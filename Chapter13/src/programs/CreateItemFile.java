package programs;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CreateItemFile {
	
	static final String DEFAULT_RECORD = "000,XXXXXXXXXXXXXXXXXXXX\n";
	static final int NUMRECS = 1000;
	
	public static void main(String[] args) throws IOException /*:skull:*/ {
		Map<Integer,String> items = new HashMap<>();
		
		//open
		Path file = Paths.get("itemfile.txt");
		FileChannel fc;
		if(Files.exists(file)) {
			// read existing files to items
			BufferedReader reader = new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(file))));
			String entry = reader.readLine();
			while(entry != null) {
				if(!entry.equals(DEFAULT_RECORD.replace("\n",""))) {
					String[] split = entry.split(",");
					int id = Integer.parseInt(split[0]);
					String description = split[1];
					items.put(id, description);
				}
				entry = reader.readLine();
			}
			
			fc = (FileChannel)Files.newByteChannel(file, WRITE);
		} else {
			fc = (FileChannel)Files.newByteChannel(file, WRITE, CREATE);
			createFile(fc);
		}
		
		//input
		boolean done = false;
		String input, desc;
		int id;
		Scanner in = new Scanner(System.in);
		while(!done) {
			System.out.print("Enter Item # or DONE to finish >> ");
			input = in.nextLine().trim().toUpperCase();
			if(!input.equals("DONE")) {
				id = Integer.parseInt(input);
				if(!items.containsKey(id)) {
					System.out.println("Enter description");
					System.out.print(" >> ");
					desc = in.nextLine();
					items.put(id, desc); // more efficient to keep separate ledgers for old and new entries
				} else {
					System.out.println("ID already exists");
				}
			} else {
				done = true;
			}
		}
		
		//write
		String entryStr;
		ByteBuffer out;
		for(Map.Entry<Integer,String> entry:items.entrySet()) {
			id = entry.getKey();
			desc = entry.getValue();
			fc.position(getPosition(id));
			entryStr = String.format("%3s", Integer.toString(id)).replace(" ", "0")+
					","+String.format("%-20s", desc).substring(0, 20)+"\n";
			out = ByteBuffer.wrap(entryStr.getBytes());
			while(out.hasRemaining())
				fc.write(out);
		}
		fc.close();

	}
	
	public static void createFile(FileChannel fc) throws IOException {
		ByteBuffer out = ByteBuffer.wrap(DEFAULT_RECORD.getBytes());
		for(int i = 0; i < NUMRECS; ++i) {
			while(out.hasRemaining())
				fc.write(out);
			out.rewind();
		}
	}
	
	public static long getPosition(int id) {
		return id*DEFAULT_RECORD.length();
	}
}
