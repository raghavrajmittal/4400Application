package Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Locale.Category;

import Database.DBModel;
import Model.Attraction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar.ButtonData;

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
	
	DBModel mainModel = DBModel.getInstance();
	
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
		if (mainModel.getUser().isManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome " + mainModel.getUser().getEmail())
		} else {
			showScreen("../View/Welcome.fxml", "Welcome " + mainModel.getUser().getEmail())

		}
	}
	
	@FXML
	public void handleReviewPressed() {
		showScreen("../View/NewCityReview.fxml", "New" + mainModel.getCity().toString() + "Review");
	}
	
	@FXML
	public void handleViewPressed() {
		showScreen("../View/CityReviews.fxml", mainModel.getCity().toString() + "'s Reviews");
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
	
	@FXML
	public void handleDeletePressed() {
		//Delete from database
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("WARNING!");
		alert.setContentText("Are you sure you want to delete this City?");
		
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType delete = new ButtonType("Delete");

		alert.getButtonTypes().setAll(cancel, delete);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == delete) {
			if (mainModel.getUser().getIsManager()) {
				showScreen("../View/ManagerWelcome.fxml", "Welcome");
			} else {
				showScreen("../View/Welcome.fxml", "Welcome");
			}
		} else{
			alert.close();
		}
	}
}
