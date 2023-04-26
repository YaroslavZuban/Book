module com.example.book {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.book to javafx.fxml;
    exports com.example.book;
}