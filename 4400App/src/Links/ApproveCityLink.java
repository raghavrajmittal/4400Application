package Links;

import Database.DBModel;
import Model.City;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

/**
 * Created by alyssatan on 7/24/17.
 */
public class ApproveCityLink extends Hyperlink {
    private City approveCity;

    public ApproveCityLink(City city) {
        approveCity = city;
        super.setText("Approve");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Approve");
                alert.setContentText("Do you want to approve this city");

                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType approve = new ButtonType("Approve");
                alert.getButtonTypes().setAll(cancel, approve);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == approve) {
                    try {
                        approveCity.setIsPending(false);
                        Connection con = DBModel.getInstance().getConnection();
                        String query = "UPDATE REVIEWABLE_ENTITY\n" +
                                "SET IsPending = FALSE\n" +
                                "WHERE EntityID = ?;";
                        PreparedStatement stmnt = con.prepareStatement(query);
                        stmnt.setInt(1, approveCity.getCityID());
                        stmnt.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Main.showScreen("../View/PendingCitiesList.fxml");
                } else {
                    alert.close();
                }
            }
        });
    }
}
