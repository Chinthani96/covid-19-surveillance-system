package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class GlobalController {

    public Label lblConfirmedCases;
    public Label lblRecoveredCases;
    public Label lblDeaths;
    public JFXTextField txtLastUpdated;
    public JFXButton btnUpdate;
    public JFXTextField txtDate;
    public JFXTextField txtDeaths;
    public JFXTextField txtConfirmedCases;
    public JFXTextField txtRecoveredCases;

    public void initialize(){
        // 1. Load the global data
        loadGlobalData();

        //initializing the date
        Date date = new Date();
        txtDate.setText(date.toString());

    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {
    }


    @SuppressWarnings("Duplicates")
    public void loadGlobalData(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM globalData");

            while(rst.next()){
                String dateTime = rst.getString(1);
                String cumulativeCount = rst.getString(2);
                String recoveries = rst.getString(3);
                String deaths = rst.getString(4);

                lblConfirmedCases.setText(cumulativeCount);
                lblRecoveredCases.setText(recoveries);
                lblDeaths.setText(deaths);
                txtLastUpdated.setText(dateTime);

                txtConfirmedCases.setText(cumulativeCount);
                txtRecoveredCases.setText(recoveries);
                txtDeaths.setText(deaths);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
