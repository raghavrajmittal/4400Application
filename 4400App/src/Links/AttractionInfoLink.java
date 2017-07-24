package Links;

import Database.DBModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import Model.Attraction;
import application.Main;

/**
 * Created by alyssatan on 7/24/17.
 */
public class AttractionInfoLink extends Hyperlink{
    private Attraction attraction;

    public AttractionInfoLink(Attraction linkedatt) {
        attraction = linkedatt;
        super.setText("More Info");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBModel.getInstance().setCurrentAttraction(attraction);
                Main.showScreen("../View/AttractionPage.fxml", DBModel.getInstance().getAttraction().getName() + "'s Page");
            }
        });
    }
}
