package Controller;

import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PendingCitiesListController extends BasicController {
	
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<City> tblPendingCities;
	@FXML
	private TableColumn<City,String> colName;
	@FXML
	private TableColumn<City,String> colCountry;
	@FXML
	private TableColumn<City,String> colSubmittedBy;
	@FXML
	private TableColumn<City,Double> colRating;
	@FXML
	private TableColumn<City,String> colComment;
	@FXML
	private TableColumn colApprove;
	@FXML
	private TableColumn colDelete;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		List<String> list = new ArrayList<>();
		//Populate Sort
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Rating");
		list.add("Country A-Z");
		list.add("Country Z-A");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
	}
	
	@FXML
	public void handleSortPressed() {
		if (cmbSort.getValue() != null) {
			if (cmbSort.getValue().equals("Name A-Z")) {
				
			} else if (cmbSort.getValue().equals("Name Z-A")) {
				
			} else if (cmbSort.getValue().equals("Rating")) {
				
			} else if (cmbSort.getValue().equals("Country A-Z")) {
				
			} else if (cmbSort.getValue().equals("Country Z-A")) {
				
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort choice");
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
