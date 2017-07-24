package Controller;

import java.util.ArrayList;
import java.util.List;

import Model.Attraction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AttractionReviewsController extends BasicController {

	@FXML
	private Label lblAttractionName;
	@FXML
	private TableView<Attraction> tblReviews;
	@FXML
	private TableColumn<Attraction,String> colName;
	@FXML
	private TableColumn<Attraction,Double> colRate;
	@FXML
	private TableColumn<Attraction,String> colCom;
	@FXML
	private ComboBox<String> cmbSort;
	
	@FXML
	public void initialize() {

		//label name is set
		lblAttractionName.setText(mainModel.getAttraction());

		//Populate combobox and table
		List<String> list = new ArrayList<>();
		list.add("A-Z");
		list.add("Z-A");
		list.add("Rating");
		
		ObservableList<String> cmbList = FXCollections.observableList(list);
		cmbSort.setItems(cmbList);
		
		//Populate the table
	}
	
	@FXML
	public void handleReviewPressed() {
		showScreen("../View/NewAttractionReview.fxml", "New " + mainModel.getAttraction().getName() + " Review");
	}
	
	@FXML
	public void handleBackPressed() {
		showScreen("../View/AttractionPage.fxml", mainModel.getAttraction().getName() + "'s Page");
	}
	
	@FXML
	public void handleSortPressed() {
		if (cmbSort.getValue() != null) {
			if (cmbSort.getValue().equals("A-Z")) {
				
			} else if (cmbSort.getValue().equals("Z-A")) {
				
			} else if (cmbSort.getValue().equals("Rating")) {
				
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Please choose a sort type");
			alert.showAndWait();
		}
	}
}
