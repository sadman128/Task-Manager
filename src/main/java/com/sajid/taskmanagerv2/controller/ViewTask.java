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

public class ViewTask implements Initializable {
    @FXML
    Label display_id;

    @FXML
    Label display_name;

    @FXML
    Label display_rank;

    @FXML
    ChoiceBox<String> employee_choice;

    @FXML
    VBox vbox1;
    @FXML
    VBox vbox2;
    @FXML
    ScrollPane scrollPane;




    User user = Login.user;
    static Task task;
    List<String> employees = new ArrayList<String>();
    DataBase db = new DataBase();
    List<Task> taskList = new ArrayList<>();

    static int index = 0;

    String search = "ALL";


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
            employees = db.getEmployee();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        employee_choice.getItems().add("ALL");
        employee_choice.getItems().addAll(employees);


        try {
            taskList = db.getTaskByAssignedForSupervisor(user.getName(), search);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        index = 0;

        vbox2.getChildren().clear();

        for (Task i : taskList) {
            try {
                addMessage(i);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @FXML
    public void onSearch(ActionEvent event) throws SQLException {
         search = employee_choice.getValue();
        if (search == null || search.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Search is empty");
            alert.showAndWait();
        }
        else{

            if (user.getRank().equals("boss")) {
                taskList = db.getTaskByAssignedForBoss(search);
            }
            else{
                taskList = db.getTaskByAssignedForSupervisor(user.getName(), search);
            }

        }


            index = 0;

            vbox2.getChildren().clear();

            for (Task i : taskList) {
                try {
                    addMessage(i);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        Main.changeScene("login.fxml");
    }

    @FXML
    public void addMessage(Task task) throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("viewtaskmessage.fxml"));
        this.task = task;

        index++;
        vbox2.setAlignment(Pos.TOP_CENTER);
        vbox2.getChildren().add(loader.load());


    }
}
