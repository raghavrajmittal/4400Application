package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Locale.Category;

import Database.DBModel;
import Links.AttractionInfoLink;
import Model.Attraction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;

public class BasicCityPageController extends BasicController {
	
	@FXML
	private Label lblCityName;
	@FXML
	private Label lblAvgRate;
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<Attraction> tblAttractions;
	@FXML
	private TableColumn<Attraction,String> colName;
	@FXML
	private TableColumn<Attraction,String> colLoc;
	@FXML
	private TableColumn<Attraction,String> colCat;
	@FXML
	private TableColumn<Attraction,Double> colAvgRate;
	@FXML
	private TableColumn<Attraction,Integer> colNumRate;
	@FXML
	private TableColumn<Attraction, AttractionInfoLink> colMoreInfo;
	
	DBModel mainModel = DBModel.getInstance();

	private List<Attraction> tableList;
	@FXML
	public void initialize() {

		lblCityName.setText(mainModel.getCity().toString());
		lblAvgRate.setText("" + mainModel.getCity().getAvgRat());
		//Populate table
		tableList = new ArrayList<>();
		colName.setCellValueFactory(new PropertyValueFactory<Attraction, String>("name"));
		colLoc.setCellValueFactory(new PropertyValueFactory<Attraction, String>("address"));
		colCat.setCellValueFactory(new PropertyValueFactory<Attraction, String>("categoriesList"));
		colAvgRate.setCellValueFactory(new PropertyValueFactory<Attraction, Double>("avgRat"));
		colNumRate.setCellValueFactory(new PropertyValueFactory<Attraction, Integer>("numRat"));
		colMoreInfo.setCellValueFactory(new PropertyValueFactory<Attraction, AttractionInfoLink>("infoHyperLink"));

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
					"on res1.AttrID = res3.AttrID);";
			PreparedStatement stmnt = con.prepareStatement(query);
			stmnt.setInt(1, mainModel.getCity().getCityID());
			stmnt.setInt(2, mainModel.getCity().getCityID());
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				int attrID = resultSet.getInt("AttrID");
				String name = resultSet.getString("Name");
				String address = resultSet.getString("Address");
				String categories = resultSet.getString("Category");
				int avgRat = resultSet.getInt("avgRat");
				int numRat = resultSet.getInt("numRat");
				Attraction a = new Attraction(name, mainModel.getCity(), address, null, null, attrID);
				a.setCategoriesList(categories);
				a.setAvgRat(avgRat);
				a.setNumRat(numRat);
				a.setInfoHyperLink(new AttractionInfoLink(a));
				tableList.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList<Attraction> oTableList = FXCollections.observableList(tableList);
		tblAttractions.setItems(oTableList);

		//Populate combobox
		List<String> list = new ArrayList<>();
		list.add("Name A-Z");
		list.add("Name Z-A");
		list.add("Avg Rating");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
		
	}
	
	@FXML
	public void handleBackPressed() {
		if (mainModel.getUser().getIsManager()) {
			showScreen("../View/ManagerWelcome.fxml", "Welcome " + mainModel.getUser().getEmail());
		} else {
			showScreen("../View/Welcome.fxml", "Welcome " + mainModel.getUser().getEmail());

		}
	}
	
	@FXML
	public void handleReviewPressed() {
		showScreen("../View/NewCityReview.fxml", "New" + mainModel.getCity().toString() + "Review");
	}
	
	@FXML
	public void handleViewPressed() {
		showScreen("../View/CityReviews.fxml", mainModel.getCity().toString() + "'s Reviews");
	}
	
	public void handleSortPressed() {
		if (cmbSort.getValue() != null) {
			tableList = new ArrayList<>();

			if (cmbSort.getValue().equals("Name A-Z")) {
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
							"on res1.AttrID = res3.AttrID) order by Name ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, mainModel.getCity().getCityID());
					stmnt.setInt(2, mainModel.getCity().getCityID());
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						int attrID = resultSet.getInt("AttrID");
						String name = resultSet.getString("Name");
						String address = resultSet.getString("Address");
						String categories = resultSet.getString("Category");
						int avgRat = resultSet.getInt("avgRat");
						int numRat = resultSet.getInt("numRat");
						Attraction a = new Attraction(name, mainModel.getCity(), address, null, null, attrID);
						a.setCategoriesList(categories);
						a.setAvgRat(avgRat);
						a.setNumRat(numRat);
						a.setInfoHyperLink(new AttractionInfoLink(a));
						tableList.add(a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Name Z-A")) {
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
							"on res1.AttrID = res3.AttrID) order by Name DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, mainModel.getCity().getCityID());
					stmnt.setInt(2, mainModel.getCity().getCityID());
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						int attrID = resultSet.getInt("AttrID");
						String name = resultSet.getString("Name");
						String address = resultSet.getString("Address");
						String categories = resultSet.getString("Category");
						int avgRat = resultSet.getInt("avgRat");
						int numRat = resultSet.getInt("numRat");
						Attraction a = new Attraction(name, mainModel.getCity(), address, null, null, attrID);
						a.setCategoriesList(categories);
						a.setAvgRat(avgRat);
						a.setNumRat(numRat);
						a.setInfoHyperLink(new AttractionInfoLink(a));
						tableList.add(a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Avg Rating")) {
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
							"on res1.AttrID = res3.AttrID) order by avgRat DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setInt(1, mainModel.getCity().getCityID());
					stmnt.setInt(2, mainModel.getCity().getCityID());
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						int attrID = resultSet.getInt("AttrID");
						String name = resultSet.getString("Name");
						String address = resultSet.getString("Address");
						String categories = resultSet.getString("Category");
						int avgRat = resultSet.getInt("avgRat");
						int numRat = resultSet.getInt("numRat");
						Attraction a = new Attraction(name, mainModel.getCity(), address, null, null, attrID);
						a.setCategoriesList(categories);
						a.setAvgRat(avgRat);
						a.setNumRat(numRat);
						a.setInfoHyperLink(new AttractionInfoLink(a));
						tableList.add(a);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			ObservableList<Attraction> oTableList = FXCollections.observableList(tableList);
			tblAttractions.setItems(oTableList);

		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort type");
			alert.showAndWait();
		}
	}
	
	@FXML
	public void handleDeletePressed() {
		//Delete from database
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle("WARNING!");
		alert.setContentText("Are you sure you want to delete this City?");
		
		ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		ButtonType delete = new ButtonType("Delete");

		alert.getButtonTypes().setAll(cancel, delete);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == delete) {
			try {
				Connection con = DBModel.getInstance().getConnection();
				//This is for the description
				String query = "DELETE FROM REVIEWABLE_ENTITY WHERE EntityID=?";
				PreparedStatement stmnt = con.prepareStatement(query);
				stmnt.setInt(1, mainModel.getCity().getCityID() );
				stmnt.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (mainModel.getUser().getIsManager()) {
				showScreen("../View/ManagerWelcome.fxml", "Welcome");
			} else {
				showScreen("../View/Welcome.fxml", "Welcome");
			}
		} else{
			alert.close();
		}
	}
}
