package Controller;

import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class AttractionsListController extends BasicController{
	
	@FXML
	private TableView tblAttractions;

	@FXML
	private ComboBox<String> cmbSort;
	
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		List<String> list = new ArrayList<String>();
		//Populate with items
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Rating:Best First");
		list.add("Rating:Best Last");
		list.add("Category A-Z");
		list.add("Category Z-A");
		ObservableList<String> cmbItems = FXCollections.observableList(list);
		cmbSort.setItems(cmbItems);
				
		//Do the initial population by A-Z sort
	}
	
	public void handleAddPressed() {
		showScreen("../View/NewAttractionForm.fxml","New Attraction");
	}
	
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome");
		} else {
			showScreen("../View/Welcome.fxml", "Welcome");
		}
	}
	
	public void handleSortPressed() {
		String content = cmbSort.getValue();
		//Use content in SQL query to sort the table.
		if (content != null) {
			if (content.equals("Name A-Z")) {
				
			} else if (content.equals("Name Z-A")){
				
			} else if (content.equals("Rating:Best First")) {
				
			} else if (content.equals("Rating:Best Last")) {
				
			} else if (content.equals("Category A-Z")) {
				
			} else if (content.equals("Category Z-A")) {
				
			}
		}
	}
}
