package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.Objects;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
		primaryStage.setTitle("Spelling checker");
		primaryStage.setScene(new Scene(root, 599, 338));
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
