package be.kuleuven.candycrushintellijproject;

import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.abs;


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
    private int newScore = 0;
    private Random rand = new Random();
    private List<recordExercises.candySwitch> switchList = new ArrayList<>();

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
    public genericBoard<recordExercises.Candy> getBoard(){
        return candyContainer;
    }
    public List<recordExercises.candySwitch> getSwitchList(){
        return switchList;
    }
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
    public int getNewScore() {return newScore;}
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
        //return if nullcandy because use is illegal
        if(candy.equals(new recordExercises.emptyCandy())){
            return true;
        }

        long count = positions
                .limit(2)
                .map(i ->candyContainer.getCellAt(i))
                .filter(Objects::nonNull)
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
        candyContainer.replaceCellAt(match.getFirst(), new recordExercises.emptyCandy());
        newScore++;
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
            if (Objects.equals(this.candyContainer.getCellAt(pos), new recordExercises.emptyCandy())){
                // let candy above "fall"
                //copy candy
                this.candyContainer.replaceCellAt(pos, this.candyContainer.getCellAt(above));
                //delete previous
                this.candyContainer.replaceCellAt(above,new recordExercises.emptyCandy());
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
        matchFlag = !Matches.isEmpty();
        //if a match occured
        if (matchFlag){
            //clear matches
            for (List<recordExercises.Position> match : Matches) {
                clearMatch(match);
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
    public boolean matchAfterSwitch(recordExercises.candySwitch sw){
        //check if switch is legal
        if((abs(sw.pos1().column() - sw.pos2().column()) + abs(sw.pos1().row() - sw.pos2().row()) != 1)){
            return false;
        }
        //swap candys
        recordExercises.Candy cap;
        cap = this.candyContainer.getCellAt(sw.pos1());
        this.candyContainer.replaceCellAt(sw.pos1(), this.candyContainer.getCellAt(sw.pos2()));
        this.candyContainer.replaceCellAt(sw.pos2(), cap);
        if(findAllMatches().isEmpty()){
            //no bitches
            return false;
        } else {
            return true;
        }
    }
    public void switchItem(recordExercises.candySwitch sw){
        recordExercises.Candy cap;
        cap = this.candyContainer.getCellAt(sw.pos1());
        this.candyContainer.replaceCellAt(sw.pos1(), this.candyContainer.getCellAt(sw.pos2()));
        this.candyContainer.replaceCellAt(sw.pos2(), cap);
    }
    public List<recordExercises.candySwitch> getValidSwitches(){
        List<recordExercises.candySwitch> switches = new ArrayList<>();
        //get vertical switches
        for(int i = 0; i < board.row() - 1; i++){
            for(int j = 0; j < board.column(); i++){
                recordExercises.candySwitch switchUnderObservation = new recordExercises.candySwitch(
                        new recordExercises.Position(i, j, board),
                        new recordExercises.Position(i + 1, j, board));
                if (matchAfterSwitch(switchUnderObservation)){
                    switches.add(switchUnderObservation);
                }
            }
        }
        //get horizontal switches
        for(int i = 0; i < board.row(); i++){
            for(int j = 0; j < board.column() - 1; i++){
                recordExercises.candySwitch switchUnderObservation = new recordExercises.candySwitch(
                        new recordExercises.Position(i, j, board),
                        new recordExercises.Position(i, j + 1, board));
                if (matchAfterSwitch(switchUnderObservation)){
                    switches.add(switchUnderObservation);
                }
            }
        }
        return switches;
    }
    public int switchCandys(){
        //clear all successive matches and increase global score
        updateBoard();
        List<recordExercises.candySwitch> switches = getValidSwitches();
        //get performance variables
        int max = 0;
        List<recordExercises.candySwitch> deepList = null;
        //generate model
        for(int i = 0; i < switches.size(); i++){
            //create new model, copy these values, switch two
            Model instanceModel = new Model();
            this.candyContainer.copyTo(instanceModel.getBoard());
            instanceModel.switchItem(switches.get(i));
            //get score value and get highest
            int runScore = instanceModel.switchCandys();
            if(runScore > max){
                //update new max
                max = runScore;
                //get the list the previous instance encountered and prepend this one
                deepList = instanceModel.getSwitchList();
                deepList.addFirst(switches.get(i));
            }
        }


        this.switchList = deepList;
        return score + max;
    }
}
