package Controller;

import Database.DBModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class NewCategoryPageController extends BasicController {
	
	@FXML
	private TextField txtCategory;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void handleCreatePressed() {
		if (!txtCategory.getText().equals("")) {
			//If category doesnt already exist

			//Add Category to the database
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Success");
			alert.setContentText("Category has been added");
			alert.showAndWait();
			if (mainModel.getUser().getIsManager()) {
				showScreen("../View/ManagerWelcome.fxml", "Welcome");
			} else {
				showScreen("../View/Welcome.fxml", "Welcome");
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please name your category");
			alert.showAndWait();
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
