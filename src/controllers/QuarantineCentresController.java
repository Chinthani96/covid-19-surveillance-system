package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import util.QuarantineCentersTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QuarantineCentresController {
    public JFXListView<QuarantineCentersTM> lstQuarantineCenters;
    public TextField txtSearchHospital;
    public JFXButton btnQuarantineCenter;
    public JFXTextField txtName;
    public JFXTextField txtID;
    public JFXComboBox cmbDistricts;
    public JFXTextField txtCity;
    public JFXTextField txtCapacity;
    public JFXTextField txtDirector;
    public JFXTextField txtDirectorContact;
    public JFXTextField txtTel1;
    public JFXTextField txtTel2;
    public JFXButton btnSave;
    public JFXButton btnDelete;

    public void initialize(){
        // 1. Load the quarantine centers and the details
        loadQuarantineCenters();

        lstQuarantineCenters.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QuarantineCentersTM>() {
            @Override
            public void changed(ObservableValue<? extends QuarantineCentersTM> observable, QuarantineCentersTM oldValue, QuarantineCentersTM selectedCenter) {
                if(selectedCenter==null){
                    return;
                }
            }
        });
    }

    public void btnQuarantineCenter_OnAction(ActionEvent actionEvent) {
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
    }

    @SuppressWarnings("Duplicates")
    public void loadQuarantineCenters(){
        try {

            ObservableList<QuarantineCentersTM> quarantineCenters = lstQuarantineCenters.getItems();
            quarantineCenters.clear();

            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM qurantineCenter");

            while(rst.next()){
                String id = rst.getString(1);
                String name = rst.getString(2);
                String city = rst.getString(3);
                String district = rst.getString(4);
                String head = rst.getString(5);
                String headContact = rst.getString(6);
                String tel1 = rst.getString(7);
                String tel2 = rst.getString(8);
                String capacity = rst.getString(9);

            quarantineCenters.add(new QuarantineCentersTM(id, name, city, district, head, headContact, tel1, tel2, capacity));


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
