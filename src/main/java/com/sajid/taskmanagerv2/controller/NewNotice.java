package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.DataBase;
import com.sajid.taskmanagerv2.Main;
import com.sajid.taskmanagerv2.Notice;
import com.sajid.taskmanagerv2.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class NewNotice implements Initializable {


    @FXML
    Label display_id;

    @FXML
    Label display_name;

    @FXML
    Label display_rank;

    @FXML
            TextField title_field;
    @FXML
            TextArea detail_field;



    User user = Login.user;
    DataBase db = new DataBase();
    List<String> employees = new ArrayList<String>();


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
    public void onSubmit(ActionEvent actionEvent) throws SQLException, IOException {
        String title = title_field.getText();
        String detail = detail_field.getText();
        LocalDate date = LocalDate.now();
        Notice notice = new Notice(user.getName(), title, detail, date.toString());

        DataBase dbd= new DataBase();

        if(db.newNotice(notice)){
            Main.changeScene("newnotice.fxml");
        }
    }
}
