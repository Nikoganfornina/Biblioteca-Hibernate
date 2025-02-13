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
    @FXML
    private ListView<Libro> listLibros; // Lista de libros para modificar
    @FXML
    private TextField txtTituloMod, txtEditorialMod, txtAnioMod, txtISBNMod; // Campos para modificar datos del libro
    @FXML
    private ListView<Autor> listaAutoresMod; // Lista de autores para modificar
    @FXML
    private Button btnGuardarMod; // Botón para guardar la modificación

    @FXML
    private TableView<Libro> tablalibrosmod;
    @FXML
    private TableColumn<Libro, String> colTituloTableviewmod;
    @FXML
    private TableColumn<Libro, String> colAutorTableviewmod;

    @FXML
    private void volver() throws Exception {
        long inicio = System.nanoTime();
        MainApp.switchScene("/org/example/bibliotecahiberfx/Main-view.fxml");
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mYendo a la pantalla principal... \u001B[36m" + tiempo + " ms\u001B[0m");
    }

    @FXML
    private void actualizarTablaAutores() {
        IlibroImpl libroDao = new IlibroImpl();
        List<Libro> libros = libroDao.findAll(); // Obtener todos los libros desde la base de datos

        // Crear una lista observable con los libros
        ObservableList<Libro> librosObservable = FXCollections.observableArrayList(libros);

        // Establecer los libros como los elementos de la tabla
        tablalibrosmod.setItems(librosObservable);

        // Asignar los valores a las columnas de la tabla
        colTituloTableviewmod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colAutorTableviewmod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor().getNombre()));
    }


    public void actualizarListaLibros() {
        IlibroImpl libroDao = new IlibroImpl();
        List<Libro> libros = libroDao.findAll();
        ObservableList<Libro> librosObservable = FXCollections.observableArrayList(libros);
        listLibros.setItems(librosObservable);
    }


    private void cargarDatosTabla() {
        colTitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor().getNombre()));
        colEditorial.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));
        colAnio.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFechapublicacion()).asObject());
        colISBN.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));

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
        colTitulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTitulo()));
        colAutor.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAutor().getNombre()));
        listLibros.setOnMouseClicked(event -> seleccionarLibroModificar());

        actualizarListaLibros();
        actualizarTablaAutores();
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


    @FXML
    public void guardarModificacion() {
        Libro libroSeleccionado = listLibros.getSelectionModel().getSelectedItem();

        if (libroSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un libro para modificar.", Alert.AlertType.ERROR);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Seguro que quieres modificar este libro?", ButtonType.YES, ButtonType.NO);
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                // Modificar los datos del libro
                libroSeleccionado.setTitulo(txtTituloMod.getText());
                libroSeleccionado.setEditorial(txtEditorialMod.getText());
                libroSeleccionado.setFechapublicacion(Integer.parseInt(txtAnioMod.getText()));
                libroSeleccionado.setIsbn(txtISBNMod.getText());

                // Actualizar el autor
                Autor autorSeleccionado = listaAutoresMod.getSelectionModel().getSelectedItem();
                libroSeleccionado.setAutor(autorSeleccionado);

                // Guardar la modificación en la base de datos
                IlibroImpl libroDao = new IlibroImpl();
                Libro libroActualizado = libroDao.update(libroSeleccionado);

                if (libroActualizado != null) {
                    lblMensaje.setText("Libro modificado con éxito");
                    lblMensaje.setStyle("-fx-text-fill: blue;");
                    actualizarListaLibros(); // Refrescar la lista de libros
                } else {
                    mostrarAlerta("Error", "No se pudo modificar el libro.", Alert.AlertType.ERROR);
                }
            }
        });
    }


    @FXML
    public void seleccionarLibroModificar() {
        Libro libroSeleccionado = listLibros.getSelectionModel().getSelectedItem();
        if (libroSeleccionado != null) {
            // Cargar datos del libro en los campos de texto
            txtTituloMod.setText(libroSeleccionado.getTitulo());
            txtEditorialMod.setText(libroSeleccionado.getEditorial());
            ObservableList<Autor> listaAutores = FXCollections.observableArrayList(libroSeleccionado.getAutor());

            txtAnioMod.setText(String.valueOf(libroSeleccionado.getFechapublicacion()));
            txtISBNMod.setText(libroSeleccionado.getIsbn());
            listaAutoresMod.setItems(listaAutores);
            // Seleccionar el autor
            listaAutoresMod.getSelectionModel().select(libroSeleccionado.getAutor());
        }
    }


}
