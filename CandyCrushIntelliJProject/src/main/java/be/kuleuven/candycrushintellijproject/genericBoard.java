package be.kuleuven.candycrushintellijproject;

import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

public class genericBoard<T> {
    private Vector<T> items;
    recordExercises.Boardsize board;
    genericBoard(recordExercises.Boardsize boardin){
        items = new Vector<>(boardin.boardLength());
        this.board = boardin;
    }
    public recordExercises.Boardsize getBoardCopy(){
        return new recordExercises.Boardsize(board.row(), board.column());
    }
    public T getCellAt(recordExercises.Position pos){
        return items.get(pos.toIndex());
    }
    public void replaceCellAt(recordExercises.Position pos, T cell){
        items.set(pos.toIndex(), cell);
    }
    public void buildCell(T tobuild){
        items.add(tobuild);
    }
    public void fill(){
        //unused
    }
    public void copyTo(genericBoard<T> newBoard){
        if (!newBoard.getBoardCopy().equals(board) ) throw new IllegalArgumentException("boards need the same dimensions");
        for (recordExercises.Position pos : board.positions()){
            newBoard.replaceCellAt(pos, this.getCellAt(pos));
        }
    }
}
