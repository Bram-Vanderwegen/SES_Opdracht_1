package be.kuleuven.candycrushintellijproject;

import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;


public class Model {
    private ArrayList<recordExercises.Candy> candyValues = new ArrayList<>();
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Stage gameStage;
    private Scene loginScene;
    private Scene gameScene;
    private String userName;
    private recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
    private CheckNeighboursInGrid NeighbourChecker = new CheckNeighboursInGrid();
    private int score = 0;
    private Random rand = new Random();

    //constructor
    public Model(){
        for (int i = 0; i < 25; i++){
            this.candyValues.add(generateRandomCandy());
        }
        //if there are no possible moves then regenerate until there are
        while (!checkPotentialMoves()){
            regenerateCandys();
        }

    }
    public void regenerateCandys(){
        for (int i = 0; i < 25; i++){
            this.candyValues.set(i, generateRandomCandy());
        }
    }
    public recordExercises.Candy generateRandomCandy(){
        int index = rand.nextInt(8);
        switch (index){
            case 0:
                return (new recordExercises.BlizzardBoom());
            case 1:
                return (new recordExercises.FireYeeter());
            case 2:
                return (new recordExercises.SnowflakeExplosion());
            case 3:
                return (new recordExercises.PlantMucus());
            default:
                return (new recordExercises.NormalCandy(index%4));
        }
    }
    //setter and getters for stage/scene//

    public void setGameStage(Stage gameStage){
        this.gameStage = gameStage;
    }
    public Stage getGameStage(){
        return this.gameStage;
    }
    public void setLoginScene(Scene loginScene){
        this.loginScene = loginScene;
    }
    public Scene getLoginScene(){
        return this.loginScene;
    }
    public void setGameScene(Scene gameScene){
        this.gameScene = gameScene;
    }
    public Scene getGameScene(){
        return this.gameScene;
    }
    public void setUserName(String name){
        if(name.equals("")) {
            this.userName = "Guest";
        } else {
            this.userName = name;
        }
    }
    public String getUserName(){
        return this.userName;
    }
    public void addShape(Shape toAdd){
        this.shapes.add(toAdd);
    }
    public void setShape(int position, Shape shape){
        shapes.set(position, shape);
    }
    public recordExercises.Candy getCandy(recordExercises.Position position){
        return candyValues.get(position.toIndex());
    }
    public int getScore(){
        return score;
    }
    public void setCandyValues(ArrayList<recordExercises.Candy> candyList){
        this.candyValues = candyList;
    }
    //method change stage attributes//
    public void setStageLogin(){
        gameStage.setScene(loginScene);
    }
    public void setStageGame(){
        gameStage.setScene(gameScene);
    }
    public void setTitle(String title){
        gameStage.setTitle(title);
    }
    public ArrayList<recordExercises.Candy> getCandyValues(){
        return this.candyValues;
    }
    public void clickCandyAndUpdate(recordExercises.Position position){
        //check
        Iterable<recordExercises.Position> complientNeighboursIterable = CheckNeighboursInGrid.getSameNeighboursPositions(candyValues, board, position);
        //convert iterable to arraylist
        ArrayList<recordExercises.Position> compliantNeighbours = new ArrayList<>();
        for (recordExercises.Position item : complientNeighboursIterable) {
            compliantNeighbours.add(item);
        }
        //reset values in occupied areas
        if(compliantNeighbours.size() >= 2){
            //increase score
            this.score += compliantNeighbours.size() + 1;
            //reset candys
            reGenCandy(position);
            for (recordExercises.Position item : complientNeighboursIterable) {
                reGenCandy(item);
            }
        }
        //if there are no possible moves then regenerate until there are
        while (!checkPotentialMoves()){
            regenerateCandys();
        }
    }
    public boolean checkPotentialMoves(){
        for(int i = 0; i < 25; i++){
            Iterable<recordExercises.Position> complientNeighboursIterable = NeighbourChecker.getSameNeighboursPositions(candyValues, board, recordExercises.fromIndex(i, board));
            //convert iterable to arraylist
            ArrayList<recordExercises.Position> complientNeighbours = new ArrayList<>();
            for (recordExercises.Position item : complientNeighboursIterable) {
                complientNeighbours.add(item);
            }
            if(complientNeighbours.size() > 2){
                return true;
            }
        }
        return false;
    }
    private void reGenCandy(recordExercises.Position pos){
        recordExercises.Candy oldVal = this.getCandy(pos);
        recordExercises.Candy newVal;
        do {
            newVal = generateRandomCandy();
        }while (oldVal==newVal);
        this.candyValues.set(pos.toIndex(), newVal);
    }
}
