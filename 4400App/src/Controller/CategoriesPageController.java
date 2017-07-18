package Controller;


import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Model.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CategoriesPageController extends BasicController {
	
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<Category> tblCategories;
	@FXML
	private TableColumn<Category, String> colName;
	@FXML
	private TableColumn<Category, Integer> colNum;
	@FXML
	private TableColumn colEdit;
	@FXML
	private TableColumn colDel;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		List<String> list = new ArrayList<>();
		list.add("A-Z");
		list.add("Z-A");
		list.add("#of Attractions");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
	}
	
	@FXML
	public void handleAddPressed() {
		showScreen("../View/NewCategoryPage.fxml", "New Category");
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
		if (cmbSort.getValue() != null) {
			if (cmbSort.getValue().equals("A-Z")) {
				
			} else if (cmbSort.getValue().equals("Z-A")) {
				
			} else if (cmbSort.getValue().equals("#of Attractions"));
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort choice");
			alert.showAndWait();
		}
	}
}
