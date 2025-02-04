module org.example.bibliotecahiberfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.bibliotecahiberfx to javafx.fxml;
    exports org.example.bibliotecahiberfx;
}