package io.re2cc.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GymApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/io/re2cc/view/MainView.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("GymJFX - Gym Management System");
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.show();
    }
}
