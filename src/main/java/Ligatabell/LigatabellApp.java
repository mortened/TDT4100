package Ligatabell;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LigatabellApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Ligatabell");
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("LigaApp.fxml")));
        primaryStage.setScene(scene);
        primaryStage.show(); 
}
}
