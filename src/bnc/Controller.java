package bnc;

import static bnc.Email.*;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

public class Controller implements Initializable{

    @FXML private ListView<String> listView;

    @FXML private TextField email;
    @FXML private TextField emailSubject;
    @FXML private TextArea emailContent = new TextArea();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = listView.getItems();

        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");

        email.setText("testEmail");
        emailSubject.setText("testSubject");
        emailContent.setWrapText(true);
        emailContent.setText("test content\ntest content");
    }

    public void sendEmailButton() throws InterruptedException {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information Dialog");
        alert.setHeaderText("Look, an Information Dialog");
        alert.setContentText("Emails Sent!");

        alert.show();

        // sendEmail();
    }
}
