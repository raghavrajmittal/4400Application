package Links;

import Database.DBModel;
import Model.Review;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

/**
 * Created by alyssatan on 7/26/17.
 */
public class DeleteReviewLink extends Hyperlink {
    private Review deleteReview;

    public DeleteReviewLink(Review  delRev) {
        deleteReview = delRev;
        super.setText("Delete");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete");
                alert.setContentText("Do you want to delete this review?");

                ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                ButtonType delete = new ButtonType("Delete");
                alert.getButtonTypes().setAll(cancel, delete);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == delete) {
                    try {
                        Connection con = DBModel.getInstance().getConnection();

                        String query = "DELETE FROM REVIEW WHERE EntityID = ? and Email = ?;";
                        PreparedStatement stmnt = con.prepareStatement(query);
                        stmnt.setInt(1, deleteReview.getEntityID());
                        stmnt.setString(2, deleteReview.getSubmittedBy());
                        stmnt.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Main.showScreen("../View/ManagerWelcome.fxml");
                } else {
                    alert.close();
                }
            }
        });
    }
}
