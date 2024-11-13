package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AssignTask implements Initializable {
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
    @FXML
    ChoiceBox<String> employee_choice;
    @FXML
    DatePicker date_field;


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
        try {
            employees = db.getEmployee();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        employee_choice.getItems().addAll(employees);

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
    public void onTaskSubmit(ActionEvent event) throws SQLException, IOException {

        if(title_field.getText().equals("") || detail_field.getText().equals("") || employee_choice.getValue().equals("") || date_field.getValue().toString().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("All fields must be filled");
            alert.showAndWait();
        }
        else {
            String name = employee_choice.getValue();
            String[] part = name.split("-");
            String assignedTo = part[1];
            String assignedBy = user.getName();
            String title= title_field.getText();
            String detail = detail_field.getText();
            LocalDate due = date_field.getValue();
            String status = "Pending";
            Task task = new Task(assignedTo, assignedBy, title, detail, due, status);
            if (db.assigntask(task)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Something went wrong");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Done");
                alert.setContentText("Task has been submitted");
                alert.showAndWait();

                String content ="```"+ user.getName() + " assigned a new task to:  " + name + ", title: "+ title + "```";
                DiscordWebhook discordWebhook = new DiscordWebhook("task", content);

                Main.changeScene("supervisor.fxml");
            }


        }


    }
}
