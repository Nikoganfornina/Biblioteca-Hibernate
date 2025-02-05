package org.example.bibliotecahiberfx;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import java.io.IOException;

public class MainController {


    @FXML
    private void goToLibros() throws Exception {
            MainApp.switchScene("GestionLibros-view.fxml");
    }

    @FXML
    private void goToAutores() throws Exception {
        MainApp.switchScene("GestionAutores-view.fxml");
    }

    @FXML
    private void goToSocios() throws Exception {
        MainApp.switchScene("GestionSocios-view.fxml");
    }

    @FXML
    private void goToPrestamos() throws Exception {
        MainApp.switchScene("Prestamos-view.fxml");
    }


}
