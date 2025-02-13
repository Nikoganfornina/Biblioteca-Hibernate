package GestionLibros.Controladores;
import DAO.IAutorImpl;
import DAO.IlibroImpl;
import GestionLibros.MainApp;
import entities.Autor;
import entities.Libro;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private ListView<Libro>  listLibrosDisponibles; // Lista de libros para modificar
    @FXML
    private TableView<Libro> tablaLibrosmodificar , tablaLibrosEliminar;
    @FXML
    private TableColumn<Libro, String> colTituloTableviewmod, colAutorTableviewmod , colAutorEliminar ,colTituloEliminar,colEditorialEliminar , colAnioEliminar , colISBNEliminar;

    @FXML
    private void volver() throws Exception {
        long inicio = System.nanoTime();
        MainApp.switchScene("/org/example/bibliotecahiberfx/Main-view.fxml");
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mYendo a la pantalla principal... \u001B[36m" + tiempo + " ms\u001B[0m");
    }

    @FXML
    public void guardarLibro() {
        String titulo = txtTitulo.getText();
        String isbn = txtISBN.getText();
        Autor autor = ListaAutoresparaLibro.getSelectionModel().getSelectedItem();
        String editorial = txtEditorial.getText();
        String fechaPublicacion = txtFechaPublicacion.getText();

        if (titulo.isEmpty() || isbn.isEmpty() || autor == null || editorial.isEmpty() || fechaPublicacion.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Faltan campos obligatorios");
            alert.setContentText("Por favor, complete todos los campos");
            alert.showAndWait();
            return;
        }

        try {
            Integer.parseInt(fechaPublicacion);
            if (fechaPublicacion.length() != 4) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Formato de fecha incorrecto");
                alert.setContentText("Por favor, ingrese una fecha válida (aaaa)");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Formato de fecha incorrecto");
            alert.setContentText("Por favor, ingrese una fecha válida (aaaa)");
            alert.showAndWait();
            return;
        }

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setIsbn(isbn);
        libro.setAutor(autor);
        libro.setEditorial(editorial);

        IlibroImpl libroDao = new IlibroImpl();
        libroDao.save(libro);

        txtTitulo.clear();
        txtISBN.clear();
        ListaAutoresparaLibro.getSelectionModel().clearSelection();
        txtEditorial.clear();
        txtFechaPublicacion.clear();
        Alert alert = new Alert( Alert.AlertType .INFORMATION , "El libro ha sido agregado con éxito" );
        ActualizarListas();
    }

    @FXML
    public void ListaAutores() {
        IAutorImpl autorDao = new IAutorImpl();
        List<Autor> autores = autorDao.findAll();
        ObservableList<Autor> obsAutores = FXCollections.observableArrayList(autores);
        ListaAutoresparaLibro.setItems(obsAutores);
    }

    public void ListaLibros() {
        IlibroImpl libroDao = new IlibroImpl();
        List<Libro> libros = libroDao.findAll();
        ObservableList<Libro> obsLibros = FXCollections.observableArrayList(libros);
        listLibrosDisponibles.setItems(obsLibros);
    }

    @FXML
    public void tablaLibrosMod() {
        // Limpiar columnas existentes para evitar duplicados
        tablaLibrosmodificar.getColumns().clear();

        // Crear columnas
        colTituloTableviewmod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colAutorTableviewmod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor().getNombre()));

        // Agregar columnas a la tabla
        tablaLibrosmodificar.getColumns().addAll(colTituloTableviewmod, colAutorTableviewmod);

        // Agregar datos a la tabla
        IlibroImpl libroDao = new IlibroImpl();
        List<Libro> libros = libroDao.findAll();
        ObservableList<Libro> obsLibros = FXCollections.observableArrayList(libros);
        tablaLibrosmodificar.setItems(obsLibros);
    }

    @FXML
    public void tablaLibroEliminar() {
        // Limpiar columnas existentes para evitar duplicados
        tablaLibrosEliminar.getColumns().clear();

        // Configurar columnas
        colISBNEliminar.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        colAnioEliminar.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getFechapublicacion())));
        colEditorialEliminar.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));
        colTituloEliminar.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        colAutorEliminar.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor().getNombre()));

        // Agregar columnas a la tabla
        tablaLibrosEliminar.getColumns().addAll(colTituloEliminar, colAutorEliminar, colISBNEliminar, colAnioEliminar, colEditorialEliminar);

        // Agregar datos a la tabla
        IlibroImpl libroDao = new IlibroImpl();
        List<Libro> libros = libroDao.findAll();
        ObservableList<Libro> obsLibros = FXCollections.observableArrayList(libros);
        tablaLibrosEliminar.setItems(obsLibros);
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

                    tablaLibrosEliminar.getItems().remove(libroSeleccionado);
                    tablaLibrosmodificar.getItems().remove(libroSeleccionado);
                    Alert eliminado = new Alert(Alert.AlertType.INFORMATION, "Libro eliminado con éxito");
                    ActualizarListas();
                }
            });
        } else {
            Alert error = new Alert(Alert.AlertType.INFORMATION, "Tienes que seleccionar un libro");
        }
    }

    @FXML
    public void ActualizarListas() {
        ListaAutores();
        ListaLibros();
        tablaLibrosMod();
        tablaLibroEliminar();
    }

    @FXML
    public void initialize() {
        ActualizarListas();
    }
}








