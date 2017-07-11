package Controller;

import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import application.Main;
import Database.DBModel;

public class LoginController extends BasicController{
	
	/**Where the user enters their email */
	private TextField emailField;
	
	/**Where the user enters their pswrd */
	private PasswordField pswdField;
	
	/**Checks the database and handles login */
	@FXML
	public final void handleLoginPressed() {
		String email = emailField.getText();
		String pswd = pswdField.getText();
		
		//This is where the database stuff will happens
		//Authenticates the user and will login
		
		//If User
		showScreen("../View/Welcome.fxml", "Welcome");
	}
}
