package Model;

public class City {
	
	private String country;
	private String state;
	private String name;
	private int cityID;

	public City(String n, int id, String c, String s) {
		cityID = id;
		name = n;
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

}
