package Model;

public class Review {

	private String name;
	private int rating;
	private String comment;
	private int entityID;

	public Review (String n, int id) {
		name = n;
		entityID = id;
	}

	public Review(String n, int r, String c, int id) {
		this(n, id);
		rating = r;
		comment = c;
	}
	
	public String getName() {
		return name;
	}
	
	public int getRating() {
		return rating;
	}
	
	public String getComment() {
		return comment;
	}
	
	public int getEntityID() {
		return entityID;
	}
}
