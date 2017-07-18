package Controller;


import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewAttractionFormController extends BasicController {

	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtAddress;
	
	@FXML
	private TextField txtDescription;
	
	@FXML
	private TextField txtHours;
	
	@FXML
	private TextField txtContact;
	
	@FXML
	private TextArea txtComment;
	
	@FXML
	private ComboBox<String> cmbCity;
	
	@FXML
	private ComboBox<String> cmbCategory;
	
	@FXML
	private Label lblRating;
	
	@FXML
	private Slider sldRating;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		
		sldRating.valueProperty().addListener((obs,oldVal,newVal)->
				sldRating.setValue((newVal.intValue())));
		lblRating.textProperty().bind(Bindings.format("%.2f", sldRating.valueProperty()));
		
		List<String> catList = new ArrayList<>();
		//Populate catList with our categories
		catList.add("Hi");
		
		List<String> cityList = new ArrayList<>();
		//Populate cityList with our cities
		cityList.add("Hola");
		
		ObservableList<String> cmbCats = FXCollections.observableList(catList);
		ObservableList<String> cmbCities = FXCollections.observableList(cityList);
		
		cmbCategory.setItems(cmbCats);
		cmbCity.setItems(cmbCities);
	}
	
	@FXML
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome");
		} else {
			showScreen("../View/Welcome.fxml", "Welcome");
		}
	}
	
	@FXML
	public void handleSubmitPressed() {
		if (txtName.getText().equals("") || txtAddress.getText().equals("")
				|| txtDescription.getText().equals("")
				|| txtComment.getText().equals("")) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Enter a value for all fields with a * next to it");
			alert.showAndWait();
		} else if (cmbCity.getValue() == null
				|| cmbCategory.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a value for both city and category");
			alert.showAndWait();
		} else {
			//This is where the add to the DB is made
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Success");
			alert.setContentText("Your Attraction has been submitted and is awaiting approval");
			alert.showAndWait();
			if (mainModel.getUser().getIsManager()) {
				showScreen("../View/ManagerWelcome.fxml", "Welcome");
			} else {
				showScreen("../View/Welcome.fxml", "Welcome");
			}
		}
	}
}
