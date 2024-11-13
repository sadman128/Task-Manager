package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ViewNotice implements Initializable {

    @FXML
    Label display_id;

    @FXML
    Label display_name;

    @FXML
    Label display_rank;

    @FXML
    VBox taskBox;

    @FXML
    VBox box_2;

    @FXML
    ScrollPane scroll_pane;

    static int index;



    User user = Login.user;
    DataBase db = new DataBase();
    List<Notice> notices = new ArrayList<>();
    List<Notice> noticesReverse = new ArrayList<>();
    static  Notice notice;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        display_name.setText(user.getName());
        display_id.setText("ID: " + String.valueOf(user.getId()));
        String rank = user.getRank().toUpperCase();
        if (user.getRank().equals("boss")) {
            rank = "Admin";
        }
        display_rank.setText("Rank: " + rank);


        try {
            notices = db.getAllNotices();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        index = 0;

        noticesReverse = notices.reversed();

        for (Notice notice : noticesReverse) {
            try {
                addNoticeMsg(notice);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



        try {
            db.markNotice(user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


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
    public void onBack(ActionEvent event) throws IOException {
        if(user.getRank().equals("supervisor")){
            Main.changeScene("supervisor.fxml");
        } else if (user.getRank().equals("boss")) {
            Main.changeScene("boss.fxml");

        } else {
            Main.changeScene("employee.fxml");
        }
    }

    public void addNoticeMsg(Notice notice) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("noticemessage.fxml"));
        this.notice = notice;
        index++;
        box_2.setAlignment(Pos.TOP_CENTER);
        box_2.getChildren().add(loader.load());
    }
}
