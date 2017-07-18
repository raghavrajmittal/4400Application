package Model;

public class User {
	
	private String email;
	private String password;
	private boolean isManager = false;
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
}
