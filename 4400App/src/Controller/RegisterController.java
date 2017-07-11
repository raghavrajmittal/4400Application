package Controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
		} else if (null == rePswdField.getText() || rePswdField.getText().equals("")) {
			//Display dialog
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please reenter your password");
			alert.showAndWait();
			flag = false;
		} else  {
			passwordOne = pswdField.getText();
			username = emailField.getText();
			passwordTwo = rePswdField.getText();
		}
		
		if (flag) {
			if (!passwordOne.equals(passwordTwo)) {
				//Display Dialog box
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Passwords do not match");
				alert.showAndWait();
				
			} else {
				//if username/email is already taken
//				Alert alert = new Alert(Alert.AlertType.ERROR);
//				alert.setTitle("Error");
//				alert.setContentText("This username is already taken");
//				alert.showAndWait();
				showScreen("../view/Login.fxml", "Login");
			}
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
