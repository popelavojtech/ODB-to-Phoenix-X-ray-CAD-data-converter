package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Parent root = null;
		try
		{
			
			root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			
		}
		primaryStage.setScene(new Scene(root, 600, 600));
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
