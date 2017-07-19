package Model;

public class Review {

	private String name;
	private double rating;
	private String comment;
	public Review(String n, double r, String c) {
		name = n;
		rating = r;
		comment = c;
	}
	
	public String getName() {
		return name;
	}
	
	public double getAvgRating() {
		return rating;
	}
	
	public String getComment() {
		return comment;
	}
}
