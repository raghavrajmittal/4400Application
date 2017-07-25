package Controller;

import Database.DBModel;
import Links.AttractionInfoLink;
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AttractionsListFilteredController extends BasicController{
	
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
	private TableColumn<Attraction,AttractionInfoLink> colInfo;
	
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
		colInfo.setCellValueFactory(new PropertyValueFactory<Attraction,AttractionInfoLink>("infoHyperLink"));

		if (mainModel.getIsFiltered()) {
			if (mainModel.getFilterCity().getName() != "null City") {
				City filterCity = mainModel.getFilterCity();
				if (!mainModel.getFilterAttrName().equals("")) {
					String filterAttr = mainModel.getFilterAttrName();
					if (!mainModel.getFilterCategory().equals("")) {
						//city, category, and attraction
						String filterCat = mainModel.getFilterCategory();
						try {
							Connection con = DBModel.getInstance().getConnection();
							String query = "SELECT res1.AttrID, Name, Address, Category, avgRat, numRat\n" +
									"FROM(\n" +
									"\n" +
									"\t(SELECT A.AttrID, LocatedIn, Name, Address\n" +
									"\tFROM ATTRACTION as A, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE LocatedIn = ? and A.AttrID = E.EntityID and E.IsPending = FALSE and A.Name = ? ) as res1\n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, AVG(Rating) as avgRat, COUNT(Rating) as numRat\n" +
									"\tFROM ATTRACTION AS A, REVIEW AS R\n" +
									"\tWHERE A.AttrID = R.EntityID AND A.LocatedIn = ?\n" +
									"\tGROUP BY A.AttrID) as res2\n" +
									"on res1.AttrID = res2.AttrID \n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
									"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
									"\tGROUP BY A.AttrID) as res3\n" +
									"on res1.AttrID = res3.AttrID)\n" +
									"\n" +
									"inner join\n" +
									"\t(SELECT AttrID\n" +
									"\tFROM FALLS_UNDER\n" +
									"\tWHERE Category = ?) as res4\n" +
									"ON res1.AttrID = res4.AttrID\n" +
									"\n" +
									"order by numRat desc;";
							PreparedStatement stmnt = con.prepareStatement(query);
							stmnt.setInt(1,filterCity.getCityID());
							stmnt.setString(2, filterAttr);
							stmnt.setInt(3,filterCity.getCityID());
							stmnt.setString(4, filterCat);
							ResultSet resultSet = stmnt.executeQuery();
							while (resultSet.next()) {
								int attrID = resultSet.getInt("attrID");
								String name = resultSet.getString("Name");
								String address = resultSet.getString("Address");
								String categories = resultSet.getString("Category");
								int avgRat = resultSet.getInt("avgRat");
								int numRat = resultSet.getInt("numRat");
								Attraction a = new Attraction(name, filterCity, attrID);
								a.setAddress(address);
								a.setCategoriesList(categories);
								a.setAvgRat(avgRat);
								a.setNumRat(numRat);
								attrList.add(a);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						//city and attraction
						try {
							Connection con = DBModel.getInstance().getConnection();
							String query = "SELECT res1.AttrID, Name, Address, Category, avgRat, numRat\n" +
									"FROM(\n" +
									"\n" +
									"\t(SELECT A.AttrID, LocatedIn, Name, Address\n" +
									"\tFROM ATTRACTION as A, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE LocatedIn = ? and A.AttrID = E.EntityID and E.IsPending = FALSE and A.Name = ? ) as res1\n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, AVG(Rating) as avgRat, COUNT(Rating) as numRat\n" +
									"\tFROM ATTRACTION AS A, REVIEW AS R\n" +
									"\tWHERE A.AttrID = R.EntityID AND A.LocatedIn = ?\n" +
									"\tGROUP BY A.AttrID) as res2\n" +
									"on res1.AttrID = res2.AttrID \n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
									"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
									"\tGROUP BY A.AttrID) as res3\n" +
									"on res1.AttrID = res3.AttrID)\n" +
									"\n" +
									"order by numRat desc;";
							PreparedStatement stmnt = con.prepareStatement(query);
							stmnt.setInt(1,filterCity.getCityID());
							stmnt.setString(2, filterAttr);
							stmnt.setInt(3,filterCity.getCityID());
							ResultSet resultSet = stmnt.executeQuery();
							while (resultSet.next()) {
								int attrID = resultSet.getInt("attrID");
								String name = resultSet.getString("Name");
								String address = resultSet.getString("Address");
								String categories = resultSet.getString("Category");
								int avgRat = resultSet.getInt("avgRat");
								int numRat = resultSet.getInt("numRat");
								Attraction a = new Attraction(name, filterCity, attrID);
								a.setAddress(address);
								a.setCategoriesList(categories);
								a.setAvgRat(avgRat);
								a.setNumRat(numRat);
								attrList.add(a);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} else {

					if (!mainModel.getFilterCategory().equals("")) {
						String filterCat = mainModel.getFilterCategory();
						//city, category
						try {
							Connection con = DBModel.getInstance().getConnection();
							String query = "SELECT res1.AttrID, Name, Address, Category, avgRat, numRat\n" +
									"FROM(\n" +
									"\n" +
									"\t(SELECT A.AttrID, LocatedIn, Name, Address\n" +
									"\tFROM ATTRACTION as A, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE LocatedIn = ? and A.AttrID = E.EntityID and E.IsPending = FALSE) as res1\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, AVG(Rating) as avgRat, COUNT(Rating) as numRat\n" +
									"\tFROM ATTRACTION AS A, REVIEW AS R\n" +
									"\tWHERE A.AttrID = R.EntityID AND A.LocatedIn = ?\n" +
									"\tGROUP BY A.AttrID) as res2\n" +
									"on res1.AttrID = res2.AttrID \n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
									"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
									"\tGROUP BY A.AttrID) as res3\n" +
									"on res1.AttrID = res3.AttrID)\n" +
									"\n" +
									"inner join\n" +
									"\t(SELECT AttrID\n" +
									"\tFROM FALLS_UNDER\n" +
									"\tWHERE Category = ?) as res4\n" +
									"ON res1.AttrID = res4.AttrID\n" +
									"\n" +
									"order by numRat desc;";
							PreparedStatement stmnt = con.prepareStatement(query);
							stmnt.setInt(1,filterCity.getCityID());
							stmnt.setInt(2,filterCity.getCityID());
							stmnt.setString(3, filterCat);
							ResultSet resultSet = stmnt.executeQuery();
							while (resultSet.next()) {
								int attrID = resultSet.getInt("attrID");
								String name = resultSet.getString("Name");
								String address = resultSet.getString("Address");
								String categories = resultSet.getString("Category");
								int avgRat = resultSet.getInt("avgRat");
								int numRat = resultSet.getInt("numRat");
								Attraction a = new Attraction(name, filterCity, attrID);
								a.setAddress(address);
								a.setCategoriesList(categories);
								a.setAvgRat(avgRat);
								a.setNumRat(numRat);
								attrList.add(a);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						//city
						try {
							Connection con = DBModel.getInstance().getConnection();
							String query = "SELECT res1.AttrID, Name, Address, Category, avgRat, numRat\n" +
									"FROM(\n" +
									"\n" +
									"\t(SELECT A.AttrID, LocatedIn, Name, Address\n" +
									"\tFROM ATTRACTION as A, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE LocatedIn = ? and A.AttrID = E.EntityID and E.IsPending = FALSE) as res1\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, AVG(Rating) as avgRat, COUNT(Rating) as numRat\n" +
									"\tFROM ATTRACTION AS A, REVIEW AS R\n" +
									"\tWHERE A.AttrID = R.EntityID AND A.LocatedIn = ?\n" +
									"\tGROUP BY A.AttrID) as res2\n" +
									"on res1.AttrID = res2.AttrID \n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
									"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
									"\tGROUP BY A.AttrID) as res3\n" +
									"on res1.AttrID = res3.AttrID)\n" +
									"\n" +
									"order by numRat desc;";
							PreparedStatement stmnt = con.prepareStatement(query);
							stmnt.setInt(1,filterCity.getCityID());
							stmnt.setInt(2,filterCity.getCityID());
							ResultSet resultSet = stmnt.executeQuery();
							while (resultSet.next()) {
								int attrID = resultSet.getInt("attrID");
								String name = resultSet.getString("Name");
								String address = resultSet.getString("Address");
								String categories = resultSet.getString("Category");
								int avgRat = resultSet.getInt("avgRat");
								int numRat = resultSet.getInt("numRat");
								Attraction a = new Attraction(name, filterCity, attrID);
								a.setAddress(address);
								a.setCategoriesList(categories);
								a.setAvgRat(avgRat);
								a.setNumRat(numRat);
								attrList.add(a);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}
			} else {
				if (!mainModel.getFilterAttrName().equals("")) {
					String filterAttr = mainModel.getFilterAttrName();
					if (!mainModel.getFilterCategory().equals("")) {
						String filterCat = mainModel.getFilterCategory();
						//attraction, category
						try {
							Connection con = DBModel.getInstance().getConnection();
							String query = "SELECT res1.AttrID, Name, Address, LocatedIn, CityName, Country, Category, avgRat, numRat\n" +
									"FROM(\n" +
									"\n" +
									"\t(SELECT A.AttrID, LocatedIn, Name, Address\n" +
									"\tFROM ATTRACTION as A, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = E.EntityID and E.IsPending = FALSE and A.Name = ? ) as res1\n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, AVG(Rating) as avgRat, COUNT(Rating) as numRat\n" +
									"\tFROM ATTRACTION AS A, REVIEW AS R\n" +
									"\tWHERE A.AttrID = R.EntityID\n" +
									"\tGROUP BY A.AttrID) as res2\n" +
									"on res1.AttrID = res2.AttrID \n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
									"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
									"\tGROUP BY A.AttrID) as res3\n" +
									"on res1.AttrID = res3.AttrID)\n" +
									"\n" +
									"inner join\n" +
									"\t(SELECT AttrID\n" +
									"\tFROM FALLS_UNDER\n" +
									"\tWHERE Category = ?) as res4\n" +
									"ON res1.AttrID = res4.AttrID\n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT DISTINCT C.CityID as CityID, C.Country, C.Name as CityName\n" +
									"    FROM CITY as C, ATTRACTION as A\n" +
									"    WHERE A.LocatedIn = C.CityID) as res5\n" +
									"on res1.LocatedIn = res5.CityID\n" +
									"\n" +
									"order by numRat desc;";
							PreparedStatement stmnt = con.prepareStatement(query);
							stmnt.setString(1, filterAttr);
							stmnt.setString(2, filterCat);
							ResultSet resultSet = stmnt.executeQuery();
							while (resultSet.next()) {
								int attrID = resultSet.getInt("attrID");
								String name = resultSet.getString("Name");
								String address = resultSet.getString("Address");
								String categories = resultSet.getString("Category");
								int avgRat = resultSet.getInt("avgRat");
								int numRat = resultSet.getInt("numRat");
								int cityID = resultSet.getInt("LocatedIn");
								String country = resultSet.getString("Country");
								String cityName = resultSet.getString("CityName");
								City c = new City(cityName, cityID, country, null);
								Attraction a = new Attraction(name, c, attrID);
								a.setAddress(address);
								a.setCategoriesList(categories);
								a.setAvgRat(avgRat);
								a.setNumRat(numRat);
								attrList.add(a);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						//attraction
						try {
							Connection con = DBModel.getInstance().getConnection();
							String query = "SELECT res1.AttrID, Name, Address, LocatedIn, CityName, Country, Category, avgRat, numRat\n" +
									"FROM(\n" +
									"\n" +
									"\t(SELECT A.AttrID, LocatedIn, Name, Address\n" +
									"\tFROM ATTRACTION as A, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = E.EntityID and E.IsPending = FALSE and A.Name = ? ) as res1\n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, AVG(Rating) as avgRat, COUNT(Rating) as numRat\n" +
									"\tFROM ATTRACTION AS A, REVIEW AS R\n" +
									"\tWHERE A.AttrID = R.EntityID\n" +
									"\tGROUP BY A.AttrID) as res2\n" +
									"on res1.AttrID = res2.AttrID \n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
									"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
									"\tGROUP BY A.AttrID) as res3\n" +
									"on res1.AttrID = res3.AttrID)\n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT DISTINCT C.CityID as CityID, C.Country, C.Name as CityName\n" +
									"    FROM CITY as C, ATTRACTION as A\n" +
									"    WHERE A.LocatedIn = C.CityID) as res5\n" +
									"on res1.LocatedIn = res5.CityID\n" +
									"\n" +
									"order by numRat desc;";
							PreparedStatement stmnt = con.prepareStatement(query);
							stmnt.setString(1, filterAttr);
							ResultSet resultSet = stmnt.executeQuery();
							while (resultSet.next()) {
								int attrID = resultSet.getInt("attrID");
								String name = resultSet.getString("Name");
								String address = resultSet.getString("Address");
								String categories = resultSet.getString("Category");
								int avgRat = resultSet.getInt("avgRat");
								int numRat = resultSet.getInt("numRat");
								int cityID = resultSet.getInt("LocatedIn");
								String cityName = resultSet.getString("CityName");
								String country = resultSet.getString("Country");
								City c = new City(cityName, cityID, country, null);
								Attraction a = new Attraction(name, c, attrID);
								a.setAddress(address);
								a.setCategoriesList(categories);
								a.setAvgRat(avgRat);
								a.setNumRat(numRat);
								attrList.add(a);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} else {

					if (!mainModel.getFilterCategory().equals("")) {
						String filterCat = mainModel.getFilterCategory();
						//category
						try {
							Connection con = DBModel.getInstance().getConnection();
							String query = "SELECT res1.AttrID, Name, Address, LocatedIn, CityName, Country, Category, avgRat, numRat\n" +
									"FROM(\n" +
									"\n" +
									"\t(SELECT A.AttrID, LocatedIn, Name, Address\n" +
									"\tFROM ATTRACTION as A, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = E.EntityID and E.IsPending = FALSE) as res1\n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, AVG(Rating) as avgRat, COUNT(Rating) as numRat\n" +
									"\tFROM ATTRACTION AS A, REVIEW AS R\n" +
									"\tWHERE A.AttrID = R.EntityID\n" +
									"\tGROUP BY A.AttrID) as res2\n" +
									"on res1.AttrID = res2.AttrID \n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT A.AttrID, GROUP_CONCAT(F.Category) as Category\n" +
									"\tFROM ATTRACTION AS A, FALLS_UNDER AS F, REVIEWABLE_ENTITY as E\n" +
									"\tWHERE A.AttrID = F.AttrID and A.AttrID = E.EntityID and E.IsPending = FALSE\n" +
									"\tGROUP BY A.AttrID) as res3\n" +
									"on res1.AttrID = res3.AttrID)\n" +
									"\n" +
									"inner join\n" +
									"\t(SELECT AttrID\n" +
									"\tFROM FALLS_UNDER\n" +
									"\tWHERE Category = ?) as res4\n" +
									"ON res1.AttrID = res4.AttrID\n" +
									"\n" +
									"inner join \n" +
									"\t(SELECT DISTINCT C.CityID as CityID, C.Country, C.Name as CityName\n" +
									"    FROM CITY as C, ATTRACTION as A\n" +
									"    WHERE A.LocatedIn = C.CityID) as res5\n" +
									"on res1.LocatedIn = res5.CityID\n" +
									"\n" +
									"order by numRat desc;";
							PreparedStatement stmnt = con.prepareStatement(query);
							stmnt.setString(1, filterCat);
							ResultSet resultSet = stmnt.executeQuery();
							while (resultSet.next()) {
								int attrID = resultSet.getInt("attrID");
								String name = resultSet.getString("Name");
								String address = resultSet.getString("Address");
								String categories = resultSet.getString("Category");
								int avgRat = resultSet.getInt("avgRat");
								int numRat = resultSet.getInt("numRat");
								int cityID = resultSet.getInt("LocatedIn");
								String cityName = resultSet.getString("CityName");
								String country = resultSet.getString("Country");
								City c = new City(cityName, cityID, country, null);
								Attraction a = new Attraction(name, c, attrID);
								a.setAddress(address);
								a.setCategoriesList(categories);
								a.setAvgRat(avgRat);
								a.setNumRat(numRat);
								attrList.add(a);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						//nothing
					}

				}
			}
		}


		ObservableList tableAttractionList = FXCollections.observableList(attrList);
		tblAttractions.setItems(tableAttractionList);
				
	}

	@FXML
	public void handleAddPressed() {
		showScreen("../View/NewAttractionForm.fxml","New Attraction");
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
