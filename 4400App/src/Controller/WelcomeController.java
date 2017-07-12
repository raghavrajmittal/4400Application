package Controller;

import java.util.Locale.Category;
import java.util.Optional;

import Model.City;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;

public class WelcomeController extends BasicController{
	
	/**All the user input variables */
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtAttraction;
	@FXML
	private TextField txtCategory;
	@FXML
	private ComboBox<City> cmbCity;
	@FXML
	private ComboBox<Category> cmbCategory;
	
	/**
	 * This will filter results based on the text fields
	 * that the user enters
	 */
	@FXML
	public final void handleSearchPressed() {
		String city = "";
		String attr = "";
		String[] categories;
		
		//Parse through txtCategory for each one and add to categories
		if (txtCity.getText().trim().equals("")
				&& txtAttraction.getText().trim().equals("")
				&& txtCategory.getText().trim().equals("")) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please put a city, attraction, or category");
		}
		
		//We need to send the sql query to the results page
		//If its category thats filled it needs to go to search results by category
		if (!txtCity.getText().trim().equals("")) {
			//First check if city is not empty
			city = txtCity.getText().trim();
			
			if (!txtAttraction.getText().trim().equals("")) {
				//Then check if attraction is not empty
				attr = txtAttraction.getText().trim();
				
				if (!txtCategory.getText().trim().equals("")) {
					//Then check if category is not empty
					categories = txtCategory.getText().trim().split(",");
					
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
				if (!txtCategory.getText().trim().equals("")) {
					//Then check if category is not empty
					categories = txtCategory.getText().trim().split(",");
					
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
				
				if (!txtCategory.getText().trim().equals("")) {
					//Then check if category is not empty
					categories = txtCategory.getText().trim().split(",");
					
					//Will have attraction and category sql thing
					
					//showScreen("../View/AttractionsList.fxml", "Search Results");

				} else {
					//If category is empty - Only get table elements
					//That has Attraction attr
					
					//showScreen("../View/AttractionsList.fxml", "Search Results");

					
				}
			}else {
				//If attraction is empty still check if category is not empty
				if (!txtCategory.getText().trim().equals("")) {
					//Then check if category is not empty
					categories = txtCategory.getText().trim().split(",");
				} else {
					//If category is empty - Only get table elements
					//That have City city and Attraction attr
					//TBH this should not be empty
					
				}
			}
			
		}
		
		//showScreen("../View/AttractionsList.fxml", "Search Results");

	}
	
	/**
	 * Show the list of all cities
	 */
	@FXML
	public final void handleViewCitiesPressed() {
		//showScreen("../View/CityList.fxml", "Cities");
	}
	
	/**
	 * Shows unfiltered Attractions
	 */
	@FXML
	public final void handleViewAttractionsPressed() {
		//showScreen("../View/AttractionsList.fxml", "Attractions");
	}
	
	/**
	 * Shows the users reviews
	 */
	@FXML
	public final void handleMyReviewsPressed() {
		showScreen("../View/UserReviews.fxml","Your Reviews");
	}
	
	/**
	 * Returns to the home screen
	 */
	@FXML
	public final void handleLogoutPressed() {
		showScreen("../view/Login.fxml", "Login");
	}
	
	/**
	 * Shows the dialog before an account is deleted
	 * If delete - sends to login screen
	 * Else returns to main screen
	 */
	@FXML
	public final void handleDeletePressed() {
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
}
