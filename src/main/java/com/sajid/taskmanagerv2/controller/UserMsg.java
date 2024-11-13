package com.sajid.taskmanagerv2.controller;

import com.sajid.taskmanagerv2.User;
import com.sajid.taskmanagerv2.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.sajid.taskmanagerv2.controller.Login.user;

public class UserMsg implements Initializable {

    @FXML
    Label nameLabel;
    @FXML
    Label rankLabel;
    @FXML
    Label indexLabel;

    User userV = ViewUser.userV;
    static User editUser;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        String name = userV.getName();
        String rank = userV.getRank().toUpperCase();
        int id = userV.getId();
        String ind = String.valueOf(id);

        if(rank.equals("BOSS")){
            rank = "Admin";
        }

        nameLabel.setText(name);
        indexLabel.setText(ind);
        rankLabel.setText(rank);


    }


    @FXML
    public void onDelete(ActionEvent event) throws SQLException, IOException {

         if (userV.getId() == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("ID 1 can not be deleted. Please Contact with the respective administrator.");
            alert.showAndWait();

        }

         else if (userV.getRank().equals("boss")){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Error");
             alert.setContentText("You are not allowed to delete this user");
             alert.showAndWait();
         }

        else if(userV.getRank().equals("boss") && user.getId() == 1){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete this user?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                DataBase db = new DataBase();
                db.deleteUser(userV.getId(), userV.getName());
                Main.changeScene("viewuser.fxml");

            }

        }
        else if (userV.getRank().equals("supervior") || userV.getRank().equals("employee")){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Are you sure you want to delete this user?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                DataBase db = new DataBase();
                db.deleteUser(userV.getId(), userV.getName());
                Main.changeScene("viewuser.fxml");
            }

        }






    }

    @FXML
    public void onEdit(ActionEvent event) throws SQLException, IOException {

        if (userV.getId() == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("ID 1 can not be edited. Please Contact with the respective administrator.");
            alert.showAndWait();
        }
        else {
            editUser = userV;
            Main.changeScene("edituser.fxml");
        }



    }


}
