package Links;

import Database.DBModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;
import Model.City;
import application.Main;

/**
 * Created by alyssatan on 7/24/17.
 */
public class CityPageLink extends Hyperlink {
    private City city;

    public CityPageLink(City linkedcity) {
        city = linkedcity;
        super.setText("View City");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBModel.getInstance().setCurrentCity(city);
                Main.showScreen("../View/BasicCityPage.fxml", DBModel.getInstance().getCity().getName() + "'s Page");
            }
        });
    }
}
