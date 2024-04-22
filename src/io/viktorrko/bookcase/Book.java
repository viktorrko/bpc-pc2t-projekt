package io.viktorrko.bookcase;

import java.util.HashSet;
import java.util.Set;

abstract class Book {
	private String title;
	private String[] authors;
	private short year;
	private boolean available;
	private static enum BookTypes {
		NOVEL,
		TEXTBOOK;
	}
	private static BookTypes bookTypes;
	
	public Book(String title, String[] authors, short year, boolean available) {
		// TODO Auto-generated constructor stub
		this.title = title;
		this.authors = authors;
		this.year = year;
		this.available = available;	
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public short getYear() {
		return year;
	}

	public void setYear(short year) {
		this.year = year;
	}

	public boolean isAvailable() {
		return available;
	}
	
	public String isAvailableString() {
		if (available == true)
			return "Available";
		else
			return "Not available";
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
	
	public String getDataCSV() {
		return "";
	}
	
	public boolean isValidAuthor(String author) {
		for (String s : authors) {
			if (s.toLowerCase().equals(author.toLowerCase()))
				return true;
		}
		return false;
	}
	
	public static boolean isValidBookType(String type) {
		for(BookTypes e : BookTypes.values())
			if(e.toString().toLowerCase().equals(type.toLowerCase()))
				return true;
		
		return false;
	}
	
	
	
	// public abstract boolean isValidParameter(String parameter);
}
