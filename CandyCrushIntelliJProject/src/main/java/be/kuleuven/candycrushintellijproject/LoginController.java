package be.kuleuven.candycrushintellijproject;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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