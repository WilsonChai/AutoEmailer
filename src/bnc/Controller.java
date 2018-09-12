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

public class Controller implements Initializable {

    @FXML
    private ListView<String> listView;

    public TextField emailTextField;
    public TextField emailSubjectTextField;
    public TextArea emailContentTextField = new TextArea();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> items = listView.getItems();
        for (Map.Entry<String, Email> entry : emails.entrySet()) {
            items.add(entry.getValue().getClient());
        }

        emailTextField.setText("");
        emailSubjectTextField.setText("");
        emailContentTextField.setWrapText(true);
        emailContentTextField.setText("");
    }

    public void sendEmailButton() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information Dialog");
        alert.setHeaderText("Message");
        alert.setContentText("Emails Sent!");

        alert.show();

        // sendEmail();
    }

    @FXML
    public void handleListClick(MouseEvent arg0) {
        emailTextField.setText(emails.get(listView.getSelectionModel().getSelectedItem()).getEmail());
        emailSubjectTextField.setText(emails.get(listView.getSelectionModel().getSelectedItem()).getSubject());
        emailContentTextField.setText(emails.get(listView.getSelectionModel().getSelectedItem()).getEmailContent().toString());
    }

    @FXML
    public void handleSaveClick() {
        emails.get(listView.getSelectionModel().getSelectedItem()).setEmail(emailTextField.getText());
        emails.get(listView.getSelectionModel().getSelectedItem()).setSubject(emailSubjectTextField.getText());
        emails.get(listView.getSelectionModel().getSelectedItem()).setEmailContent(new StringBuilder(emailContentTextField.getText()));
    }
}
