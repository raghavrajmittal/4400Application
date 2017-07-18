package Database;

import Model.User;

public class DBModel {

	public static final DBModel mainModel = new DBModel();
	
	public static DBModel getInstance() {
		return mainModel;
	}
	
	private User currentUser;
	
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
}
