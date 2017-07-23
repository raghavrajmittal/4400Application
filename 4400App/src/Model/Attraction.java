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
	
	public Attraction(String n, int id) {
		name = n;
		attrID = id;
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
	
	public String getHours() {
		return hoursOfOp;
	}
	
	public String getContactInfo() {
		return contact;
	}
	
	public String getName() {
		return name;
	}

	public String toString() {
		return this.getName();
	}

	public void setIsPending(boolean b) {
		isPending = b;
	}

	public boolean getIsPending() {
		return isPending;
	}
}
