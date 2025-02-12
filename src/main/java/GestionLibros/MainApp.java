package GestionLibros;

import Util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import entities.Autor;
import DAO.IAutorImpl;
import javafx.stage.StageStyle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage Stage) throws Exception {
        primaryStage = Stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/bibliotecahiberfx/Main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Biblioteca Niko");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void switchScene(String fxml) throws Exception {
        FXMLLoader fXMLLoader = new FXMLLoader(MainApp.class.getResource(fxml));
        Scene scene = new Scene(fXMLLoader.load());
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();  // Lanza la aplicación
    }
}
