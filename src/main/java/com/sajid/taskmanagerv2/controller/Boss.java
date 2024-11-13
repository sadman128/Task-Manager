package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.DataBase;
import com.sajid.taskmanagerv2.Main;
import com.sajid.taskmanagerv2.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Boss implements Initializable {

    @FXML
    Label display_id;

    @FXML
    Label display_name;

    @FXML
    Label display_rank;

    User user = Login.user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        display_name.setText(user.getName());
        display_id.setText("ID: " + String.valueOf(user.getId()));

        String rank = user.getRank().toUpperCase();
        if (user.getRank().equals("boss")) {
            rank = "Admin";
        }
        display_rank.setText("Rank: " + rank);




    }

    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        Main.changeScene("login.fxml");
    }
    @FXML
    public void onPasswordChange(ActionEvent event) throws IOException {
        Main.changeScene("changepassword.fxml");
    }

    @FXML
    public void onCreateUser(ActionEvent event) throws IOException {
        Main.changeScene("createuser.fxml");
    }
    @FXML
    public void onAssignTask(ActionEvent event) throws IOException {
        Main.changeScene("assigntask.fxml");
    }
    @FXML
    public void onViewTask(ActionEvent event) throws IOException {
        Main.changeScene("viewtask.fxml");
    }
    @FXML
    public void onNotice(ActionEvent event) throws IOException {
        Main.changeScene("newnotice.fxml");
    }

    @FXML
    public void viewNotice(ActionEvent event) throws IOException {
        Main.changeScene("viewnotice.fxml");
    }

    @FXML
    public void onViewUser(ActionEvent event) throws IOException {
        Main.changeScene("viewuser.fxml");
    }
}
