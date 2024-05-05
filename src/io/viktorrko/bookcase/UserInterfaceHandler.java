package io.viktorrko.bookcase;

public class UserInterfaceHandler {
	static int version = 3;
	private static BookList books = new BookList();
	
	private static String[] menuOptions = new String[] {
		"1  | Add new book",
		"2  | Edit existing book",
		"3  | Delete book",
		"4  | Change book availibility",
		"5  | Print all books",
		"6  | Search a book",
		"7  | Print books by author",
		"8  | Print novels by genre",
		"9  | Print all unavailable books",
		"10 | Save book info to a file",
		"11 | Load book from a file",
		"Q  | Quit",
		"",
	};
	
	private static String getActionTitle(String s) {
		return "\n[" + menuOptions[Integer.parseInt(s)-1].substring(5).toUpperCase() + "]\n";
	}

	
	public static void startUI() {
		System.out.println("---   BOOK DATABASE   ---");
		System.out.println("  Viktor Voscek 247199");
		System.out.println("  Version " + version);
		System.out.println();
		System.out.println("[MAIN MENU]");
		
		String input = "";
		KeyboardInput.openScanner();
		
		while (!input.equalsIgnoreCase("q")) {
			System.out.print(String.join("\n", menuOptions) + "\n");
			
			input = KeyboardInput.scanString("option");
	        
	        switch (input.toLowerCase()) {
	            case "1":
	            	System.out.println();
	                books.addBook();
	                System.out.println();
	                break;
	            case "2":
	            	System.out.println(getActionTitle(input));
	                books.editBook();
	                System.out.println();
	                break;
	            case "3":
	            	System.out.println(getActionTitle(input));
	                books.removeBook();
	                System.out.println();
	                break;
	            case "4":
	            	System.out.println(getActionTitle(input));
	                books.setBookAvailability();
	                System.out.println();
	                break;
	            case "5":
	            	System.out.println(getActionTitle(input));
	                books.printAllBooks();
	                System.out.println();
	                break;
	            case "6":
	            	System.out.println(getActionTitle(input));
	                books.searchBook();
	                System.out.println();
	                break;
	            case "7":
	            	System.out.println(getActionTitle(input));
	                books.printBooksByAuthor();
	                System.out.println();
	                break;
	            case "8":
	            	System.out.println(getActionTitle(input));
	                books.printBooksByGenre();
	                System.out.println();
	                break;
	            case "9":
	            	System.out.println(getActionTitle(input));
	                books.printBooksNotAvailable();
	                System.out.println();
	                break;
	            case "10":
	            	System.out.println(getActionTitle(input));
	                books.exportBookInfo();
	                System.out.println();
	                break;
	            case "11":
	            	System.out.println(getActionTitle(input));
	                books.importBookInfo();
	                System.out.println();
	                break;
	            case "q":
	            	System.out.println("[QUIT]");
	                quitProgramUI();
	                break;

	            default:
	                System.out.println("Invalid command.");
	        }
	    }
	}

	public static void loadFromDatabaseUI() {
		System.out.println("Loading data from SQL database...");
		System.out.println(String.format("Location: %s\\db\\bookcase.db", System.getProperty("user.dir")));
		books.loadFromDatabase();
		System.out.println("");
	}
	
	public static void quitProgramUI() {
		System.out.println("Saving data to SQL database...");
		System.out.println(String.format("Location: %s\\db\\bookcase.db", System.getProperty("user.dir")));
		books.saveToDatabase();
	}
}
