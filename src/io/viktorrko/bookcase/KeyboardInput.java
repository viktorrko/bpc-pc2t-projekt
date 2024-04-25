package io.viktorrko.bookcase;

import java.util.Scanner;

class KeyboardInput {
	public static String scanString(String parameter) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print(String.format("%s: ", parameter));
		
		return sc.nextLine();
	}
	
	public static short scanShort(String parameter) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print(String.format("%s: ", parameter));
		while (!sc.hasNextShort()) {
            // If the next token is not an integer, prompt the user to enter again
            System.out.print(String.format("Invalid input.\n%s : ", parameter));
            sc.next();
        }
		return sc.nextShort();
	}
	
	public static boolean scanBoolean(String parameter) {

		Scanner sc = new Scanner(System.in);
		
		System.out.print(String.format("%s: ", parameter));
		while (!sc.hasNextBoolean()) {
            // If the next token is not an integer, prompt the user to enter again
            System.out.print(String.format("Invalid input.\n%s : ", parameter));
            sc.next();
        }
		return sc.nextBoolean();
	}

	public static String[] trimStringArray(String[] s) {
		for (int i=0; i<s.length; i++) {
			s[i] = s[i].trim();
		}
		return s;
	}

	public static String scanGenre() {
		Scanner sc = new Scanner(System.in);
		String parameter;
		
		while(true) {
			parameter = scanString("Genre");
			if(Novel.isValidParameter(parameter))
				return parameter;
			else
				System.out.println("Invalid input.");
		}
	}
	
	public static String scanTargetGrade() {
		Scanner sc = new Scanner(System.in);
		String parameter;
		
		while(true) {
			parameter = KeyboardInput.scanString("Target grade");
			if(Textbook.isValidParameter(parameter))
				return parameter;
			else
				System.out.println("Invalid input.");
		}
	}
	
	public static boolean isBoolean(String s) {
        return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
    }
}
