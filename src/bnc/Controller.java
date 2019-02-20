package bnc;

import static bnc.Email.*;
import static bnc.Main.emails;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ListView<String> listView;
    public TextField emailTextField;
    public TextField emailSubjectTextField;
    public TextArea emailContentTextField = new TextArea();

    private ObservableList<String> items;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items = listView.getItems();

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
        alert.setTitle("Sending Emails...");
        alert.setHeaderText("Message");
        alert.setContentText("Emails Sent!");

        Alert alertPrompt = new Alert(Alert.AlertType.CONFIRMATION, "Send All?", ButtonType.YES, ButtonType.NO);
        alertPrompt.setTitle("Send All Emails");

        alertPrompt.showAndWait();

        if (alertPrompt.getResult() == ButtonType.YES) {
            alert.show();
            for (Map.Entry<String, Email> e : emails.entrySet()) {
                sendEmail("email@email.com",
                        e.getValue().getSubject(),
                        e.getValue().getEmailContent().toString());
            }
        }
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

    @FXML
    public void handleRemoveClick() {
        emails.remove(listView.getSelectionModel().getSelectedItem());
        emailTextField.setText("");
        emailSubjectTextField.setText("");
        emailContentTextField.setText("");

        items.remove(listView.getSelectionModel().getSelectedItem());
    }
}
