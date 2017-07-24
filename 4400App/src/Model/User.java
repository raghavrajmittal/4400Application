package Model;

public class User {
	
	private String email;
	private String password;
	private boolean isManager = false;
	private boolean isSuspended = false;
	private String dateJoined;

	public User(String e, String p) {
		email = e;
		password = p;
	}
	
	public User(String e, String p, boolean m) {
		this(e, p);
		isManager = m;
		
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public boolean getIsManager() {
		return isManager;
	}

	public void toggleManager() {
		isManager = !isManager;
	}
	public void toggleSuspended() {
		isSuspended = !isSuspended;
	}

	public boolean getSuspended() {
		return isSuspended;
	}

	public void setDateJoined(String s) {
		dateJoined = s;
	}

	public void setIsSuspended(boolean b) {
		isSuspended = b;
	}

	public String getDateJoined() {
		return dateJoined;
	}
}
