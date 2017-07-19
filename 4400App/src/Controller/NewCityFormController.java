package Controller;


import Database.DBModel;
import Model.City;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewCityFormController extends BasicController {
	
	@FXML
	private TextField txtNameField;
	@FXML
	private TextField txtCountryField;
	@FXML
	private TextField txtStateField;
	@FXML
	private Slider sldRating;
	@FXML
	private Label lblRating;
	@FXML
	private TextArea txtComment;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		sldRating.valueProperty().addListener((obs,oldVal,newVal)->
				sldRating.setValue((newVal.intValue())));
		lblRating.textProperty().bind(Bindings.format("%.0f", sldRating.valueProperty()));
		
		

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
		if (txtNameField.getText().equals("")
				|| txtCountryField.getText().equals("")
				|| txtComment.getText().equals("")) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Enter a value for all fields with a * next to it");
			alert.showAndWait();
		} else {
			String name = txtNameField.getText();
			String count = txtCountryField.getText();
			String com = txtComment.getText();
			String state = txtStateField.getText();
			double rating = sldRating.getValue();
			
			City city = new City (name,rating,com,count,state);
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Success");
			alert.setContentText("Your City has been submitted and is awaiting approval");
			alert.showAndWait();
			if (mainModel.getUser().getIsManager()) {
				showScreen("../View/ManagerWelcome.fxml", "Welcome");
			} else {
				showScreen("../View/Welcome.fxml", "Welcome");
			}
			
		}
	}
}
