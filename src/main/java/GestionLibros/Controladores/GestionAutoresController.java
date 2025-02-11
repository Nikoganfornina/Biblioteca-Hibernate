package GestionLibros.Controladores;

import DAO.IAutorImpl;
import GestionLibros.MainApp;
import entities.Autor;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.scene.control.Alert.AlertType;

import java.util.List;

public class GestionAutoresController {

    @FXML
    private TextField txtNombre, txtNacionalidad, txtNombreMod, txtNacionalidadMod, txtBuscar;
    @FXML
    private Button btnGuardar, btnGuardarMod, btnBuscar;
    @FXML

    private TableView<Autor> tablaAutores;
    @FXML
    private TableColumn<Autor, String> colNombre, colNacionalidad;
    @FXML
    private ListView<Autor> listAutoresEliminar, listAutoresModificar, listResultados , ListaAutoresparaLibro ;

    private Autor autorSeleccionado;

    @FXML
    private void volver() throws Exception {
        MainApp.switchScene("/org/example/bibliotecahiberfx/Main-view.fxml");
    }

    @FXML
    public void guardarAutor() {
        String nombre = txtNombre.getText();
        String nacionalidad = txtNacionalidad.getText();

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);

        IAutorImpl autorDao = new IAutorImpl();
        autorDao.save(autor);

        txtNombre.clear();
        txtNacionalidad.clear();
        actualizarListaAutores();
    }

    @FXML
    public void seleccionarAutorModificar() {
        autorSeleccionado = listAutoresModificar.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {
            txtNombreMod.setText(autorSeleccionado.getNombre());
            txtNacionalidadMod.setText(autorSeleccionado.getNacionalidad());
        }
    }

    @FXML
    public void modificarAutor() {
        System.out.println("Botón presionado: modificarAutor() ejecutado");

        if (autorSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un autor", AlertType.ERROR);
            System.out.println("Error: No hay autor seleccionado");
            return;
        }

        Alert confirmacion = new Alert(AlertType.CONFIRMATION, "¿Seguro que quieres modificar este autor?", ButtonType.YES, ButtonType.NO);
        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                System.out.println("Confirmación recibida, modificando autor...");

                // Actualizamos los datos del autor seleccionado
                autorSeleccionado.setNombre(txtNombreMod.getText());
                autorSeleccionado.setNacionalidad(txtNacionalidadMod.getText());

                System.out.println("Nuevos valores - Nombre: " + autorSeleccionado.getNombre() + ", Nacionalidad: " + autorSeleccionado.getNacionalidad());

                // Llamamos al método update de IAutorImpl
                IAutorImpl autorDao = new IAutorImpl();
                Autor actualizado = autorDao.update(autorSeleccionado);

                if (actualizado != null) {

                    System.out.println("Autor actualizado correctamente");
                    actualizarListaAutores(); // Refresca la lista de autores
                } else {
                    mostrarAlerta("Error", "No se pudo modificar el autor.", AlertType.ERROR);
                    System.out.println("Error: La actualización falló");
                }
            }
        });
    }

    @FXML
    public void buscarAutor() {
        String busqueda = txtBuscar.getText();
        IAutorImpl autorDao = new IAutorImpl();
        List<Autor> autoresEncontrados = autorDao.findByNombre(busqueda);
        ObservableList<Autor> autores = FXCollections.observableArrayList(autoresEncontrados);
        listResultados.setItems(autores);
    }


    public void actualizarListaAutores() {
        IAutorImpl autorDao = new IAutorImpl();
        List<Autor> autores = autorDao.findAll();

        listAutoresEliminar.getItems().clear();
        listAutoresModificar.getItems().clear();
        tablaAutores.getItems().clear();

        listAutoresEliminar.getItems().addAll(autores);
        listAutoresModificar.getItems().addAll(autores);
        tablaAutores.getItems().addAll(autores);
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }



    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
        colNacionalidad.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNacionalidad()));
        listAutoresModificar.setOnMouseClicked(event -> seleccionarAutorModificar());
        actualizarListaAutores();
    }


    @FXML
    public void eliminarAutor() {
        Autor autorSeleccionado = listAutoresEliminar.getSelectionModel().getSelectedItem();
        if (autorSeleccionado != null) {
            Alert confirmacion = new Alert(AlertType.CONFIRMATION, "¿Seguro que quieres eliminar este autor?", ButtonType.YES, ButtonType.NO);
            confirmacion.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    IAutorImpl autorDao = new IAutorImpl();
                    autorDao.delete(autorSeleccionado);

                    listAutoresEliminar.getItems().remove(autorSeleccionado);
                    listAutoresModificar.getItems().remove(autorSeleccionado);
                    tablaAutores.getItems().remove(autorSeleccionado);

                    mostrarAlerta("Información", "Autor eliminado con éxito", AlertType.INFORMATION);
                }
            });
        } else {
            mostrarAlerta("Error", "Debe seleccionar un autor", AlertType.ERROR);
        }
    }
}
