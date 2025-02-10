package GestionLibros.Controladores;

import DAO.IAutorImpl;
import DAO.IlibroImpl;
import GestionLibros.MainApp;
import entities.Autor;
import entities.Libro;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.util.List;

public class GestionLibrosController {

    @FXML
    private ListView<Autor> ListaAutoresparaLibro;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtISBN;
    @FXML
    private TextField txtEditorial;
    @FXML
    private TextField txtFechaPublicacion;
    @FXML
    private Label lblMensaje;
    @FXML
    private ListView<Libro> listLibrosEliminar;

    private ObservableList<Libro> listaLibrosObservable = FXCollections.observableArrayList();

    // Método para volver a la pantalla principal
    @FXML
    private void volver() throws Exception {
        long inicio = System.nanoTime();
        MainApp.switchScene("/org/example/bibliotecahiberfx/Main-view.fxml");
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mYendo a la pantalla principal... \u001B[36m" + tiempo + " ms\u001B[0m");
    }

    @FXML
    // Método para inicializar la lista de autores en el ListView
    public void actualizarListaAutores() {
        IAutorImpl autorDao = new IAutorImpl();
        List<Autor> autores = autorDao.findAll();

        ListaAutoresparaLibro.getItems().clear();
        ListaAutoresparaLibro.getItems().addAll(autores);
    }

    public void actualizarListaLibros() {
        IlibroImpl libroDao = new IlibroImpl();
        List<Libro> libros = libroDao.findAll();

        listaLibrosObservable.clear();  // Limpiar la lista antes de actualizar
        listaLibrosObservable.addAll(libros);
        listLibrosEliminar.setItems(listaLibrosObservable);  // Asignar ObservableList al ListView
    }

    // Método para guardar un nuevo libro
    @FXML
    public void guardarLibro() {
        String titulo = txtTitulo.getText();
        String isbn = txtISBN.getText();
        Autor autor = ListaAutoresparaLibro.getSelectionModel().getSelectedItem();
        String editorial = txtEditorial.getText();
        Integer fechapublicacion = Integer.parseInt(txtFechaPublicacion.getText());

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setIsbn(isbn);
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setFechapublicacion(fechapublicacion);

        IlibroImpl libroDao = new IlibroImpl();
        libroDao.save(libro);

        txtTitulo.clear();
        txtISBN.clear();
        ListaAutoresparaLibro.getSelectionModel().clearSelection();
        txtEditorial.clear();
        txtFechaPublicacion.clear();
        lblMensaje.setText("Autor añadido con éxito");
        lblMensaje.setStyle("-fx-text-fill: green;");
        ocultarMensajeDespuesDeTiempo();

        actualizarListaAutores();
    }

    // Método para inicializar la interfaz (por ejemplo, cargar autores en la lista)
    @FXML
    public void initialize() {
        actualizarListaAutores();
        actualizarListaLibros();
    }

    private void ocultarMensajeDespuesDeTiempo() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                event -> lblMensaje.setText("")
        ));
        timeline.play();
    }

    @FXML
    public void eliminarLibro() {
        Libro libroSeleccionado = listLibrosEliminar.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que quieres eliminar este libro?", ButtonType.YES, ButtonType.NO);
            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    IlibroImpl libroDao = new IlibroImpl();
                    libroDao.delete(libroSeleccionado); // Eliminar de la base de datos

                    // Aquí actualizas la lista después de la eliminación
                    listaLibrosObservable.remove(libroSeleccionado);  // Eliminar del ObservableList

                    mostrarAlerta("Información", "Libro eliminado con éxito", Alert.AlertType.INFORMATION);
                    ocultarMensajeDespuesDeTiempo();
                }
            });
        } else {
            mostrarAlerta("Error", "Debe seleccionar un libro", Alert.AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
