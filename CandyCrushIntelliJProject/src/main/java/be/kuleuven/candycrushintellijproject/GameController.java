package be.kuleuven.candycrushintellijproject;


import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class GameController {
    private Model gameModel;

    @FXML
    private GridPane gameGrid;
    @FXML
    private Label testLabel;
    @FXML
    private Label score;

    @FXML
    protected void BackButtonClick() {
        gameModel.setTitle("Candy Crush login");
        gameModel.setStageLogin();
    }



    public void generateCandy() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Button button = new Button("0");
                button.setPrefSize(80,80);
                int finalJ = j;
                int finalI = i;
                button.setOnMouseClicked(mouseEvent -> clickCandy(finalJ, finalI));
                gameModel.addButton(button);
                gameGrid.add(button, j, i); // Add dynamically created Label to the GridPane
            }
        }
        updateCandy();
    }

    public void clickCandy(int row, int column) {
        testLabel.setText("callback");
        gameModel.checkAndUpdate(row * 5 + column);
        score.setText("Score: " + gameModel.getScore());
        updateCandy();
    }
    public void updateCandy(){
        for (int i = 0; i < 25; i++) {
                gameModel.getButton(i).setText(String.valueOf(gameModel.getCandy(i)));
            }
    }

    public void setModel(Model gameModel) {
        this.gameModel = gameModel;
    }
}