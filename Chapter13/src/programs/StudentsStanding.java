/*
 * Foster Smith
 * 9/12/22
 * StudentsStanding.java
 * 8a
 */

package programs;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class StudentsStanding {
	final static int NUMRECS = 1000;
	final static String DEFAULT_STRING = "000,XXXXXXXXXXXXXXX,XXXXXXXXXXXXXXX,0.000\n";
	final static byte[] DEFAULT_RECORD = DEFAULT_STRING.getBytes();
	
	public static void main(String[] args) {
		Path goodFile = Paths.get("goodstanding.txt");
		Path badFile = Paths.get("badstanding.txt");
		FileChannel goodChannel, badChannel;
		
		try {
			//create if necessary
			if(Files.exists(goodFile)) {
				goodChannel = (FileChannel)Files.newByteChannel(goodFile, WRITE);
			} else {
				goodChannel = (FileChannel)Files.newByteChannel(goodFile, WRITE, CREATE);
				ByteBuffer out = ByteBuffer.wrap(DEFAULT_RECORD);
				for(int i = 0; i < NUMRECS; ++i) {
					while(out.hasRemaining()) {
						goodChannel.write(out);
					}
					out.rewind();
				}
			}
			
			if(Files.exists(badFile)) {
				badChannel = (FileChannel)Files.newByteChannel(badFile, WRITE);
			} else {
				badChannel = (FileChannel)Files.newByteChannel(badFile, WRITE, CREATE);
				ByteBuffer out = ByteBuffer.wrap(DEFAULT_RECORD);
				for(int i = 0; i < NUMRECS; ++i) {
					while(out.hasRemaining()) {
						badChannel.write(out);
					}
					out.rewind();
				}
			}
			
			
			
			//input data
			boolean done = false;
			Scanner in = new Scanner(System.in);
			String input = null;
			int studentid;
			String first, last;
			float gpa;
			ByteBuffer out;
			while(!done) {
				System.out.print("Enter Student ID, or QUIT to quit >> ");
				input = in.nextLine().trim().toUpperCase();
				if(input.equals("QUIT")) {
					done = true;
				} else {
					studentid = Integer.parseInt(input);
					System.out.print("("+studentid+") Enter First >> ");
					first = in.nextLine();
					System.out.print("("+studentid+") Enter Last >> ");
					last = in.nextLine();
					System.out.print("("+studentid+") Enter GPA >> ");
					gpa = Float.parseFloat(in.nextLine());
					String entry = String.format("%3s", Integer.toString(studentid)).replace(" ", "0")+","
							+ String.format("%-15s", first)+"," + String.format("%-15s", last)+","+
							Float.toString(gpa);
					System.out.println("writing '"+entry+"' to record");
					out = ByteBuffer.wrap(entry.getBytes());
					if(gpa < 2.0) {
						badChannel.position(recordPos(studentid));
						while(out.hasRemaining()) {
							badChannel.write(out);
						}
					} else {
						goodChannel.position(recordPos(studentid));
						while(out.hasRemaining()) {
							goodChannel.write(out);
						}
					}
				}
			}
			
			goodChannel.close();
			badChannel.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static long recordPos(int id) {
		return (DEFAULT_STRING.length())*id;
	}
	
}
