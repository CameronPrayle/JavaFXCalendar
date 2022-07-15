package com.example.calendertest2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class Calendar extends Application {
    @Override
    public void start(Stage stage) throws IOException {

//      Load FXML File
        FXMLLoader fxmlLoader = new FXMLLoader(Calendar.class.getResource("Calendar-View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 650);

//      Get CSS file
        URL cssURL = getClass().getResource("css/HelloControllerCSS.css");
        scene.getStylesheets().add(cssURL.toExternalForm());

//      Create Window
        stage.setTitle("Birthday Reminder");
        stage.getIcons().add(new Image(Calendar.class.getResourceAsStream("img/bdaylogo2.png")));
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(675);
        stage.show();
    }

    @Override
    public void stop(){
    }

    public static void main(String[] args) {
        launch();
    }


}