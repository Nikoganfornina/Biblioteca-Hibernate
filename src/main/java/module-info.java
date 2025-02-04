module org.example.bibliotecahiberfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens org.example.bibliotecahiberfx to javafx.fxml;
    exports org.example.bibliotecahiberfx;
}