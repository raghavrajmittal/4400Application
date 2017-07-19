package Model;

public class City extends Review{
	
	private String country;
	private String state;
	
	public City(String n, double r, String co) {
		super(n, r, co);
	}
	
	public City (String n, double r, String co, String c, String s) {
		this(n,r,co);
		country = c;
		state = s;
	}
	
	public String getCountry() {
		return country;
	}
	
	public String getState() {
		return state;
	}
	
	public String toString() {
		return this.getName();
	}
}
