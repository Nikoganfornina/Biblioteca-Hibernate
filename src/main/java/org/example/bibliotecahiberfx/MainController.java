package org.example.bibliotecahiberfx;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import java.io.IOException;

public class MainController {

    @FXML
    private void goToPage1() throws IOException {
            MainApp.switchScene("page1.fxml");
    }

    @FXML
    private void goToPage2() throws IOException {
        MainApp.switchScene("page2.fxml");
    }

    @FXML
    private void goToPage3() throws IOException {
        MainApp.switchScene("page3.fxml");
    }

    @FXML
    private void goToPage4() throws IOException {
        MainApp.switchScene("page4.fxml");
    }
}
