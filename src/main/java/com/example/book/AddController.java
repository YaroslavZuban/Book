package com.example.book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddController {

    @FXML
    private Button cancelField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField mobileField;

    @FXML
    private TextField officialField;

    @FXML
    private Button saveButton;

    @FXML
    private TextField titleField;

    @FXML
    private TextField websiteField;

    @FXML
    void handleCancel(ActionEvent event) {
        ((Stage) saveButton.getScene().getWindow()).close();
    }

    @FXML
    void handleSave(ActionEvent event) {
        if (validateInput()) {

            Subscriber subscriber=new Subscriber();

            subscriber.setTitle(titleField.getText());
            subscriber.setFirstName(firstNameField.getText());
            subscriber.setLastName(lastNameField.getText());
            subscriber.setPhoneOfficial(officialField.getText());
            subscriber.setPhoneMobile(mobileField.getText());
            subscriber.setEmail(emailField.getText());
            subscriber.setWebsite(websiteField.getText());

           // SubscriberList.data.add(subscriber);
            PersonList.PERSONLIST.add(subscriber);

            ((Stage) saveButton.getScene().getWindow()).close();
            SubscriberWriter.writeToFile(PersonList.PERSONLIST);
        }
    }

    private boolean validateInput() {

        String errorMessage = "";

        if (titleField.getText() == null || titleField.getText().length() == 0) {
            errorMessage += "Нет идентификатора электронной почты!\n";
        }

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Нет действительного имени!\n";
        }

        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Нет действительной фамилии!\n";
        }


        if (officialField.getText() == null || officialField.getText().length() == 0) {
            errorMessage += "Нет действительного номера офиса!\n";
        }

        if (mobileField.getText() == null || mobileField.getText().length() == 0) {
            errorMessage += "Нет личного номера телефона!\n";
        }

        if (emailField.getText() == null || emailField.getText().length() == 0) {
            errorMessage += "Нет идентификатора электронной почты!\n";
        }

        if (websiteField.getText() == null || websiteField.getText().length() == 0) {
            errorMessage += "Нет идентификатора электронной почты!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Пустые поля");
            alert.setHeaderText("Пожалуйста, исправьте пустые поля");
            alert.setContentText(errorMessage);
            alert.showAndWait();

            return false;
        }
    }
}
