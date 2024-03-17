package be.kuleuven.candycrushintellijproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Model mainModel = new Model();
        FXMLLoader LoginLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader GameLoader = new FXMLLoader(HelloApplication.class.getResource("game_view.fxml"));
        Scene loginScene = new Scene(LoginLoader.load(), 750, 750);
        Scene gameScene = new Scene(GameLoader.load(), 750, 750);
        //set scenes to model
        mainModel.setGameStage(stage);
        mainModel.setLoginScene(loginScene);
        mainModel.setGameScene(gameScene);
        // init controllers
        //login
        LoginController L_Control = (LoginController) LoginLoader.getController();
        //game
        GameController G_Control = (GameController) GameLoader.getController();

        //sync scenes etc
        L_Control.setModel(mainModel);
        G_Control.setModel(mainModel);

        stage.setTitle("Candy Crush login");
        stage.setScene(loginScene);
        G_Control.generateButtons();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}