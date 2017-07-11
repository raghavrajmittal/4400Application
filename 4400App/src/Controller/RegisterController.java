package Controller;


import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController extends BasicController{
	/**Here are the variables that the 
	 * user enters stuff into */
	@FXML
	private PasswordField pswdField;
	@FXML
	private PasswordField rePswdField;
	@FXML
	private TextField emailField;
	
	
	/**
	 * Create a new user for the database
	 */
	@FXML
	public final void handleCreatePressed() {
		//Here we add a user to the database
		String username = "";
		String passwordOne = "";
		String passwordTwo = "";
		
		if (null == emailField.getText()) {
			//Display dialog
		} else  {
			username = emailField.getText();
		}
		
		if (null == pswdField.getText()) {
			//Display dialog
		} else {
			passwordOne = pswdField.getText();
		}
		
		if (null == rePswdField.getText()) {
			//Display dialog
		} else {
			passwordTwo = rePswdField.getText();
		}
		
		if (!passwordOne.equals(passwordTwo)) {
			//Display Dialog box
			
		} else {
			showScreen("../view/Login.fxml", "Login");
		}
		
	}
	
	/**
	 * Return to the login/home screen
	 */
	@FXML
	public final void handleBackPressed() {
		showScreen("../view/Login.fxml", "Login");
	}
}
