package Model;

public class City extends Review{
	
	private String country;
	private String state;
	
	public City(String n, double r, String co, int id) {
		super(n, r, co, id);
	}
	
	public City (String n, double r, String co, int id, String c, String s) {
		this(n,r,co, id);
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
