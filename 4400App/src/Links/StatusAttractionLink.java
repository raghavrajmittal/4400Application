package Links;

import Database.DBModel;
import Model.Attraction;
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
public class StatusAttractionLink extends Hyperlink {
    private Attraction statusAttraction;

    public StatusAttractionLink(Attraction att) {
        statusAttraction = att;
        super.setText("Status");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Status");
                alert.setContentText("Do you Approve/Delete to unsuspend this user");
                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType approve = new ButtonType("Approve");
                ButtonType delete = new ButtonType("Delete");
                alert.getButtonTypes().setAll(cancel, approve, delete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == approve) {
                    statusAttraction.setIsPending(false);
                    try {
                        Connection con = DBModel.getInstance().getConnection();
                        String query = "UPDATE REVIEWABLE_ENTITY\n" +
                                "SET IsPending = FALSE\n" +
                                "WHERE EntityID = ?;";
                        PreparedStatement stmnt = con.prepareStatement(query);
                        stmnt.setInt(1, statusAttraction.getAttractionID());
                        stmnt.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Main.showScreen("../View/PendingAttractionList.fxml");
                } else if (result.get() == delete) {
                    try {
                        Connection con = DBModel.getInstance().getConnection();
                        String query = "DELETE FROM ATTRACTION WHERE Name = ?;";
                        PreparedStatement stmnt = con.prepareStatement(query);
                        stmnt.setString(1, statusAttraction.getName());
                        stmnt.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Main.showScreen("../View/PendingAttractionList.fxml");
                } else {
                    alert.close();
                }
            }
        });
    }
}
