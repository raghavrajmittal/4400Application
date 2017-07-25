package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import Database.DBModel;
import Links.AttractionInfoLink;
import Model.Attraction;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
	private Button btnSubmit;
	@FXML
	private Button btnDelete;

	private boolean exists = false;

	DBModel mainModel = DBModel.getInstance();

	@FXML
	public void initialize() {
		lblCityName.setText(mainModel.getCity().toString());
		//Link slider and label
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
			stmnt.setInt(2, mainModel.getCity().getCityID());
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

		if (exists) {
			btnSubmit.setText("Edit Review");
		} else {
			btnDelete.setVisible(false);
			btnDelete.setDisable(true);
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
			//Push review into database
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
					stmnt.setInt(4, mainModel.getCity().getCityID());
					stmnt.execute();
				} else {
					query = "\n" +
							"INSERT INTO REVIEW\n" +
							"VALUES (?, ?, now(), ?, ?);";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setString(1, mainModel.getUser().getEmail());
					stmnt.setInt(2, mainModel.getCity().getCityID());
					stmnt.setString(3, comment);
					stmnt.setInt(4, rating);
					stmnt.execute();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			//
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setContentText("Your review has been processed");
			alert.setTitle("Success");
			alert.showAndWait();
			
			showScreen("../View/BasicCityPage.fxml", mainModel.getCity().toString() + " Page");
		}
	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/BasicCityPage.fxml", mainModel.getCity().toString() + " Page");
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
			try {
				Connection con = DBModel.getInstance().getConnection();
				String query = "DELETE FROM REVIEW\n" +
						"WHERE Email = ? AND EntityID = ?;";
				PreparedStatement stmnt = con.prepareStatement(query);
				stmnt.setString(1, mainModel.getUser().getEmail());
				stmnt.setInt(2, mainModel.getCity().getCityID());
				stmnt.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
			showScreen("../view/BasicCityPage.fxml", mainModel.getCity().toString() + " Page");
		} else{
			alert.close();
		}
		
	}

}
