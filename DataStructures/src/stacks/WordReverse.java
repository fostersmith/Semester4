package stacks;

import java.util.Scanner;

public class WordReverse {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String word = "";
		do {
			System.out.print("Enter a word to reverse, or QUIT to quit >> ");
			word = in.nextLine();
			Stack stack = new Stack();
			for(int i = 0; i < word.length(); ++i) {
				stack.push((Character)word.charAt(i));
			}
			Character c;
			while((c = (Character)stack.pop())!=null) {
				System.out.print(c);
			}
			System.out.println();
		} while(!word.equals("QUIT"));
	}
	
}
