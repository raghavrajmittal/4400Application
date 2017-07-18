package Controller;


import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UserReviewsController extends BasicController{
	@FXML
	ComboBox<String> cmbSort;
	
	@FXML
	TableView tblReviews;
	
	//Instance of Database
	DBModel mainModel = DBModel.getInstance();
		
	@FXML
	public void initialize() {
		List<String> list = new ArrayList<String>();
		//Populate with items
		list.add("A-Z");
		list.add("Z-A");
		list.add("Rating:Best First");
		list.add("Rating:Best Last");
		ObservableList<String> cmbItems = FXCollections.observableList(list);
		cmbSort.setItems(cmbItems);
		
		//Do the initial population by A-Z sort
	}
	
	@FXML
	public void handleSortPressed() {
		String content = cmbSort.getPromptText();
		//Use content in SQL query to sort the table.
		if (content.equals("A-Z")) {
			
		} else if (content.equals("Z-A")){
			
		} else if (content.equals("Rating:Best First")) {
			
		} else if (content.equals("Rating:Best Last")) {
			
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
