package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.DataBase;
import com.sajid.taskmanagerv2.DiscordWebhook;
import com.sajid.taskmanagerv2.Main;
import com.sajid.taskmanagerv2.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class ChangePassword implements Initializable {


    @FXML
    Label display_id;

    @FXML
    Label display_name;

    @FXML
    Label display_rank;

    @FXML
    PasswordField current_password;

    @FXML
    PasswordField new_password;

    @FXML
    PasswordField confirm_password;

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
    public void onChangePassword(ActionEvent event) throws IOException, SQLException {

        String curPass = current_password.getText();
        String newPass = new_password.getText();
        String confPass = confirm_password.getText();
        if (curPass.equals(user.getPassword())) {
            if (confPass.equals(newPass)) {
                user.setPassword(newPass);
                DataBase db = new DataBase();
                db.changePassword(String.valueOf(user.getId()), newPass);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Successfull");
                alert.setContentText("Successfully changed password");
                alert.showAndWait();

                String content ="```"+ user.getName() + " updated password " + "```";
                DiscordWebhook discordWebhook = new DiscordWebhook("activity", content);

                Main.changeScene("login.fxml");

            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Confirm Passwords do not match");
                alert.showAndWait();
            }

        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Confirm your current password");
            alert.showAndWait();
        }
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
}
