package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

import Model.Attraction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class BasicCityPageController extends BasicController {
	
	@FXML
	private Label lblCityName;
	@FXML
	private Label lblAvgRate;
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<Attraction> tblAttractions;
	@FXML
	private TableColumn<Attraction,String> colName;
	@FXML
	private TableColumn<Attraction,String> colLoc;
	@FXML
	private TableColumn<Attraction,Category> colCat;
	@FXML
	private TableColumn<Attraction,Double> colAvgRate;
	@FXML
	private TableColumn<Attraction,Integer> colNumRate;
	@FXML
	private TableColumn<Attraction,String> colMoreInfo;
	
	
	@FXML
	public void initialize() {
		//Populate table
		
		//Populate combobox
		List<String> list = new ArrayList<>();
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Category A-Z");
		list.add("Avg Rating");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
		
	}
	
	@FXML
	public void handleBackPressed() {
		//Use the previous query to go back to the table
		//That this city was found in
	}
	
	@FXML
	public void handleReviewPressed() {
		showScreen("../View/NewCityReview.fxml", "New City Review");
	}
	
	@FXML
	public void handleViewPressed() {
		showScreen("../View/CityReviews.fxml", "City Reviews");
	}
	
	public void handleSortPressed() {
		if (cmbSort.getValue() != null) {
			if (cmbSort.getValue().equals("Name A-Z")) {
				
			} else if (cmbSort.getValue().equals("Name Z-A")) {
				
			} else if (cmbSort.getValue().equals("Category A-Z")) {
				
			} else if (cmbSort.getValue().equals("Avg Rating")) {
				
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort type");
			alert.showAndWait();
		}
	}
}
