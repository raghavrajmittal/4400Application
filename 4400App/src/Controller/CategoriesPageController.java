package Controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Model.Category;
import Model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoriesPageController extends BasicController {
	
	@FXML
	private ComboBox<String> cmbSort;
	@FXML
	private TableView<Category> tblCategories;
	@FXML
	private TableColumn<Category, String> colName;
	@FXML
	private TableColumn<Category, Integer> colNum;
	@FXML
	private TableColumn<Category, String> colEdit;
	@FXML
	private TableColumn<Category, String> colDel;

	private List<Category> tableList;

	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		//Populate the table
		tableList = new ArrayList<>();
		colName.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
		colNum.setCellValueFactory(new PropertyValueFactory<Category, Integer>("numOfAttr"));

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT res1.CName as CName, ifnull(res2.numAttr, 0) as Num\n" +
					"FROM (\n" +
					"\n" +
					"(SELECT CName\n" +
					"FROM CATEGORY) as res1\n" +
					"\n" +
					"left join\n" +
					"\n" +
					"(SELECT F.Category, COUNT(F.AttrID) as numAttr\n" +
					"FROM FALLS_UNDER AS F, REVIEWABLE_ENTITY AS E\n" +
					"WHERE F.AttrID = E.EntityID and E.IsPending = FALSE\n" +
					"GROUP BY F.Category) as res2\n" +
					"\n" +
					"on res1.CName = res2.Category);";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String category = resultSet.getString("CName");
				int numAttr = resultSet.getInt("Num");
				Category c = new Category(category);
				c.setNumOfAttr(numAttr);
				tableList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList<Category> tblList = FXCollections.observableList(tableList);
		tblCategories.setItems(tblList);
		//Populate the combo box
		List<String> list = new ArrayList<>();
		list.add("A-Z");
		list.add("Z-A");
		list.add("#of Attractions");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
	}
	
	@FXML
	public void handleAddPressed() {
		showScreen("../View/NewCategoryPage.fxml", "New Category");
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
	public void handleSortPressed() {
		//Sort the table and display it
		if (cmbSort.getValue() != null) {
			tableList = new ArrayList<>();
			if (cmbSort.getValue().equals("A-Z")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT res1.CName as CName, ifnull(res2.numAttr, 0) as Num\n" +
							"FROM (\n" +
							"\n" +
							"(SELECT CName\n" +
							"FROM CATEGORY) as res1\n" +
							"\n" +
							"left join\n" +
							"\n" +
							"(SELECT F.Category, COUNT(F.AttrID) as numAttr\n" +
							"FROM FALLS_UNDER AS F, REVIEWABLE_ENTITY AS E\n" +
							"WHERE F.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"GROUP BY F.Category) as res2\n" +
							"\n" +
							"on res1.CName = res2.Category) order by CName ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String category = resultSet.getString("CName");
						int numAttr = resultSet.getInt("Num");
						Category c = new Category(category);
						c.setNumOfAttr(numAttr);
						tableList.add(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Z-A")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT res1.CName as CName, ifnull(res2.numAttr, 0) as Num\n" +
							"FROM (\n" +
							"\n" +
							"(SELECT CName\n" +
							"FROM CATEGORY) as res1\n" +
							"\n" +
							"left join\n" +
							"\n" +
							"(SELECT F.Category, COUNT(F.AttrID) as numAttr\n" +
							"FROM FALLS_UNDER AS F, REVIEWABLE_ENTITY AS E\n" +
							"WHERE F.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"GROUP BY F.Category) as res2\n" +
							"\n" +
							"on res1.CName = res2.Category) order by CName DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String category = resultSet.getString("CName");
						int numAttr = resultSet.getInt("Num");
						Category c = new Category(category);
						c.setNumOfAttr(numAttr);
						tableList.add(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("#of Attractions")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT res1.CName as CName, ifnull(res2.numAttr, 0) as Num\n" +
							"FROM (\n" +
							"\n" +
							"(SELECT CName\n" +
							"FROM CATEGORY) as res1\n" +
							"\n" +
							"left join\n" +
							"\n" +
							"(SELECT F.Category, COUNT(F.AttrID) as numAttr\n" +
							"FROM FALLS_UNDER AS F, REVIEWABLE_ENTITY AS E\n" +
							"WHERE F.AttrID = E.EntityID and E.IsPending = FALSE\n" +
							"GROUP BY F.Category) as res2\n" +
							"\n" +
							"on res1.CName = res2.Category) order by Num DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String category = resultSet.getString("CName");
						int numAttr = resultSet.getInt("Num");
						Category c = new Category(category);
						c.setNumOfAttr(numAttr);
						tableList.add(c);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ObservableList<Category> tblList = FXCollections.observableList(tableList);
			tblCategories.setItems(tblList);
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort choice");
			alert.showAndWait();
		}
	}
}
