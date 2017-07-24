package Model;

import Links.CityPageLink;

public class City {
	
	private String country;
	private String state;
	private String name;
	private int cityID;
	private boolean isPending;
	private String submittedBy;
	private String comment;
	private int rating;

	private int numAttr = 0;
	private int numRat = 0;
	private double avgRat = 0;
	private CityPageLink cityHyperLink;

	public City(String n, int id) {
		cityID = id;
		name = n;
	}
	public City(String n, int id, String c, String s) {
		this(n, id);
		country = c;
		state = s;
	}


	
	public String getCountry() {
		return country;
	}
	
	public String getState() {
		return state;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		return this.getName() + ", " + this.getCountry();
	}

	public int getCityID() {
		return cityID;
	}

	public int getNumAttr() { return numAttr; }

	public int getNumRat() { return numRat; }

	public double getAvgRat() { return avgRat; }

	public boolean getIsPending() { return isPending; }

	public void setIsPending(boolean b) {
		isPending = b;
	}
	public void setNumAttr(int n) {
		numAttr = n;
	}

	public void setNumRat(int n){
		numRat = n;
	}

	public void setAvgRat(double n){
		avgRat = n;
	}

	public void setSubmittedBy(String s) {
		submittedBy = s;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setComment(String s) {
		comment = s;
	}

	public void setRating(int x) {
		rating = x;
	}

	public String getComment() {
		return comment;
	}

	public int getRating() {
		return rating;
	}

	public CityPageLink getCityHyperLink() {
		return cityHyperLink;
	}

	public void setCityHyperLink(CityPageLink c) {
		cityHyperLink = c;
	}
}
