package GestionLibros;

import Util.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import entities.Autor;
import DAO.IAutorImpl;
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
        primaryStage.show();

        // Llamar al método findAll() al arrancar el programa
        listarAutores(); // Asegúrate de llamar a la función aquí
    }

    // Método para listar autores
    public void listarAutores() {
        IAutorImpl autorDao = new IAutorImpl();
        List<Autor> autores = autorDao.findAll();

        // Verificar si hemos obtenido autores y mostrarlos en consola
        if (autores != null && !autores.isEmpty()) {
            System.out.println("Autores encontrados:");
            for (Autor autor : autores) {
                System.out.println("Nombre: " + autor.getNombre() + ", Nacionalidad: " + autor.getNacionalidad());
            }
        } else {
            System.out.println("No se encontraron autores en la base de datos.");
        }
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
