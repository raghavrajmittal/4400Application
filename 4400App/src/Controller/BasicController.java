package Controller;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BasicController {
	
	protected final BasicController showScreen(final String path) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(path));
			
			Parent root = loader.load();
			Stage mainStage = Main.getStage();
			
			mainStage.setScene(new Scene(root, 600, 400));
			mainStage.show();
			
			return loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	protected final BasicController showScreen(final String path,
												final String title) {
		BasicController controller = showScreen(path);
		Main.getStage().setTitle(title);
		
		return controller;
				
	}
}
