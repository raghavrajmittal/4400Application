package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Model.City;
import Links.CityPageLink;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CityListController extends BasicController{
	
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<City> tblCities;
	@FXML
	private TableColumn<City,String> colName;
	@FXML
	private TableColumn<City,Double> colRate;
	@FXML
	private TableColumn<City,Integer> colNumRate;
	@FXML
	private TableColumn<City,Integer> colNumAttr;
	@FXML
	private TableColumn<City,CityPageLink> colCityPage;

	DBModel mainModel = DBModel.getInstance();
	@FXML
	public void initialize() {

		colName.setCellValueFactory(new PropertyValueFactory<City, String>("name"));
		colRate.setCellValueFactory(new PropertyValueFactory<City, Double>("avgRat"));
		colNumAttr.setCellValueFactory(new PropertyValueFactory<City, Integer>("numAttr"));
		colNumRate.setCellValueFactory(new PropertyValueFactory<City, Integer>("numRat"));
		colCityPage.setCellValueFactory(new PropertyValueFactory<City,CityPageLink>("cityHyperLink"));

		List<City> cityList = new ArrayList<>();
		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = " SELECT res1.cityID, res1.Name, Country, State, res1.avgRat, res1.totalRat, res2.numAttr\n" +
					"FROM (\n" +
					"\n" +
					"\n" +
					"\t(SELECT C.CityID, C.Name, C.Country, C.State, AVG(Rating) as avgRat, COUNT(Rating) as totalRat\n" +
					"\tFROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
					"\tWHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = FALSE\n" +
					"\tGROUP BY C.CityID ) as res1\n" +
					"    \n" +
					"    left join \n" +
					"\t\n" +
					"\t(SELECT A.LocatedIn, COUNT(*) as numAttr\n" +
					"\tFROM ATTRACTION AS A, REVIEWABLE_ENTITY as E\n" +
					"\tWHERE A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
					"\tGROUP BY A.LocatedIn) as res2\n" +
					"    \n" +
					"    on res1.CityID = res2.LocatedIn) ;";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("Name");
				String country = resultSet.getString("Country");
				String state = resultSet.getString("State");
				int cityID = resultSet.getInt("CityID");
				int avgRat = resultSet.getInt("avgRat");
				int totalRat = resultSet.getInt("totalRat");
				int numAttr = resultSet.getInt("numAttr");
				City c = new City(name, cityID, country, state);
				c.setAvgRat(avgRat);
				c.setNumRat(totalRat);
				c.setNumAttr(numAttr);
				c.setCityHyperLink(new CityPageLink(c));
				cityList.add(c);
			}
			ObservableList<City> tableCityList = FXCollections.observableList(cityList);
			tblCities.setItems(tableCityList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		//populate combo box
		List<String> list = new ArrayList<>();
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Avg Rating");
		list.add("#of Ratings");
		list.add("#of Attractions");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
	}
	
	@FXML
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome");
		} else {
			showScreen("../View/Welcome.fxml", "Welcome");
		}
	}
	
	@FXML
	public void handleAddPressed() {
		showScreen("../View/NewCityForm.fxml", "New City Form");
	}
	
	@FXML
	public void handleSortPressed() {
		if (cmbSort.getValue() != null) {

			colName.setCellValueFactory(new PropertyValueFactory<City, String>("name"));
			colRate.setCellValueFactory(new PropertyValueFactory<City, Double>("avgRat"));
			colNumAttr.setCellValueFactory(new PropertyValueFactory<City, Integer>("numAttr"));
			colNumRate.setCellValueFactory(new PropertyValueFactory<City, Integer>("numRat"));

			List<City> cityList = new ArrayList<>();

			if (cmbSort.getValue().equals("Name A-Z")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = " SELECT res1.cityID, res1.Name as Name, Country, State, res1.avgRat, res1.totalRat, res2.numAttr\n" +
							"FROM (\n" +
							"\n" +
							"\n" +
							"\t(SELECT C.CityID, C.Name, C.Country, C.State, AVG(Rating) as avgRat, COUNT(Rating) as totalRat\n" +
							"\tFROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY C.CityID ) as res1\n" +
							"    \n" +
							"    left join \n" +
							"\t\n" +
							"\t(SELECT A.LocatedIn, COUNT(*) as numAttr\n" +
							"\tFROM ATTRACTION AS A, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.LocatedIn) as res2\n" +
							"    \n" +
							"    on res1.CityID = res2.LocatedIn ) order by Name ASC ;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String state = resultSet.getString("State");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int totalRat = resultSet.getInt("totalRat");
						int numAttr = resultSet.getInt("numAttr");
						City c = new City(name, cityID, country, state);
						c.setAvgRat(avgRat);
						c.setNumRat(totalRat);
						c.setNumAttr(numAttr);
						c.setCityHyperLink(new CityPageLink(c));

						cityList.add(c);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Name Z-A")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = " SELECT res1.cityID, res1.Name as Name, Country, State, res1.avgRat, res1.totalRat, res2.numAttr\n" +
							"FROM (\n" +
							"\n" +
							"\n" +
							"\t(SELECT C.CityID, C.Name, C.Country, C.State, AVG(Rating) as avgRat, COUNT(Rating) as totalRat\n" +
							"\tFROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY C.CityID ) as res1\n" +
							"    \n" +
							"    left join \n" +
							"\t\n" +
							"\t(SELECT A.LocatedIn, COUNT(*) as numAttr\n" +
							"\tFROM ATTRACTION AS A, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.LocatedIn) as res2\n" +
							"    \n" +
							"    on res1.CityID = res2.LocatedIn ) order by Name DESC ;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String state = resultSet.getString("State");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int totalRat = resultSet.getInt("totalRat");
						int numAttr = resultSet.getInt("numAttr");
						City c = new City(name, cityID, country, state);
						c.setAvgRat(avgRat);
						c.setNumRat(totalRat);
						c.setNumAttr(numAttr);
						c.setCityHyperLink(new CityPageLink(c));

						cityList.add(c);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Avg Rating")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = " SELECT res1.cityID, res1.Name as Name, Country, State, res1.avgRat, res1.totalRat, res2.numAttr\n" +
							"FROM (\n" +
							"\n" +
							"\n" +
							"\t(SELECT C.CityID, C.Name, C.Country, C.State, AVG(Rating) as avgRat, COUNT(Rating) as totalRat\n" +
							"\tFROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY C.CityID ) as res1\n" +
							"    \n" +
							"    left join \n" +
							"\t\n" +
							"\t(SELECT A.LocatedIn, COUNT(*) as numAttr\n" +
							"\tFROM ATTRACTION AS A, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.LocatedIn) as res2\n" +
							"    \n" +
							"    on res1.CityID = res2.LocatedIn ) order by res1.avgRat DESC ;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String state = resultSet.getString("State");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int totalRat = resultSet.getInt("totalRat");
						int numAttr = resultSet.getInt("numAttr");
						City c = new City(name, cityID, country, state);
						c.setAvgRat(avgRat);
						c.setNumRat(totalRat);
						c.setNumAttr(numAttr);
						c.setCityHyperLink(new CityPageLink(c));

						cityList.add(c);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("#of Ratings")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = " SELECT res1.cityID, res1.Name as Name, Country, State, res1.avgRat, res1.totalRat, res2.numAttr\n" +
							"FROM (\n" +
							"\n" +
							"\n" +
							"\t(SELECT C.CityID, C.Name, C.Country, C.State, AVG(Rating) as avgRat, COUNT(Rating) as totalRat\n" +
							"\tFROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY C.CityID ) as res1\n" +
							"    \n" +
							"    left join \n" +
							"\t\n" +
							"\t(SELECT A.LocatedIn, COUNT(*) as numAttr\n" +
							"\tFROM ATTRACTION AS A, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.LocatedIn) as res2\n" +
							"    \n" +
							"    on res1.CityID = res2.LocatedIn ) order by res1.totalRat DESC ;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String state = resultSet.getString("State");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int totalRat = resultSet.getInt("totalRat");
						int numAttr = resultSet.getInt("numAttr");
						City c = new City(name, cityID, country, state);
						c.setAvgRat(avgRat);
						c.setNumRat(totalRat);
						c.setNumAttr(numAttr);
						c.setCityHyperLink(new CityPageLink(c));

						cityList.add(c);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("#of Attractions")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = " SELECT res1.cityID, res1.Name as Name, Country, State, res1.avgRat, res1.totalRat, res2.numAttr\n" +
							"FROM (\n" +
							"\n" +
							"\n" +
							"\t(SELECT C.CityID, C.Name, C.Country, C.State, AVG(Rating) as avgRat, COUNT(Rating) as totalRat\n" +
							"\tFROM CITY AS C, REVIEW AS R, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE C.CityID = R.EntityID and C.CityID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY C.CityID ) as res1\n" +
							"    \n" +
							"    left join \n" +
							"\t\n" +
							"\t(SELECT A.LocatedIn, COUNT(*) as numAttr\n" +
							"\tFROM ATTRACTION AS A, REVIEWABLE_ENTITY as E\n" +
							"\tWHERE A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"\tGROUP BY A.LocatedIn) as res2\n" +
							"    \n" +
							"    on res1.CityID = res2.LocatedIn ) order by res2.numAttr DESC ;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String name = resultSet.getString("Name");
						String country = resultSet.getString("Country");
						String state = resultSet.getString("State");
						int cityID = resultSet.getInt("CityID");
						int avgRat = resultSet.getInt("avgRat");
						int totalRat = resultSet.getInt("totalRat");
						int numAttr = resultSet.getInt("numAttr");
						City c = new City(name, cityID, country, state);
						c.setAvgRat(avgRat);
						c.setNumRat(totalRat);
						c.setNumAttr(numAttr);
						c.setCityHyperLink(new CityPageLink(c));

						cityList.add(c);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			ObservableList<City> tableCityList = FXCollections.observableList(cityList);
			tblCities.setItems(tableCityList);
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please select a sort type");
			alert.showAndWait();
		}
	}
}
