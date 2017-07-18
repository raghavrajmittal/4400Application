package Model;

public class Review {

	private String name;
	private double rating;
	public Review(String n, double r) {
		name = n;
		rating = r;
	}
	
	public String getName() {
		return name;
	}
	
	public double getAvgRating() {
		return rating;
	}
}
