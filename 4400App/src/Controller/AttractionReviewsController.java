package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Links.DeleteReviewLink;
import Model.Attraction;
import Model.Review;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AttractionReviewsController extends BasicController {

	@FXML
	private Label lblAttractionName;
	@FXML
	private TableView<Review> tblReviews;
	@FXML
	private TableColumn<Review,String> colName;
	@FXML
	private TableColumn<Review,Integer> colRate;
	@FXML
	private TableColumn<Review,String> colCom;

	@FXML
	private TableColumn<Review, DeleteReviewLink> colDel;

	@FXML
	private ComboBox<String> cmbSort;

	private List<Review> tableList;

	DBModel mainModel = DBModel.getInstance();
	@FXML
	public void initialize() {

		//label name is set
		lblAttractionName.setText(mainModel.getAttraction().toString());

		tableList = new ArrayList<>();
		colName.setCellValueFactory(new PropertyValueFactory<Review, String>("name"));
		colRate.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
		colCom.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));
		if (mainModel.getUser().getIsManager()) {
			colDel.setCellValueFactory(new PropertyValueFactory<Review, DeleteReviewLink>("deleteReviewLink"));
		}

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT Email, Rating, Comment\n" +
					"FROM REVIEW\n" +
					"WHERE EntityID = ?\n" +
					"ORDER BY Rating desc;\n";
			PreparedStatement stmnt = con.prepareStatement(query);
			stmnt.setInt(1, mainModel.getAttraction().getAttractionID() );
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String email = resultSet.getString("Email");
				int rating = resultSet.getInt("Rating");
				String comment = resultSet.getString("Comment");
				Review r = new Review(email, rating, comment, mainModel.getAttraction().getAttractionID());
				r.setSubmittedBy(email);
				if (mainModel.getUser().getIsManager()) {
					r.setDeleteReviewLink(new DeleteReviewLink(r));
				}
				tableList.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObservableList<Review> oTableList = FXCollections.observableList(tableList);
		tblReviews.setItems(oTableList);
		//Populate combobox and table
		List<String> list = new ArrayList<>();
		list.add("A-Z");
		list.add("Z-A");
		list.add("Rating");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
		
		//Populate the table
	}
	
	@FXML
	public void handleReviewPressed() {
		showScreen("../View/NewAttractionReview.fxml", "New " + mainModel.getAttraction().getName() + " Review");
	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/AttractionPage.fxml", mainModel.getAttraction().getName() + "'s Page");
	}
	
	@FXML
	public void handleSortPressed() {
		if (cmbSort.getValue() != null) {
			tableList = new ArrayList<>();


			if (cmbSort.getValue().equals("A-Z")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT Email, Rating, Comment\n" +
							"FROM REVIEW\n" +
							"WHERE EntityID = ?\n" +
							"ORDER BY Email ASC;\n";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, mainModel.getAttraction().getAttractionID() );
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String email = resultSet.getString("Email");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						Review r = new Review(email, rating, comment, mainModel.getAttraction().getAttractionID());
						r.setSubmittedBy(email);
						if (mainModel.getUser().getIsManager()) {
							r.setDeleteReviewLink(new DeleteReviewLink(r));
						}
						tableList.add(r);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Z-A")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT Email, Rating, Comment\n" +
							"FROM REVIEW\n" +
							"WHERE EntityID = ?\n" +
							"ORDER BY Email DESC;\n";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, mainModel.getAttraction().getAttractionID() );
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String email = resultSet.getString("Email");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						Review r = new Review(email, rating, comment, mainModel.getAttraction().getAttractionID());
						r.setSubmittedBy(email);
						if (mainModel.getUser().getIsManager()) {
							r.setDeleteReviewLink(new DeleteReviewLink(r));
						}
						tableList.add(r);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Rating")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT Email, Rating, Comment\n" +
							"FROM REVIEW\n" +
							"WHERE EntityID = ?\n" +
							"ORDER BY Rating desc;\n";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, mainModel.getAttraction().getAttractionID() );
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String email = resultSet.getString("Email");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						Review r = new Review(email, rating, comment, mainModel.getAttraction().getAttractionID());
						r.setSubmittedBy(email);
						if (mainModel.getUser().getIsManager()) {
							r.setDeleteReviewLink(new DeleteReviewLink(r));
						}
						tableList.add(r);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ObservableList<Review> oTableList = FXCollections.observableList(tableList);
			tblReviews.setItems(oTableList);
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort type");
			alert.showAndWait();
		}
	}
}
