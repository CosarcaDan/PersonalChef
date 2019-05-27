package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class appStart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(appStart.class.getResource("login.fxml"));
        primaryStage.setTitle("Personal Chef Login");
        primaryStage.setScene(new Scene(root, 350, 450));
        primaryStage.show();

    }
}
