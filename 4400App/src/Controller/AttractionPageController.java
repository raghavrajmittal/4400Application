package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import Database.DBModel;
import Model.Review;
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


		try {
			Connection con = DBModel.getInstance().getConnection();
			//This is for the description
			String query = "SELECT Address, Description\n" +
					"FROM ATTRACTION\n" +
					"WHERE AttrID = ?;";
			PreparedStatement stmnt = con.prepareStatement(query);
			stmnt.setInt(1, mainModel.getAttraction().getAttractionID() );
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String address = resultSet.getString("Address");
				String description = resultSet.getString("Description");
				mainModel.getAttraction().setDescription(description);
				mainModel.getAttraction().setAddress(address);
			}

			//This is for the contact info
			query = "SELECT GROUP_CONCAT(ContactMethod , \":\" ,MethodValue , \" \") as ContactInfo\n" +
					"FROM CONTACT_INFO\n" +
					"WHERE AttrID = ?;";
			stmnt = con.prepareStatement(query);
			stmnt.setInt(1, mainModel.getAttraction().getAttractionID());
			resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String contactInfo = resultSet.getString("ContactInfo");
				mainModel.getAttraction().setContact(contactInfo);
			}

			//THis is for the hours of op
			query = "SELECT GROUP_CONCAT(DayOfTheWeek , \": \" , OpenTime , \"-\" , CloseTime , \" \") as HoursOfoperation\n" +
					"FROM HOURS_OF_OPERATION\n" +
					"WHERE AttrID = ?\n" +
					"GROUP by AttrID;\n";
			stmnt = con.prepareStatement(query);
			stmnt.setInt(1, mainModel.getAttraction().getAttractionID());
			resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String hours = resultSet.getString("HoursOfOperation");
				mainModel.getAttraction().setHoursOfOp(hours);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		lblAddress.setText(mainModel.getAttraction().getAddress());
		lblRating.setText("" + mainModel.getAttraction().getAvgRat());
		lblCategory.setText(mainModel.getAttraction().getCategoriesList());

		lblHours.setText(mainModel.getAttraction().getHoursOfOp());
		lblContact.setText(mainModel.getAttraction().getContact());
		lblDescription.setText(mainModel.getAttraction().getDescription());

//		String categories = "";
//		for (int i = 0; i < mainModel.getAttraction().getCategory().length; i++) {
//			categories = mainModel.getAttraction()
//					.getCategory()[i].toString() + ", ";
//		}
//		lblCategory.setText(categories);
	}
	
	@FXML
	public void handleReviewPressed() {
		showScreen("../View/NewAttractionReview.fxml", "New " + mainModel.getAttraction().getName() + " Review");
	}
	
	@FXML
	public void handleViewReviewsPressed() {
		showScreen("../View/AttractionReviews.fxml", mainModel.getAttraction().getName()+ " Reviews");
	}
	
	@FXML
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome " + mainModel.getUser().getEmail());
		} else {
			showScreen("../View/Welcome.fxml", "Welcome " + mainModel.getUser().getEmail());
		}
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
				showScreen("../View/ManagerWelcome.fxml", "Welcome " + mainModel.getUser().getEmail() );
			} else {
				showScreen("../View/Welcome.fxml", "Welcome" +  mainModel.getUser().getEmail());
			}
		} else{
			alert.close();
		}
	}
}
