package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.Notice;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewNoticeMessage implements Initializable {

    @FXML
    Label title;
    @FXML
    Label index;
    @FXML
    Label author;
    @FXML
    Label date;

    Notice notice = ViewNotice.notice;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        title.setText(notice.getTitle());
        author.setText(notice.getAuthor());
        date.setText(notice.getDate());
        int index1 = ViewNotice.index;
        String ind = Integer.toString(index1);
        index.setText(ind);

        System.out.println(ind);

    }

    @FXML
    public void onView(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notice Title : " + notice.getTitle());
        alert.setHeaderText("Publish Date : " + notice.getDate());
        alert.setContentText("Notice:" + "\n" + notice.getDetail() + "\n" + "\n" + "Author: " + notice.getAuthor());
        alert.showAndWait();
    }

}
