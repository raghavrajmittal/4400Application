package Links;

import Model.Category;
import Database.DBModel;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Optional;

/**
 * Created by alyssatan on 7/24/17.
 */
public class DeleteCategoryLink extends Hyperlink {
    private Category deleteCategory;

    public DeleteCategoryLink(Category delCat) {
        deleteCategory = delCat;
        super.setText("Delete");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete");
                alert.setContentText("Deleting this category will delete all attractions solely containing this category");

                ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                ButtonType delete = new ButtonType("Delete");
                alert.getButtonTypes().setAll(cancel, delete);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == delete) {
                    try {
                        Connection con = DBModel.getInstance().getConnection();
                        String query = "DELETE FROM CATEGORY WHERE CName = ?;";
                        PreparedStatement stmnt = con.prepareStatement(query);
                        stmnt.setString(1, deleteCategory.getName());
                        stmnt.execute();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Main.showScreen("../View/CategoriesPage.fxml");
                } else {
                    alert.close();
                }
            }
        });
    }
}
