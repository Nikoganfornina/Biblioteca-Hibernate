package GestionLibros;

import javafx.fxml.FXML;

public class MainController {


    @FXML
    public void goToLibros() throws Exception {
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
