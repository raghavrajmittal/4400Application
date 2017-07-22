package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Review;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CityReviewsController extends BasicController {

	@FXML
	private Label lblCityName;
	@FXML
	private TableView<Review> tblReviews;
	@FXML
	private TableColumn<Review,String> colName;
	@FXML
	private TableColumn<Review,Double> colRate;
	@FXML
	private TableColumn<Review,String> colComment;
	@FXML
	private ComboBox<String> cmbSort;
	
	@FXML
	public void initialize() {
		//Populate table
		
		//Populate combo box
		List<String> list = new ArrayList<>();
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Avg Rating");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
	}
	
	@FXML
	public void handleReviewPressed() {
		showScreen("../View/NewCityReview.fxml", "New City Review");
	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/BasicCityPage.fxml", "Basic City Page");
	}
	
	@FXML
	public void handleSortPressed() {
		if (cmbSort.getValue() != null) {
			if (cmbSort.getValue().equals("Name A-Z")) {
				
			} else if (cmbSort.getValue().equals("Name Z-A")) {
				
			} else if (cmbSort.getValue().equals("Avg Rating")) {
				
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort type");
			alert.showAndWait();
		}
	}
}
