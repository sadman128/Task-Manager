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
import java.util.Optional;
import java.util.ResourceBundle;

public class EditUser implements Initializable {

    @FXML
    Label display_id;

    @FXML
    Label display_name;

    @FXML
    Label display_rank;

    @FXML
    TextField name_field;
    @FXML
    ChoiceBox<String> rank_field;
    @FXML
    PasswordField password_field;



    User userV = UserMsg.editUser;
    User user = Login.user;
    DataBase db = new DataBase();
    List<User> userList = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        display_name.setText(user.getName());
        display_id.setText("ID: " + String.valueOf(user.getId()));
        String rank = user.getRank().toUpperCase();
        if (user.getRank().equals("boss")) {
            rank = "Admin";
        }
        display_rank.setText("Rank: " + rank);
        rank_field.getItems().addAll("SUPERVISOR", "EMPLOYEE", "ADMIN");

        name_field.setText(userV.getName());

        String rank2 = userV.getRank().toUpperCase();
        if (userV.getRank().equals("boss")) {
            rank_field.setValue("ADMIN");
        }
        else {
            rank_field.setValue(rank2);
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
        Main.changeScene("viewuser.fxml");
    }



    @FXML
    public void viewNotice(ActionEvent event) throws IOException {
        Main.changeScene("viewnotice.fxml");
    }

    @FXML
    public void onSubmit(ActionEvent event) throws IOException, SQLException {
        
        
        String name = name_field.getText();
        String rank = "";
         if (rank_field.getValue().equals("ADMIN")) {
             rank = "boss";
         }
         else if (rank_field.getValue().equals("SUPERVISOR")) {
             rank = "supervisor";
         }
         else if (rank_field.getValue().equals("EMPLOYEE")) {
             rank = "employee";
         }
         
        
        
        String password = password_field.getText();
        DataBase db = new DataBase();
        
        if(name.equals("")){
            name = userV.getName();
        }
        if(rank.equals("")){
            rank = userV.getRank();
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Are you sure you want to update this user? \nIf you click ok these changes will be performed");
        alert.setContentText("Name : " + userV.getName() + "->" + name + "\nRank : " + userV.getRank() + "->" + rank + "\nPassword : " + password);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            userV.setName(name);
            userV.setRank(rank);
            if (!password.isEmpty()) {
                userV.setPassword(password);
            }
            db.edituser(userV);
            Main.changeScene("viewuser.fxml");
            
        }

    }



}
