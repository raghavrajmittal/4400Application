package Model;

public class Attraction {
	
	private City city;
	private String address;
	private String description;
	private Category[] category;
	private String hoursOfOp;
	private String contact;
	private int attrID;
	private String name;
	private boolean isPending;

	//For the tables
	private String categoriesList;
	private int rating;
	private String comment;
	private String submittedBy;
	private String cityString;

	private int numRat = 0;
	private double avgRat = 0;

	public Attraction(String n, int id) {
		name = n;
		attrID = id;
	}

	public Attraction(String n, City c, int id) {
		this(n, id);
		city = c;
	}

	public Attraction(String n, City c, String a,
			String d, Category[] cat,  int id) {
		this(n, id);
		city = c;
		address = a;
		description = d;
		category = cat;
	}
	
	public Attraction(String n, City c, String a,
			String d, Category[] cat, int id, String h, String cont) {
		this(n,c,a,d,cat, id);
		hoursOfOp = h;
		contact = cont;
	}
	
	public int getAttractionID() {
		return attrID;
	}
	
	public City getCity() {
		return city;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Category[] getCategory() {
		return category;
	}
	
	public String getHoursOfOp() {
		return hoursOfOp;
	}
	
	public String getContact() {
		return contact;
	}
	
	public String getName() {
		return name;
	}

	public String toString() {
		return this.getName();
	}

	public void setCategoriesList(String s) {
		categoriesList = s;

	}

	public String getCategoriesList() {
		return categoriesList;
	}

	public void setIsPending(boolean b) {
		isPending = b;
	}

	public boolean getIsPending() {
		return isPending;
	}


	public int getNumRat() { return numRat; }

	public double getAvgRat() { return avgRat; }

	public void setNumRat(int n){
		numRat = n;
	}

	public void setAvgRat(double n){
		avgRat = n;
	}

	public void setCityString(String s) {
		cityString = s;
	}

	public String getCityString() {
		return cityString;
	}

	public void setRating(int n) {
		rating = n;
	}

	public int getRating() {
		return rating;
	}

	public void setSubmittedBy(String s) {
		submittedBy = s;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String s) {
		comment = s;
	}

	public void setHoursOfOp(String s) {
		hoursOfOp = s;
	}

	public void setContact(String s) {
		contact = s;
	}


}
