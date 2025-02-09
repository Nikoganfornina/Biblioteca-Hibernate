package GestionLibros.Controladores;

import DAO.IAutorImpl;
import GestionLibros.MainApp;
import entities.Autor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.util.List;

public class GestionAutoresController {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtNacionalidad;
    @FXML
    private Button btnGuardar;
    @FXML
    private Label lblMensaje;  // Label para mostrar el mensaje de éxito

    @FXML
    private TableView<Autor> tablaAutores;
    @FXML
    private TableColumn<Autor, String> colNombre;
    @FXML
    private TableColumn<Autor, String> colNacionalidad;

    @FXML
    private void volver() throws Exception {
        long inicio = System.nanoTime();
        MainApp.switchScene("/org/example/bibliotecahiberfx/Main-view.fxml");
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mYendo a la pantalla principal... \u001B[36m" + tiempo + " ms\u001B[0m");
    }

    // Método para guardar un autor
    @FXML
    public void guardarAutor() {
        String nombre = txtNombre.getText();
        String nacionalidad = txtNacionalidad.getText();

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);
        long inicio = System.nanoTime();

        IAutorImpl autorDao = new IAutorImpl();
        autorDao.save(autor);
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mAutor : " + autor + " guardado \u001B[36m" + tiempo + " ms\u001B[0m");

        txtNombre.clear();  // Limpiar el campo de texto Nombre
        txtNacionalidad.clear();  // Limpiar el campo de texto Nacionalidad

        lblMensaje.setText("Autor añadido con éxito");  // Mostrar el mensaje de éxito

        // Crear un Timeline para hacer desaparecer el mensaje después de 3 segundos
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                event -> lblMensaje.setText("")  // Limpiar el texto del mensaje después de 3 segundos
        ));
        timeline.play();  // Reproducir la animación

        // Actualizar la tabla después de guardar el autor
        actualizarTabla();
    }

    // Método para actualizar la tabla con los autores
    public void actualizarTabla() {
        IAutorImpl autorDao = new IAutorImpl();
        List<Autor> autores = autorDao.findAll(); // Obtener todos los autores desde la base de datos

        // Limpiar la tabla
        tablaAutores.getItems().clear();

        // Llenar la tabla con los autores
        tablaAutores.getItems().addAll(autores);
    }

    // Método que se ejecuta cuando se inicializa la vista
    @FXML
    public void initialize() {
        // Configurar las columnas de la tabla
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colNacionalidad.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNacionalidad()));

        // Llamar a actualizarTabla para cargar los autores en la tabla al inicio
        actualizarTabla();
    }
}
