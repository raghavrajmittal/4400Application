package Controller;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import Database.DBModel;

public class LoginController extends BasicController{
	
	/**Where the user enters their email */
	@FXML
	private TextField emailField;
	
	/**Where the user enters their pswrd */
	@FXML
	private PasswordField pswdField;
	
	/**Checks the database and handles login */
	@FXML
	public final void handleLoginPressed() {
		String email = "";
		String pswd = "";
		
		boolean flag = true;
		
		if (null == emailField.getText() || emailField.getText().equals("")) {
			//Display dialog
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please enter a username");
			alert.showAndWait();
			
			flag = false;
		} else if (null == pswdField.getText() || pswdField.getText().equals("")) {
			//Display dialog
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please enter a password");
			alert.showAndWait();
			flag = false;
		} else  {
			pswd = pswdField.getText();
			email = emailField.getText();
		}
		
		
		//If User
		if (flag) {
			showScreen("../view/Welcome.fxml", "Welcome");
			//This is where the database stuff will happens
			//Authenticates the user and will login
			
			//If username is not valid or password is incorrect
//			Alert alert = new Alert(Alert.AlertType.ERROR);
//			alert.setTitle("Error");
//			alert.setContentText("Username or password is incorrect");
//			alert.showAndWait();
		}
		
		//If manager
		//showScreen("../view/ManagerWelcome.fxml", "Welcome");
	}
	
	@FXML
	public final void handleRegisterPressed() {
		showScreen("../view/Register.fxml", "Register");
	}
}
