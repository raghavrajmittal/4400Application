package Links;

import Database.DBModel;
import Model.User;
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
public class UserSuspendLink extends Hyperlink {
    private User suspendUser;

    public UserSuspendLink(User user) {
        suspendUser = user;

        if (suspendUser.getSuspended()) {
            super.setText("Yes");
        } else {
            super.setText("No");
        }
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                if (suspendUser.getSuspended()) {
                    alert.setTitle("Unsuspend");
                    alert.setContentText("Do you want to unsuspend this user");
                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    ButtonType unsuspend = new ButtonType("Unsuspend");
                    alert.getButtonTypes().setAll(cancel, unsuspend);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == unsuspend) {
                        suspendUser.setIsSuspended(false);
                        try {
                            Connection con = DBModel.getInstance().getConnection();
                            String query = "UPDATE USER\n" +
                                    "SET IsSuspended = FALSE\n" +
                                    "WHERE Email = ?";
                            PreparedStatement stmnt = con.prepareStatement(query);
                            stmnt.setString(1, suspendUser.getEmail());
                            stmnt.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Main.showScreen("../View/UsersList.fxml");
                    } else {
                        alert.close();
                    }
                } else {
                    alert.setTitle("Suspend");
                    alert.setContentText("Do you want to suspend this user");
                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    ButtonType suspend = new ButtonType("Suspend");
                    alert.getButtonTypes().setAll(cancel, suspend);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == suspend) {
                        suspendUser.setIsSuspended(true);
                        try {
                            Connection con = DBModel.getInstance().getConnection();
                            String query = "UPDATE USER\n" +
                                    "SET IsSuspended = TRUE\n" +
                                    "WHERE Email = ?";
                            PreparedStatement stmnt = con.prepareStatement(query);
                            stmnt.setString(1, suspendUser.getEmail());
                            stmnt.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Main.showScreen("../View/UsersList.fxml");
                    } else {
                        alert.close();
                    }
                }
            }
        });
    }
}
