package be.kuleuven.candycrushintellijproject;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class ModelTest {
    @Test
    public void givenUsername_ifRequested_returnUsername(){
        Model testModel = new Model();
        testModel.setUserName("testUser");
        assert(testModel.getUserName() == "testUser");
        testModel.setUserName("");
        assert(testModel.getUserName() == "Guest");
    }

    @Test
    public void giveBoard_ifCandyRequested_returnCandy(){
        Model testModel = new Model();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
                4, 4, 1, 2, 3,
                4, 4, 1, 3, 2,
                5, 5, 5, 5, 5,
                5, 5, 5, 2, 1,
                5, 5, 5, 2, 2
        ));
        testModel.setCandyValues(list);
        assert(testModel.getCandy(0) == 4);
        assert(testModel.getCandy(4) == 3);
        assert(testModel.getCandy(24) == 2);
    }
    @Test
    public void giveBoard_ifClicked_scoreUpdated(){
        Model testModel = new Model();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
                4, 4, 1, 2, 3,
                4, 4, 1, 3, 2,
                5, 5, 5, 5, 5,
                5, 5, 5, 2, 1,
                5, 5, 5, 2, 2
        ));
        testModel.setCandyValues(list);
        testModel.clickCandyAndUpdate(0);
        assert(testModel.getScore() == 4);
        testModel.setCandyValues(list);
        testModel.clickCandyAndUpdate(24);
        int a  = 0;
        assert(testModel.getScore() == 7);
    }
    @Test
    public void generateCandy_requestSize_checkSize() {
        Model testModel = new Model();
        testModel.generateRandomCandy();
        assert(testModel.getCandyValues().size() == 25);
    }
    @Test
    public void generateCandy_clickWrong_CheckGridAndScore(){
        Model testModel = new Model();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
                4, 4, 1, 2, 3,
                4, 4, 1, 3, 2,
                5, 5, 5, 5, 5,
                5, 5, 5, 2, 1,
                5, 5, 5, 2, 2
        ));
        testModel.setCandyValues((ArrayList<Integer>) list.clone());
        testModel.clickCandyAndUpdate(2);
        assert(testModel.getScore() == 0);
        assert(testModel.getCandyValues().equals(list));
    }
    @Test
    public void generateCandy_clickValidCandy_checkGridChange(){
        Model testModel = new Model();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
                4, 4, 1, 2, 3,
                4, 4, 1, 3, 2,
                5, 5, 5, 5, 5,
                5, 5, 5, 2, 1,
                5, 5, 5, 2, 2
        ));
        testModel.setCandyValues((ArrayList<Integer>) list.clone());
        testModel.clickCandyAndUpdate(0);
        assert(!testModel.getCandyValues().equals(list));
    }
    @Test
    public void giveNoMoveGrid_checkGrid_returnFalse(){
        Model testModel = new Model();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
                1, 2, 3, 4, 5,
                5, 4, 5, 1, 2,
                1, 2, 3, 4, 5,
                5, 4, 5, 1, 2,
                1, 2, 3, 4, 5
        ));
        testModel.setCandyValues((ArrayList<Integer>) list.clone());
        assert(!testModel.checkPotentialMoves());
    }
    @Test
    public void giveMoveGrid_checkGrid_returnTrue(){
        Model testModel = new Model();
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
                5, 5, 3, 4, 5,
                5, 4, 5, 1, 2,
                1, 2, 3, 4, 5,
                5, 4, 5, 1, 2,
                1, 2, 3, 4, 5
        ));
        testModel.setCandyValues((ArrayList<Integer>) list.clone());
        assert(testModel.checkPotentialMoves());
    }

}
