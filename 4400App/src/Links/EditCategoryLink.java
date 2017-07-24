package Links;

import Model.Category;
import Database.DBModel;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

/**
 * Created by alyssatan on 7/24/17.
 */
public class EditCategoryLink extends Hyperlink{
    private Category category;

    public EditCategoryLink(Category linkedcategory) {
        category = linkedcategory;
        super.setText("Edit");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBModel.getInstance().setCurrentCategory(category);
                Main.showScreen("../View/NewCategoryPage.fxml", "Edit " + DBModel.getInstance().getCategory().getName() + " Category");
            }
        });
    }
}
