package com.example.book;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.book.PersonList.PERSONLIST;

public class HelloController implements Initializable {
    @FXML
    private TableView<Subscriber> tableView;
    @FXML
    private Button editButton, deleteButton;
    @FXML
    private TableColumn<Subscriber, String> firstColumn, lastColumn;

    @FXML
    private TextField searchField, titleField, firstNameField,
            lastNameField, organizationField, officialField,
            mobileField, emailField, websiteField, linkedinField, facebookField;
    @FXML
    private TextArea addressArea;
    @FXML
    private ImageView pictureView;
    @FXML
    private Label welcomeText;

    private List<Subscriber> data;
    private double xOffset = 0;
    private double yOffset = 0;

    public void handleDelete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Удалить контакт");
        alert.setHeaderText("Удалить контакт из списка");
        alert.setContentText("Ты уверен?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Subscriber selectedSubscriber = tableView.getSelectionModel().getSelectedItem();

            PERSONLIST.remove(selectedSubscriber);
        }

        tableView.getSelectionModel().clearSelection();
        SubscriberWriter.writeToFile(PersonList.PERSONLIST);
    }

    public void handleEdit(ActionEvent actionEvent) throws IOException {
        Subscriber selectedSubscriber = tableView.getSelectionModel().getSelectedItem();
        int selectedPersonId = tableView.getSelectionModel().getSelectedIndex();

        EditorController.subscriber=selectedSubscriber;
        EditorController.personId=selectedPersonId;

        tableView.getSelectionModel().clearSelection();
        Parent root = FXMLLoader.load(getClass().getResource("Editor.fxml"));
        EditorController controller = new EditorController();
        Stage stage = new Stage();

        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root);
        stage.setTitle("Редактировать личные данные");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public void handleNew(ActionEvent actionEvent) throws IOException {
        tableView.getSelectionModel().clearSelection();
        Parent root = FXMLLoader.load(getClass().getResource("Add.fxml"));
        Stage stage = new Stage();

        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });

        Scene scene = new Scene(root);
        stage.setTitle("Добавить новый");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            SubscriberRead.readSubscribersFromFile("contacts.txt",PERSONLIST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        firstColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    showDetails(newValue);
                });
        tableView.setItems(PERSONLIST);

        editButton
                .disableProperty()
                .bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));
        deleteButton
                .disableProperty()
                .bind(Bindings.isEmpty(tableView.getSelectionModel().getSelectedItems()));

        filterData();
    }


    private void showDetails(Subscriber general) {
        if (general != null) {
            titleField.setText(general.getTitle());
            firstNameField.setText(general.getFirstName());
            lastNameField.setText(general.getLastName());

            officialField.setText(general.getPhoneOfficial());
            mobileField.setText(general.getPhoneMobile());

            emailField.setText(general.getEmail());
            websiteField.setText(general.getWebsite());
        } else {
            titleField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");

            officialField.setText("");
            mobileField.setText("");
            emailField.setText("");
            websiteField.setText("");
        }
    }

    private void filterData() {
        FilteredList<Subscriber> searchedData = new FilteredList<>(PERSONLIST, e -> true);

        searchField.setOnKeyPressed(e -> {
            tableView.getSelectionModel().clearSelection();
        });

        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(person -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    if (person.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (person.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Subscriber> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        });
    }
}