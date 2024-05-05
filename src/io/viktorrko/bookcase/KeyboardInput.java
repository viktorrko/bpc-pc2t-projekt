package io.viktorrko.bookcase;

import java.util.Scanner;

class KeyboardInput {
	private static Scanner sc;
	private static boolean scannerOpen = false;
	
	public static void openScanner() {
		if (!scannerOpen) {
			sc = new Scanner(System.in);
			scannerOpen = true;
		}
	}
	
	public static void closeScanner() {
		if (scannerOpen) {
			sc.close();
			scannerOpen = false;
		}
	}
	
	public static String scanString(String parameter) {
		while(true) {
			System.out.print(String.format("%s: ", parameter));
			String out = sc.nextLine();
			//sc.close();
			if(!out.equals(""))
				return out;
			System.out.println("Field can't be empty");
		}
	}
	
	public static short scanShort(String parameter) {
		System.out.print(String.format("%s: ", parameter));
		while (!sc.hasNextShort()) {
            System.out.print(String.format("Invalid input.\n%s: ", parameter));
            sc.next();
        }
		short out = sc.nextShort();
		sc.nextLine();
		return out;
	}
	
	public static boolean scanBoolean(String parameter) {
		System.out.print(String.format("%s: ", parameter));
		while (!sc.hasNextBoolean()) {
            System.out.print(String.format("Invalid input.\n%s : ", parameter));
            sc.next();
        }
		
		boolean out = sc.nextBoolean();
		sc.nextLine();
		return out;
	}

	public static String[] trimStringArray(String[] s) {
		for (int i=0; i<s.length; i++) {
			s[i] = s[i].trim();
		}
		return s;
	}
	
	public static String scanGenre() {
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
