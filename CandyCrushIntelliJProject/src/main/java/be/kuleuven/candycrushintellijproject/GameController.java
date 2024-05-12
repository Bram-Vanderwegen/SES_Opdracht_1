package be.kuleuven.candycrushintellijproject;


import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;

public class GameController {
    private Model gameModel;

    private recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);

    @FXML
    private GridPane gameGrid;
    @FXML
    private Label score;

    @FXML
    protected void BackButtonClick() {
        gameModel.setTitle("Candy Crush login");
        gameModel.setStageLogin();
    }
    @FXML
    protected void resetButtonClick(){
        gameModel.regenerateCandys();
        updateCandy();
    }


    public void generateButtons() {

        for (int i = 0; i < 25; i++) {
            recordExercises.Position pos = recordExercises.fromIndex(i, board);
            Circle button = new Circle(40);
            button.setOnMouseClicked(mouseEvent -> clickCandy(pos.row(), pos.column()));
            gameModel.addShape(button);
            gameGrid.add(button, pos.row(), pos.column()); // Add dynamically created Label to the GridPane
        }
        updateCandy();
    }
    Node makeCandyShape(recordExercises.Position position, recordExercises.Candy candy){
        Shape shape;
        switch (candy){
            case recordExercises.NormalCandy ignored:
                shape = new Circle(40);
                shape.setFill(getColor(((recordExercises.NormalCandy) gameModel.getCandy(position)).color()));
                break;
            case recordExercises.BlizzardBoom ignored:
                shape = new Rectangle(80,80);
                shape.setFill(Color.BLUE);
                break;
            case recordExercises.FireYeeter ignored:
                shape = new Rectangle(80,80);
                shape.setFill(Color.RED);
                break;
            case recordExercises.SnowflakeExplosion ignored:
                shape = new Rectangle(80,80);
                shape.setFill(Color.WHITE);
                break;
            case recordExercises.PlantMucus ignored:
                shape = new Rectangle(80,80);
                shape.setFill(Color.GREEN);
                break;
            default:
                shape = new Ellipse(20,40);
                shape.setFill(Color.GREY);
        }
        shape.setOnMouseClicked(mouseEvent -> clickCandy(position.row(), position.column()));
        gameModel.setShape(position.toIndex(), shape);
        return shape;
    }

    public void clickCandy(int row, int column) {
        gameModel.clickCandyAndUpdate(new recordExercises.Position(row, column, board));
        score.setText("Score: " + gameModel.getScore());
        updateCandy();
    }
    public void updateCandy(){
        for (int i = 0; i < 25; i++) {gameGrid.getChildren().remove(0);}
        for (int i = 0; i < 25; i++) {
            gameGrid.add(makeCandyShape(recordExercises.fromIndex(i, board),
                    gameModel.getCandy(recordExercises.fromIndex(i, board))),
                    recordExercises.fromIndex(i, board).row(), recordExercises.fromIndex(i, board).column());
            }
    }

    public String getScore(){
        return score.getText();
    }

    public void setModel(Model gameModel) {
        this.gameModel = gameModel;
    }
    private Paint getColor(int color){
        return switch (color) {
            case 0 -> Color.RED;
            case 1 -> Color.GREEN;
            case 2 -> Color.BLUE;
            case 3 -> Color.YELLOW;
            default -> Color.WHITE;
        };
    }
}