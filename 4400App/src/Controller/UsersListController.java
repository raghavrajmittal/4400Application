package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Database.DBModel;
import Links.UserDeleteLink;
import Model.Category;
import Model.City;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Model.User;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsersListController extends BasicController{
	
	DBModel mainModel = DBModel.getInstance();

	@FXML
	private TableView<User> tblUsers;
	@FXML
	private TableColumn<User,String> colName;
	@FXML
	private TableColumn<User,String> colDate;
	@FXML
	private TableColumn<User,String> colClass;
	@FXML
	private TableColumn<User, UserDeleteLink> colDelete;
	@FXML
	private TableColumn<User,String> colSuspended;

	private List<User> tableList;

	@FXML
	public void initialize() {

		colName.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		colDate.setCellValueFactory(new PropertyValueFactory<User, String>("dateJoined"));
		colDelete.setCellValueFactory(new PropertyValueFactory<User, UserDeleteLink>("userDeleteHyperLink"));

		tableList = new ArrayList<>();

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT Email, DateJoined, IsSuspended, IsManager\n" +
					"FROM USER order by DateJoined ASC;";
			PreparedStatement stmnt = con.prepareStatement(query);
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String email = resultSet.getString("Email");
				String dateJoined = resultSet.getString("DateJoined");
				boolean isSuspended = resultSet.getBoolean("IsSuspended");
				boolean isManager = resultSet.getBoolean("IsManager");
				User u = new User(email, null, isManager);
				u.setIsSuspended(isSuspended);
				u.setDateJoined(dateJoined);
				u.setUserDeleteHyperLink(new UserDeleteLink(u));
				tableList.add(u);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ObservableList<User> tblList = FXCollections.observableList(tableList);
		tblUsers.setItems(tblList);

		//Populate the combobox
		List<String> list = new ArrayList<>();
		list.add("A-Z");
		list.add("Z-A");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
		
		
	}
	@FXML
	private ComboBox<String> cmbSort;
	
	@FXML
	public void handleAddPressed() {
		showScreen("../View/NewUserForm.fxml", "Add New User");
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
					String query = "SELECT Email, DateJoined, IsSuspended, IsManager\n" +
							"FROM USER\n" +
							"ORDER BY Email ASC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String email = resultSet.getString("Email");
						String dateJoined = resultSet.getString("DateJoined");
						boolean isSuspended = resultSet.getBoolean("IsSuspended");
						boolean isManager = resultSet.getBoolean("IsManager");
						User u = new User(email, null, isManager);
						u.setIsSuspended(isSuspended);
						u.setDateJoined(dateJoined);
						tableList.add(u);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (cmbSort.getValue().equals("Z-A")) {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT Email, DateJoined, IsSuspended, IsManager\n" +
							"FROM USER\n" +
							"ORDER BY Email DESC;";
					PreparedStatement stmnt = con.prepareStatement(query);
					ResultSet resultSet = stmnt.executeQuery();
					while (resultSet.next()) {
						String email = resultSet.getString("Email");
						String dateJoined = resultSet.getString("DateJoined");
						boolean isSuspended = resultSet.getBoolean("IsSuspended");
						boolean isManager = resultSet.getBoolean("IsManager");
						User u = new User(email, null, isManager);
						u.setIsSuspended(isSuspended);
						u.setDateJoined(dateJoined);
						tableList.add(u);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			ObservableList<User> tblList = FXCollections.observableList(tableList);
			tblUsers.setItems(tblList);
		}
	}
}
