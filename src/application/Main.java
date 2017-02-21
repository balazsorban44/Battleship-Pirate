package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/Game.fxml"));
			Scene scene = new Scene(root);
			String css = this.getClass().getResource("/view/style.css").toExternalForm();
			scene.getStylesheets().add(css);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			primaryStage.setTitle("Battleship Pirate ☠ v1.1 by Balázs Orbán NTNU - 2017");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
