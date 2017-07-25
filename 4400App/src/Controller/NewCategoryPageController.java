package Controller;

import Database.DBModel;
import Model.Category;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class NewCategoryPageController extends BasicController {
	
	@FXML
	private TextField txtCategory;

	@FXML
	private Button btnCreateCategory;

	@FXML
	private Label lblTitle;
	private boolean exists = false;
	DBModel mainModel = DBModel.getInstance();

	@FXML
	public void initialize() {

		if (mainModel.getCategory() != null) {
			try {
				Connection con = DBModel.getInstance().getConnection();
				String query = "SELECT CName\n" +
						"FROM CATEGORY\n" +
						"WHERE Cname = ?;";
				PreparedStatement stmnt = con.prepareStatement(query);
				stmnt.setString(1, mainModel.getCategory().getName());
				ResultSet resultSet = stmnt.executeQuery();
				while (resultSet.next()) {
					String cname = resultSet.getString("CName");
					exists = true;
					txtCategory.setText(cname);
					btnCreateCategory.setText("Update Category");
					lblTitle.setText("Update Category");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	@FXML
	public void handleCreatePressed() {
		if (!txtCategory.getText().equals("")) {
			//If category doesnt already exist
			try {
				if (exists) {
					Connection con = DBModel.getInstance().getConnection();
					String query = "UPDATE CATEGORY\n" +
							"SET CName = ?\n" +
							"WHERE CName = ?;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setString(1, txtCategory.getText());
					stmnt.setString(2, mainModel.getCategory().getName());
					stmnt.execute();
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Success");
					alert.setContentText("Category has been edited.");
					alert.showAndWait();
					showScreen("../View/CategoriesPage.fxml", "Category page");
				} else {
					Connection con = DBModel.getInstance().getConnection();
					String query = "INSERT INTO CATEGORY\n" +
							"VALUES (?);";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setString(1, txtCategory.getText());
					stmnt.execute();
					Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
					alert.setTitle("Success");
					alert.setContentText("Category has been added.");
					alert.showAndWait();
					showScreen("../View/CategoriesPage.fxml", "Category page");
				}

			} catch (Exception e) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("This category could not be added/edited ;-;");
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
