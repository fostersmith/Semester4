package programs;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CompareFolders {
	
	public static void main(String[] args) {
		
		Path a = Paths.get("testdataa\\fila.txt"),
				b = Paths.get("testdatab\\nestedfolder\\filb.txt"),
				c = Paths.get("testdataa\\filc.txt");
		
		a = a.toAbsolutePath();
		b = b.toAbsolutePath();
		c = c.toAbsolutePath();
		
		String aname = a.getFileName().toString();
		String bname = b.getFileName().toString();
		String cname = c.getFileName().toString();
		
		if(compare(a,c)) {
			System.out.println(aname+" and "+cname+" are in the same folder");
		} else {
			System.out.println(aname+" and "+cname+" are in different folders");
		}
		
		if(compare(b,c)) {
			System.out.println(bname+" and "+cname+" are in the same folder");
		} else {
			System.out.println(bname+" and "+cname+" are in different folders");
		}
	}
	
	/**
	 * returns if the paths belong to the same folder
	 * a and b MUST be absolute
	 * @param a
	 * @param b
	 * @return
	 */
	public static boolean compare(Path a, Path b) {
		if(a.getNameCount() == b.getNameCount()) {
			for(int i = 0; i < a.getNameCount()-1; ++i) {
				if(!a.getName(i).equals(b.getName(i))) {
					return false;
				}
			}
			return true;
		} else { 
			return false;
		}		
	}
	
}
