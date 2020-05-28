package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.util.StringUtils;
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

    // Date validation

    public void initialize(){

        loadGlobalData();

        LocalDate today = LocalDate.now();
        txtDate.setText(today.toString());
        updatedCountPerDay();

    }

    @SuppressWarnings("Duplicates")
    public void btnUpdate_OnAction(ActionEvent actionEvent) {

        String date = txtDate.getText();
        String confirmedCases = txtConfirmedCases.getText();
        String recoveries = txtRecoveredCases.getText();
        String deaths = txtDeaths.getText();

        //-----------------------------------------------------------------------------VALIDATIONS----------------------------------------------------------------------------------------------------------------------------------------------------------

        if(confirmedCases.trim().length()==0 || recoveries.trim().length()==0 || deaths.trim().length()==0 || date.trim().length()==0){
            new Alert(Alert.AlertType.ERROR, "The fields cannot be empty", ButtonType.OK).show();
        }

        // TODO : VALIDATION IF SYMBOLS ARE TYPED.
        if(confirmedCases.matches("^[a-zA-Z]*$")||
                recoveries.matches("^[a-zA-Z]*$")||
                deaths.matches("^[a-zA-Z]*$")
        ){
            new Alert(Alert.AlertType.ERROR,"The fields, Confirmed Cases, Recovered Cases and Deaths should be numeric",ButtonType.OK).show();
        }


        if(updatedCountPerDay()>=1 && updatedCountPerDay()<2){ ButtonType type = null;
            Alert alert = new Alert(Alert.AlertType.ERROR, "An entry for today was already updated. Are you sure you want to go ahead for another entry?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait().ifPresent(buttonType -> {
                if(buttonType==ButtonType.YES){
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
                    alert.close();
                }
                else{
                    alert.close();
                }
            });
        }
        else if(updatedCountPerDay()==2){
            new Alert(Alert.AlertType.ERROR,"You have exceeded the maximum amount of updates allowed per day. Please try again tomorrow",ButtonType.OK).show();
        }
        else{
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

        }



        loadGlobalData();
    }

    @SuppressWarnings("Duplicates")
    public void loadGlobalData(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT updatedDate,cumulativeCount,recoveries,deaths FROM globalData ORDER BY updateId DESC LIMIT 1");

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

    private int updatedCountPerDay(){
        String date = txtDate.getText();

        int count=0;
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT count(updatedDate) FROM globalData where updatedDate='"+date+"'");

            while(rst.next()){
                String countStr = rst.getString(1);
                count = Integer.parseInt(countStr);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}
