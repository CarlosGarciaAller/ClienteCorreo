package com.carlos.sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class Main extends Application {

    @FXML
    MenuItem login = new MenuItem("Login");

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/clienteCorreo.fxml"));
        Parent root = fxmlLoader.load();
        Controller exampleController = fxmlLoader.getController();

        primaryStage.setTitle("Cliente de correo");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}