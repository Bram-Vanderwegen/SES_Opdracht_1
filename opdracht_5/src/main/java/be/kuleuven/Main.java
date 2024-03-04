package be.kuleuven;
import be.kuleuven.CheckNeighboursInGrid;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>(Arrays.asList(
                0, 0, 1, 0,
                1, 1, 0, 2,
                2, 0, 1, 3,
                0, 1, 1, 1 ));
        System.out.print(CheckNeighboursInGrid.getSameNeighboursIds(list, 4, 4, 5));
        System.out.printf("Hello and welcome!");
    }
}