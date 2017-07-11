package Controller;

import java.util.Locale.Category;

import Model.City;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
	
	
	
	@FXML
	public final void handleSearchPressed() {
		
	}
	
	@FXML
	public final void handleViewCitiesPressed() {
		
	}
	
	@FXML
	public final void handleViewAttractionsPressed() {
		
	}
	
	@FXML
	public final void handleMyReviewsPressed() {
		
	}
	
	@FXML
	public final void handleLogoutPressed() {
		
	}
	
	@FXML
	public final void handleDeletePressed() {
		
	}
}
