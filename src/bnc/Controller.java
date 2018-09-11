package bnc;

import static bnc.Email.*;
import static bnc.Main.emails;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Map;
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

        for (Map.Entry<String, Email> entry : emails.entrySet()) {
            items.add(entry.getValue().getClient());
        }

        email.setText("");
        emailSubject.setText("");
        emailContent.setWrapText(true);
        emailContent.setText("");
    }

    public void sendEmailButton() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information Dialog");
        alert.setHeaderText("Message");
        alert.setContentText("Emails Sent!");

        alert.show();

        // sendEmail();
    }

    @FXML public void handleListClick(MouseEvent arg0) {
        email.setText(emails.get(listView.getSelectionModel().getSelectedItem()).getClient() + "@email.com");
        emailSubject.setText(emails.get(listView.getSelectionModel().getSelectedItem()).generateSubject());
        emailContent.setText(emails.get(listView.getSelectionModel().getSelectedItem()).getEmailContent().toString());
    }
}
