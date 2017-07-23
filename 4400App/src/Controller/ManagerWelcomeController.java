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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;

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
			String query = "SELECT * FROM CATEGORY";
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
			String query = "SELECT * FROM CITY as C, REVIEWABLE_ENTITY as E WHERE C.CityID = E.EntityID AND E.IsPending = FALSE;";
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
		Category categories;
		
		//Parse through txtCategory for each one and add to categories
		if (cmbCity.getValue() == null
				&& txtAttraction.getText().trim().equals("")
				&& cmbCategory.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please put a city, attraction, or category");
			alert.showAndWait();
			return;
		}
		
		//We need to send the sql query to the results page
		//If its category thats filled it needs to go to search results by category
		if (cmbCity.getValue() != null) {
			//First check if city is not empty
			city = cmbCity.getValue();
			
			if (!txtAttraction.getText().trim().equals("")) {
				//Then check if attraction is not empty
				attr = txtAttraction.getText().trim();
				
				if (cmbCategory.getValue() != null) {
					//Then check if category is not empty					
					//Now we search for a city that has that attraction
					//With those categories
					
					showScreen("../View/AttractionsList.fxml", "Search Results");
				} else {
					//If category is empty - Only get table elements
					//That have City city and Attraction attr
					
					showScreen("../View/AttractionsList.fxml", "Search Results");
					
				}
			}else {
				
				//If attraction is empty still check if category is not empty
				if (cmbCategory.getValue() != null) {
					//Then check if category is not empty
					categories = cmbCategory.getValue();
					//Now we search for an attraction
					//With those categories regardless of city
					
					showScreen("../View/AttractionsList.fxml", "Search Results");

					
				} else {
					//If category is empty - Only get table elements
					//That has City city
					
					showScreen("../View/AttractionsList.fxml", "Search Results");

					
				}
			}
	
		} else {
			//If city is empty Then we check attraction
			if (!txtAttraction.getText().trim().equals("")) {
				//Then check if attraction is not empty
				attr = txtAttraction.getText().trim();
				
				if (cmbCategory.getValue() != null) {
					//Then check if category is not empty
					categories = cmbCategory.getValue();
					//Will have attraction and category sql thing
					
					showScreen("../View/AttractionsList.fxml", "Search Results");

				} else {
					//If category is empty - Only get table elements
					//That has Attraction attr
					
					showScreen("../View/AttractionsList.fxml", "Search Results");

					
				}
			}else {
				//If attraction is empty still check if category is not empty
				if (cmbCategory.getValue() != null) {
					//Then check if category is not empty
					categories = cmbCategory.getValue();
					showScreen("../View/AttractionsList.fxml", "Search Results");

				} else {
					//If category is empty - Only get table elements
					//That have City city and Attraction attr
					//TBH this should not be empty
					
					showScreen("../View/AttractionsList.fxml", "Search Results");

					
				}
			}
			
		}
		
		//showScreen("../View/AttractionsList.fxml", "Search Results");
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
		showScreen("../View/UserReviews.fxml","Your Reviews");
	}
	
	@FXML
	public void handleLogoutPressed() {
		showScreen("../view/Login.fxml", "Login");
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
			showScreen("../view/Login.fxml", "Login");
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

			showScreen("../View/UsersList.fxml", "User List");
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
		showScreen("../View/NewCategoryPage.fxml", "New Category");
	}
	
	@FXML
	public void handleViewCategoriesPressed() {
		showScreen("../View/CategoriesPage.fxml", "Categories Page");

	}
}
