package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
		} catch (IOException e) {
			e.printStackTrace();

		}
		primaryStage.setScene(new Scene(root, 700, 800));
		primaryStage.setMinWidth(500);
		primaryStage.setMinHeight(720);
		primaryStage.setMaxWidth(500);
		primaryStage.setMaxHeight(720);
		primaryStage.setTitle("ODB++ to Phoenix X-ray CAD data converter");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
