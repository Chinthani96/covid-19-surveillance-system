package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsersController {
    public JFXListView lstUsers;
    public TextField txtSearchUser;
    public JFXButton btnAddUser;
    public JFXTextField txtName;
    public JFXTextField txtID;
    public JFXTextField txtEmail;
    public JFXTextField txtTel;
    public JFXTextField txtxUsername;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXTextField txtPassword;
    public JFXComboBox cmbUserRole;

    public void initialize(){
        // 1. Load the users

    }

    public void btnAddUser_OnAction(ActionEvent actionEvent) {
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
    }

    public void loadUsers(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM user");



        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
