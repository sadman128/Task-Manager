package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.Main;
import com.sajid.taskmanagerv2.Task;
import com.sajid.taskmanagerv2.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailView implements Initializable {

    @FXML
    private Label assignedBy_label;

    @FXML
    private Label date_label;

    @FXML
    private Text detail_text;

    @FXML
    private Label title_label;

    @FXML
    private Label display_id;

    @FXML
    private Label display_name;

    @FXML
    private Label display_rank;
    @FXML
    VBox detail_box;


    User user = Login.user;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        display_name.setText(user.getName());
        display_id.setText("ID: " + String.valueOf(user.getId()));
        String rank = user.getRank().toUpperCase();
        if (user.getRank().equals("boss")) {
            rank = "Admin";
        }
        display_rank.setText("Rank: " + rank);

         Task task = Taskmessage.viewTask;
        title_label.setText(task.getTitle());
        assignedBy_label.setText(task.getAssignedBy());

        date_label.setText(task.getDue().toString());
        detail_box.getChildren().clear();
        detail_box.setAlignment(Pos.TOP_CENTER);
        detail_box.getChildren().add(detail_text);
        detail_text.setText(task.getDescription());

    }


    @FXML
    public void onBack(ActionEvent event) throws IOException {
            Main.changeScene("employee.fxml");

    }

    @FXML
    public void onLogout(ActionEvent event) throws IOException {
        Main.changeScene("login.fxml");
    }
}
