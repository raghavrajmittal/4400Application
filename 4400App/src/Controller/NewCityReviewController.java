package Controller;

import java.util.Optional;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ButtonBar.ButtonData;

public class NewCityReviewController extends BasicController {
	
	@FXML
	private Label lblCityName;
	@FXML
	private Label lblRating;
	@FXML
	private Slider sldRating;
	@FXML
	private TextArea txtComment;
	
	
	@FXML
	public void initialize() {
		//Link slider and label
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
			//Push review into database
			String comment = txtComment.getText();
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setContentText("Your review has been processed");
			alert.setTitle("Success");
			alert.showAndWait();
			
			showScreen("../View/BasicCityPage.fxml", "Basic City Page");
		}
	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/BasicCityPage.fxml", "Baisc City Page");
	}
	
	@FXML
	public void handleDeletePressed() {
		//Delete from database
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("WARNING!");
		alert.setContentText("Are you sure you want to delete this review");
		
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType delete = new ButtonType("Delete");

		alert.getButtonTypes().setAll(cancel, delete);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == delete) {
			showScreen("../view/BasicCityPage.fxml", "Basic City Page");
		} else{
			alert.close();
		}
		
	}

}
