package Controller;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Model.City;
import Model.Attraction;
import Model.Category;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewAttractionFormController extends BasicController {

	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtAddress;
	
	@FXML
	private TextField txtDescription;
	
	@FXML
	private TextField txtHours;
	
	@FXML
	private TextField txtContact;
	
	@FXML
	private TextArea txtComment;
	
	@FXML
	private ComboBox<City> cmbCity;
	
	@FXML
	private ComboBox<Category> cmbCategory;
	
	@FXML
	private Label lblRating;
	
	@FXML
	private Slider sldRating;
	
	DBModel mainModel = DBModel.getInstance();
	
	@FXML
	public void initialize() {
		
		sldRating.valueProperty().addListener((obs,oldVal,newVal)->
				sldRating.setValue((newVal.intValue())));
		lblRating.textProperty().bind(Bindings.format("%.0f", sldRating.valueProperty()));

		List<Category> catList = new ArrayList<>();
		//Populate catList with our categories
		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT * FROM CATEGORY order by Cname ASC";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("CName");
				Category c = new Category(name);
				catList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<City> cityList = new ArrayList<>();
		//Populate cityList with our cities


		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT * FROM CITY as C, REVIEWABLE_ENTITY as E WHERE C.CityID = E.EntityID AND E.IsPending = FALSE order by name ASC;";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String name = resultSet.getString("Name");
				String country = resultSet.getString("Country");
				String state = resultSet.getString("State");
				int cityID = resultSet.getInt("CityID");
				City c = new City(name, cityID, country, state);
				cityList.add(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList<Category> cmbCats = FXCollections.observableList(catList);
		ObservableList<City> cmbCities = FXCollections.observableList(cityList);

		cmbCategory.setItems(cmbCats);
		cmbCity.setItems(cmbCities);
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
	public void handleSubmitPressed() {
		if (txtName.getText().equals("") || txtAddress.getText().equals("")
				|| txtDescription.getText().equals("")
				|| txtComment.getText().equals("")) {
			
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please Enter a value for all fields with a * next to it");
			alert.showAndWait();
		} else if (cmbCity.getValue() == null
				|| cmbCategory.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a value for both city and category");
			alert.showAndWait();
		} else if(sldRating.getValue() == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Rating has to be greater than 0");
			alert.showAndWait();

		} else {
			//This is where the add to the DB is made
			String name = txtName.getText();
			String addr = txtAddress.getText();
			String desc = txtDescription.getText();
			String com = txtComment.getText();
			City city = cmbCity.getValue();
			Category cat = cmbCategory.getValue();
			double rating = sldRating.getValue();
			String[] hours = txtHours.getText().split(",");
			String[] contact = txtContact.getText().split(":");
			//Attraction attraction = new Attraction(name, rating, city,addr,desc,cat,com, DBModel.makeEntityID(), hours,contact);

			try {
				Connection con = DBModel.getInstance().getConnection();
				String query = "INSERT INTO REVIEWABLE_ENTITY\n" +
						"VALUES (?, null, now(), ?);";
				PreparedStatement stmnt = con.prepareStatement(query);
				stmnt.setString(1, mainModel.getUser().getEmail());
				stmnt.setBoolean(2, !mainModel.getUser().getIsManager());
				stmnt.execute();

				query = "INSERT INTO ATTRACTION\n" +
						"VALUES (LAST_INSERT_ID(), ?, ?, ?, ?);";
				stmnt = con.prepareStatement(query);
				stmnt.setInt(1, city.getCityID());
				stmnt.setString(2, name);
				stmnt.setString(3, addr);
				stmnt.setString(4, desc);
				stmnt.execute();

				query = "INSERT INTO REVIEW\n" +
						"VALUES (?, LAST_INSERT_ID(), now(), ?, ?);";
				stmnt = con.prepareStatement(query);
				stmnt.setString(1, mainModel.getUser().getEmail());
				stmnt.setString(2, com);
				stmnt.setInt(3, (int) rating);
				stmnt.execute();

				query = "INSERT INTO FALLS_UNDER\n" +
						"VALUES (LAST_INSERT_ID(), ?);";
				stmnt = con.prepareStatement(query);
				stmnt.setString(1, cat.getName());
				stmnt.execute();

				if (!txtContact.getText().trim().equals("")) {
					query = "INSERT INTO CONTACT_INFO\n" +
							"VALUES (LAST_INSERT_ID(), ?, ?);";
					stmnt = con.prepareStatement(query);
					stmnt.setString(1, contact[0]);
					stmnt.setString(2, contact[1]);
					stmnt.execute();
				}

				if (!txtHours.getText().trim().equals("")) {
					query = "INSERT INTO HOURS_OF_OPERATION\n" +
							"VALUES (LAST_INSERT_ID(), ?, ?,?);";
					stmnt = con.prepareStatement(query);
					stmnt.setString(1, hours[0]);
					stmnt.setString(2, hours[1]);
					stmnt.setString(3, hours[2]);
					stmnt.execute();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Success");
			alert.setContentText("Your Attraction has been submitted and is awaiting approval");
			alert.showAndWait();
			if (mainModel.getUser().getIsManager()) {
				showScreen("../View/ManagerWelcome.fxml", "Welcome");
			} else {
				showScreen("../View/Welcome.fxml", "Welcome");
			}
		}
	}
}
