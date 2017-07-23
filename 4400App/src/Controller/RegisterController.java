package Controller;


import Database.DBModel;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController extends BasicController{
	/**Here are the variables that the 
	 * user enters stuff into */
	@FXML
	private PasswordField pswdField;
	@FXML
	private PasswordField rePswdField;
	@FXML
	private TextField emailField;
	
	
	/**
	 * Create a new user for the database
	 */
	@FXML
	public final void handleCreatePressed() {
		//Here we add a user to the database
		String username = "";
		String passwordOne = "";
		String passwordTwo = "";
		boolean flag = true;
		
		if (null == emailField.getText() || emailField.getText().equals("")) {
			//Display dialog
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please enter a username");
			alert.showAndWait();
			
			flag = false;
		} else if (!checkUsername(emailField.getText())) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please enter a valid email");
			alert.showAndWait();

			flag = false;
		} else if (!checkPassword(pswdField.getText())) {
			flag = false;
		} else if (null == pswdField.getText() || pswdField.getText().equals("")) {
			//Display dialog
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please enter a password");
			alert.showAndWait();
			flag = false;
		} else if (null == rePswdField.getText() || rePswdField.getText().equals("")) {
			//Display dialog
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Please reenter your password");
			alert.showAndWait();
			flag = false;
		} else  {
			passwordOne = pswdField.getText();
			username = emailField.getText();
			passwordTwo = rePswdField.getText();
		}
		
		if (flag) {
			if (!passwordOne.equals(passwordTwo)) {
				//Display Dialog box
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Passwords do not match");
				alert.showAndWait();
				
			} else {
				try {
					Connection con = DBModel.getInstance().getConnection();
					String query = "SELECT Email, IsSuspended, IsManager FROM USER WHERE Email =?;";
					PreparedStatement stmnt = con.prepareStatement(query);
					stmnt.setString(1, username);
					ResultSet resultSet = stmnt.executeQuery();
					String emailVal = null;
					while (resultSet.next()) {
						emailVal = resultSet.getString("Email");
					}
					if (emailVal != null) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("This username already exists");
						alert.showAndWait();
					} else {
						query = "INSERT INTO USER VALUES (?, ?, now(), FALSE, FALSE);";
						stmnt = con.prepareStatement(query);
						stmnt.setString(1, username);
						stmnt.setInt(2, passwordOne.hashCode());
						stmnt.execute();
						showScreen("../view/Login.fxml", "Login");
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				//if username/email is already taken
//				Alert alert = new Alert(Alert.AlertType.ERROR);
//				alert.setTitle("Error");
//				alert.setContentText("This username is already taken");
//				alert.showAndWait();
			}
		}
		
		
	}

	private boolean checkUsername(String username) {
		String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		return matcher.matches();
	}

	private boolean checkPassword(String pass) {
		boolean flag = true;
		String nonAlpha =pass.replaceAll("[*a-zA-Z]", "");

		if (pass.length() < 8) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Password length must be at least 8 characters long");
			alert.showAndWait();
			flag = false;
		} else if (nonAlpha.length() == 0) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Password must contain digit or symbol");
			alert.showAndWait();
			flag = false;
		}

		return flag;
	}
	
	/**
	 * Return to the login/home screen
	 */
	@FXML
	public final void handleBackPressed() {
		showScreen("../view/Login.fxml", "Login");
	}
}
