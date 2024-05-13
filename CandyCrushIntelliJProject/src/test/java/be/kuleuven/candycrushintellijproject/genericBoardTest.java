package be.kuleuven.candycrushintellijproject;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class genericBoardTest {
    @Test
    public void giveArray_requestMap_getMap(){
        recordExercises.Boardsize board = new recordExercises.Boardsize(1, 1);
        genericBoard<recordExercises.Candy> candyBoard = new genericBoard<>(board);
        // add candys to board
        candyBoard.replaceCellAt(recordExercises.fromIndex(0, board), new recordExercises.NormalCandy(0));
        candyBoard.replaceCellAt(recordExercises.fromIndex(1, board), new recordExercises.NormalCandy(0));
        candyBoard.replaceCellAt(recordExercises.fromIndex(2, board), new recordExercises.FireYeeter());
        candyBoard.replaceCellAt(recordExercises.fromIndex(3, board), new recordExercises.NormalCandy(0));
        // fill resulting list
        List<recordExercises.Position> posList = new ArrayList<>();
        posList.add(recordExercises.fromIndex(0, board));
        posList.add(recordExercises.fromIndex(1, board));
        posList.add(recordExercises.fromIndex(3, board));
        assert (candyBoard.getPositionsOfElement(new recordExercises.NormalCandy(0)).equals(posList));
    }
}
