package GestionLibros.Controladores;

import DAO.IAutorImpl;
import DAO.IlibroImpl;
import GestionLibros.MainApp;
import entities.Autor;
import entities.Libro;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableView<Libro> tablaLibrosEliminar;
    @FXML
    private TableColumn<Libro, String> colTitulo;
    @FXML
    private TableColumn<Libro, String> colAutor;
    @FXML
    private TableColumn<Libro, String> colEditorial;
    @FXML
    private TableColumn<Libro, Integer> colAnio;
    @FXML
    private TableColumn<Libro, String> colISBN;

    private ObservableList<Libro> listaLibrosObservable = FXCollections.observableArrayList();

    @FXML
    private void volver() throws Exception {
        long inicio = System.nanoTime();
        MainApp.switchScene("/org/example/bibliotecahiberfx/Main-view.fxml");
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mYendo a la pantalla principal... \u001B[36m" + tiempo + " ms\u001B[0m");
    }

    @FXML
    public void actualizarListaAutores() {
        IAutorImpl autorDao = new IAutorImpl();
        List<Autor> autores = autorDao.findAll();
        ListaAutoresparaLibro.getItems().setAll(autores);
    }

    public void actualizarListaLibros() {
        IlibroImpl libroDao = new IlibroImpl();
        List<Libro> libros = libroDao.findAll();
        listaLibrosObservable.setAll(libros);
    }

    private void cargarDatosTabla() {
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor().getNombre()));
        colEditorial.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));
        colAnio.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFechapublicacion()).asObject());
        colISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));

        tablaLibrosEliminar.setItems(listaLibrosObservable);
        actualizarListaLibros();
    }

    @FXML
    public void guardarLibro() {
        String titulo = txtTitulo.getText();
        String isbn = txtISBN.getText();
        Autor autor = ListaAutoresparaLibro.getSelectionModel().getSelectedItem();
        String editorial = txtEditorial.getText();
        Integer fechapublicacion = Integer.parseInt(txtFechaPublicacion.getText());

        Libro libro = new Libro(null , titulo, isbn, autor, editorial, fechapublicacion);

        IlibroImpl libroDao = new IlibroImpl();
        libroDao.save(libro);

        txtTitulo.clear();
        txtISBN.clear();
        ListaAutoresparaLibro.getSelectionModel().clearSelection();
        txtEditorial.clear();
        txtFechaPublicacion.clear();
        lblMensaje.setText("Libro añadido con éxito");
        lblMensaje.setStyle("-fx-text-fill: green;");
        ocultarMensajeDespuesDeTiempo();
        actualizarListaLibros();
    }

    @FXML
    public void initialize() {
        actualizarListaAutores();
        cargarDatosTabla();
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
        Libro libroSeleccionado = tablaLibrosEliminar.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION, "¿Seguro que quieres eliminar este libro?", ButtonType.YES, ButtonType.NO);
            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    IlibroImpl libroDao = new IlibroImpl();
                    libroDao.delete(libroSeleccionado);
                    listaLibrosObservable.remove(libroSeleccionado);
                    mostrarAlerta("Información", "Libro eliminado con éxito", Alert.AlertType.INFORMATION);
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
