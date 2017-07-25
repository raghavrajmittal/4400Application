package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Links.CityPageLink;
import Links.DeleteReviewLink;
import Model.City;
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
import sun.security.x509.DeltaCRLIndicatorExtension;

public class CityReviewsController extends BasicController {

	@FXML
	private Label lblCityName;
	@FXML
	private TableView<Review> tblReviews;
	@FXML
	private TableColumn<Review,String> colName;
	@FXML
	private TableColumn<Review,Integer> colRate;
	@FXML
	private TableColumn<Review,String> colComment;
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableColumn<Review, DeleteReviewLink> colDelete;

	private List<Review> tableList;
	DBModel mainModel = DBModel.getInstance();
	@FXML
	public void initialize() {
		//Populate table
		tableList = new ArrayList<>();
		colName.setCellValueFactory(new PropertyValueFactory<Review, String>("name"));
		colRate.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
		colComment.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));

		if (mainModel.getUser().getIsManager()) {
			colDelete.setCellValueFactory(new PropertyValueFactory<Review, DeleteReviewLink>("deleteReviewLink"));
		}

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT Email, Rating, Comment\n" +
					"FROM REVIEW AS R\n" +
					"WHERE R.EntityID = ?\n" +
					"ORDER BY Rating desc;\n";
			PreparedStatement stmnt = con.prepareStatement(query);
			stmnt.setInt(1, mainModel.getCity().getCityID() );
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String email = resultSet.getString("Email");
				int rating = resultSet.getInt("Rating");
				String comment = resultSet.getString("Comment");
				Review r = new Review(email, rating,comment,mainModel.getCity().getCityID());
				r.setSubmittedBy(email);
				if (mainModel.getUser().getIsManager()) {
					r.setDeleteReviewLink(new DeleteReviewLink(r));
				}
				tableList.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList oTableList = FXCollections.observableList(tableList);
		tblReviews.setItems(oTableList);
		//Populate combo box
		List<String> list = new ArrayList<>();
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Avg Rating");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
	}
	
	@FXML
	public void handleReviewPressed() {
		showScreen("../View/NewCityReview.fxml", "new" + mainModel.getCity().toString() + " Review");
	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/BasicCityPage.fxml", mainModel.getCity().toString() + " Page");
	}
	
	@FXML
	public void handleSortPressed() {
		if (cmbSort.getValue() != null) {
			tableList = new ArrayList<>();

			if (cmbSort.getValue().equals("Name A-Z")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT Email, Rating, Comment\n" +
							"FROM REVIEW AS R\n" +
							"WHERE R.EntityID = ?\n" +
							"ORDER BY Email ASC;\n";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, mainModel.getCity().getCityID() );
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String email = resultSet.getString("Email");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						Review r = new Review(email, rating,comment,mainModel.getCity().getCityID());
						r.setSubmittedBy(email);
						if (mainModel.getUser().getIsManager()) {
							r.setDeleteReviewLink(new DeleteReviewLink(r));
						}
						tableList.add(r);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (cmbSort.getValue().equals("Name Z-A")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT Email, Rating, Comment\n" +
							"FROM REVIEW AS R\n" +
							"WHERE R.EntityID = ?\n" +
							"ORDER BY Email desc;\n";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, mainModel.getCity().getCityID() );
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String email = resultSet.getString("Email");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						Review r = new Review(email, rating,comment,mainModel.getCity().getCityID());
						r.setSubmittedBy(email);
						if (mainModel.getUser().getIsManager()) {
							r.setDeleteReviewLink(new DeleteReviewLink(r));
						}
						tableList.add(r);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (cmbSort.getValue().equals("Avg Rating")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT Email, Rating, Comment\n" +
							"FROM REVIEW AS R\n" +
							"WHERE R.EntityID = ?\n" +
							"ORDER BY Rating desc;\n";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, mainModel.getCity().getCityID() );
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String email = resultSet.getString("Email");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						Review r = new Review(email, rating,comment,mainModel.getCity().getCityID());
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
			ObservableList oTableList = FXCollections.observableList(tableList);
			tblReviews.setItems(oTableList);
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort type");
			alert.showAndWait();
		}
	}
}
