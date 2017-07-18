package Model;

public class Attraction {
	
	private String name;
	private double avgRating;
	
	public Attraction(String n) {
		name = n;
	}
	
	public Attraction(String n, double r) {
		this(n);
		avgRating = r;
	}
}
