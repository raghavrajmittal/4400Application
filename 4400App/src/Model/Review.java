package Model;

public class Review {

	private String name;
	private double rating;
	private String comment;
	private int entityID;
	
	public Review(String n, double r, String c, int id) {
		name = n;
		rating = r;
		comment = c;
		entityID = id;
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
	
	public int getEntityID() {
		return entityID;
	}
}
