package be.kuleuven.candycrushintellijproject;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class genericBoard<T> {
    private Map<recordExercises.Position, T> TMap = new HashMap<>();
    private Map<T, ArrayList<recordExercises.Position>> reverseMap = new HashMap<>();
    recordExercises.Boardsize board;
    genericBoard(recordExercises.Boardsize boardin){
        this.board = boardin;
    }
    public recordExercises.Boardsize getBoardCopy(){
        return new recordExercises.Boardsize(board.row(), board.column());
    }
    public T getCellAt(recordExercises.Position pos){
        return TMap.get(pos);
    }
    public void replaceCellAt(recordExercises.Position pos, T cell){
        try {
            TMap.put(pos, cell);
        } catch (Exception e){
            TMap.replace(pos, cell);
        }
        this.updateReverseMap();
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
    private void updateReverseMap(){
        //clear map
        reverseMap.clear();
        //loop over items and add accordingly
        for(int i = 0; i < board.boardLength(); i++){
            T item = TMap.get(recordExercises.fromIndex(i, board));
            //insert to reverseMap
            try {
                reverseMap.get(item).add(recordExercises.fromIndex(i, board));
            } catch (Exception e){
                reverseMap.put(item, new ArrayList<>());
                reverseMap.get(item).add(recordExercises.fromIndex(i, board));
            }

        }
    }
    public List<recordExercises.Position> getPositionsOfElement(T item){
        if (reverseMap.get(item) == null){
            return Collections.unmodifiableList(new ArrayList<recordExercises.Position>());
        } else {
            return Collections.unmodifiableList(reverseMap.get(item));
        }
    }
}
