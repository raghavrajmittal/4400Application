package Controller;

import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Model.Attraction;
import Model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PendingAttractionsListController extends BasicController{
	
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<Attraction> tblAttractions;
	@FXML
	private TableColumn<Attraction,String> colName;
	@FXML
	private TableColumn<Attraction,City> colCity;
	@FXML
	private TableColumn<Attraction,String> colAddr;
	@FXML
	private TableColumn<Attraction,String> colCat;
	@FXML
	private TableColumn<Attraction,String> colDescription;
	@FXML
	private TableColumn<Attraction,String> colHours;
	@FXML
	private TableColumn<Attraction,String> colContact;
	@FXML
	private TableColumn<Attraction,String> colSubmittedBy;
	@FXML
	private TableColumn<Attraction,Double> colRating;
	@FXML
	private TableColumn<Attraction,String> colComment;
	@FXML
	private TableColumn colStatus;
	
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
