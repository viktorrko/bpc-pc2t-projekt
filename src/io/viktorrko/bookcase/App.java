package io.viktorrko.bookcase;

public class App {
	private static BookList books = new BookList();
	
	public static void main(String[] args) {
		// :3
		books.initTestDatabase();
		
		books.printAllBooks();
		
		//books.exportBookInfo();
		//books.removeBook();
		
		//books.printAllBooks();
	}
}
