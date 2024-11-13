module com.sajid.taskmanagerv2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.sajid.taskmanagerv2 to javafx.fxml;
    exports com.sajid.taskmanagerv2;
    exports com.sajid.taskmanagerv2.controller;
    opens com.sajid.taskmanagerv2.controller to javafx.fxml;
}