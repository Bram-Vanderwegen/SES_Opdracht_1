package be.kuleuven.candycrushintellijproject;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class CheckNeighboursInGridTest {
    @Test
    public void listGiven_ifEdgeRequested_noCrash(){
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
                4, 4, 1, 2, 3,
                4, 4, 1, 3, 2,
                5, 5, 5, 5, 5,
                5, 5, 5, 2, 1,
                5, 5, 5, 2, 2
        ));
        CheckNeighboursInGrid.getSameNeighboursIds(list, 5, 5, 0);
        //if not crashed
        assert(true);
        CheckNeighboursInGrid.getSameNeighboursIds(list, 5, 5, 4);
        //if not crashed
        assert(true);
        CheckNeighboursInGrid.getSameNeighboursIds(list, 5, 5, 20);
        //if not crashed
        assert(true);
        CheckNeighboursInGrid.getSameNeighboursIds(list, 5, 5, 24);
        //if not crashed
        assert(true);
    }
    @Test
    public void listGiven_neighboursRequested_returnNeighbours(){
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(
                4, 4, 1, 2, 3,
                4, 4, 1, 3, 2,
                5, 5, 5, 5, 5,
                5, 5, 5, 2, 1,
                5, 5, 5, 2, 2
        ));
        Iterable<Integer> complientNeighboursIterable = CheckNeighboursInGrid.getSameNeighboursIds(list, 5, 5, 0);
        ArrayList<Integer> complientNeighbours = new ArrayList<>();
        for (Integer item : complientNeighboursIterable) {
            complientNeighbours.add(item);
        }
        assert(complientNeighbours.size() == 3);
    }
}
