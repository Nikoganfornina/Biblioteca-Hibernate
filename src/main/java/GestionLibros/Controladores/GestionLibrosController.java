package GestionLibros.Controladores;

import GestionLibros.MainApp;
import javafx.fxml.FXML;

public class GestionLibrosController {

    @FXML
    private void volver() throws Exception {
        long inicio = System.nanoTime();
        MainApp.switchScene("/org/example/bibliotecahiberfx/Main-view.fxml");
        long tiempo = (System.nanoTime() - inicio) / 1_000_000;
        System.out.println("\u001B[32mYendo a la pantalla principal... \u001B[36m" + tiempo + " ms\u001B[0m");
    }

}
