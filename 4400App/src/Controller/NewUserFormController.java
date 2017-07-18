package Controller;

import Database.DBModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class NewUserFormController extends BasicController {
	@FXML
	private TextField emailField;
	@FXML
	private TextField pswdField;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void handleCreatePressed() {
		//Add user to the database
		//mainModel.addUser
		
		if ((emailField.getText() != null && emailField.getText().equals(""))
				|| (pswdField.getText() != null && pswdField.getText().equals(""))) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Enter a username and password");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Success");
			alert.setContentText("Congratulations, New User was created");
			alert.showAndWait();
			
			if (mainModel.getUser().getIsManager()) {
				showScreen("../View/ManagerWelcome.fxml", "Welcome");
			} else {
				showScreen("../View/Welcome.fxml", "Welcome");
			}
		}
	}
	
	@FXML
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome");
		} else {
			showScreen("../View/Welcome.fxml", "Welcome");
		}
	}
}
