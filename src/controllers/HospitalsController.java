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
import util.HospitalsTM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HospitalsController {
    public JFXListView<HospitalsTM> lstHospitals;
    public TextField txtSearchHospital;
    public JFXButton btnAddHospital;
    public JFXTextField txtName;
    public JFXTextField txtID;
    public JFXComboBox cmbDistricts;
    public JFXTextField txtCity;
    public JFXTextField txtCapacity;
    public JFXTextField txtDirector;
    public JFXTextField txtDirectorContact;
    public JFXTextField txtTel1;
    public JFXTextField txtTel2;
    public JFXTextField txtTel3;
    public JFXTextField txtFax;
    public JFXTextField txtEmail;
    public JFXButton btnSave;
    public JFXButton btnDelete;

    public void initialize(){

        loadHospitals();


        lstHospitals.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HospitalsTM>() {
            @Override
            public void changed(ObservableValue<? extends HospitalsTM> observable, HospitalsTM oldValue, HospitalsTM selectedHospital) {
                if(selectedHospital==null){
                    return;
                }

            }
        });
    }

    public void btnAddHospital_OnAction(ActionEvent actionEvent) {
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
    }

    @SuppressWarnings("Duplicates")
    public void loadHospitals(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM hospital");

            ObservableList<HospitalsTM> hospitals = lstHospitals.getItems();
            hospitals.clear();

            while(rst.next()){
                String id = rst.getString(1);
                String name = rst.getString(2);
                String city = rst.getString(3);
                String district = rst.getString(4);
                String capacity = rst.getString(5);
                String director = rst.getString(6);
                String directorContact = rst.getString(7);
                String tel1 = rst.getString(8);
                String tel2 = rst.getString(9);
                String fax = rst.getString(10);
                String email = rst.getString(11);

                hospitals.add(new HospitalsTM(id,name,city,district,capacity,director,directorContact,tel1,tel2,fax,email));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
