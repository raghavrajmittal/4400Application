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
import java.sql.ResultSet;
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

                        String query = "SELECT res1.AttrID as AttrID, cnt\n" +
                                "FROM\n" +
                                "\t(SELECT AttrID\n" +
                                "\tFROM FALLS_UNDER\n" +
                                "\twhere Category = ?) as res1\n" +
                                "\n" +
                                "inner join\n" +
                                "\t(SELECT AttrID, Count(Category) as cnt\n" +
                                "    FROM FALLS_UNDER\n" +
                                "    GROUP BY AttrID) as res2\n" +
                                "\n" +
                                "on res1.AttrID = res2.AttrID; ";
                        PreparedStatement stmnt = con.prepareStatement(query);
                        stmnt.setString(1, deleteCategory.getName());
                        ResultSet resultSet = stmnt.executeQuery();

                        while (resultSet.next()){
                            int attrID = resultSet.getInt("AttrID");
                            int cnt = resultSet.getInt("cnt");
                            if (cnt <= 1) {
                                //Deletes the entity
                                query = "DELETE FROM REVIEWABLE_ENTITY WHERE EntityID=?";
                                stmnt = con.prepareStatement(query);
                                stmnt.setInt(1, attrID );
                                stmnt.execute();
                            }
                        }

                        query = "DELETE FROM CATEGORY WHERE CName = ?;";
                        stmnt = con.prepareStatement(query);
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
