package Controller;

import Database.DBModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
		//lblAttractionName.setText(mainModel.getAttraction().getName());
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
		
	}
	
	@FXML
	public void handleBackPressed() {
//		if (mainModel.getUser().getIsManager()) {
//			showScreen("../View/ManagerWelcome.fxml", "Welcome");
//		} else {
//			showScreen("../View/Welcome.fxml", "Welcome");
//		}
	}
}
