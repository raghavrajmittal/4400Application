package Controller;

import java.util.Optional;

import Database.DBModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ButtonBar.ButtonData;

public class NewAttractionReviewController extends BasicController{

	@FXML
	private Label lblUserName;
	@FXML
	private Label lblAttractionName;
	@FXML
	private Slider sldRating;
	@FXML
	private Label lblRating;
	@FXML
	private TextArea txtComment;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		//label name is set
		lblAttractionName.setText(mainModel.getAttraction().getName());
		lblUserName.setText(mainModel.getUser().getName());
		sldRating.valueProperty().addListener((obs,oldVal,newVal)->
				sldRating.setValue((newVal.intValue())));
		lblRating.textProperty().bind(Bindings.format("%.0f", sldRating.valueProperty()));

	}
	
	@FXML
	public void handleSubmitPressed() {
		if (txtComment.getText().equals("")) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please enter a comment");
			alert.showAndWait();
		} else if(sldRating.getValue() == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Rating has to be greater than 0");
			alert.showAndWait();

		} else {
			//Submit the report
			//Review rev = new Attraction(mainModel.getAttraction().getName(),
			//	sldRating.getValue(), txtComment.getText());
			
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Success!");
			alert.setContentText("Your review has been submitted");
			alert.showAndWait();
			
			//Attraction cur = mainModel.getAttraction()
			//Return to the previous page making a sql query
			//To get the info on cur
			showScreen("../View/AttractionPage.fxml", "Attraction Page");

			
		}
	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/AttractionPage.fxml", "Attraction Page");
	}
	
	@FXML
	public void handleDeletePressed() {
		/***TODO*/
		//If no review exists for this user, then disable the warning button
		
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("WARNING!");
		alert.setContentText("Are you sure you want to delete this review?");
		
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType delete = new ButtonType("Delete");

		alert.getButtonTypes().setAll(cancel, delete);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == delete) {
			showScreen("../view/AttractionPage.fxml", "Attraction Page");
		} else{
			alert.close();
		}
	}
}
