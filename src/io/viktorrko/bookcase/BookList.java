package io.viktorrko.bookcase;

import java.io.File;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BookList {
	private List<Book> books = new ArrayList<Book>();
		
	public int bookExists(String title) {
		int i = 0;
		for (; i<books.size(); i++) {
			if(books.get(i).getTitle().equalsIgnoreCase(title)) {
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
	
	public boolean isBooksEmpty() {
		if (books.size() == 0)
		{
			System.out.println("There are no books in the database.");
			return true;
		}
		
		return false;
	}

	public void messageBookNotFound(String title) {
		System.out.println(String.format("Book '%s' not found.", title));
	}
	
	public void printBookList(List<Book> books) {
		for (Book e : books)
			System.out.println(e);
	}
	
	public void addBook(String type, String title, String[] authors, short year, boolean available, String parameter) {
		Book e;
		int i = bookExists(title);
		
		
		if(i == -1) {
			switch (type) {
				case "novel":
					e = new Novel(title, authors, year, available, parameter);
					books.add(e);
					break;
				
				case "textbook":
					e = new Textbook(title, authors, year, available, parameter);
					books.add(e);
					break;
			}
		}
		else {
			books.get(i).setAuthors(authors);
			books.get(i).setYear(year);
			books.get(i).setAvailable(available);
			books.get(i).setParameter(parameter);
		}
	}
	
	public void addBook() {
		while (true) {
			KeyboardInput.openScanner();
			System.out.println("(Novel | Texbook)");
			String type = KeyboardInput.scanString("Book type");
			
			if(Book.isValidBookType(type)) {
				String title = KeyboardInput.scanString("Title");
				
				String[] authors = KeyboardInput.scanString("Authors (separated by ',')").split(",");
				
				//System.out.print("Year: ");
				short year = KeyboardInput.scanShort("Year");
				
				//System.out.print("Available: ");
				boolean available = KeyboardInput.scanBoolean("Available (true/false)");
				
				String parameter = null;
				switch (type) {
					case "novel":
						System.out.println("(Thriller | Romance | Scifi | Fantasy | Adventure)");
						parameter = KeyboardInput.scanGenre();
						break;
					
					case "textbook":
						System.out.println("(First | Second | Third | Fourth)");
						parameter = KeyboardInput.scanTargetGrade();
						break;
				}
				
				addBook(type, title, authors, year, available, parameter);
			}
			else
				System.out.println("Invalid book type.");
			
			break;
		}
	}
	
	public void editBook() {
		if (!isBooksEmpty()) {
			String title = KeyboardInput.scanString("Title");
			int i = bookExists(title);
			if(i != -1) {
				System.out.println(String.format("[Editing  '%s']\n", books.get(i).getTitle()));
				
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
	}
	
	public void removeBook() {
		if(!isBooksEmpty()) {
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
	}
	
	public void setBookAvailability() {
		if(!isBooksEmpty()) {
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
	}
	
	public void printAllBooks() {
		if (!isBooksEmpty()) {
			List<Book> sortedBooks = books;
			Collections.sort(sortedBooks, (p1, p2) -> p1.getTitle().compareTo(p2.getTitle()));
			
			printBookList(sortedBooks);
		}
	}
	
	public void searchBook() {
		if(!isBooksEmpty()) {
			String search = KeyboardInput.scanString("Title (search)");
			
			List<Book> tempBooks = new ArrayList<Book>();
			for (Book e : books) {
				if (e.getTitle().toLowerCase().contains(search.toLowerCase())) {
					tempBooks.add(e);
				}
			}
			
			if (tempBooks.size() > 0)
				printBookList(tempBooks);
			else
				messageBookNotFound(search);
		}
	}

	public void printBooksByAuthor() {
		if(!isBooksEmpty()) {
			String author = KeyboardInput.scanString("Author");
			
			if (!isBooksEmpty() && authorExists(author))
			{
				List<Book> tempBooks = new ArrayList<Book>();
				for (Book e : books) {
					if (e.isValidAuthor(author)) {
						tempBooks.add(e);
					}
				}
				tempBooks.sort((p1, p2) -> Integer.compare(p1.getYear(), p2.getYear()));
				printBookList(tempBooks);
			}
			else {
				System.out.println("Author not found");
			}
		}
	}
	
	public void printBooksByGenre() {
		if (!isBooksEmpty()) {
			
			System.out.println("(Thriller | Romance | Scifi | Fantasy | Adventure)");
			String genre = KeyboardInput.scanGenre();
			
			List<Book> tempBooks = new ArrayList<Book>();
			for (Book e : books) {
				if (e instanceof Novel && ((Novel) e).getGenreString().equalsIgnoreCase(genre)) {
					tempBooks.add(e);
				}
			}
			
			if (tempBooks.isEmpty())
				System.out.println(String.format("No books with the genre %s exist.", genre));
			else
				for (Book e : tempBooks)
					System.out.println(e);
		}
	}

	public void printBooksNotAvailable() {
		
		if (!isBooksEmpty()) {
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
	}
	
	public void exportBookInfo() {
		if (!isBooksEmpty()) {
			String title = KeyboardInput.scanString("Title");
			int i = bookExists(title);
			
			if (i != -1) {
				File file = FileHandler.createFile(books.get(i).getTitle()+".txt");
				if(FileHandler.writeToFile(books.get(i).getDataCSV(), file))
					System.out.println("Book exported to:\n" + file);
				else
					System.out.println("Book failed to export.");
			}
			else
				messageBookNotFound(title);
		}
	}
	
	public void importBookInfo() {
		String fileName = KeyboardInput.scanString("File");
		
		if (Paths.get(fileName).toFile().exists()) {
			String data = FileHandler.readFromFile(Paths.get(fileName));
			
			if (Book.isValidCSV(data)) {
				if (bookExists(data.split(";")[1]) == -1) {
					addBook(data.split(";")[0], data.split(";")[1], data.split(";")[2].split(","), Short.parseShort(data.split(";")[4]), Boolean.parseBoolean(data.split(";")[5]), data.split(";")[3]);
					System.out.println(String.format("Loaded book:\n%s", books.get(bookExists(data.split(";")[1])).toString()));
				}
				else
					System.out.println("Book already exists.");
			}
			else
				System.out.println("File contains invalid data.");
		}
		else
			System.out.println("File not found.");
	}
	
	public void loadFromDatabase() {		
		if (SQLOperationHandler.isTableEmpty())
			System.out.println("Database is empty.");
		else {
			int i = 0;
			try (ResultSet rs = SQLOperationHandler.executeQueryStatement("SELECT * FROM Books")) {
				
				while(rs.next()) {
					addBook(rs.getString(1), rs.getString(2), rs.getString(3).split(","), Short.parseShort(rs.getString(4)), rs.getBoolean(5), rs.getString(6));
					i++;
				}
			}
			catch(SQLException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("Loaded " + i + " book(s) from SQL database.");
		}
	}
	
	public void saveToDatabase() {
		SQLOperationHandler.initializeTable();
		
		if(!isBooksEmpty()) {
			String sql;
			for (Book e : books) {
				if(e instanceof Novel)
					sql = "INSERT INTO books (\"type\", title, authors, \"year\", available, \"parameter\") " + 
							String.format("VALUES ('novel', '%s', '%s', %d, %s, '%s');", e.getTitle(), String.join(",", e.getAuthors()), e.getYear(), e.isAvailable(), e.getParameter());
				else
					sql = "INSERT INTO books (\"type\", title, authors, \"year\", available, \"parameter\") " + 
							String.format("VALUES ('textbook', '%s', '%s', %d, %s, '%s');", e.getTitle(), String.join(",", e.getAuthors()), e.getYear(), e.isAvailable(), e.getParameter());
				
				SQLOperationHandler.executeStatement(sql);
			}
		}
	}
	
	public void initTestDatabase() {
		addBook("novel", "Book1", "John".split(","), (short) 2000, true, "Romance");
		addBook("novel", "CBook2", "David".split(","), (short) 2000, true, "Adventure");
		addBook("textbook", "Aook3", "Zidan".split(","), (short) 2000, true, "First");
		addBook("textbook", "Dook4", "Peso,Seso".split(","), (short) 2010, true, "Fourth");
	}
}
