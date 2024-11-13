package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.DataBase;
import com.sajid.taskmanagerv2.Main;
import com.sajid.taskmanagerv2.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewTaskMessage implements Initializable {

    @FXML
    Label due_label;

    @FXML
    Label title_label;
    @FXML
    Label status_label;
    @FXML
    Label index_label;

    @FXML
    Label name_label;

    Task task = ViewTask.task;

    static Task viewTask;

    String title = task.getTitle();
    String name = task.getAssignedTo();
    String due = task.getDue().toString();
    String status = task.getStatus();
    int index = ViewTask.index;
    String ind = Integer.toString(index);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        title_label.setText(title);
        name_label.setText(name);
        due_label.setText(due);
        index_label.setText(ind);
        status_label.setText(status);
    }

    @FXML
    public void onDeleteTask(ActionEvent event) throws SQLException, IOException {
        DataBase db = new DataBase();
        db.deleteTask(title, name);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Done");
        alert.setContentText("Deleted Successfully");
        alert.showAndWait();

        Main.changeScene("ViewTask.fxml");

    }

    @FXML
    public void onRedo(ActionEvent event) throws SQLException, IOException {
        DataBase db = new DataBase();
        db.markRedo(title, name);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Done");
        alert.setContentText("Marked Redo Successfully");
        alert.showAndWait();
        Main.changeScene("ViewTask.fxml");
    }


}
