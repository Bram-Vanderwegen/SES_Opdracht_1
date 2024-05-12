package be.kuleuven.candycrushintellijproject;


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
    public void generateCandy_requestSize_checkSize() {
        Model testModel = new Model();
        testModel.generateRandomCandy();
        assert(testModel.getCandyValues().size() == 25);
    }



}
