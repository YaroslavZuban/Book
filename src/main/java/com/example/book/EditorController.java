package com.example.book;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static com.example.book.PersonList.PERSONLIST;

public class EditorController implements Initializable {
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
    public static Subscriber subscriber = new Subscriber();
    public static int personId;

    public void handleSave(ActionEvent actionEvent) {
        if (validateInput()) {
            Subscriber person = new Subscriber();

            person.setTitle(titleField.getText());
            person.setLastName(lastNameField.getText());
            person.setFirstName(firstNameField.getText());
            person.setPhoneMobile(mobileField.getText());
            person.setPhoneOfficial(officialField.getText());
            person.setEmail(emailField.getText());
            person.setWebsite(websiteField.getText());

            PERSONLIST.set(personId, person);

            ((Stage) saveButton.getScene().getWindow()).close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Успешный");
            alert.setHeaderText("Контакт изменен");
            alert.setContentText("Контакт успешно изменен");
            alert.showAndWait();

            SubscriberWriter.writeToFile(PersonList.PERSONLIST);
        }
    }

    public void handleCancel(ActionEvent actionEvent) {
        ((Stage) saveButton.getScene().getWindow()).close();
    }

    private void setData() {
        titleField.setText(subscriber.getTitle());
        firstNameField.setText(subscriber.getFirstName());
        lastNameField.setText(subscriber.getLastName());
        officialField.setText(subscriber.getPhoneOfficial());
        mobileField.setText(subscriber.getPhoneMobile());
        emailField.setText(subscriber.getEmail());
        websiteField.setText(subscriber.getWebsite());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setData();
    }
}
