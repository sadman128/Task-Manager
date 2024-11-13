package com.sajid.taskmanagerv2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.sajid.taskmanagerv2.controller.Login.user;


public class Main extends Application {

    static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Task Manager");
        stage.setScene(scene);
        stage.show();

        this.stage = stage;
    }

    public static void main(String[] args) throws InterruptedException {
        launch();
    }

    public static void changeScene(String file) throws IOException {

        if(file.equals("login.fxml")) {
            String content ="```"+ user.getName() + " logged out " + LocalTime.now().withNano(0) + " date : " + LocalDate.now() + "```";
            DiscordWebhook discordWebhook = new DiscordWebhook("duty", content );
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(file));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Task Manager");
        stage.setScene(scene);
        stage.show();


    }


}