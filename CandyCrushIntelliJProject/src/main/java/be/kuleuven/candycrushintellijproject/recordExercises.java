package be.kuleuven.candycrushintellijproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;
import java.util.stream.Stream;

public class recordExercises {
    public record Boardsize(int row, int column){
        public Boardsize {
            if (row < 0 ) throw new IllegalArgumentException("row must be 0 or greater");
            if (column < 0) throw new IllegalArgumentException("column must be 0 or greater");
        }
        Collection<Position> positions(){
            Vector<Position> poss = new Vector<Position>();
            for (int i = 0; i < (row + 1) * (column + 1); i++) {
                poss.add(fromIndex(i, this));
            }
            return poss;
        }
        public int boardLength(){
            return (this.row + 1) * (this.column + 1);
        }
    }

    public record Position(int row, int column, Boardsize size){
        public Position {
            if (row > size.row || column > size.column) throw new IllegalArgumentException("position must be within the Boardsize");
        }
        public int toIndex() {
            return (row) * (size.column + 1) + column;
        }
        public Iterable<Position> neighbourPositions(){
            Vector<Position> result = new Vector<Position>(0);
            //get ups
            if (row > 0) {
                if (column > 0) {
                    result.add(new Position(row - 1, column - 1, size));
                }
                result.add(new Position(row - 1, column, size));
                if (column < size.column){
                    result.add(new Position(row - 1, column + 1, size));
                }
            }
            //get same row
            if (column > 0) {
                result.add(new Position(row, column - 1, size));
            }
            if (column < size.column){
                result.add(new Position(row, column + 1, size));
            }
            //get downs
            if (row > size.row) {
                if (column > 0) {
                    result.add(new Position(row + 1, column - 1, size));
                }
                result.add(new Position(row - 1, column, size));
                if (column < size.column){
                    result.add(new Position(row + 1, column + 1, size));
                }
            }
            return result;
        }
        public boolean isLastColumn(){
            return column == size.column;
        }

        public Stream<Position> walkLeft(){
            ArrayList<Position> walkList = new ArrayList<>();
            Position pos = this;
            while(pos.column > 0){
                walkList.add(pos);
                pos = new Position(pos.row, pos.column-1, this.size);
            }
            walkList.add(pos);
            return walkList.stream();
        }
        public Stream<Position> walkRight(){
            ArrayList<Position> walkList = new ArrayList<>();
            Position pos = this;
            while(pos.column < size.column){
                walkList.add(pos);
                pos = new Position(pos.row, pos.column+1, this.size);
            }
            walkList.add(pos);
            return walkList.stream();
        }
        public Stream<Position> walkUp(){
            ArrayList<Position> walkList = new ArrayList<>();
            Position pos = this;
            while(pos.row > 0){
                walkList.add(pos);
                pos = new Position(pos.row - 1, pos.column, this.size);
            }
            walkList.add(pos);
            return walkList.stream();
        }
        public Stream<Position> walkDown(){
            ArrayList<Position> walkList = new ArrayList<>();
            Position pos = this;
            while(pos.row < size.row){
                walkList.add(pos);
                pos = new Position(pos.row + 1, pos.column, this.size);
            }
            walkList.add(pos);
            return walkList.stream();
        }


    }
    static public Position fromIndex(int index, Boardsize size){
        Position pos = new Position(index/(size.column + 1), index%(size.column + 1), size);
        return pos;
    }

    public sealed interface Candy permits NormalCandy, BlizzardBoom, FireYeeter, SnowflakeExplosion, PlantMucus {}
    record NormalCandy(int color) implements Candy {
        public NormalCandy{
            if (color < 0 || color > 3) throw new IllegalArgumentException("color must be within [0;3]");
        }
    }
    record BlizzardBoom() implements Candy {}
    record FireYeeter() implements Candy {}
    record SnowflakeExplosion() implements Candy {}
    record PlantMucus() implements Candy {}
}
