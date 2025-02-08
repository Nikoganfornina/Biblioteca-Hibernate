package GestionLibros.Controladores;

import DAO.IAutorImpl;
import GestionLibros.MainApp;
import entities.Autor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.util.List;

public class GestionAutoresController {

    @FXML
    private void volver() throws Exception {
        long inicio = System.nanoTime();
        MainApp.switchScene("/org/example/bibliotecahiberfx/Main-view.fxml");
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mYendo a la pantalla principal... \u001B[36m" + tiempo + " ms\u001B[0m");
    }

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtNacionalidad;
    @FXML
    private Button btnGuardar;

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
        System.out.println("\u001B[32maAutor : " + autor + " guardado \u001B[36m" + tiempo + " ms\u001B[0m");

    }

    @FXML
    private TextField txtBuscar;

    @FXML

    private ListView<Autor> Vertodo;

    @FXML
    private ListView<Autor> listResultados;

    @FXML
    public void buscarAutor() {

        String nombre = txtBuscar.getText();  // Obtener el texto del campo de búsqueda

        IAutorImpl autorDao = new IAutorImpl();
        List<Autor> autores = autorDao.findByNombre(nombre);  // Buscar autores por nombre

        // Limpiar la lista antes de agregar los nuevos resultados
        listResultados.getItems().clear();

        // Agregar los autores encontrados a la ListView
        listResultados.getItems().addAll(autores);
    }

    public void ListaAutores() {
        IAutorImpl autorDao = new IAutorImpl();
        List<Autor> autores = autorDao.findAll();
        Vertodo.getItems().addAll(autores);
    }

}
