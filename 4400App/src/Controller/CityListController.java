package Controller;

import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CityListController extends BasicController{
	
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<City> tblCities;
	@FXML
	private TableColumn<City,String> colName;
	@FXML
	private TableColumn<City,Double> colRate;
	@FXML
	private TableColumn<City,Integer> colNumRate;
	@FXML
	private TableColumn<City,Integer> colNumAttr;
	@FXML
	private TableColumn<City,String> colCityPage;

	DBModel mainModel = DBModel.getInstance();
	@FXML
	public void initialize() {
		//populate combo box
		List<String> list = new ArrayList<>();
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Avg Rating");
		list.add("#of Ratings");
		list.add("#of Attractions");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
	}
	
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome");
		} else {
			showScreen("../View/Welcome.fxml", "Welcome");
		}
	}
}
