package Links;

import Model.Review;
import Database.DBModel;
import application.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Hyperlink;

/**
 * Created by alyssatan on 7/24/17.
 */
public class EditReviewLink extends Hyperlink{
    private Review review;

    public EditReviewLink(Review linkedreview) {
        review = linkedreview;
        super.setText("Edit");
        super.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBModel.getInstance().setCurrentReview(review);
                Main.showScreen("../View/NewAttractionReview.fxml", "Edit " + DBModel.getInstance().getReview().getName() + " Review");
            }
        });
    }
}
