package io.viktorrko.bookcase;

enum Genre {
	THRILLER,
	ROMANCE,
	SCIFI,
	FANTASY,
	ADVENTURE
}

enum TargetGrade {
	FIRST,
	SECOND,
	THIRD,
	FOURTH
}

abstract class Book {
	private String title;
	private String[] authors;
	private short year;
	private boolean available;
	private static enum BookTypes {
		NOVEL,
		TEXTBOOK;
	}
	
	public Book(String title, String[] authors, short year, boolean available) {
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
	
	public abstract Enum<?> getParameter();
	
	public abstract void setParameter(String s);
	
	//TODO reorder value order for everything
	public String getDataCSV() {
		return null;
	}
	
	public static boolean isValidCSV(String data) {
		String[] splitData = data.split(";");
		
		if(splitData.length != 6) {
			return false;
		}
		
		if(!isValidBookType(splitData[0])) {
			return false;
		}
		
		
		switch(splitData[0].toLowerCase()) {
		case "novel":
			if(!Novel.isValidParameter(splitData[3])) {
				return false;
			}
			break;
		case "textbook":
			if(!Textbook.isValidParameter(splitData[3])) {
				return false;
			}
			break;
		}
		
		if(!KeyboardInput.isBoolean(splitData[5])) {
			return false;
		}
		
		return true;
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
	
	public static boolean isValidParameter(String parameter) {
		return false;
	}
	
	/*@Override
    public int compareTo(Book other) {
        return String.compare(this.title, other.title);
    }*/
}
