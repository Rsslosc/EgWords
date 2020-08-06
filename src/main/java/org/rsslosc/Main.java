package org.rsslosc;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            if (!new File(Name.HISTORY).exists())
                new GetHistory().init(new GetWord().getLen());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("stage.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Update update = Update.getInstance();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, update::run);
        scene.heightProperty().addListener((observable, oldValue, newValue) -> update.changeH(newValue));
        scene.widthProperty().addListener((observable, oldValue, newValue) -> update.changeW(newValue));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            try {
                update.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        //scene.setFill(new ImagePattern(new Image("file:2.jpg")));
        primaryStage.setTitle("EgWords - Rsslosc, z-y");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
