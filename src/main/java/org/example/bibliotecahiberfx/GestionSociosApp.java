package org.example.bibliotecahiberfx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GestionSociosApp {

    private static Stage primaryStage;


    public void start(Stage Stage) throws Exception {

        primaryStage = Stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GestionSocios-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Gestion Libros");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    public static void main(String[] args) {
        launch();
    }
}
