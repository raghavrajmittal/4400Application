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
public class UserClassLink extends Hyperlink {
    private User classUser;

    public UserClassLink(User user) {
        classUser = user;

        if (classUser.getIsManager()) {
            super.setText("Manager");
        } else {
            super.setText("User");
        }
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING);

                if (classUser.getIsManager()) {
                    alert.setTitle("Demote");
                    alert.setContentText("Do you want to demote this user");
                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    ButtonType demote = new ButtonType("Demote");
                    alert.getButtonTypes().setAll(cancel, demote);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == demote) {
                        classUser.toggleManager();
                        try {
                            Connection con = DBModel.getInstance().getConnection();
                            String query = "UPDATE USER\n" +
                                    "SET IsManager = FALSE\n" +
                                    "WHERE Email = ?;";
                            PreparedStatement stmnt = con.prepareStatement(query);
                            stmnt.setString(1, classUser.getEmail());
                            stmnt.execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Main.showScreen("../View/UsersList.fxml");
                    } else {
                        alert.close();
                    }
                } else {
                    alert.setTitle("Promote");
                    alert.setContentText("Do you want to promote this user");
                    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                    ButtonType promote = new ButtonType("Promote");
                    alert.getButtonTypes().setAll(cancel, promote);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == promote) {
                        classUser.toggleManager();
                        try {
                            Connection con = DBModel.getInstance().getConnection();
                            String query = "UPDATE USER\n" +
                                    "SET IsManager = TRUE\n" +
                                    "WHERE Email = ?;";
                            PreparedStatement stmnt = con.prepareStatement(query);
                            stmnt.setString(1, classUser.getEmail());
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
