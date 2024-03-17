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
            this.candyValues.add(rand.nextInt(5));
        }
        //if there are no possible moves then regenerate until there are
        while (!checkPotentialMoves()){
            generateRandomCandy();
        }

    }
    public void generateRandomCandy(){
        for (int i = 0; i < 25; i++){
            this.candyValues.set(i, rand.nextInt(5));
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
    public void addButton(Button toAdd){
        this.buttons.add(toAdd);
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
    public void setCandyValues(ArrayList<Integer> candyList){
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
    public ArrayList<Integer> getCandyValues(){
        return this.candyValues;
    }
    public void clickCandyAndUpdate(int position){
        //check
        Iterable<Integer> complientNeighboursIterable = NeighbourChecker.getSameNeighboursIds(candyValues, 5, 5, position);
        //convert iterable to arraylist
        ArrayList<Integer> complientNeighbours = new ArrayList<>();
        for (Integer item : complientNeighboursIterable) {
            complientNeighbours.add(item);
        }
        //reset values in occupied areas
        if(complientNeighbours.size() >= 2){
            //increase score
            this.score += complientNeighbours.size() + 1;
            //reset candys
            reGenCandy(position);
            for (Integer item : complientNeighboursIterable) {
                reGenCandy(item);
            }
        }
        //if there are no possible moves then regenerate until there are
        while (!checkPotentialMoves()){
            generateRandomCandy();
        }
    }
    public boolean checkPotentialMoves(){
        for(int i = 0; i < 25; i++){
            Iterable<Integer> complientNeighboursIterable = NeighbourChecker.getSameNeighboursIds(candyValues, 5, 5, i);
            //convert iterable to arraylist
            ArrayList<Integer> complientNeighbours = new ArrayList<>();
            for (Integer item : complientNeighboursIterable) {
                complientNeighbours.add(item);
            }
            if(complientNeighbours.size() > 2){
                return true;
            }
        }
        return false;
    }
    private void reGenCandy(int pos){
        int oldVal = this.getCandy(pos);
        int newVal;
        do {
            newVal = rand.nextInt(5);
        }while (oldVal==newVal);
        this.candyValues.set(pos, newVal);
    }
}
