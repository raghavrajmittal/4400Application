package Database;

import Model.Attraction;
import Model.City;
import Model.User;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBModel {

	public static final DBModel mainModel = new DBModel();
	
	public static int entityID;

	private Connection con;
	
	public static DBModel getInstance() {
		return mainModel;
	}

	private DBModel() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection("jdbc:mysql://localhost:3306/4400DB?autoReconnect=true&useSSL=false",
					"admin", "password");
		} catch (Exception e) {
			this.con = null;
			e.printStackTrace();
		}
	}
	
	private User currentUser;
	private City currentCity;
	private Attraction currentAttraction;
	
	//Subject to Change
	private String currentQuery;

	public Connection getConnection() {
		return this.con;
	}

	
	public final boolean addUser(User u) {
		//If User already exists return false
		
		return true;
	}
	
	public final User getUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User u) {
		currentUser = u;
	}
	
	public final boolean authenticateUser(User u) {
		//Returns false if user is not authenticated on login
		
		return true;
	}
	
	public final String getCurrentQuery() {
		return currentQuery;
	}
	
	public void setCurrentQuery(String c) {
		currentQuery = c;
	}
	
	public final City getCity() {
		return currentCity;
	}
	
	public void setCurrentCity(City c) {
		currentCity = c;
	}
	
	public final Attraction getAttraction() {
		return currentAttraction;
	}
	
	public void setCurrentAttraction(Attraction a) {
		currentAttraction = a;
	}
	
	public static int makeEntityID() {
		return ++entityID;
	}
}
