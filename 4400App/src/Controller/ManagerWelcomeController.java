package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Database.DBModel;
import Model.Category;
import Model.City;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;

import javax.xml.transform.Result;

public class ManagerWelcomeController extends BasicController {

	@FXML
	private TextField txtAttraction;
	@FXML
	private ComboBox<City> cmbCity;
	@FXML
	private ComboBox<Category> cmbCategory;
	@FXML
	private TextField txtUserSearch;
	@FXML
	private Label lblName;
	
	DBModel mainModel = DBModel.getInstance();
	@FXML
	public void initialize() {
		//Set name of screen
		lblName.setText(mainModel.getUser().getEmail());
		//Populate comboboxes
		
		List<Category> catList = new ArrayList<>();
		//Populate catList with our categories
		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT * FROM CATEGORY order by Cname ASC";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("CName");
				Category c = new Category(name);
				catList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<City> cityList = new ArrayList<>();
		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT * FROM CITY as C, REVIEWABLE_ENTITY as E WHERE C.CityID = E.EntityID AND E.IsPending = FALSE order by name ASC;";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("Name");
				String country = resultSet.getString("Country");
				String state = resultSet.getString("State");
				int cityID = resultSet.getInt("CityID");
				City c = new City(name, cityID, country, state);
				cityList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList<Category> cmbCats = FXCollections.observableList(catList);
		ObservableList<City> cmbCities = FXCollections.observableList(cityList);
		
		cmbCategory.setItems(cmbCats);
		cmbCity.setItems(cmbCities);
	}

	@FXML
	public void handleSearchAttractionsPressed() {
		City city;
		String attr = "";
		String cat = "";


		
		//Parse through txtCategory for each one and add to categories
		if (cmbCity.getValue() == null
				&& txtAttraction.getText().trim().equals("")
				&& cmbCategory.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please put a city, attraction, or category");
			alert.showAndWait();

		} else {


			if (cmbCity.getValue() == null) {
				city = new City("null City");
			} else {
				city = cmbCity.getValue();
			}
			if (txtAttraction.getText().trim().equals("")) {
				attr = "";
			} else {
				attr = txtAttraction.getText();
			}
			if (cmbCategory.getValue() != null) {
				cat = cmbCategory.getValue().getName();
			} else {
				cat = "";
			}

			mainModel.setFilter(city, attr, cat, true);

			showScreen("../View/AttractionsListFiltered.fxml", "Search Results");

		}
	}

	
	@FXML
	public void handleViewCitiesPressed() {
		showScreen("../View/CityList.fxml", "Cities List");
	}
	
	@FXML
	public void handleViewAttractionsPressed() {
		showScreen("../View/AttractionsList.fxml", "Attractions");
	}
	
	@FXML
	public void handleMyReviewsPressed() {
		showScreen("../View/UserReviews.fxml","Your Reviews" + mainModel.getUser().getEmail());
	}
	
	@FXML
	public void handleLogoutPressed() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setContentText("Are you sure you would like to log out?");

		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType okay = new ButtonType("Okay");

		alert.getButtonTypes().setAll(cancel, okay);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == okay) {
			showScreen("../view/Login.fxml", "Login");
		} else {
			alert.close();
		}
	}
	
	@FXML
	public void handleDeletePressed() {
		//Delete the account from the database
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("WARNING!");
		alert.setContentText("Deleting this account will delete all the data that this account made or is related to");
		
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType delete = new ButtonType("Delete");

		alert.getButtonTypes().setAll(cancel, delete);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == delete) {
			try {
				//If its last manager
				boolean last = false;
				Connection con = DBModel.getInstance().getConnection();
				String query = "SELECT COUNT(IsManager) as ManagerCount\n" +
						"FROM USER\n" +
						"where isManager = 1;";
				PreparedStatement stmnt = con.prepareStatement(query);
				ResultSet resultSet = stmnt.executeQuery();
				while (resultSet.next()){
					int count = resultSet.getInt("ManagerCount");
					if (count <= 1){
						last = true;
					}
				}

				if (last) {
					Alert manAlert = new Alert(Alert.AlertType.ERROR);
					manAlert.setContentText("You are the last manager alive, you cant be deleted");
					manAlert.showAndWait();
				} else {
					query = "DELETE FROM USER WHERE Email = ?;";
					stmnt = con.prepareStatement(query);
					stmnt.setString(1, mainModel.getUser().getEmail());
					stmnt.execute();
					showScreen("../view/Login.fxml", "Login");

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else{
			alert.close();
		}
	}
	
	@FXML
	public void handleSearchUsersPressed() {
		String user = "";
		if (txtUserSearch.getText().equals("")) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please enter a username");
			alert.showAndWait();
		} else {
			//Make a query with the user name
			user = txtUserSearch.getText();
			mainModel.setFilteredUser(user);
			showScreen("../View/UsersListFiltered.fxml", "Search Result");
		}
		
		
	}
	
	@FXML
	public void handlePendingCitiesPressed() {
		showScreen("../View/PendingCitiesList.fxml", "Pending Cities");
	}
	
	@FXML
	public void handlePendingAttractionsPressed() {
		showScreen("../View/PendingAttractionList.fxml", "Pending Attractions");
	}
	
	@FXML
	public void handleViewUsersPressed() {
		//Make Query Empty
		mainModel.setCurrentQuery("");
		showScreen("../View/UsersList.fxml", "User List");
	}
	
	@FXML
	public void handleAddUserPressed() {
		showScreen("../View/NewUserForm.fxml", "Add New User");
	}
	@FXML
	public void handleAddCategoryPressed() {
		mainModel.setCurrentCategory(null);
		showScreen("../View/NewCategoryPage.fxml", "New Category");
	}
	
	@FXML
	public void handleViewCategoriesPressed() {
		showScreen("../View/CategoriesPage.fxml", "Categories Page");

	}
}
