package io.viktorrko.bookcase;

class Textbook extends Book {
	private enum TargetGrade {
		FIRST,
		SECOND,
		THIRD,
		FOURTH
	}
	private TargetGrade targetGrade;
	
	public Textbook(String title, String[] author, short year, boolean available, String targetGrade) {
		super(title, author, year, available);
		this.targetGrade = TargetGrade.valueOf(targetGrade.toUpperCase());
	}

	public Enum<?> getParameter() {
		return targetGrade;
	}
	
	public TargetGrade getTargetGrade() {
		return targetGrade;
	}
	
	public String getTargetGradeString() {
		return targetGrade.toString().substring(0, 1).toUpperCase() + targetGrade.toString().substring(1).toLowerCase() + " grade";
	}

	public void setTargetGrade(TargetGrade targetGrade) {
		this.targetGrade = targetGrade;
	}
	
	@Override
	public String getDataCSV() {
		String data;
		
		data = String.join(";", "textbook", getTitle(), String.join(",", getAuthors()), getTargetGradeString(), Short.toString(getYear()), Boolean.toString(isAvailable()));
		
		return data;
	}
	
	public boolean isValidTargetGrade(String genre) {
		for(TargetGrade e : TargetGrade.values())
			if(e.toString().toLowerCase().equals(genre.toLowerCase()))
				return true;
		return false;
	}
	
	public static boolean isValidParameter(String parameter) {
		for(TargetGrade e : TargetGrade.values())
			if(e.toString().toLowerCase().equals(parameter.toLowerCase()))
				return true;
		return false;
	}
	
	@Override
	public String toString() {
		return String.format("%s | %s | %s | %d | %s", getTitle(), String.join(", ", getAuthors()), getTargetGradeString(), getYear(), isAvailableString());
	}
}
