package be.kuleuven.candycrushintellijproject;

import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Model {
    private genericBoard<recordExercises.Candy> candyContainer;
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
        candyContainer = new genericBoard<recordExercises.Candy>(board);
        for (int i = 0; i < 25; i++){
            this.candyContainer.replaceCellAt(recordExercises.fromIndex(i, board), generateRandomCandy());
        }
        //if there are no possible moves then regenerate until there are
        while (!checkPotentialMoves()){
            regenerateCandys();
        }

    }
    public void regenerateCandys(){
        for (int i = 0; i < 25; i++){
            this.candyContainer.replaceCellAt(recordExercises.fromIndex(i, this.board), generateRandomCandy());
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

    public void setAllCandys(recordExercises.Candy candy){
        for(int i = 0; i < board.boardLength(); i++){
            this.setCandy(recordExercises.fromIndex(i, board), candy);
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
        return candyContainer.getCellAt(position);
    }
    public void setCandy(recordExercises.Position pos, recordExercises.Candy candy){
        candyContainer.replaceCellAt(pos, candy);
    }
    public int getScore(){
        return score;
    }
    public void setCandyValues(ArrayList<recordExercises.Candy> candyList){
        for(int i = 0; i < board.boardLength(); i++){
            candyContainer.replaceCellAt(recordExercises.fromIndex(i, board), candyList.get(i));
        }
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
        ArrayList<recordExercises.Candy> toReturn = new ArrayList<>();
        for(int i = 0; i < candyContainer.getBoardCopy().boardLength(); i++){
            toReturn.add(candyContainer.getCellAt(recordExercises.fromIndex(i, board)));
        }
        return toReturn;
    }
    public void clickCandyAndUpdate(recordExercises.Position position){
        //check
        Iterable<recordExercises.Position> complientNeighboursIterable = CheckNeighboursInGrid.getSameNeighboursPositions(getCandyValues(), board, position);
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
            Iterable<recordExercises.Position> complientNeighboursIterable = NeighbourChecker.getSameNeighboursPositions(getCandyValues(), board, recordExercises.fromIndex(i, board));
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
        this.candyContainer.replaceCellAt(pos, newVal);
    }
    public Set<List<recordExercises.Position>> findAllMatches(){
        Stream<recordExercises.Position> hor = horizontalStartingPositions();
        List<List<recordExercises.Position>> Matches = hor
                .map(this::longestMatchToRight)
                .filter(i -> i.size() > 2)
                .collect(Collectors.toList());
        Stream<recordExercises.Position> ver = verticalStartingPositions();
        Matches.addAll(ver
                .map(this::longestMatchDown)
                .filter(i -> i.size() > 2)
                .collect(Collectors.toList()));

        return new HashSet<>(Matches);
    }
    public boolean firstTwoHaveCandy(recordExercises.Candy candy, Stream<recordExercises.Position> positions){

        long count = positions
                .limit(2)
                .map(i ->candyContainer.getCellAt(i))
                .filter(cell -> cell.equals(candy))
                .count();
        return count == 2;
    }
    public Stream<recordExercises.Position> horizontalStartingPositions(){
        List<recordExercises.Position> startingPos = new ArrayList<>();
        //for all rows
        for(int i = 0; i <= board.row(); i++){
            //for all columns
            for(int j = 0; j <= board.column(); j++){
                if(!this.firstTwoHaveCandy(candyContainer.getCellAt(new recordExercises.Position(i, j, board)),
                        new recordExercises.Position(i, j, board).walkLeft())){
                    startingPos.add(new recordExercises.Position(i, j, board));
                }
            }
        }
        return startingPos.stream();
    }
    public Stream<recordExercises.Position> verticalStartingPositions(){
        List<recordExercises.Position> startingPos = new ArrayList<>();
        //for all rows
        for(int i = 0; i <= board.row(); i++){
            //for all columns
            for(int j = 0; j <= board.column(); j++){
                if(!this.firstTwoHaveCandy(candyContainer.getCellAt(new recordExercises.Position(i, j, board)),
                        new recordExercises.Position(i, j, board).walkUp())){
                    startingPos.add(new recordExercises.Position(i, j, board));
                }
            }
        }
        return startingPos.stream();
    }
    public List<recordExercises.Position> longestMatchToRight(recordExercises.Position pos){
        Stream<recordExercises.Position> path = pos.walkRight();
        return path
                .takeWhile(c -> (this.candyContainer.getCellAt(c)).equals(this.candyContainer.getCellAt(pos)))
                .collect(Collectors.toList());
    }
    public List<recordExercises.Position> longestMatchDown(recordExercises.Position pos){
        Stream<recordExercises.Position> path = pos.walkDown();
        return path
                .takeWhile(c -> (this.candyContainer.getCellAt(c)).equals(this.candyContainer.getCellAt(pos)))
                .collect(Collectors.toList());
    }
    public void clearMatch(List<recordExercises.Position> match){
        candyContainer.replaceCellAt(match.getFirst(), null);
        match.removeFirst();
        if(!match.isEmpty()){
            this.clearMatch(match);
        }
    }
    public void fallDownTo(recordExercises.Position pos){
        // if not on top
        if(pos.row() > 0){
            recordExercises.Position above = new recordExercises.Position(pos.row() - 1, pos.column(), board);
            //if void block
            if (this.candyContainer.getCellAt(pos) == null){
                // let candy above "fall"
                //copy candy
                this.candyContainer.replaceCellAt(pos, this.candyContainer.getCellAt(above));
                //delete previous
                this.candyContainer.replaceCellAt(above,null);
            }
            //check the one above
            this.fallDownTo(above);
        }
    }
    public boolean updateBoard(){
        //flag for return
        boolean matchFlag;
        //get the matches
        Set<List<recordExercises.Position>> Matches = findAllMatches();
        //check if a match occured
        matchFlag = Matches.size() > 1;
        //if a match occured
        if (matchFlag){
            //clear matches
            while (Matches.iterator().hasNext()){
                clearMatch(Matches.iterator().next());
            }
            //let every column fall down
            for (int i = 0; i <= board.column(); i++){
                fallDownTo(new recordExercises.Position(board.row(), i, board));
            }
            //update again
            updateBoard();
        }
        return matchFlag;
    }
}
