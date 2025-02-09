package GestionLibros;

import GestionLibros.Controladores.GestionAutoresController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class GestionAutoresApp {
    private static Stage primaryStage;


    public void start(Stage Stage) throws Exception {

        primaryStage = Stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GestionAutores-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Gestion Libros");
        primaryStage.setScene(scene);
        primaryStage.show();


    }
    public static void main(String[] args) {
        launch();
    }
}
