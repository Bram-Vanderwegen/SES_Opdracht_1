package be.kuleuven.candycrushintellijproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private Model gameModel;

    @FXML
    private TextField loginDataBox;

    @FXML
    protected void LoginButtonClick() {
        String loginName = loginDataBox.getText();
        gameModel.setUserName(loginName);
        openGameScene();
    }

    protected void openGameScene() {
        gameModel.setStageGame();
        gameModel.setTitle(gameModel.getUserName() + "'s game");

    }
    public void setModel(Model gameModel) {
        this.gameModel = gameModel;
    }
}