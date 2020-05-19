package controllers;

import com.jfoenix.controls.JFXButton;
import db.DBConnection;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DashboardController {

    public JFXButton btnDashboard;
    public JFXButton btnGlobal;
    public JFXButton btnHospitals;
    public JFXButton btnQuarantineCenters;
    public JFXButton btnUsers;
    public Label lblConfirmedCases;
    public Label lblRecoveredCases;
    public Label lblDeaths;
    public Label lblUsers;
    public Label lblHospitals;
    public Label lblQuarantineCenters;
    public AnchorPane root;
    public AnchorPane subAnchorPane;

    public void initialize(){

        loadGlobalData();


        loadNumberOfUsers();


        loadNumberOfHospitals();


        loadNumberOfQuarantineCenters();

    }


    public void btnDashboard_OnAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/Dashboard.fxml"));
        Scene memberScene = new Scene(root);
        Stage mainStage = (Stage)this.root.getScene().getWindow();
        mainStage.setScene(memberScene);
        mainStage.centerOnScreen();
    }

    public void btnGlobal_OnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(this.getClass().getResource("/view/Global.fxml"));
            subAnchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            System.out.println("There's problem loading the dashboard");
            e.printStackTrace();
        }
    }

    public void btnHospitals_OnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(this.getClass().getResource("/view/Hospitals.fxml"));
            subAnchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            System.out.println("There's problem loading the dashboard");
            e.printStackTrace();
        }
    }

    public void btnQuarantineCenters_OnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(this.getClass().getResource("/view/QuarantineCenters.fxml"));
            subAnchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            System.out.println("There's problem loading the dashboard");
            e.printStackTrace();
        }
    }

    public void btnUsers_OnAction(ActionEvent actionEvent) {
        try {
            AnchorPane pane = FXMLLoader.load(this.getClass().getResource("/view/Users.fxml"));
            subAnchorPane.getChildren().setAll(pane);
        } catch (IOException e) {
            System.out.println("There's problem loading the dashboard");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    public void loadGlobalData(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT cumulativeCount,recoveries,deaths FROM globalData");

            while(rst.next()){
                String cumulativeCount = rst.getString(1);
                String recoveries = rst.getString(2);
                String deaths = rst.getString(3);

                lblConfirmedCases.setText(cumulativeCount);
                lblRecoveredCases.setText(recoveries);
                lblDeaths.setText(deaths);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadNumberOfUsers(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet resultSet = stm.executeQuery("SELECT COUNT(username) FROM user");

            while(resultSet.next()){
                String numberOfUsers = resultSet.getString(1);

                lblUsers.setText(numberOfUsers);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadNumberOfHospitals(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(id) FROM hospital");

            while(rst.next()){
                String numberOfHospitals = rst.getString(1);

                lblHospitals.setText(numberOfHospitals);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadNumberOfQuarantineCenters(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT COUNT(id) FROM qurantineCenter");

            while(rst.next()){
                String numberOfQuarantineCenter = rst.getString(1);

                lblQuarantineCenters.setText(numberOfQuarantineCenter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
