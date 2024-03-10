package be.kuleuven.candycrushintellijproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField loginDataBox;

    @FXML
    protected void LoginButtonClick() {
        String loginName = loginDataBox.getText();
        welcomeText.setText("Welcome " + loginName);
    }
}