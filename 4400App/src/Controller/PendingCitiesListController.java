package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Links.ApproveCityLink;
import Links.DeleteCityLink;
import Model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PendingCitiesListController extends BasicController {
	
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<City> tblPendingCities;
	@FXML
	private TableColumn<City,String> colName;
	@FXML
	private TableColumn<City,String> colCountry;
	@FXML
	private TableColumn<City,String> colSubmittedBy;
	@FXML
	private TableColumn<City,Integer> colRating;
	@FXML
	private TableColumn<City,String> colComment;
	@FXML
	private TableColumn<City, ApproveCityLink> colApprove;
	@FXML
	private TableColumn<City, DeleteCityLink> colDelete;

	private List<City> tableList;
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {

		tableList = new ArrayList<>();
		colName.setCellValueFactory(new PropertyValueFactory<City, String>("name"));
		colCountry.setCellValueFactory(new PropertyValueFactory<City, String>("country"));
		colSubmittedBy.setCellValueFactory(new PropertyValueFactory<City, String>("submittedBy"));
		colRating.setCellValueFactory(new PropertyValueFactory<City, Integer>("rating"));
		colComment.setCellValueFactory(new PropertyValueFactory<City, String>("comment"));
		colDelete.setCellValueFactory(new PropertyValueFactory<City, DeleteCityLink>("deleteCityHyperLink"));
		colApprove.setCellValueFactory(new PropertyValueFactory<City, ApproveCityLink>("approveCityHyperLink"));

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT E.EntityID as id, C.Name, C.Country, E.SubmittedBy, R.Rating, R.Comment\n" +
					"FROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
					"WHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = TRUE\n" +
					"ORDER by C.Name ASC;";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				int entityID = resultSet.getInt("id");
				String name = resultSet.getString("Name");
				String country = resultSet.getString("Country");
				String submittedBy = resultSet.getString("SubmittedBy");
				int rating = resultSet.getInt("Rating");
				String comment = resultSet.getString("Comment");
				City c = new City(name, entityID, country, null);
				c.setSubmittedBy(submittedBy);
				c.setRating(rating);
				c.setComment(comment);
				c.setDeleteCityHyperLink(new DeleteCityLink(c));
				c.setApproveCityHyperLink(new ApproveCityLink(c));
				tableList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList<City> cityTable = FXCollections.observableList(tableList);
		tblPendingCities.setItems(cityTable);

		List<String> list = new ArrayList<>();
		//Populate Sort
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Rating");
		list.add("Country A-Z");
		list.add("Country Z-A");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
	}
	
	@FXML
	public void handleSortPressed() {
		if (cmbSort.getValue() != null) {
			tableList = new ArrayList<>();

			if (cmbSort.getValue().equals("Name A-Z")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT E.EntityID as id, C.Name, C.Country, E.SubmittedBy, R.Rating, R.Comment\n" +
							"FROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"WHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = TRUE\n" +
							"ORDER by C.Name ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						int entityID = resultSet.getInt("id");
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String submittedBy = resultSet.getString("SubmittedBy");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						City c = new City(name, entityID, country, null);
						c.setSubmittedBy(submittedBy);
						c.setRating(rating);
						c.setComment(comment);
						c.setDeleteCityHyperLink(new DeleteCityLink(c));
						c.setApproveCityHyperLink(new ApproveCityLink(c));
						tableList.add(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Name Z-A")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT E.EntityID as id, C.Name, C.Country, E.SubmittedBy, R.Rating, R.Comment\n" +
							"FROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"WHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = TRUE\n" +
							"ORDER by C.Name DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						int entityID = resultSet.getInt("id");
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String submittedBy = resultSet.getString("SubmittedBy");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						City c = new City(name, entityID, country, null);
						c.setSubmittedBy(submittedBy);
						c.setRating(rating);
						c.setComment(comment);
						c.setDeleteCityHyperLink(new DeleteCityLink(c));
						c.setApproveCityHyperLink(new ApproveCityLink(c));
						tableList.add(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Rating")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT E.EntityID as id, C.Name, C.Country, E.SubmittedBy, R.Rating, R.Comment\n" +
							"FROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"WHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = TRUE\n" +
							"ORDER by R.Rating DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						int entityID = resultSet.getInt("id");
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String submittedBy = resultSet.getString("SubmittedBy");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						City c = new City(name, entityID, country, null);
						c.setSubmittedBy(submittedBy);
						c.setRating(rating);
						c.setComment(comment);
						c.setDeleteCityHyperLink(new DeleteCityLink(c));
						c.setApproveCityHyperLink(new ApproveCityLink(c));
						tableList.add(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Country A-Z")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT E.EntityID as id, C.Name, C.Country, E.SubmittedBy, R.Rating, R.Comment\n" +
							"FROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"WHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = TRUE\n" +
							"ORDER by C.Country ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						int entityID = resultSet.getInt("id");
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String submittedBy = resultSet.getString("SubmittedBy");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						City c = new City(name, entityID, country, null);
						c.setSubmittedBy(submittedBy);
						c.setRating(rating);
						c.setComment(comment);
						c.setDeleteCityHyperLink(new DeleteCityLink(c));
						c.setApproveCityHyperLink(new ApproveCityLink(c));
						tableList.add(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Country Z-A")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT E.EntityID as id, C.Name, C.Country, E.SubmittedBy, R.Rating, R.Comment\n" +
							"FROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"WHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = TRUE\n" +
							"ORDER by C.Country DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						int entityID = resultSet.getInt("id");
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String submittedBy = resultSet.getString("SubmittedBy");
						int rating = resultSet.getInt("Rating");
						String comment = resultSet.getString("Comment");
						City c = new City(name, entityID, country, null);
						c.setSubmittedBy(submittedBy);
						c.setRating(rating);
						c.setComment(comment);
						c.setDeleteCityHyperLink(new DeleteCityLink(c));
						c.setApproveCityHyperLink(new ApproveCityLink(c));
						tableList.add(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ObservableList<City> cityTable = FXCollections.observableList(tableList);
			tblPendingCities.setItems(cityTable);
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
