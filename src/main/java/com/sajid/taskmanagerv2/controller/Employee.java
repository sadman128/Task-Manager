package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.*;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Employee implements Initializable {
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
    List<String> employees = new ArrayList<String>();
    DataBase db = new DataBase();
    List<Task> taskList = new ArrayList<>();
    static Task viewTask;
    static Task task;


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
            taskList = db.getTaskByAssignedForEmployee(user.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //scroll_pane.setVvalue(0.5);

        index = 0;
        for (Task i : taskList) {
            try {
                addTaskMessage(i);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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

    public void addTaskMessage(Task task) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("taskmessage.fxml"));
        this.task = task;
//        taskBox.setAlignment(Pos.TOP_CENTER);
//        taskBox.getChildren().add(loader.load());
        index++;
        box_2.setAlignment(Pos.TOP_CENTER);
        box_2.getChildren().add(loader.load());
    }

    @FXML
    public void viewNotice(ActionEvent event) throws IOException {
        Main.changeScene("viewnotice.fxml");
    }




}
