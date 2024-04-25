package io.viktorrko.bookcase;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class BookList {
	//TODO experiment with overrides - namely parameters
	private List<Book> books = new ArrayList<Book>();
		
	public int bookExists(String title) {
		int i = 0;
		for (; i<books.size(); i++) {
			if(books.get(i).getTitle().equals(title)) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean authorExists(String author) {
		for (Book e : books) {
			if (e.isValidAuthor(author))
				return true;
		}
		return false;
	}

	public void messageBookNotFound(String title) {
		System.out.println(String.format("Book '%s' not found.", title));
	}
	
	public void addBook(String type, String title, String[] authors, short year, boolean available, String parameter) {
		Book e;
		switch (type) {
		
		// novel
		case "novel":
			e = new Novel(title, authors, year, available, parameter);
			books.add(e);
			break;
		
		// textbook
		case "textbook":
			e = new Textbook(title, authors, year, available, parameter);
			books.add(e);
			break;
		}
	}
	
	public void addBook() {
		while (true) {
			System.out.println("(Novel | Texbook)");
			String type = KeyboardInput.scanString("Book type");
			
			if(Book.isValidBookType(type)) {
				//System.out.print("Title: ");
				String title = KeyboardInput.scanString("Title");
				
				String[] authors = KeyboardInput.scanString("Authors (separated by ',')").split(",");
				
				//System.out.print("Year: ");
				short year = KeyboardInput.scanShort("Year");
				
				//System.out.print("Available: ");
				boolean available = KeyboardInput.scanBoolean("Available (true/false)");
				
				String parameter = null;
				switch (type) {
					case "novel":
						parameter = KeyboardInput.scanGenre();
						break;
					
					case "textbook":
						parameter = KeyboardInput.scanTargetGrade();
						break;
				}
				
				addBook(type, title, authors, year, available, parameter);
			}
			else
				System.out.println("Invalid book type."); break;
		}
	}
	
	public void editBook() {
		System.out.println("[2. EDIT BOOK]");
		String title = KeyboardInput.scanString("Title");
		
		int i = bookExists(title);
		if(i != -1) {
			System.out.println(String.format("[Editing book %s]", books.get(i).getTitle()));
			
			// edit author
			System.out.println(String.join(", ", books.get(i).getAuthors()));
			String[] author = KeyboardInput.scanString("Authors").split(",");
			books.get(i).setAuthors(KeyboardInput.trimStringArray(author));
			
			// edit year
			System.out.println(books.get(i).getYear());
			books.get(i).setYear(KeyboardInput.scanShort("Year"));
			
			// edit availability
			System.out.println(books.get(i).isAvailable());
			books.get(i).setAvailable(KeyboardInput.scanBoolean("Availability"));
			
			// print the edited book
			System.out.println();
			System.out.println("Edited book:");
			System.out.println(books.get(i));
		}
		else {
			messageBookNotFound(title);
		}
	}
	
	public void removeBook() {
		String title = KeyboardInput.scanString("Title");
		int i = bookExists(title);
		
		if (i!=-1) {
			books.remove(i);
			System.out.println(String.format("Book '%s' removed.", title));
		}
		else {
			messageBookNotFound(title);
		}
	}
	
	public void setBookAvailability() {
		String title = KeyboardInput.scanString("Title");
		int i = bookExists(title);
		
		if (i !=-1) {
			books.get(i).setAvailable(KeyboardInput.scanBoolean("Available"));
			
			System.out.println("Edited book:");
			System.out.println(books.get(i));
		}
		else 
			messageBookNotFound(title);
	}
	
	public void printAllBooks() {
		for (Book e : books) {
			System.out.println(e);
		}
	}
	
	public void searchBook() {
		String title = KeyboardInput.scanString("Title");
		int i = bookExists(title);
		
		if (i != -1)
			System.out.println(books.get(i));
		else
			messageBookNotFound(title);
	}

	public void printBooksByAuthor() {
		String author = KeyboardInput.scanString("Author");
		
		if(authorExists(author)) {
			List<Book> tempBooks = new ArrayList<Book>();
			for (Book e : books) {
				if (e.isValidAuthor(author)) {
					tempBooks.add(e);
				}
			}
			//TODO sort algorithm
			
			for (Book e : tempBooks)
				System.out.println(e);
		}
		else {
			System.out.println("Author not found");
		}
	}
	
	public void printBooksByGenre() {
		// TODO test
		String genre = KeyboardInput.scanGenre();
		
		List<Book> tempBooks = new ArrayList<Book>();
		for (Book e : books) {
			if (e instanceof Novel && ((Novel) e).getGenreString().toLowerCase().equals(genre.toLowerCase())) {
				tempBooks.add(e);
			}
		}
		
		if (tempBooks.isEmpty())
			System.out.println(String.format("No books with the genre %s exist.", genre));
		else
			for (Book e : tempBooks)
				System.out.println(e);
	}

	// i.1)
	public void printBooksNotAvailable() {
		//TODO edit book toString function to show book type (Novel/Textbook)
		List<Book> tempBooks = new ArrayList<Book>();
		
		for (Book e : books)
			if (!e.isAvailable())
				tempBooks.add(e);
		
		if (tempBooks.isEmpty())
			System.out.println("All books are available.");
		else
			for (Book e : tempBooks)
				System.out.println(e);
	}
	
	public void exportBookInfo() {
		String title = KeyboardInput.scanString("Title");
		String data = "";
		int i = bookExists(title);
		
		if (i != -1) {
			// FileHandler.writeToFile(, books.get(i).getTitle()+".txt");
			File file = FileHandler.createFile(books.get(i).getTitle()+".txt");
			if(FileHandler.writeToFile(books.get(i).getDataCSV(), file))
				System.out.println("Book exported to:\n" + file);
			else
				System.out.println("Book failed to export.");
		}
		else
			messageBookNotFound(title);
		
		// System.out.println(data);
	}
	
	public void importBookInfo() {
		// TODO check if this works
		String fileName = KeyboardInput.scanString("File");
		
		if (Paths.get(fileName).toFile().exists()) {
			String data = FileHandler.readFromFile(Paths.get(fileName));
			
			if (Book.isValidCSV(data)) {
				addBook(data.split(";")[0], data.split(";")[1], data.split(";")[2].split(","), Short.parseShort(data.split(";")[3]), Boolean.parseBoolean(data.split(";")[4]), data.split(";")[5]);
			}
		}
	}
	
	public void initTestDatabase() {
		addBook("novel", "Book1", "John".split(","), (short) 2000, true, "Romance");
		addBook("novel", "Book2", "David".split(","), (short) 2000, true, "Adventure");
		addBook("textbook", "Book3", "Zidan".split(","), (short) 2000, true, "First");
		addBook("textbook", "Book4", "Peso,Seso".split(","), (short) 2010, true, "Fourth");
	}
}
