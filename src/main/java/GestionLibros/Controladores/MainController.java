package GestionLibros.Controladores;

import GestionLibros.MainApp;
import javafx.fxml.FXML;

public class MainController {


    @FXML
    public void goToLibros() throws Exception {
        long inicio = System.nanoTime();
        MainApp.switchScene("/org/example/bibliotecahiberfx/GestionLibros-view.fxml");
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mYendo a la gestión de libros... \u001B[36m" + tiempo + " ms\u001B[0m");
    }

    @FXML
    private void goToAutores() throws Exception {
        long inicio = System.nanoTime();
        MainApp.switchScene("/org/example/bibliotecahiberfx/GestionAutores-view.fxml");
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mYendo a la gestión de autores... \u001B[36m" + tiempo + " ms\u001B[0m");
    }


    @FXML
    private void goToSocios() throws Exception {
        MainApp.switchScene("GestionSocios-view.fxml");
    }

    @FXML
    private void goToPrestamos() throws Exception {
        MainApp.switchScene("Prestamos-view.fxml");
    }

    @FXML
    private void salir() {
        System.exit(0);
    }



}
