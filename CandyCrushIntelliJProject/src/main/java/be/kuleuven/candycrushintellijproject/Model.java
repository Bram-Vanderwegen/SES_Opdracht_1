package be.kuleuven.candycrushintellijproject;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;


public class Model {
    private ArrayList<Integer> candyValues = new ArrayList<>();
    private ArrayList<Button> buttons = new ArrayList<>();
    private Stage gameStage;
    private Scene loginScene;
    private Scene gameScene;
    private String userName;
    private CheckNeighboursInGrid NeighbourChecker = new CheckNeighboursInGrid();
    private int score = 0;
    private Random rand = new Random();

    //constructor
    public Model(){
        for (int i = 0; i < 25; i++){
            candyValues.add(rand.nextInt(5));
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
        this.userName = name;
    }
    public String getUserName(){
        return this.userName;
    }
    public void addButton(Button toAdd){
        buttons.add(toAdd);
    }
    public Button getButton(int position){
        return buttons.get(position);
    }
    public int getCandy(int position){
        return candyValues.get(position);
    }
    public int getScore(){
        return score;
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
    //methods for neighbourchecking
    public void checkAndUpdate(int position){
        //check
        Iterable<Integer> complientNeighboursIterable = NeighbourChecker.getSameNeighboursIds(candyValues, 5, 5, position);
        //convert iterable to arraylist
        ArrayList<Integer> complientNeighbours = new ArrayList<>();
        for (Integer item : complientNeighboursIterable) {
            complientNeighbours.add(item);
        }
        //calculate size and add to score
        score += complientNeighbours.size();
        //reset values in occupied areas
        if(complientNeighbours.size() >= 3){
            candyValues.set(position, rand.nextInt());
            for (Integer item : complientNeighboursIterable) {
                candyValues.set(item, rand.nextInt());
            }
        }

    }
}
