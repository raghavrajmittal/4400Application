package Controller;

import java.util.Optional;

import Model.City;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;

public class ManagerWelcomeController extends BasicController {

	@FXML
	private TextField txtAttraction;
	@FXML
	private ComboBox<City> cmbCity;
	@FXML
	private ComboBox<String> cmbCategory;
	
	@FXML
	public void initialize() {
		//Populate comboboxes
	}

	@FXML
	public void handleSearchAttractionsPressed() {
		String city = "";
		String attr = "";
		String categories = "";
		
		//Parse through txtCategory for each one and add to categories
		if (cmbCity.getValue() == null
				&& txtAttraction.getText().trim().equals("")
				&& cmbCategory.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please put a city, attraction, or category");
			alert.showAndWait();
		}
		
		//We need to send the sql query to the results page
		//If its category thats filled it needs to go to search results by category
		if (cmbCity.getValue() != null) {
			//First check if city is not empty
			city = cmbCity.getValue().getName();
			
			if (!txtAttraction.getText().trim().equals("")) {
				//Then check if attraction is not empty
				attr = txtAttraction.getText().trim();
				
				if (cmbCategory.getValue() != null) {
					//Then check if category is not empty					
					//Now we search for a city that has that attraction
					//With those categories
					
					//showScreen("../View/AttractionsList.fxml", "Search Results");
				} else {
					//If category is empty - Only get table elements
					//That have City city and Attraction attr
					
					//showScreen("../View/AttractionsList.fxml", "Search Results");
					
				}
			}else {
				
				//If attraction is empty still check if category is not empty
				if (cmbCategory.getValue() != null) {
					//Then check if category is not empty
					categories = cmbCategory.getValue();
					//Now we search for an attraction
					//With those categories regardless of city
					
					//showScreen("../View/AttractionsList.fxml", "Search Results");

					
				} else {
					//If category is empty - Only get table elements
					//That has City city
					
					//showScreen("../View/AttractionsList.fxml", "Search Results");

					
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
					
					//showScreen("../View/AttractionsList.fxml", "Search Results");

				} else {
					//If category is empty - Only get table elements
					//That has Attraction attr
					
					//showScreen("../View/AttractionsList.fxml", "Search Results");

					
				}
			}else {
				//If attraction is empty still check if category is not empty
				if (cmbCategory.getValue() != null) {
					//Then check if category is not empty
					categories = cmbCategory.getValue();
				} else {
					//If category is empty - Only get table elements
					//That have City city and Attraction attr
					//TBH this should not be empty
					
				}
			}
			
		}
		
		//showScreen("../View/AttractionsList.fxml", "Search Results");
	}

	
	@FXML
	public void handleViewCitiesPressed() {
		
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
		
	}
	
	@FXML
	public void handlePendingCitiesPressed() {
		
	}
	
	@FXML
	public void handlePendingAttractionsPressed() {
		
	}
	
	@FXML
	public void handleViewUsersPressed() {
		
	}
	
	@FXML
	public void handleAddUserPressed() {
		
	}
	@FXML
	public void handleAddCategoryPressed() {
		
	}
	
	@FXML
	public void handleViewCategoriesPressed() {
		
	}
}
