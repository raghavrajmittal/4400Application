package Controller;

import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class UsersListController extends BasicController{
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		//Populate the combobox
		List<String> list = new ArrayList<>();
		list.add("A-Z");
		list.add("Z-A");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
		
		
	}
	@FXML
	private ComboBox<String> cmbSort;
	
	@FXML
	public void handleAddPressed() {
		showScreen("../View/NewUserForm.fxml", "Add New User");
	}
	
	@FXML
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome");
		} else {
			showScreen("../View/Welcome.fxml", "Welcome");
		}
	}
	
	@FXML
	public void handleSortPressed() {
		//Sort the table and display it
	}
}
