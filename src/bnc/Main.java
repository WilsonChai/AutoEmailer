package bnc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

import static bnc.Email.*;

public class Main extends Application {

    static Map<String, Email> emails = new HashMap<>();

    public static void main(String[] args) throws Exception{

        emails = generateEmail();
        for(Map.Entry<String, Email> email : emails.entrySet())
            System.out.println(email.getKey() + ": " + email.getValue().getEmail());
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
