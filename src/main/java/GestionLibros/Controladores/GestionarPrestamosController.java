package GestionLibros.Controladores;

import DAO.IPrestamosImpl;
import GestionLibros.MainApp;
import entities.Libro;
import entities.Prestamos;
import entities.Socio;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;


import java.time.LocalDate;
import java.util.List;

public class GestionarPrestamosController {

    @FXML
    private ComboBox<Libro> cmbLibro;

    @FXML
    private ComboBox<Socio> cmbSocio;

    @FXML
    private DatePicker dpFechaPrestamo;

    @FXML
    private DatePicker dpFechaDevolucion;

    @FXML
    private Label lblMensaje;

    @FXML
    private TableView<Prestamos> tablaLibrosPrestados;

    @FXML
    private ComboBox<Socio> cmbSocioHistorial;

    @FXML
    private TableView<Prestamos> tablaHistorial;

    private IPrestamosImpl prestamosImpl;

    @FXML
    public void initialize() {
        prestamosImpl = new IPrestamosImpl();

        // Cargar libros y socios en los ComboBox
        cargarLibros();
        cargarSocios();
        cargarSociosHistorial();

        // Configurar las tablas
        configurarTablaPrestamos();
        configurarTablaHistorial();

        // Configurar listener para cargar historial cuando se selecciona un socio
        cmbSocioHistorial.valueProperty().addListener((observable, oldValue, newValue) -> cargarHistorial());

        // Cargar libros prestados actualmente al iniciar
        actualizarTablaPrestamos();
    }



    @FXML
    private void volver() throws Exception {
        MainApp.switchScene("/org/example/bibliotecafx/hello-view.fxml");
    }

    private void cargarLibros() {
        List<Libro> libros = prestamosImpl.getAllLibros();
        cmbLibro.getItems().addAll(libros);
    }

    private void cargarSocios() {
        List<Socio> socios = prestamosImpl.getAllSocios();
        cmbSocio.getItems().addAll(socios);
    }

    private void cargarSociosHistorial() {
        List<Socio> socios = prestamosImpl.getAllSocios();
        cmbSocioHistorial.getItems().addAll(socios);
    }

    private void configurarTablaPrestamos() {
        // Configurar las columnas de la tabla de libros prestados
        TableColumn<Prestamos, String> colLibro = (TableColumn<Prestamos, String>) tablaLibrosPrestados.getColumns().get(0);
        colLibro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getTitulo()));

        TableColumn<Prestamos, String> colSocio = (TableColumn<Prestamos, String>) tablaLibrosPrestados.getColumns().get(1);
        colSocio.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSocio().getNombre()));

        TableColumn<Prestamos, String> colFechaPrestamo = (TableColumn<Prestamos, String>) tablaLibrosPrestados.getColumns().get(2);
        colFechaPrestamo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaPrestamo().toString()));

        TableColumn<Prestamos, String> colFechaDevolucion = (TableColumn<Prestamos, String>) tablaLibrosPrestados.getColumns().get(3);
        colFechaDevolucion.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFechaDevolucion() != null ? cellData.getValue().getFechaDevolucion().toString() : "No devuelto"));
    }



    @FXML
    public void registrarPrestamo() {
        Libro libro = cmbLibro.getValue();
        Socio socio = cmbSocio.getValue();
        LocalDate fechaPrestamo = dpFechaPrestamo.getValue();
        LocalDate fechaDevolucion = dpFechaDevolucion.getValue();

        if (libro != null && socio != null && fechaPrestamo != null && fechaDevolucion != null) {
            Prestamos prestamo = new Prestamos(libro, socio, fechaPrestamo, fechaDevolucion);
            prestamosImpl.registrarPrestamo(prestamo);
            lblMensaje.setTextFill(Color.GREEN);
            lblMensaje.setText("Préstamo registrado exitosamente");
            actualizarTablaPrestamos();
        } else {
            lblMensaje.setTextFill(Color.RED);
            lblMensaje.setText("Todos los campos deben ser completos");
        }
    }

    private void actualizarTablaPrestamos() {
        List<Prestamos> prestamos = prestamosImpl.getAllPrestamos();
        tablaLibrosPrestados.getItems().clear();
        tablaLibrosPrestados.getItems().addAll(prestamos);
    }

    @FXML
    public void cargarHistorial() {
        Socio socio = cmbSocioHistorial.getValue();

        if (socio != null) {
            List<Prestamos> historial = prestamosImpl.getHistorialPrestamosPorSocio(socio);
            tablaHistorial.getItems().clear();
            tablaHistorial.getItems().addAll(historial);
        }
    }

    @FXML
    private void configurarTablaHistorial() {
        TableColumn<Prestamos, String> colLibro = (TableColumn<Prestamos, String>) tablaHistorial.getColumns().get(0);
        colLibro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getTitulo()));

        TableColumn<Prestamos, String> colFechaPrestamo = (TableColumn<Prestamos, String>) tablaHistorial.getColumns().get(1);
        colFechaPrestamo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFechaPrestamo().toString()));

        TableColumn<Prestamos, String> colFechaDevolucion = (TableColumn<Prestamos, String>) tablaHistorial.getColumns().get(2);
        colFechaDevolucion.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getFechaDevolucion() != null ? cellData.getValue().getFechaDevolucion().toString() : "No devuelto"));
    }


}
