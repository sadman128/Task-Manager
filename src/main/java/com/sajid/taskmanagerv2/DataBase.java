package com.sajid.taskmanagerv2;

import com.sajid.taskmanagerv2.controller.Login;
import com.sajid.taskmanagerv2.controller.UserMsg;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DataBase {

     static Connection connection;

     public void getConnection() throws SQLException {
          //connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root");
         String host = "mysql-taskmanger-d-taskmanager-discord.i.aivencloud.com";
         String databaseName = "defaultdb";
         String port = "18996";
         String userName = "avnadmin";
         String password = "AVNS_Yv7-InPPJR6qBVfgU0W";
         connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName + "?sslmode=require", userName, password);

         System.out.println("Connected to database");
     }

    public boolean checkLogin(String id, String password) throws SQLException {
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root");
        Statement statement = connection.createStatement();
        String query = "select * from user where id = " + Integer.parseInt(id) + " ;";
        ResultSet resultSet = statement.executeQuery(query);
        if (!resultSet.next()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No Such User Found");
            alert.showAndWait();
        }
            String pass = resultSet.getString("password");
            if (pass.equals(password)) {

                return true;
            }

        return false;
    }

    public User getUser(String id) throws SQLException {
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root");
        Statement statement = connection.createStatement();
        String query = "select * from user where id = " + Integer.parseInt(id) + " ;";
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
           String name = resultSet.getString("name");
           String password = resultSet.getString("password");
           String rank = resultSet.getString("user_rank");
           String gender = resultSet.getString("gender");
           User user = new User(Integer.parseInt(id), name, rank, gender, password);
            return user;
        }

        return null;

    }
    public void changePassword(String id, String password) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "update user set password = '" + password + "' where id = " + Integer.parseInt(id) + " ;";
        statement.executeUpdate(query);
    }
    public int getNextId() throws SQLException {
         Statement statement = connection.createStatement();
         String query = "select * from user ;";
         ResultSet resultSet = statement.executeQuery(query);
         int id = 0;
         while (resultSet.next()) {
             id = Integer.parseInt(resultSet.getString("id"));
         }
         return (id +1);
    }

    public boolean createUser(User newUser) throws SQLException {
         Statement statement = connection.createStatement();
         String query = "INSERT INTO user VALUES(" + String.valueOf(newUser.getId()) + ", '" + newUser.getName() + "', '" + newUser.getRank() + "', '" + newUser.getGender() + "', '" + newUser.getPassword() + "', 'TRUE');";
         return statement.execute(query);
    }

    public List<String> getEmployee() throws SQLException {
        Statement statement = connection.createStatement();
        String query = "select * from user where user_rank = 'employee';";
        ResultSet resultSet = statement.executeQuery(query);
        List<String> employeelist = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String id = resultSet.getString("id");
            employeelist.add(id + "-" + name);
        }
        return employeelist;
    }

    public boolean assigntask(Task task) throws SQLException {
         Statement statement = connection.createStatement();
         String query = "INSERT INTO task VALUES('" + task.getAssignedTo() + "', '" + task.getAssignedBy() + "', '" + task.getTitle() + "','" + task.getDescription() + "','" + task.getDue() + "','" + task.getStatus() + "');";
         return statement.execute(query);
    }

    public List<Task> getTaskByAssignedForSupervisor(String name, String search) throws SQLException {
         List<Task> taskList = new ArrayList<>();
         Statement statement = connection.createStatement();
         String query;
         if (search.equals("ALL")) {
             query = "SELECT * FROM task WHERE assigned_by = '"+ name + "';" ;
         }
         else{
             String[] part = search.split("-");
             search = part[1];
             query = "SELECT * FROM task WHERE assigned_by = '"+ name + "' and assigned_to = '"+ search + "';" ;
         }
         ResultSet resultSet = statement.executeQuery(query);
         while (resultSet.next()) {
             String assignedTo = resultSet.getString("assigned_to");
             String assignedBy = resultSet.getString("assigned_by");
             String title = resultSet.getString("title");
             String detail = resultSet.getString("details");
             LocalDate due = resultSet.getDate("due").toLocalDate();
             String status = resultSet.getString("status");
             Task task = new Task(assignedTo,assignedBy,title,detail,due,status);
             taskList.add(task);
         }
         return taskList;

    }

    public void deleteTask(String title, String name) throws SQLException, IOException {
         Statement statement = connection.createStatement();
         String query = "DELETE FROM task WHERE assigned_to = '"+ name + "' and title = '"+ title + "';";
         statement.execute(query);
        String content ="```"+ Login.user.getName() + " deleted a task of :  " + name + ", title: "+ title + "```";
        DiscordWebhook discordWebhook = new DiscordWebhook("task", content);

    }

    public List<Task> getTaskByAssignedForEmployee(String name) throws SQLException {
        List<Task> taskList = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM task WHERE assigned_to = '"+ name + "';" ;
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String assignedTo = resultSet.getString("assigned_to");
            String assignedBy = resultSet.getString("assigned_by");
            String title = resultSet.getString("title");
            String detail = resultSet.getString("details");
            LocalDate due = resultSet.getDate("due").toLocalDate();
            String status = resultSet.getString("status");
            Task task = new Task(assignedTo,assignedBy,title,detail,due,status);
            taskList.add(task);
        }
        return taskList;

    }

    public void markDone(String title, String name) throws SQLException, IOException {
         Statement statement = connection.createStatement();
         String query = "update task set status = 'Done' where assigned_to = '" + name + "' and title = '"+ title + "';";
         statement.execute(query);

        String content ="```"+ name + " marked done a task, title: "+ title + "```";
        DiscordWebhook discordWebhook = new DiscordWebhook("task", content);
    }

    public Task viewTask(String title1, String name) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM task WHERE assigned_to = '" + name + "' and title = '"+ title1 + "';";
        ResultSet resultSet = statement.executeQuery(query);
        Task task = null;
        while (resultSet.next()) {
            String assignedTo = resultSet.getString("assigned_to");
            String assignedBy = resultSet.getString("assigned_by");
            String title = resultSet.getString("title");
            String detail = resultSet.getString("details");
            LocalDate due = resultSet.getDate("due").toLocalDate();
            String status = resultSet.getString("status");
            task = new Task(assignedTo,assignedBy,title,detail,due,status);

        }
        return task;
    }

    public void markRedo(String title, String name) throws SQLException, IOException {
        Statement statement = connection.createStatement();
        String query = "update task set status = 'Redo' where assigned_to = '" + name + "' and title = '"+ title + "';";
        statement.execute(query);

        String content ="```"+ Login.user.getName() + " marked Redo a task assigned to: " + name + " title: "+ title + "```";
        DiscordWebhook discordWebhook = new DiscordWebhook("task", content);
    }

    public boolean newNotice(Notice notice) throws SQLException, IOException {
         Statement statement = connection.createStatement();
         String query = "insert into notice values('" + notice.author + "' , '" + notice.title + "', '" + notice.detail + "', '" + notice.date + "');";
         if(!statement.execute(query)) {

             String query2 = "update user set noticeCheck = 'TRUE' where name != '" + notice.author + "'; ";
             statement.execute(query2);

             String content = "```" + Login.user.getName() + " created a new notice, title: " + notice.title + "```";
             DiscordWebhook discordWebhook = new DiscordWebhook("task", content);

             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
             alert.setTitle("Success");
             alert.setContentText("Notice Published Successfully");
             alert.showAndWait();
             return true;

         }
         else {
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setTitle("Erros");
             alert.setContentText("Something went wrong");
             alert.showAndWait();
             return false;
         }
    }

    public List<Notice> getAllNotices() throws SQLException {
         Statement statement = connection.createStatement();
         List<Notice> noticeList = new ArrayList<>();
         String query = "SELECT * FROM notice;";
         ResultSet resultSet = statement.executeQuery(query);
         while (resultSet.next()) {
             String author = resultSet.getString("author");
             String title = resultSet.getString("title");
             String detail = resultSet.getString("detail");
             String date = resultSet.getString("publish");
             Notice notice = new Notice(author,title,detail,date);
             noticeList.add(notice);
         }
         return noticeList;
    }

    public boolean getNoticeCheck(int id) throws SQLException {
         Statement statement = connection.createStatement();
         String query = "SELECT * FROM user WHERE id = " + id;
         ResultSet resultSet = statement.executeQuery(query);
         while (resultSet.next()) {
             String noticeCheck = resultSet.getString("noticeCheck");
             if(noticeCheck.equals("TRUE")) {
                 return true;
             }
             else{
                 return false;
             }
         }
         return false;
    }

    public void markNotice(int id) throws SQLException {
         Statement statement = connection.createStatement();
         String query = "update user set noticeCheck = 'FALSE' where id = " + id;
         statement.execute(query);
    }

    public List<Task> getTaskByAssignedForBoss(String search) throws SQLException {
        List<Task> taskList = new ArrayList<>();
        Statement statement = connection.createStatement();
        String query;
        if (search.equals("ALL")) {
            query = "SELECT * FROM task ;" ;
        }
        else{
            String[] part = search.split("-");
            search = part[1];
            query = "SELECT * FROM task where assigned_to = '"+ search + "';" ;
        }
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            String assignedTo = resultSet.getString("assigned_to");
            String assignedBy = resultSet.getString("assigned_by");
            String title = resultSet.getString("title");
            String detail = resultSet.getString("details");
            LocalDate due = resultSet.getDate("due").toLocalDate();
            String status = resultSet.getString("status");
            Task task = new Task(assignedTo,assignedBy,title,detail,due,status);
            taskList.add(task);
        }
        return taskList;

    }


    public List<User> getUserAll() throws SQLException {
        //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "root", "root");
        Statement statement = connection.createStatement();
        String query = "select * from user;";
        ResultSet resultSet = statement.executeQuery(query);

        List<User> userList = new ArrayList<>();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String rank = resultSet.getString("user_rank");
            String gender = resultSet.getString("gender");

            User user = new User(id, name, rank, gender, password);
            userList.add(user);

        }

        return userList;

    }

    public void deleteUser(int id ,String name) throws SQLException, IOException {
        Statement statement = connection.createStatement();
        String query = "DELETE FROM user WHERE id = " + id + ";";
        statement.execute(query);
        String content ="```"+ Login.user.getName() + " deleted a user, id =  " + id + " name : " + name + "```";
        DiscordWebhook discordWebhook = new DiscordWebhook("task", content);

    }

    public void edituser(User user) throws SQLException, IOException {
         Statement statement = connection.createStatement();
         String query = "update user set name = '" + user.name + "', password = '" + user.password + "', user_rank = '" + user.getRank() + "' where id = " + user.id ;
         statement.execute(query);
        String content ="```"+ Login.user.getName() + " updated a user, id =  " + user.getId() + " name : " + user.getName() + "```";
        DiscordWebhook discordWebhook = new DiscordWebhook("activity", content);

    }


}
