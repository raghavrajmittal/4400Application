package Model;

public class Attraction extends Review{
	
	private City city;
	private String address;
	private String description;
	private Category[] category;
	private String hoursOfOp;
	private String contact;
	
	public Attraction(String n, double r, String co, int id) {
		super(n, r, co, id);
	}
	
	public Attraction(String n, double r, City c, String a,
			String d, Category[] cat, String co, int id) {
		this(n, r, co, id);
		city = c;
		address = a;
		description = d;
		category = cat;
	}
	
	public Attraction(String n, double r, City c, String a,
			String d, Category[] cat, String co, int id, String h, String cont) {
		this(n,r,c,a,d,cat,co, id);
		hoursOfOp = h;
		contact = cont;
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
	
	public String toString() {
		return this.getName();
	}
}
