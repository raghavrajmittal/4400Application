package Model;

import Links.DeleteReviewLink;
import Links.EditReviewLink;

public class Review {

	private String name;
	private int rating;
	private String comment;
	private int entityID;
	private EditReviewLink editReviewHyperLink;
	private boolean isCity;

	private DeleteReviewLink deleteReviewLink;
	private String submittedBy;

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

	public EditReviewLink getEditReviewHyperLink() { return editReviewHyperLink; }

	public void setEditReviewHyperLink(EditReviewLink e) { editReviewHyperLink = e; }

	public boolean getIsCity() {return isCity;}

	public void setIsCity(boolean c) {isCity = c;}

	public void setDeleteReviewLink(DeleteReviewLink d) {
		deleteReviewLink = d;
	}

	public DeleteReviewLink getDeleteReviewLink() {
		return deleteReviewLink;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String s) {
		submittedBy = s;
	}
}
