package be.kuleuven.candycrushintellijproject;

import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class recordtest {
    @Test
    public void initBoard_outsideBounds_throwError(){
        assertThrows(IllegalArgumentException.class, () -> {
            new recordExercises.Boardsize(-5, 0);
        });
    }
    @Test
    public void makePos_requestIndex_getCorrespondingIndex(){
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 3);
        recordExercises.Position pos = new recordExercises.Position(4, 2, board);
        assert(pos.toIndex() == 18);
    }
    @Test
    public void initPos_fromIndex_getCorrespondingPosition(){
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 3);
        recordExercises.Position pos = new recordExercises.Position(4, 2, board);
        assert(recordExercises.fromIndex(18, board).equals(pos));
    }
    @Test
    public void initPos_getNeighbours_getCorrespondingNeighbours(){
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 3);
        recordExercises.Position pos = new recordExercises.Position(4, 0, board);
        Vector<recordExercises.Position> expectedResult = new Vector<>();
        expectedResult.add(new recordExercises.Position(3,0, board));
        expectedResult.add(new recordExercises.Position(3,1, board));
        expectedResult.add(new recordExercises.Position(4,1, board));
        assert(pos.neighbourPositions().equals(expectedResult));
    }
    @Test
    public void initPos_requestEOColumn_returnState(){
        recordExercises.Boardsize board = new recordExercises.Boardsize(4, 3);
        recordExercises.Position pos1 = new recordExercises.Position(4, 0, board);
        recordExercises.Position pos2 = new recordExercises.Position(4, 3, board);
        assert(!pos1.isLastColumn());
        assert(pos2.isLastColumn());
    }
    @Test
    public void initBoard_requestConstituents_getConstituents(){
        recordExercises.Boardsize board = new recordExercises.Boardsize(1, 1);
        Vector<recordExercises.Position> expectedResult = new Vector<>();
        expectedResult.add(new recordExercises.Position(0, 0, board));
        expectedResult.add(new recordExercises.Position(0, 1, board));
        expectedResult.add(new recordExercises.Position(1, 0, board));
        expectedResult.add(new recordExercises.Position(1, 1, board));
        assert(board.positions().equals(expectedResult));
    }
}
