package Controller;

import Database.DBModel;
import Links.UserClassLink;
import Links.UserDeleteLink;
import Links.UserSuspendLink;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UsersListFilteredController extends BasicController{
	
	DBModel mainModel = DBModel.getInstance();

	@FXML
	private TableView<User> tblUsers;
	@FXML
	private TableColumn<User,String> colName;
	@FXML
	private TableColumn<User,String> colDate;
	@FXML
	private TableColumn<User, UserClassLink> colClass;
	@FXML
	private TableColumn<User, UserDeleteLink> colDelete;
	@FXML
	private TableColumn<User, UserSuspendLink> colSuspended;

	private List<User> tableList;

	@FXML
	public void initialize() {

		colName.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		colDate.setCellValueFactory(new PropertyValueFactory<User, String>("dateJoined"));
		colDelete.setCellValueFactory(new PropertyValueFactory<User, UserDeleteLink>("userDeleteHyperLink"));
		colClass.setCellValueFactory(new PropertyValueFactory<User, UserClassLink>("userClassHyperLink"));
        colSuspended.setCellValueFactory(new PropertyValueFactory<User, UserSuspendLink>("userSuspendHyperLink"));

		tableList = new ArrayList<>();

		String filterUser = mainModel.getFilteredUser();

		try {
			Connection con = DBModel.getInstance().getConnection();
			String query = "SELECT Email, DateJoined, IsSuspended, IsManager\n" +
					"FROM USER Where Email Like ? order by DateJoined ASC;";
			PreparedStatement stmnt = con.prepareStatement(query);
			stmnt.setString(1, "%" + filterUser + "%");
			ResultSet resultSet = stmnt.executeQuery();
			while (resultSet.next()) {
				String email = resultSet.getString("Email");
				String dateJoined = resultSet.getString("DateJoined");
				boolean isSuspended = resultSet.getBoolean("IsSuspended");
				boolean isManager = resultSet.getBoolean("IsManager");
				User u = new User(email, null, isManager);
				u.setIsSuspended(isSuspended);
				u.setDateJoined(dateJoined);
				if (!u.getEmail().equals(mainModel.getUser().getEmail())) {
					u.setUserDeleteHyperLink(new UserDeleteLink(u));
					u.setUserClassHyperLink(new UserClassLink(u));
					u.setUserSuspendHyperLink(new UserSuspendLink(u));
				}
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
						if (!u.getEmail().equals(mainModel.getUser().getEmail())) {
							u.setUserDeleteHyperLink(new UserDeleteLink(u));
							u.setUserClassHyperLink(new UserClassLink(u));
							u.setUserSuspendHyperLink(new UserSuspendLink(u));
						}
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
						if (!u.getEmail().equals(mainModel.getUser().getEmail())) {
							u.setUserDeleteHyperLink(new UserDeleteLink(u));
							u.setUserClassHyperLink(new UserClassLink(u));
							u.setUserSuspendHyperLink(new UserSuspendLink(u));
						}
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
