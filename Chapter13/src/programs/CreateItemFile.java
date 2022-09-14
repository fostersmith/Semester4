package programs;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CreateItemFile {
	
	static final String DEFAULT_RECORD = "000,XXXXXXXXXXXXXXXXXXXX\n";
	static final String DEFAULT_DESC = "XXXXXXXXXXXXXXXXXXXX";
	static final int NUMRECS = 1000;
	
	public static void main(String[] args) {
		try {
			//open
			Path file = Paths.get("itemfile.txt");
			FileChannel fc;
			if(Files.exists(file)) {
				fc = (FileChannel)Files.newByteChannel(file, WRITE, READ);
			} else {
				fc = (FileChannel)Files.newByteChannel(file, WRITE, READ, CREATE);
				createFile(fc);
			}
			
			//input
			boolean done = false;
			String input, desc;
			int id;
			Set<Integer> usedIDs = new HashSet<>();
			Scanner in = new Scanner(System.in);
			while(!done) {
				System.out.print("Enter Item # or DONE to finish >> ");
				input = in.nextLine().trim().toUpperCase();
				if(!input.equals("DONE")) {
					id = Integer.parseInt(input);
					if(!usedIDs.contains(id)) {
						if(getEntry(fc, id)[1].equals(DEFAULT_DESC)) {
							System.out.println("Enter description");
							System.out.print(" >> ");
							desc = in.nextLine();
							writeEntry(fc, id, desc);
							usedIDs.add(id);
						} else {
							System.out.println("ID already exists in file");
							usedIDs.add(id);
						}
					} else {
						System.out.println("ID already entered");
					}
				} else {
					done = true;
				}
			}
		
			fc.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
	
	public static String[] getEntry(FileChannel fc, int id) throws IOException {
		fc.position(getPosition(id));
		byte[] bytes = DEFAULT_RECORD.substring(0, DEFAULT_RECORD.length()-1).getBytes();
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		fc.read(buffer);
		return new String(bytes).split(",");
	}
	
	public static void writeEntry(FileChannel fc, int id, String desc) throws IOException {
		fc.position(getPosition(id));
		String entryStr = String.format("%3s", Integer.toString(id)).replace(" ", "0")+
				","+String.format("%-20s", desc).substring(0, 20)+"\n";
		ByteBuffer out = ByteBuffer.wrap(entryStr.getBytes());
		while(out.hasRemaining())
			fc.write(out);
	}
}
