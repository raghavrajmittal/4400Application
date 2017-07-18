package Controller;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXML;
import Database.DBModel;
import Model.User;

public class LoginController extends BasicController{
	
	/**Where the user enters their email */
	@FXML
	private TextField emailField;
	
	/**Where the user enters their pswrd */
	@FXML
	private PasswordField pswdField;
	
	//Instance of the database
	DBModel mainModel = DBModel.getInstance();
	
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
		
		if (flag) {
			if (emailField.getText().equals("m")) {
				//If manager
				mainModel.setCurrentUser(new User(emailField.getText()
						,pswdField.getText(),true));
				showScreen("../view/ManagerWelcome.fxml", "Welcome");
				
				//If username is not valid or password is incorrect
				//			Alert alert = new Alert(Alert.AlertType.ERROR);
				//			alert.setTitle("Error");
				//			alert.setContentText("Username or password is incorrect");
				//			alert.showAndWait();
			} else {
				mainModel.setCurrentUser(new User(emailField.getText()
						,pswdField.getText()));
				showScreen("../view/Welcome.fxml", "Welcome");
				//This is where the database stuff will happens
				//Authenticates the user and will login
				
				//If username is not valid or password is incorrect
	//			Alert alert = new Alert(Alert.AlertType.ERROR);
	//			alert.setTitle("Error");
	//			alert.setContentText("Username or password is incorrect");
	//			alert.showAndWait();
			}
		}
	}
	
	@FXML
	public final void handleRegisterPressed() {
		showScreen("../view/Register.fxml", "Register");
	}
}
