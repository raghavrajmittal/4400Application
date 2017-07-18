package Model;

public class Attraction extends Review{
	
	private City city;
	private String address;
	private String description;
	private String comment;
	private Category[] category;
	private String hoursOfOp;
	private String contact;
	
	public Attraction(String n, double r) {
		super(n, r);
	}
	
	public Attraction(String n, double r, City c, String a,
			String d, Category[] cat, String co) {
		this(n, r);
		city = c;
		address = a;
		description = d;
		category = cat;
		comment = co;
	}
	
	public Attraction(String n, double r, City c, String a,
			String d, Category[] cat, String co, String h, String cont) {
		this(n,r,c,a,d,cat,co);
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
	
	public String getComment() {
		return comment;
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
}
