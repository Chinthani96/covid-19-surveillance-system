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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import util.HospitalsTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class HospitalsController {
    public JFXListView<HospitalsTM> lstHospitals;
    public TextField txtSearchHospital;
    public JFXButton btnAddHospital;
    public JFXTextField txtName;
    public JFXTextField txtID;
    public JFXComboBox<String> cmbDistricts;
    public JFXTextField txtCity;
    public JFXTextField txtCapacity;
    public JFXTextField txtDirector;
    public JFXTextField txtDirectorContact;
    public JFXTextField txtTel1;
    public JFXTextField txtTel2;
    public JFXTextField txtFax;
    public JFXTextField txtEmail;
    public JFXButton btnSave;
    public JFXButton btnDelete;

    ArrayList<HospitalsTM> hospitalsList = new ArrayList<>();

    @SuppressWarnings("Duplicates")
    public void initialize(){

        //basic initializations
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

        loadHospitals();

        ObservableList districts = cmbDistricts.getItems();
        districts.addAll("Northern-Jaffna","Northern-Kilinochchi","Northern-Mannar","Northern-Mullaitivu","Northern-Vavuniya",
                "NorthWestern-Kurunegala","NorthWestern-Puttalam",
                "NorthCentral-Anuradhapura","NorthCentral-Polonnaruwa",
                "Central-Kandy","Central-Matale","Central-Nuwara Eliya",
                "Western-Colombo","Western-Gampaha","Western-Kalutara",
                "Southern-Galle","Southern-Matara","Southern-Hambantota",
                "Sabaragamuwa-Kegalle","Sabaragamuwa-Ratnapura",
                "Eastern-Trincomalee","Eastern-Batticaloa","Eastern-Ampara",
                "Uva-Badulla","Uva-Monaragala");


        lstHospitals.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HospitalsTM>() {
            @Override
            public void changed(ObservableValue<? extends HospitalsTM> observable, HospitalsTM oldValue, HospitalsTM selectedHospital) {
                if(selectedHospital==null){
                    return;
                }
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                btnSave.setText("Update");
                txtID.setText(selectedHospital.getId());
                txtName.setText(selectedHospital.getName());
                txtCapacity.setText(selectedHospital.getCapacity());
                txtCity.setText(selectedHospital.getCity());
                txtDirector.setText(selectedHospital.getDirector());
                txtDirectorContact.setText(selectedHospital.getDirectorContact());
                txtTel1.setText(selectedHospital.getTel1());
                txtTel2.setText(selectedHospital.getTel2());
                txtFax.setText(selectedHospital.getFax());
                txtEmail.setText(selectedHospital.getEmail());
                cmbDistricts.getSelectionModel().select(selectedHospital.getDistrict());
            }
        });

        txtSearchHospital.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<HospitalsTM> hospitals = lstHospitals.getItems();
                hospitals.clear();

                for (HospitalsTM hospital:hospitalsList){
                    if(hospital.getName().contains(newValue)){
                        hospitals.add(hospital);
                    }
                }
            }
        });
    }

    @SuppressWarnings("Duplicates")
    public void btnAddHospital_OnAction(ActionEvent actionEvent) {
        //initializing the buttons and the text fields right after the Add Hospital Button is clicked
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        btnSave.setText("Save");

        clearTextFields();

        //ID auto increment
        IdAutoIncrement();

    }

    @SuppressWarnings("Duplicates")
    public void btnSave_OnAction(ActionEvent actionEvent) {
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setText("Save");
        clearTextFields();

        String id = txtID.getText();
        String name = txtName.getText();
        String city = txtCity.getText();
        String district = cmbDistricts.getSelectionModel().getSelectedItem();
        String capacityString = txtCapacity.getText();
        int capacity = Integer.parseInt(capacityString);
        String director = txtDirector.getText();
        String directorContact = txtDirectorContact.getText();
        String tel1 = txtTel1.getText();
        String tel2 = txtTel2.getText();
        String fax = txtFax.getText();
        String email = txtEmail.getText();

        if(id.trim().length()==0 ||
                name.trim().length()==0 ||
                city.trim().length()==0 ||
                capacityString.trim().length()==0 ||
                director.trim().length()==0 ||
                directorContact.trim().length()==0 ||
                tel1.trim().length()==0 ||
                tel2.trim().length()==0 ||
                fax.trim().length() ==0 ||
                email.trim().length() ==0
        ){
            new Alert(Alert.AlertType.ERROR,"The fields cannot be empty. Please fill all fields",ButtonType.OK).show();
        }

        if(btnSave.getText().equals("Update")){
            try {
                PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("UPDATE hospital SET name=?,city=?,district=?,capacity=?,director=?,directorContactNo=?,tel1=?,tel2=?,fax=?,email=? WHERE id='" + txtID.getText() + "'");
                pst.setObject(1,name);
                pst.setObject(2,city);
                pst.setObject(3,district);
                pst.setObject(4,capacity);
                pst.setObject(5,director);
                pst.setObject(6,directorContact);
                pst.setObject(7,tel1);
                pst.setObject(8,tel2);
                pst.setObject(9,fax);
                pst.setObject(10,email);

                int affectedRows = pst.executeUpdate();
                if(affectedRows<0){
                    new Alert(Alert.AlertType.ERROR,"Failed to update hospital details", ButtonType.OK).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else{
            try {
                PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO hospital VALUES(?,?,?,?,?,?,?,?,?,?,?)");
                pst.setObject(1,id);
                pst.setObject(2,name);
                pst.setObject(3,city);
                pst.setObject(4,district);
                pst.setObject(5,capacity);
                pst.setObject(6,director);
                pst.setObject(7,directorContact);
                pst.setObject(8,tel1);
                pst.setObject(9,tel2);
                pst.setObject(10,fax);
                pst.setObject(11,email);

                int affectedRows = pst.executeUpdate();

                if(affectedRows<0){
                    new Alert(Alert.AlertType.ERROR,"Failed to add hospital details", ButtonType.OK).show();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        loadHospitals();
    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setText("Save");
        clearTextFields();

        String id = txtID.getText();

        try {
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM hospital WHERE id='" + id + "'");
            int affectedRows = pst.executeUpdate();

            if(affectedRows<0){
                new Alert(Alert.AlertType.ERROR,"Failed to delete hospital details", ButtonType.OK).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadHospitals();
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
                hospitalsList.add(new HospitalsTM(id,name,city,district,capacity,director,directorContact,tel1,tel2,fax,email));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void IdAutoIncrement(){

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT id FROM hospital ORDER BY id DESC LIMIT 1");

            while(rst.next()){
                String id = rst.getString(1);

                if(id==null){
                    txtID.setText("H001");
                }
                else {
                    String val = id.substring(1, 4);
                    int value = Integer.parseInt(val);

                    if (value < 10) {
                        value += 1;
                        txtID.setText("H00"+value);
                    }
                    else if(value<100){
                        value +=1;
                        txtID.setText("H0"+value);
                    }
                    else{
                        value+=1;
                        txtID.setText("H"+value);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearTextFields(){
        txtName.clear();
        txtCapacity.clear();
        txtCity.clear();
        txtDirector.clear();
        txtDirectorContact.clear();
        txtFax.clear();
        txtTel1.clear();
        txtTel2.clear();
        cmbDistricts.getSelectionModel().clearSelection();
        txtEmail.clear();
    }
}
