package Controller;


import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UserReviewsController extends BasicController{
	@FXML
	ComboBox cmbSort;
	
	@FXML
	TableView tblReviews;
	
	@FXML
	public void initialize() {
		List<String> list = new ArrayList<String>();
		//Populate with items
		list.add("Hello");
		ObservableList<String> cmbItems = FXCollections.observableList(list);
		cmbSort.setItems(cmbItems);
	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/Welcome.fxml", "Welcome");
	}
}
