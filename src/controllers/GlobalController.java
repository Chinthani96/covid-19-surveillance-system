package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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

        loadGlobalData();

        LocalDate today = LocalDate.now();
        txtDate.setText(today.toString());

    }

    public void btnUpdate_OnAction(ActionEvent actionEvent) {

        String date = txtDate.getText();
        String confirmedCases = txtConfirmedCases.getText();
        String recoveries = txtRecoveredCases.getText();
        String deaths = txtDeaths.getText();



        try {
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO globalData(updatedDate,cumulativeCount,recoveries,deaths) VALUES(?,?,?,?)");
            pst.setObject(1, date);
            pst.setObject(2, confirmedCases);
            pst.setObject(3, recoveries);
            pst.setObject(4, deaths);

            int affectedRows = pst.executeUpdate();
            loadGlobalData();

            if(affectedRows<0){

                new Alert(Alert.AlertType.ERROR,"Failed to update",ButtonType.OK).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        loadGlobalData();
    }


    @SuppressWarnings("Duplicates")
    public void loadGlobalData(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT updatedDate,cumulativeCount,recoveries,deaths FROM globalData ORDER BY updatedDate DESC LIMIT 1");

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
