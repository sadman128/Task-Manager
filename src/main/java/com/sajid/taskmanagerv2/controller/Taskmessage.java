package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.DataBase;
import com.sajid.taskmanagerv2.DiscordWebhook;
import com.sajid.taskmanagerv2.Main;
import com.sajid.taskmanagerv2.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Taskmessage implements Initializable {

    @FXML
     Label due_label;

    @FXML
     Label title_label;
    @FXML
    Label status_label;
    @FXML
    Label index_label;

    Task task = Employee.task;
    static Task viewTask;

    String title = task.getTitle();
    String due = task.getDue().toString();
    String status = task.getStatus();
    int index = Employee.index;
    String ind = Integer.toString(index);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title_label.setText(title);
        due_label.setText(due);
        index_label.setText(ind);
        status_label.setText(status);
    }

    @FXML
    public void onMarkDone(ActionEvent event) throws SQLException, IOException {
        DataBase db = new DataBase();
        db.markDone(title, Login.user.getName());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Done");
        alert.setContentText("Mark Done");
        alert.showAndWait();
        Main.changeScene("employee.fxml");
    }

    @FXML
    public void onView(ActionEvent event) throws SQLException, IOException {
            DataBase db = new DataBase();
           viewTask = db.viewTask(title, Login.user.getName());

           Main.changeScene("detailview.fxml");
    }


}
