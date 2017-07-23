package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale.Category;

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

public class AttractionsListController extends BasicController{
	
	@FXML
	private TableView<Attraction> tblAttractions;
	@FXML
	private TableColumn<Attraction,String> colName;
	@FXML
	private TableColumn<Attraction,String> colCat;
	@FXML
	private TableColumn<Attraction,City> colLoc;
	@FXML
	private TableColumn<Attraction,Double> colRate;
	@FXML
	private TableColumn<Attraction,Integer> colNumOfRate;
	@FXML
	private TableColumn<Attraction,String> colInfo;
	
	@FXML
	private ComboBox<String> cmbSort;

	private List<Attraction> attrList;

	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		//Populate table
		attrList = new ArrayList<>();

		colName.setCellValueFactory(new PropertyValueFactory<Attraction, String>("name"));
		colCat.setCellValueFactory(new PropertyValueFactory<Attraction, String>("categoriesList"));
		colLoc.setCellValueFactory(new PropertyValueFactory<Attraction, City>("city"));
		colRate.setCellValueFactory(new PropertyValueFactory<Attraction, Double>("avgRat"));
		colNumOfRate.setCellValueFactory(new PropertyValueFactory<Attraction, Integer>("numRat"));

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT res1.AttrID as attrID, res1.Name as AttrName, Category, res3.Name as CityName, res1.LocatedIn as cityID, res3.Country as Country, avgRat, numRat\n" +
					"from\n" +
					"\t(SELECT A.AttrID, A.Name, A.LocatedIn, Avg(Rating) as avgRat, COUNT(Rating) as numRat\n" +
					"\tFROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E\n" +
					"\tWHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
					"\tGROUP BY R.EntityID) as res1\n" +
					"inner join \n" +
					"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
					"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
					"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
					"\tGROUP BY A.AttrID) as res2\n" +
					"on res1.AttrID = res2.AttrID\n" +
					"\n" +
					"inner join\n" +
					"\t(SELECT Name, Country, CityID\n" +
					"    FROM CITY) as res3\n" +
					"on res1.LocatedIn = res3.CityID;";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			String emailVal = null;
			while (resultSet.next()) {
				int attrID = resultSet.getInt("attrID");
				String attrName = resultSet.getString("AttrName");
				String categories = resultSet.getString("Category");
				String cityName = resultSet.getString("CityName");
				int cityID = resultSet.getInt("CityID");
				int avgRat = resultSet.getInt("avgRat");
				int numRat = resultSet.getInt("numRat");
				String country = resultSet.getString("Country");
				City c = new City(cityName, cityID, country, null);
				Attraction a = new Attraction(attrName, c, attrID);
				a.setCategoriesList(categories);
				a.setAvgRat(avgRat);
				a.setNumRat(numRat);
				attrList.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		ObservableList tableAttractionList = FXCollections.observableList(attrList);
		tblAttractions.setItems(tableAttractionList);

		List<String> list = new ArrayList<String>();
		//Populate with items
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Rating:Best First");
		list.add("Rating:Best Last");
		list.add("City A-Z");
		list.add("City Z-A");
		ObservableList<String> cmbItems = FXCollections.observableList(list);
		cmbSort.setItems(cmbItems);
				
		//Do the initial population by A-Z sort
	}
	
