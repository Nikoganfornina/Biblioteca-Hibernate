package GestionLibros;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GestionLibrosApp {


    private static Stage primaryStage;


    public void start(Stage Stage) throws Exception {

        primaryStage = Stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliotecahiberfx/GestionLibros-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Gestion Libros");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void switchScene(String fxml) throws Exception {
        FXMLLoader fXMLLoader = new FXMLLoader(MainApp.class.getResource(fxml));
        Scene scene = new Scene(fXMLLoader.load());
        primaryStage.setScene(scene);

    }
    public static void main(String[] args) {
        launch();
    }
}
