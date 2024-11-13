package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.DataBase;
import com.sajid.taskmanagerv2.DiscordWebhook;
import com.sajid.taskmanagerv2.Main;
import com.sajid.taskmanagerv2.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CreateUser implements Initializable {
    @FXML
    Label display_id;

    @FXML
    Label display_name;

    @FXML
    Label display_rank;

    @FXML
    Label next_id;

    @FXML
    TextField name_field;

    @FXML
    PasswordField password_field;

    @FXML
    ChoiceBox<String> gender_field;

    @FXML
    ChoiceBox<String> rank_field;

    User user = Login.user;

    DataBase db = new DataBase();

    int nextID = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        display_name.setText(user.getName());
        display_id.setText("ID: " + String.valueOf(user.getId()));
        String rank = user.getRank().toUpperCase();
        if (user.getRank().equals("boss")) {
            rank = "Admin";
        }
        display_rank.setText("Rank: " + rank);

        gender_field.getItems().addAll("MALE", "FEMALE");

        if(user.getRank().equals("boss")){
            rank_field.getItems().addAll("SUPERVISOR", "EMPLOYEE", "ADMIN");
        }
        else {
            rank_field.getItems().addAll("SUPERVISOR", "EMPLOYEE");
        }



        try {
            nextID = db.getNextId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        next_id.setText(String.valueOf(nextID));
    }

    @FXML
    public void onLogout (ActionEvent event) throws IOException {
        Main.changeScene("login.fxml");
    }
    @FXML
    public void onBack(ActionEvent event) throws IOException {
        if(user.getRank().equals("supervisor")){
            Main.changeScene("supervisor.fxml");
        } else if (user.getRank().equals("boss")) {
            Main.changeScene("boss.fxml");

        } else {
            Main.changeScene("employee.fxml");
        }
    }
    @FXML
    public void onCreateUserSubmit(ActionEvent event) throws IOException, SQLException {

        if (name_field.getText().equals("") || password_field.getText().equals("") || gender_field.getValue().equals("") || rank_field.getValue().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please Input All Fields");
            alert.showAndWait();
        }
        else{

            String rank = rank_field.getValue();
            if(rank.equals("ADMIN")){
                rank = "boss";
            }
            else {
                rank = rank.toLowerCase();
            }

            User newUser = new User(nextID, name_field.getText(), rank , gender_field.getValue(),password_field.getText());
                if (db.createUser(newUser) ){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Something went wrong");
                    alert.showAndWait();

                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Done");
                    alert.setContentText("User created successfully");
                    alert.showAndWait();

                    String content ="```"+ user.getName() + " Created a new user name: " + newUser.getName() + " rank: " + newUser.getRank() + "```";
                    DiscordWebhook discordWebhook = new DiscordWebhook("activity", content);

                    Main.changeScene("supervisor.fxml");
                }
        }

    }

}
