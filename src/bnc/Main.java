package bnc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static bnc.Email.*;

public class Main extends Application {

    public static void main(String[] args) throws Exception{

        List<String> list = new ArrayList<>();

        list = generateEmail();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("BNC International Inc.");
        primaryStage.setScene(new Scene(root, 600, 500));
        primaryStage.show();
    }
}
