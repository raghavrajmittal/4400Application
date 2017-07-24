package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Model.Attraction;
import Model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PendingAttractionsListController extends BasicController{
	
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<Attraction> tblAttractions;
	@FXML
	private TableColumn<Attraction,String> colName;
	@FXML
	private TableColumn<Attraction,City> colCity;
	@FXML
	private TableColumn<Attraction,String> colAddr;
	@FXML
	private TableColumn<Attraction,String> colCat;
	@FXML
	private TableColumn<Attraction,String> colDescription;
	@FXML
	private TableColumn<Attraction,String> colHours;
	@FXML
	private TableColumn<Attraction,String> colContact;
	@FXML
	private TableColumn<Attraction,String> colSubmittedBy;
	@FXML
	private TableColumn<Attraction,Integer> colRating;
	@FXML
	private TableColumn<Attraction,String> colComment;
	@FXML
	private TableColumn<Attraction,String> colStatus;

	private List<Attraction> tableList;
	DBModel mainModel = DBModel.getInstance();
	@FXML
	public void initialize() {

		tableList = new ArrayList<>();
		colName.setCellValueFactory(new PropertyValueFactory<Attraction, String>("name"));
		colCity.setCellValueFactory(new PropertyValueFactory<Attraction, City>("city"));
		colAddr.setCellValueFactory(new PropertyValueFactory<Attraction, String>("address"));
		colCat.setCellValueFactory(new PropertyValueFactory<Attraction, String>("categoriesList"));
		colDescription.setCellValueFactory(new PropertyValueFactory<Attraction, String>("description"));
		colHours.setCellValueFactory(new PropertyValueFactory<Attraction, String>("hoursOfOp"));
		colContact.setCellValueFactory(new PropertyValueFactory<Attraction, String>("contact"));
		colSubmittedBy.setCellValueFactory(new PropertyValueFactory<Attraction, String>("submittedBy"));
		colRating.setCellValueFactory(new PropertyValueFactory<Attraction, Integer>("rating"));
		colComment.setCellValueFactory(new PropertyValueFactory<Attraction, String>("comment"));

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT res1.AttrID, Name, LocatedIn, Address, Category, Description, HoursOfOperation, ContactInfo, SubmittedBy, Rating, Comment\n" +
					"from\n" +
					"\n" +
					"\t(SELECT A.AttrID, A.Name, A.LocatedIn, A.Address, A.Description, E.SubmittedBy, R.Rating, R.Comment\n" +
					"\tFROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E\n" +
					"\tWHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = TRUE) as res1\n" +
					"inner join \n" +
					"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
					"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
					"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = TRUE\n" +
					"\tGROUP BY A.AttrID) as res2\n" +
					"on res1.AttrID = res2.AttrID\n" +
					"\n" +
					"left join\n" +
					"\t(SELECT AttrID, GROUP_CONCAT(DayOfTheWeek, \": \", OpenTime, \"-\", CloseTime ,\" \") as HoursOfoperation\n" +
					"\tFROM HOURS_OF_OPERATION\n" +
					"\tGROUP by AttrID) as res3\n" +
					"on res1.AttrID = res3.AttrID\n" +
					"\n" +
					"left join\n" +
					"\t(SELECT AttrID, GROUP_CONCAT(ContactMethod, \":\" ,MethodValue , \" \") as ContactInfo\n" +
					"\tFROM CONTACT_INFO\n" +
					"    GROUP BY AttrID) as res4\n" +
					"on res1.AttrID = res4.AttrID\n" +
					"\n" +
					"order by res1.LocatedIn asc;";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				int attrID = resultSet.getInt("AttrID");
				String name = resultSet.getString("Name");
				int cityID = resultSet.getInt("LocatedIn");
				String address = resultSet.getString("Address");
				String categories = resultSet.getString("Category");
				String description = resultSet.getString("Description");
				String hours = resultSet.getString("HoursOfOperation");
				String contactInfo = resultSet.getString("ContactInfo");
				String submittedBy = resultSet.getString("SubmittedBy");
				int rating = resultSet.getInt("Rating");

				String comment = resultSet.getString("Comment");
				query = "SELECT Name, Country FROM CITY WHERE CityID = ?";
				stmnt = con.prepareStatement(query);
				stmnt.setInt(1, cityID);
				ResultSet newSet = stmnt.executeQuery();
				String cityName = "";
				String cityCountry = "";
				while (newSet.next()){
					cityName = newSet.getString("Name");
					cityCountry = newSet.getString("Country");
				}

				City c = new City(cityName, cityID, cityCountry, null);
				Attraction a = new Attraction(name, c, address, description, null, attrID);
				a.setContact(contactInfo);
				a.setHoursOfOp(hours);
//				System.out.println("Attraction hours- " + a.getHours());
//				System.out.println("Contact Info- " + a.getContactInfo());
				a.setCategoriesList(categories);
				a.setSubmittedBy(submittedBy);
				a.setRating(rating);
				a.setComment(comment);
				tableList.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList<Attraction> oTableList = FXCollections.observableList(tableList);
		tblAttractions.setItems(oTableList);

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
				
			} else if (cmbSort.getValue().equals("Name Z-A")) {
				
			} else if (cmbSort.getValue().equals("Rating")) {
				
			} else if (cmbSort.getValue().equals("Country A-Z")) {
				
			} else if (cmbSort.getValue().equals("Country Z-A")) {
				
			}
			ObservableList<Attraction> oTableList = FXCollections.observableList(tableList);
			tblAttractions.setItems(oTableList);
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
