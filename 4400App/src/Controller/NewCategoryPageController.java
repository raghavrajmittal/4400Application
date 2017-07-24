package Controller;

import Database.DBModel;
import Model.Category;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NewCategoryPageController extends BasicController {
	
	@FXML
	private TextField txtCategory;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void handleCreatePressed() {
		if (!txtCategory.getText().equals("")) {
			//If category doesnt already exist
			try {
				Connection con = DBModel.getInstance().getConnection();
				String query = "INSERT INTO CATEGORY\n" +
						"VALUES (?);";
				PreparedStatement stmnt = con.prepareStatement(query);
				stmnt.setString(1, txtCategory.getText());
				stmnt.execute();
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Success");
				alert.setContentText("Category has been added");
				alert.showAndWait();
				showScreen("../View/CategoriesPage.fxml", "Category page");
			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("This category could not be added ;-;");
				alert.showAndWait();
				//e.printStackTrace();
			}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			//Add Category to the database

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
