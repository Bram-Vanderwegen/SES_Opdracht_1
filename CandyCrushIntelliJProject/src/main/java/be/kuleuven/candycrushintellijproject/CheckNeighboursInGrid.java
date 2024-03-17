package be.kuleuven.candycrushintellijproject;

import java.util.Vector;

public class CheckNeighboursInGrid {
    /**
     * This method takes a 1D Iterable and an element in the array and gives back an iterable containing the indexes of all neighbours with the same value as the specified element
     *@return - Returns a 1D Iterable of ints, the Integers represent the indexes of all neighbours with the same value as the specified element on index 'indexToCheck'.
     *@param grid - This is a 1D Iterable containing all elements of the grid. The elements are integers.
     *@param width - Specifies the width of the grid.
     *@param height - Specifies the height of the grid (extra for checking if 1D grid is complete given the specified width)
     *@param indexToCheck - Specifies the index of the element which neighbours that need to be checked
     */
    public static Iterable<Integer> getSameNeighboursIds(Iterable<Integer> grid,int width, int height, int indexToCheck){
        Vector<Integer> arrayToCheck = new Vector<Integer>(0);
        Vector<Integer> result = new Vector<Integer>(0);
        Vector<Integer> gridVector = new Vector<>();
        for (Integer item : grid) {
            gridVector.add(item);
        }
        //start checks
        //get eligible positions
        //above
        if(indexToCheck >= width){
            //left
            if(indexToCheck % width != 0){
                arrayToCheck.add(indexToCheck - width - 1);
            }
            //middle
            arrayToCheck.add(indexToCheck - width);
            //right
            if(indexToCheck % width != 4){
                arrayToCheck.add(indexToCheck - width + 1);
            }
        }
        //left
        if(indexToCheck % width != 0){
            arrayToCheck.add(indexToCheck - 1);
        }
        //right
        if(indexToCheck % width != 4){
            arrayToCheck.add(indexToCheck + 1);
        }
        //down
        if(indexToCheck / width < height - 1){
            //left
            if(indexToCheck % width != 0){
                arrayToCheck.add(indexToCheck + width - 1);
            }
            //middle
            arrayToCheck.add(indexToCheck + width);
            //right
            if(indexToCheck % width != 4){
                arrayToCheck.add(indexToCheck + width + 1);
            }
        }
        //check for same value
        int indexValue = gridVector.get(indexToCheck);
        for(Integer item : arrayToCheck){
            if(gridVector.get(item) == indexValue){
                result.add(item);
            }
        }
        return result;
    }
}