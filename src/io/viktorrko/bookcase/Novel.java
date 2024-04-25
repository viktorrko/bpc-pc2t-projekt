package io.viktorrko.bookcase;

class Novel extends Book {
	/*private enum Genre {
		THRILLER,
		ROMANCE,
		SCIFI,
		FANTASY,
		ADVENTURE
	}*/
	private Genre genre;
	
	public Novel(String title, String[] author, short year, boolean available, String genre) {
		super(title, author, year, available);
		this.genre = Genre.valueOf(genre.toUpperCase());
	}

	public Enum<?> getParameter() {
		return genre;
	}
	
	public Genre getGenre() {
		return genre;
	}
	
	public String getGenreString() {
		return genre.toString().substring(0, 1).toUpperCase() + genre.toString().substring(1).toLowerCase();
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	@Override
	public String getDataCSV() {
		String data;
		
		data = String.join(";", "novel", getTitle(), String.join(",", getAuthors()), getGenreString(), Short.toString(getYear()), Boolean.toString(isAvailable()));
		
		return data;
	}
	
	//@Override
	public static boolean isValidParameter(String parameter) {
		for(Genre e : Genre.values())
			if(e.toString().toLowerCase().equals(parameter.toLowerCase()))
				return true;
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("%s | %s | %s | %d | %s", getTitle(), String.join(", ", getAuthors()), getGenreString(), getYear(), isAvailableString());
	}
}
