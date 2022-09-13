/*
 * Foster Smith
 * 9/12/22
 * StudentsStanding2.java
 * 8b
 */

package programs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StudentsStanding2 {
	
	public static final String DEFAULT_STRING = StudentsStanding.DEFAULT_STRING.replace("\n","");
	
	public static void main(String[] args) {
		
		Path goodPath = Paths.get("goodstanding.txt");
		Path badPath = Paths.get("badstanding.txt");
		try {
			InputStream goodStream = new BufferedInputStream(Files.newInputStream(goodPath));
			InputStream badStream = new BufferedInputStream(Files.newInputStream(badPath));
			BufferedReader goodReader = new BufferedReader(new InputStreamReader(goodStream));
			BufferedReader badReader = new BufferedReader(new InputStreamReader(badStream));
			
			System.out.println("-----Good Standing-----");
			String read = goodReader.readLine();
			String[] array;
			int studentid;
			String first, last;
			float gpa;
			while(read != null) {
				if(!read.equals(DEFAULT_STRING)) {
					array = read.split(",");
					studentid = Integer.parseInt(array[0]);
					first = array[1].trim();
					last = array[2].trim();
					gpa = Float.parseFloat(array[3]);
					System.out.println("ID: "+studentid+", First: "+first+", Last: "+last+", GPA: "+gpa);
					System.out.println("GPA is "+(gpa-2f)+" above the good standing cutoff");
				}
				read = goodReader.readLine();
			}
			System.out.println("-----Bad Standing-----");
			read = badReader.readLine();
			while(read != null) {
				if(!read.equals(DEFAULT_STRING)) {
					array = read.split(",");
					studentid = Integer.parseInt(array[0]);
					first = array[1].trim();
					last = array[2].trim();
					gpa = Float.parseFloat(array[3]);
					System.out.println("ID: "+studentid+", First: "+first+", Last: "+last+", GPA: "+gpa);
					System.out.println((2f-gpa)+" points are needed to reach the good standing cutoff");
				}
				read = badReader.readLine();				
			}
			
			goodReader.close();
			badReader.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
