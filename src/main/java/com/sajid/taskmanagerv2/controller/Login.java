package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.DataBase;
import com.sajid.taskmanagerv2.DiscordWebhook;
import com.sajid.taskmanagerv2.Main;
import com.sajid.taskmanagerv2.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Login {

    @FXML
    private TextField id_field;

    @FXML
    private PasswordField password_field;

    public static User user;

    @FXML
    public void onLogin(ActionEvent event) throws SQLException, IOException {
        String id = id_field.getText();
        String password = password_field.getText();
        DataBase db = new DataBase();
        db.getConnection();

        if(db.checkLogin(id, password)) {
            System.out.println("Login Successful");
            user = db.getUser(id);

            String content = "```" + user.getName() + " Logged in at " + LocalTime.now().withNano(0) + " date : " + LocalDate.now() + "```";
            DiscordWebhook discordWebhook = new DiscordWebhook("duty", content);

            if (user.getRank().equals("supervisor")){
                Main.changeScene("supervisor.fxml");
            } else if (user.getRank().equals("boss")) {
                Main.changeScene("boss.fxml");
            } else {
                Main.changeScene("employee.fxml");

                if(db.getNoticeCheck(user.getId())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notice");
                    alert.setHeaderText("You have unread notice(s)");
                    alert.setContentText("Check your notice(s) from View Notices");
                    alert.showAndWait();

                }
            }
        }
        else{
            System.out.println("Login Failed");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Login Failed. Check ID and Password");
            alert.showAndWait();
        }

    }
}
