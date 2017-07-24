package Controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Model.Attraction;
import Model.City;
import Model.Review;
import Links.EditReviewLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserReviewsController extends BasicController{
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private Label lblUserName;
	@FXML
	private TableView<Review> tblReviews;
	@FXML
	private TableColumn<Review,String> colName;
	@FXML
	private TableColumn<Review,Integer> colRate;
	@FXML
	private TableColumn<Review,String> colComment;
	@FXML
	private TableColumn<Review,EditReviewLink> colEdit;
	
	//Instance of Database
	DBModel mainModel = DBModel.getInstance();
	private List<Review> reviewList;

	@FXML
	public void initialize() {
		lblUserName.setText(mainModel.getUser().getEmail());
		//Populate table
		colName.setCellValueFactory(new PropertyValueFactory<Review, String>("name"));
		colRate.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
		colComment.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));
		colEdit.setCellValueFactory(new PropertyValueFactory<Review,EditReviewLink>("editReviewHyperLink"));

		reviewList = new ArrayList<>();

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "(SELECT C.CityID as EntityID, C.Name, R.Rating, R.Comment\n" +
					"FROM REVIEW AS R, CITY AS C\n" +
					"WHERE ? = R.Email AND C.CityID = R.EntityID)\n" +
					"UNION\n" +
					"(SELECT A.AttrID as EntityID, A.Name , R.Rating, R.Comment\n" +
					"FROM REVIEW AS R, ATTRACTION AS A\n" +
					"WHERE ? = R.Email AND A.AttrID = R.EntityID);";
			PreparedStatement stmnt = con.prepareStatement(query);
			stmnt.setString(1, mainModel.getUser().getEmail());
			stmnt.setString(2, mainModel.getUser().getEmail());
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("Name");
				int rating = resultSet.getInt("Rating");
				String comment = resultSet.getString("Comment");
				int entityID = resultSet.getInt("EntityID");
				Review r = new Review(name, rating, comment, entityID);
				r.setEditReviewHyperLink(new EditReviewLink(r));
				reviewList.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList<Review> tableList = FXCollections.observableList(reviewList);
		tblReviews.setItems(tableList);

		//Populate with items
		List<String> list = new ArrayList<String>();

		list.add("A-Z");
		list.add("Z-A");
		list.add("Rating:Best First");
		list.add("Rating:Best Last");
		ObservableList<String> cmbItems = FXCollections.observableList(list);
		cmbSort.setItems(cmbItems);
		
		//Do the initial population by A-Z sort
	}
	
	@FXML
	public void handleSortPressed() {
		String content = cmbSort.getValue();
		//Use content in SQL query to sort the table.
		if (content != null) {
			reviewList = new ArrayList<>();
			if (content.equals("A-Z")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "(SELECT C.CityID as EntityID, C.Name, R.Rating, R.Comment\n" +
							"FROM REVIEW AS R, CITY AS C\n" +
							"WHERE ? = R.Email AND C.CityID = R.EntityID)\n" +
							"UNION\n" +
							"(SELECT A.AttrID as EntityID, A.Name , R.Rating, R.Comment\n" +
							"FROM REVIEW AS R, ATTRACTION AS A\n" +
							"WHERE ? = R.Email AND A.AttrID = R.EntityID) order by Name ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setString(1, mainModel.getUser().getEmail());
					stmnt.setString(2, mainModel.getUser().getEmail());
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String name = resultSet.getString("Name");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						int entityID = resultSet.getInt("EntityID");
						Review r = new Review(name, rating, comment, entityID);
						reviewList.add(r);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (content.equals("Z-A")){
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "(SELECT C.CityID as EntityID, C.Name, R.Rating, R.Comment\n" +
							"FROM REVIEW AS R, CITY AS C\n" +
							"WHERE ? = R.Email AND C.CityID = R.EntityID)\n" +
							"UNION\n" +
							"(SELECT A.AttrID as EntityID, A.Name , R.Rating, R.Comment\n" +
							"FROM REVIEW AS R, ATTRACTION AS A\n" +
							"WHERE ? = R.Email AND A.AttrID = R.EntityID) order by Name DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setString(1, mainModel.getUser().getEmail());
					stmnt.setString(2, mainModel.getUser().getEmail());
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String name = resultSet.getString("Name");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						int entityID = resultSet.getInt("EntityID");
						Review r = new Review(name, rating, comment, entityID);
						reviewList.add(r);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (content.equals("Rating:Best First")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "(SELECT C.CityID as EntityID, C.Name, R.Rating, R.Comment\n" +
							"FROM REVIEW AS R, CITY AS C\n" +
							"WHERE ? = R.Email AND C.CityID = R.EntityID)\n" +
							"UNION\n" +
							"(SELECT A.AttrID as EntityID, A.Name , R.Rating, R.Comment\n" +
							"FROM REVIEW AS R, ATTRACTION AS A\n" +
							"WHERE ? = R.Email AND A.AttrID = R.EntityID) order by Rating DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setString(1, mainModel.getUser().getEmail());
					stmnt.setString(2, mainModel.getUser().getEmail());
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String name = resultSet.getString("Name");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						int entityID = resultSet.getInt("EntityID");
						Review r = new Review(name, rating, comment, entityID);
						reviewList.add(r);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (content.equals("Rating:Best Last")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "(SELECT C.CityID as EntityID, C.Name, R.Rating, R.Comment\n" +
							"FROM REVIEW AS R, CITY AS C\n" +
							"WHERE ? = R.Email AND C.CityID = R.EntityID)\n" +
							"UNION\n" +
							"(SELECT A.AttrID as EntityID, A.Name , R.Rating, R.Comment\n" +
							"FROM REVIEW AS R, ATTRACTION AS A\n" +
							"WHERE ? = R.Email AND A.AttrID = R.EntityID) order by Rating ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setString(1, mainModel.getUser().getEmail());
					stmnt.setString(2, mainModel.getUser().getEmail());
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String name = resultSet.getString("Name");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						int entityID = resultSet.getInt("EntityID");
						Review r = new Review(name, rating, comment, entityID);
						reviewList.add(r);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ObservableList<Review> tableList = FXCollections.observableList(reviewList);
			tblReviews.setItems(tableList);
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort choice");
			alert.showAndWait();
		}

	}
	
	@FXML
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome");
		} else {
			showScreen("../View/Welcome.fxml", "Welcome");
		}
	}
}
