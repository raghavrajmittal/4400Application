package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	private boolean exists = false;

	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		//label name is set
		lblAttractionName.setText(mainModel.getAttraction().getName());
		lblUserName.setText(mainModel.getUser().getEmail());
		sldRating.valueProperty().addListener((obs,oldVal,newVal)->
				sldRating.setValue((newVal.intValue())));
		lblRating.textProperty().bind(Bindings.format("%.0f", sldRating.valueProperty()));

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT Comment, Rating\n" +
					"FROM REVIEW\n" +
					"WHERE Email = ?  AND EntityID = ?;";
			PreparedStatement stmnt = con.prepareStatement(query);
			stmnt.setString(1, mainModel.getUser().getEmail());
			stmnt.setInt(2, mainModel.getAttraction().getAttractionID());
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String comment = resultSet.getString("Comment");
				int rating = resultSet.getInt("rating");
				exists = true;
				txtComment.setText(comment);
				sldRating.setValue(rating);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

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
			String comment = txtComment.getText();
			int rating = (int) sldRating.getValue();
			try {
				Connection con = DBModel.getInstance().getConnection();
				String query;
				if (exists) {
					query = "UPDATE REVIEW\n" +
							"SET Rating = ?, Comment=?, DateSubmitted = now()\n" +
							"WHERE Email = ? AND EntityID = ?;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, rating);
					stmnt.setString(2, comment);
					stmnt.setString(3, mainModel.getUser().getEmail());
					stmnt.setInt(4, mainModel.getAttraction().getAttractionID());
					stmnt.execute();
				} else {
					query = "\n" +
							"INSERT INTO REVIEW\n" +
							"VALUES (?, ?, now(), ?, ?);";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setString(1, mainModel.getUser().getEmail());
					stmnt.setInt(2, mainModel.getAttraction().getAttractionID());
					stmnt.setString(3, comment);
					stmnt.setInt(4, rating);
					stmnt.execute();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Success!");
			alert.setContentText("Your review has been submitted");
			alert.showAndWait();
			
			//Attraction cur = mainModel.getAttraction()
			//Return to the previous page making a sql query
			//To get the info on cur
			showScreen("../View/AttractionPage.fxml", mainModel.getAttraction().getName() + "'s Page");

			
		}
	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/AttractionPage.fxml",mainModel.getAttraction().getName() + "'s Page");
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
			try {
				Connection con = DBModel.getInstance().getConnection();
				String query = "DELETE FROM REVIEW\n" +
						"WHERE Email = ? AND EntityID = ?;";
				PreparedStatement stmnt = con.prepareStatement(query);
				stmnt.setString(1, mainModel.getUser().getEmail());
				stmnt.setInt(2, mainModel.getAttraction().getAttractionID());
				stmnt.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
			showScreen("../view/AttractionPage.fxml", mainModel.getAttraction().getName() + "'s Page");
		} else{
			alert.close();
		}
	}
}