	public void handleAddPressed() {
		showScreen("../View/NewAttractionForm.fxml","New Attraction");
	}
	
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome");
		} else {
			showScreen("../View/Welcome.fxml", "Welcome");
		}
	}
	
	public void handleSortPressed() {
		String content = cmbSort.getValue();
		//Use content in SQL query to sort the table.
		if (content != null) {
			attrList = new ArrayList<>();

			if (content.equals("Name A-Z")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT res1.AttrID as attrID, res1.Name as AttrName, Category, res3.Name as CityName, res1.LocatedIn as cityID, res3.Country as Country, avgRat, numRat\n" +
							"from\n" +
							"\t(SELECT A.AttrID, A.Name, A.LocatedIn, Avg(Rating) as avgRat, COUNT(Rating) as numRat\n" +
							"\tFROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY R.EntityID) as res1\n" +
							"inner join \n" +
							"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
							"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.AttrID) as res2\n" +
							"on res1.AttrID = res2.AttrID\n" +
							"\n" +
							"inner join\n" +
							"\t(SELECT Name, Country, CityID\n" +
							"    FROM CITY) as res3\n" +
							"on res1.LocatedIn = res3.CityID order by AttrName ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					String emailVal = null;
					while (resultSet.next()) {
						int attrID = resultSet.getInt("attrID");
						String attrName = resultSet.getString("AttrName");
						String categories = resultSet.getString("Category");
						String cityName = resultSet.getString("CityName");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int numRat = resultSet.getInt("numRat");
						String country = resultSet.getString("Country");
						City c = new City(cityName, cityID, country, null);
						Attraction a = new Attraction(attrName, c, attrID);
						a.setCategoriesList(categories);
						a.setAvgRat(avgRat);
						a.setNumRat(numRat);
						attrList.add(a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (content.equals("Name Z-A")){
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT res1.AttrID as attrID, res1.Name as AttrName, Category, res3.Name as CityName, res1.LocatedIn as cityID, res3.Country as Country, avgRat, numRat\n" +
							"from\n" +
							"\t(SELECT A.AttrID, A.Name, A.LocatedIn, Avg(Rating) as avgRat, COUNT(Rating) as numRat\n" +
							"\tFROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY R.EntityID) as res1\n" +
							"inner join \n" +
							"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
							"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.AttrID) as res2\n" +
							"on res1.AttrID = res2.AttrID\n" +
							"\n" +
							"inner join\n" +
							"\t(SELECT Name, Country, CityID\n" +
							"    FROM CITY) as res3\n" +
							"on res1.LocatedIn = res3.CityID order by AttrName DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					String emailVal = null;
					while (resultSet.next()) {
						int attrID = resultSet.getInt("attrID");
						String attrName = resultSet.getString("AttrName");
						String categories = resultSet.getString("Category");
						String cityName = resultSet.getString("CityName");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int numRat = resultSet.getInt("numRat");
						String country = resultSet.getString("Country");
						City c = new City(cityName, cityID, country, null);
						Attraction a = new Attraction(attrName, c, attrID);
						a.setCategoriesList(categories);
						a.setAvgRat(avgRat);
						a.setNumRat(numRat);
						attrList.add(a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (content.equals("Rating:Best First")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT res1.AttrID as attrID, res1.Name as AttrName, Category, res3.Name as CityName, res1.LocatedIn as cityID, res3.Country as Country, avgRat, numRat\n" +
							"from\n" +
							"\t(SELECT A.AttrID, A.Name, A.LocatedIn, Avg(Rating) as avgRat, COUNT(Rating) as numRat\n" +
							"\tFROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY R.EntityID) as res1\n" +
							"inner join \n" +
							"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
							"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.AttrID) as res2\n" +
							"on res1.AttrID = res2.AttrID\n" +
							"\n" +
							"inner join\n" +
							"\t(SELECT Name, Country, CityID\n" +
							"    FROM CITY) as res3\n" +
							"on res1.LocatedIn = res3.CityID order by avgRat DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					String emailVal = null;
					while (resultSet.next()) {
						int attrID = resultSet.getInt("attrID");
						String attrName = resultSet.getString("AttrName");
						String categories = resultSet.getString("Category");
						String cityName = resultSet.getString("CityName");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int numRat = resultSet.getInt("numRat");
						String country = resultSet.getString("Country");
						City c = new City(cityName, cityID, country, null);
						Attraction a = new Attraction(attrName, c, attrID);
						a.setCategoriesList(categories);
						a.setAvgRat(avgRat);
						a.setNumRat(numRat);
						attrList.add(a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (content.equals("Rating:Best Last")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT res1.AttrID as attrID, res1.Name as AttrName, Category, res3.Name as CityName, res1.LocatedIn as cityID, res3.Country as Country, avgRat, numRat\n" +
							"from\n" +
							"\t(SELECT A.AttrID, A.Name, A.LocatedIn, Avg(Rating) as avgRat, COUNT(Rating) as numRat\n" +
							"\tFROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY R.EntityID) as res1\n" +
							"inner join \n" +
							"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
							"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.AttrID) as res2\n" +
							"on res1.AttrID = res2.AttrID\n" +
							"\n" +
							"inner join\n" +
							"\t(SELECT Name, Country, CityID\n" +
							"    FROM CITY) as res3\n" +
							"on res1.LocatedIn = res3.CityID order by avgRat ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					String emailVal = null;
					while (resultSet.next()) {
						int attrID = resultSet.getInt("attrID");
						String attrName = resultSet.getString("AttrName");
						String categories = resultSet.getString("Category");
						String cityName = resultSet.getString("CityName");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int numRat = resultSet.getInt("numRat");
						String country = resultSet.getString("Country");
						City c = new City(cityName, cityID, country, null);
						Attraction a = new Attraction(attrName, c, attrID);
						a.setCategoriesList(categories);
						a.setAvgRat(avgRat);
						a.setNumRat(numRat);
						attrList.add(a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (content.equals("City A-Z")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT res1.AttrID as attrID, res1.Name as AttrName, Category, res3.Name as CityName, res1.LocatedIn as cityID, res3.Country as Country, avgRat, numRat\n" +
							"from\n" +
							"\t(SELECT A.AttrID, A.Name, A.LocatedIn, Avg(Rating) as avgRat, COUNT(Rating) as numRat\n" +
							"\tFROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY R.EntityID) as res1\n" +
							"inner join \n" +
							"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
							"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.AttrID) as res2\n" +
							"on res1.AttrID = res2.AttrID\n" +
							"\n" +
							"inner join\n" +
							"\t(SELECT Name, Country, CityID\n" +
							"    FROM CITY) as res3\n" +
							"on res1.LocatedIn = res3.CityID order by CityName ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					String emailVal = null;
					while (resultSet.next()) {
						int attrID = resultSet.getInt("attrID");
						String attrName = resultSet.getString("AttrName");
						String categories = resultSet.getString("Category");
						String cityName = resultSet.getString("CityName");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int numRat = resultSet.getInt("numRat");
						String country = resultSet.getString("Country");
						City c = new City(cityName, cityID, country, null);
						Attraction a = new Attraction(attrName, c, attrID);
						a.setCategoriesList(categories);
						a.setAvgRat(avgRat);
						a.setNumRat(numRat);
						attrList.add(a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (content.equals("City Z-A")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT res1.AttrID as attrID, res1.Name as AttrName, Category, res3.Name as CityName, res1.LocatedIn as cityID, res3.Country as Country, avgRat, numRat\n" +
							"from\n" +
							"\t(SELECT A.AttrID, A.Name, A.LocatedIn, Avg(Rating) as avgRat, COUNT(Rating) as numRat\n" +
							"\tFROM ATTRACTION AS A, REVIEW AS R,  REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = R.EntityID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY R.EntityID) as res1\n" +
							"inner join \n" +
							"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
							"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.AttrID) as res2\n" +
							"on res1.AttrID = res2.AttrID\n" +
							"\n" +
							"inner join\n" +
							"\t(SELECT Name, Country, CityID\n" +
							"    FROM CITY) as res3\n" +
							"on res1.LocatedIn = res3.CityID order by CityName DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					String emailVal = null;
					while (resultSet.next()) {
						int attrID = resultSet.getInt("attrID");
						String attrName = resultSet.getString("AttrName");
						String categories = resultSet.getString("Category");
						String cityName = resultSet.getString("CityName");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int numRat = resultSet.getInt("numRat");
						String country = resultSet.getString("Country");
						City c = new City(cityName, cityID, country, null);
						Attraction a = new Attraction(attrName, c, attrID);
						a.setCategoriesList(categories);
						a.setAvgRat(avgRat);
						a.setNumRat(numRat);
						attrList.add(a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ObservableList tableAttractionList = FXCollections.observableList(attrList);
			tblAttractions.setItems(tableAttractionList);
		}else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort choice");
			alert.showAndWait();
		}
	}
}
