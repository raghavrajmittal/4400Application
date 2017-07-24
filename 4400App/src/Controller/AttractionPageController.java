package Controller;

import java.util.Optional;

import Database.DBModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonBar.ButtonData;

public class AttractionPageController extends BasicController{

	@FXML
	private Label lblAttractionName;
	@FXML
	private Label lblAddress;
	@FXML
	private Label lblDescription;
	@FXML
	private Label lblRating;
	@FXML
	private Label lblHours;
	@FXML
	private Label lblContact;
	@FXML
	private Label lblCategory;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		lblAttractionName.setText(mainModel.getAttraction().getName());
		//Populate all the labels with information
		
//		lblAddress.setText(mainModel.getAttraction().getAddress());
//		lblDescription.setText(mainModel.getAttraction().getDescription());
//		lblRating.setText("" + mainModel.getAttraction().getAvgRating());
//		lblHours.setText(mainModel.getAttraction().getHours());
//		lblContact.setText(mainModel.getAttraction().getContactInfo());
//		String categories = "";
//		for (int i = 0; i < mainModel.getAttraction().getCategory().length; i++) {
//			categories = mainModel.getAttraction()
//					.getCategory()[i].toString() + ", ";
//		}
//		lblCategory.setText(categories);
	}
	
	@FXML
	public void handleReviewPressed() {
		showScreen("../View/NewAttractionReview.fxml", "New Attraction Review");
	}
	
	@FXML
	public void handleViewReviewsPressed() {
		showScreen("../View/AttractionReviews.fxml", "Attraction Reviews");
	}
	
	@FXML
	public void handleBackPressed() {
//		if (mainModel.getUser().getIsManager()) {
//			showScreen("../View/ManagerWelcome.fxml", "Welcome");
//		} else {
//			showScreen("../View/Welcome.fxml", "Welcome");
//		}
	}
	
	@FXML
	public void handleDeletePressed() {
		//Delete from database
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("WARNING!");
		alert.setContentText("Are you sure you want to delete this Attraction?");
		
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType delete = new ButtonType("Delete");

		alert.getButtonTypes().setAll(cancel, delete);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == delete) {
			if (mainModel.getUser().getIsManager()) {
				showScreen("../View/ManagerWelcome.fxml", "Welcome");
			} else {
				showScreen("../View/Welcome.fxml", "Welcome");
			}
		} else{
			alert.close();
		}
	}
}
