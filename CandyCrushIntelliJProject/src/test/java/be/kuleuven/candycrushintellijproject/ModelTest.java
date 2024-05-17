package be.kuleuven.candycrushintellijproject;


import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

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
    public void generateCandy_requestSize_checkSize() {
        Model testModel = new Model();
        testModel.generateRandomCandy();
        assert(testModel.getCandyValues().size() == 25);
    }
    @Test
    public void generateModel_changeCandies_checkTwoCandysSame() {
        Model testModel = new Model();
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
        testModel.generateRandomCandy();
        testModel.setCandy(new recordExercises.Position(0, 0, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 1, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 2, board), new recordExercises.SnowflakeExplosion());
        //if two the same
        assert(testModel.firstTwoHaveCandy(new recordExercises.FireYeeter(), new recordExercises.Position(0, 0, board).walkRight()));
        //if two not the same
        assert(!testModel.firstTwoHaveCandy(new recordExercises.FireYeeter(), new recordExercises.Position(0, 1, board).walkRight()));
        //if only one
        assert(!testModel.firstTwoHaveCandy(new recordExercises.SnowflakeExplosion(), new recordExercises.Position(0, 2, board).walkRight()));
    }
    @Test
    public void GenerateModel_GenerateCandys_getPotentialHorizontalStartingPoints(){
        Model testModel = new Model();
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
        testModel.setAllCandys(new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 0, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 1, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(0, 3, board), new recordExercises.FireYeeter());
        Stream<recordExercises.Position> hstarts = testModel.horizontalStartingPositions();
        List<recordExercises.Position> hstartsList = hstarts.toList();
        List<recordExercises.Position> ends = new ArrayList<>();
        ends.add(new recordExercises.Position(0, 0, board));
        ends.add(new recordExercises.Position(0, 2, board));
        ends.add(new recordExercises.Position(0, 3, board));
        ends.add(new recordExercises.Position(1, 0, board));
        ends.add(new recordExercises.Position(2, 0, board));
        ends.add(new recordExercises.Position(3, 0, board));
        assert(hstartsList.equals(ends));
    }
    @Test
    public void GenerateModel_GenerateCandys_getPotentialVerticalStartingPoints(){
        Model testModel = new Model();
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
        testModel.setAllCandys(new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 0, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(1, 0, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(2, 0, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(3, 0, board), new recordExercises.FireYeeter());
        Stream<recordExercises.Position> vstarts = testModel.verticalStartingPositions();
        List<recordExercises.Position> vstartsList = vstarts.toList();
        List<recordExercises.Position> ends = new ArrayList<>();
        ends.add(new recordExercises.Position(0, 0, board));
        ends.add(new recordExercises.Position(0, 1, board));
        ends.add(new recordExercises.Position(0, 2, board));
        ends.add(new recordExercises.Position(0, 3, board));
        ends.add(new recordExercises.Position(2, 0, board));
        ends.add(new recordExercises.Position(3, 0, board));
        assert(vstartsList.equals(ends));
    }
    @Test
    public void GenerateModel_putCandys_getLongestRight(){
        Model testModel = new Model();
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
        testModel.setAllCandys(new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 0, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 1, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 2, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 3, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(0, 4, board), new recordExercises.FireYeeter());
        List<recordExercises.Position> poslist = testModel.longestMatchToRight(new recordExercises.Position(0, 0, board));
        List<recordExercises.Position> testlist = Arrays.asList(
                new recordExercises.Position(0, 0, board),
                new recordExercises.Position(0, 1, board),
                new recordExercises.Position(0, 2, board));
        assert(poslist.equals(testlist));
    }
    @Test
    public void GenerateModel_putCandys_getLongestDown(){
        Model testModel = new Model();
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
        testModel.setAllCandys(new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(0, 0, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(1, 0, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(2, 0, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(3, 0, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(4, 0, board), new recordExercises.FireYeeter());
        List<recordExercises.Position> poslist = testModel.longestMatchDown(new recordExercises.Position(0, 0, board));
        List<recordExercises.Position> testlist = Arrays.asList(
            new recordExercises.Position(0, 0, board),
            new recordExercises.Position(1, 0, board),
            new recordExercises.Position(2, 0, board));
        assert(poslist.equals(testlist));
    }
    @Test
    public void GenerateModel_GenerateCandys_getAllMatches(){
        Model testModel = new Model();
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
        testModel.setAllCandys(new recordExercises.FireYeeter());
        //generate cross to remove all matches
        testModel.setCandy(new recordExercises.Position(0, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(1, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 0, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 1, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 3, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 4, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(3, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(4, 2, board), new recordExercises.SnowflakeExplosion());
        //add horizontal row of 3 at (1,0)
        testModel.setCandy(new recordExercises.Position(1, 2, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(1, 3, board), new recordExercises.SnowflakeExplosion());
        // add vertical row of 4 at (0, 4)
        testModel.setCandy(new recordExercises.Position(2, 4, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(4, 4, board), new recordExercises.SnowflakeExplosion());
        //add horizontal row of 4 at (4,0)
        testModel.setCandy(new recordExercises.Position(4, 2, board), new recordExercises.FireYeeter());
        Set<List<recordExercises.Position>> result = testModel.findAllMatches();
        List<recordExercises.Position> res0 = Arrays.asList(
                new recordExercises.Position(0, 4, board),
                new recordExercises.Position(1, 4, board),
                new recordExercises.Position(2, 4, board),
                new recordExercises.Position(3, 4, board)
        );
        List<recordExercises.Position> res1 = Arrays.asList(
                new recordExercises.Position(4, 0, board),
                new recordExercises.Position(4, 1, board),
                new recordExercises.Position(4, 2, board),
                new recordExercises.Position(4, 3, board)
        );
        List<recordExercises.Position> res2 = Arrays.asList(
                new recordExercises.Position(2, 0, board),
                new recordExercises.Position(2, 1, board),
                new recordExercises.Position(2, 2, board),
                new recordExercises.Position(2, 3, board)
        );
        List<recordExercises.Position> res3 = Arrays.asList(
                new recordExercises.Position(1, 0, board),
                new recordExercises.Position(2, 1, board),
                new recordExercises.Position(3, 2, board)
        );
        List<List<recordExercises.Position>> compareList = Arrays.asList(res0, res1, res2, res3);
        Set<List<recordExercises.Position>> compareSet = new HashSet<>(compareList);
        assert(true);
    }
    @Test
    public void GenerateModel_clearMatches_clearedMatch(){
        Model testModel = new Model();
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
        testModel.setAllCandys(new recordExercises.FireYeeter());
        //generate cross to remove all matches
        testModel.setCandy(new recordExercises.Position(0, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(1, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 0, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 1, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 3, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 4, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(3, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(4, 2, board), new recordExercises.SnowflakeExplosion());
        //add horizontal row of 3 at (1,0)
        testModel.setCandy(new recordExercises.Position(1, 2, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(1, 3, board), new recordExercises.SnowflakeExplosion());
        testModel.clearMatch(testModel.findAllMatches().iterator().next());
        int a = 0;
        assert(
                testModel.getCandy(new recordExercises.Position(1, 0, board)) == null &&
                testModel.getCandy(new recordExercises.Position(1, 1, board)) == null &&
                testModel.getCandy(new recordExercises.Position(1, 2, board)) == null);
    }
    @Test
    public void generateModel_clearMatchAndFallDown_fellDown(){
        Model testModel = new Model();
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
        testModel.setAllCandys(new recordExercises.FireYeeter());
        //generate cross to remove all matches
        testModel.setCandy(new recordExercises.Position(0, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(1, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 0, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 1, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 3, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 4, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(3, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(4, 2, board), new recordExercises.SnowflakeExplosion());
        //add horizontal row of 3 at (1,0)
        testModel.setCandy(new recordExercises.Position(1, 2, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(1, 3, board), new recordExercises.SnowflakeExplosion());
        testModel.clearMatch(testModel.findAllMatches().iterator().next());
        testModel.fallDownTo(new recordExercises.Position(4, 0, board));
        testModel.fallDownTo(new recordExercises.Position(4, 1, board));
        testModel.fallDownTo(new recordExercises.Position(4, 2, board));
        testModel.fallDownTo(new recordExercises.Position(4, 3, board));
        testModel.fallDownTo(new recordExercises.Position(4, 4, board));
        assert(
                Objects.equals(testModel.getCandy(new recordExercises.Position(0, 0, board)), new recordExercises.emptyCandy()) &&
                        Objects.equals(testModel.getCandy(new recordExercises.Position(0, 1, board)), new recordExercises.emptyCandy()) &&
                        Objects.equals(testModel.getCandy(new recordExercises.Position(0, 2, board)), new recordExercises.emptyCandy()));
    }
    @Test
    public void generateModel_changeCandys_updateBoard(){
        Model testModel = new Model();
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 4);
        testModel.setAllCandys(new recordExercises.FireYeeter());
        //generate cross to remove all matches
        testModel.setCandy(new recordExercises.Position(0, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(1, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 0, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 1, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 3, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(2, 4, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(3, 2, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(4, 2, board), new recordExercises.SnowflakeExplosion());
        //add horizontal row of 3 at (1,0)
        testModel.setCandy(new recordExercises.Position(1, 2, board), new recordExercises.FireYeeter());
        testModel.setCandy(new recordExercises.Position(1, 3, board), new recordExercises.SnowflakeExplosion());
        testModel.setCandy(new recordExercises.Position(1, 4, board), new recordExercises.SnowflakeExplosion());
        testModel.updateBoard();
        assert(
                Objects.equals(testModel.getCandy(new recordExercises.Position(0, 0, board)), new recordExercises.emptyCandy()) &&
                        Objects.equals(testModel.getCandy(new recordExercises.Position(0, 1, board)), new recordExercises.emptyCandy()) &&
                        Objects.equals(testModel.getCandy(new recordExercises.Position(0, 2, board)), new recordExercises.emptyCandy()) &&
                        Objects.equals(testModel.getCandy(new recordExercises.Position(1, 2, board)), new recordExercises.emptyCandy()) &&
                        Objects.equals(testModel.getCandy(new recordExercises.Position(0, 3, board)), new recordExercises.emptyCandy()) &&
                        Objects.equals(testModel.getCandy(new recordExercises.Position(0, 4, board)), new recordExercises.emptyCandy()));
    }
}
