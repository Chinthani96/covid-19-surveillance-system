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
import util.QuarantineCentersTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class QuarantineCentresController {
    public JFXListView<QuarantineCentersTM> lstQuarantineCenters;
    public TextField txtSearch;
    public JFXButton btnQuarantineCenter;
    public JFXTextField txtName;
    public JFXTextField txtID;
    public JFXComboBox<String> cmbDistricts;
    public JFXTextField txtCity;
    public JFXTextField txtCapacity;
    public JFXTextField txtHead;
    public JFXTextField txtHeadContact;
    public JFXTextField txtTel1;
    public JFXTextField txtTel2;
    public JFXButton btnSave;
    public JFXButton btnDelete;

    ArrayList<QuarantineCentersTM> centers = new ArrayList<>();

    @SuppressWarnings("Duplicates")
    public void initialize(){
        btnSave.setDisable(true);
        btnDelete.setDisable(true);


        disableTextFields();
        loadQuarantineCenters();

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



        lstQuarantineCenters.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<QuarantineCentersTM>() {
            @Override
            public void changed(ObservableValue<? extends QuarantineCentersTM> observable, QuarantineCentersTM oldValue, QuarantineCentersTM selectedCenter) {
                if(selectedCenter==null){
                    return;
                }
                enableTextFields();
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                btnSave.setText("Update");

                txtID.setText(selectedCenter.getId());
                txtName.setText(selectedCenter.getName());
                txtCapacity.setText(selectedCenter.getCapacity());
                txtCity.setText(selectedCenter.getCity());
                txtHead.setText(selectedCenter.getHead());
                txtHeadContact.setText(selectedCenter.getHeadContact());
                cmbDistricts.getSelectionModel().select(selectedCenter.getDistrict());
                txtTel1.setText(selectedCenter.getTel1());
                txtTel2.setText(selectedCenter.getTel2());

            }
        });

        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<QuarantineCentersTM> quarantineCenters = lstQuarantineCenters.getItems();
                quarantineCenters.clear();

                for (QuarantineCentersTM center : centers){
                    if(center.getName().contains(newValue)){
                        quarantineCenters.add(center);
                    }
                }
            }
        });
    }

    @SuppressWarnings("Duplicates")
    public void btnQuarantineCenter_OnAction(ActionEvent actionEvent) {

        btnDelete.setDisable(true);
        btnSave.setDisable(false);

        enableTextFields();

        clearTextFields();

        //ID auto increment
        IdAutoIncrement();
    }

    @SuppressWarnings("Duplicates")
    public void btnSave_OnAction(ActionEvent actionEvent) {
        String id = txtID.getText();
        String name = txtName.getText();
        String city = txtCity.getText();
        String district = cmbDistricts.getSelectionModel().getSelectedItem();
        String capacity = txtCapacity.getText();
        String head = txtHead.getText();
        String headContact = txtHeadContact.getText();
        String tel1 = txtTel1.getText();
        String tel2 = txtTel2.getText();

        //-----------------------------------------------------------------------------VALIDATIONS----------------------------------------------------------------------------------------------------------------------------------------------------------

        if(
                id.trim().length() ==0 ||
                name.trim().length()==0 ||
                city.trim().length()==0 ||
                district.trim().length()==0 ||
                capacity.trim().length()==0 ||
                tel1.trim().length()==0 ||
                tel2.trim().length()==0 ||
                head.trim().length() ==0 ||
                headContact.trim().length() ==0
        ){
            System.out.println(name.trim().length());
            System.out.println(city.trim().length());
            System.out.println(district.trim().length());
            System.out.println(capacity.trim().length());
            System.out.println(tel1.trim().length());
            System.out.println(tel2.trim().length());
            System.out.println(head.trim().length());
            System.out.println(headContact.trim().length());
            new Alert(Alert.AlertType.ERROR,"The fields cannot be empty. Please fill all fields",ButtonType.OK).show();
            return;
        }

        if(capacity.matches("^[a-zA-Z]$")){
            new Alert(Alert.AlertType.ERROR,"Capacity field should have a numeric value. Please enter valid value!",ButtonType.OK).show();
            return;
        }

        if(tel1.trim().length()!=11 || tel2.trim().length()!=11 || headContact.trim().length()!=11){
            int stringCount = tel1.length();
            int diff=10-stringCount;

            if (diff>0) {
                new Alert(Alert.AlertType.ERROR,""+diff+" digit(s) missing from the number. Please enter a valid number!",ButtonType.OK).show();
            }
            else{
                new Alert(Alert.AlertType.ERROR,"The contact number field has "+(-1*(diff))+" digit(s) more than the expected amount(10). Please enter a valid number!",ButtonType.OK).show();
            }
            return;
        }

        if(!tel1.matches("^[0-9]{1,3}[-][0-9]{1,7}$") ||
            !tel2.matches("^[0-9]{1,3}[-][0-9]{1,7}$") ||
                !headContact.matches("^[0-9]{1,3}[-][0-9]{1,7}$")
        ){
            new Alert(Alert.AlertType.ERROR,"Invalid contact number(s). Please check the format given and enter a valid number!",ButtonType.OK).show();
            return;
        }

        //        ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


        int capacityInt = Integer.parseInt(capacity);

        if(btnSave.getText().equals("Update")){
            try {
                PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("UPDATE qurantineCenter SET name=?,city=?,district=?,head=?,headContactNo=?,tel1=?,tel2=?,capacity=? WHERE id='" + txtID.getText() + "'");
                pst.setObject(1,name);
                pst.setObject(2,city);
                pst.setObject(3,district);
                pst.setObject(4,head);
                pst.setObject(5,headContact);
                pst.setObject(6,tel1);
                pst.setObject(7,tel2);
                pst.setObject(8,capacityInt);


                int affectedRows = pst.executeUpdate();
                if(affectedRows<0){
                    new Alert(Alert.AlertType.ERROR,"Failed to update quarantine center details", ButtonType.OK).show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        else{
            try {
                PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO qurantineCenter VALUES(?,?,?,?,?,?,?,?,?)");
                pst.setObject(1,id);
                pst.setObject(2,name);
                pst.setObject(3,city);
                pst.setObject(4,district);
                pst.setObject(5,head);
                pst.setObject(6,headContact);
                pst.setObject(7,tel1);
                pst.setObject(8,tel2);
                pst.setObject(9,capacityInt);


                int affectedRows = pst.executeUpdate();

                if(affectedRows<0){
                    new Alert(Alert.AlertType.ERROR,"Failed to add quarantine center details", ButtonType.OK).show();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        loadQuarantineCenters();

        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setText("Save");
        clearTextFields();

    }

    public void btnDelete_OnAction(ActionEvent actionEvent) {
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setText("Save");
        clearTextFields();

        String id = txtID.getText();

        try {
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM qurantineCenter WHERE id='" + id + "'");
            int affectedRows = pst.executeUpdate();

            if(affectedRows<0){
                new Alert(Alert.AlertType.ERROR,"Failed to delete quarantine center details", ButtonType.OK).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadQuarantineCenters();
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
            centers.add(new QuarantineCentersTM(id, name, city, district, head, headContact, tel1, tel2, capacity));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void IdAutoIncrement(){

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT id FROM qurantineCenter ORDER BY id DESC LIMIT 1");

            while(rst.next()){
                String id = rst.getString(1);

                if(id==null){
                    txtID.setText("Q001");
                }
                else {
                    String val = id.substring(1, 4);
                    int value = Integer.parseInt(val);

                    if (value < 10) {
                        value += 1;
                        txtID.setText("Q00"+value);
                    }
                    else if(value<100){
                        value +=1;
                        txtID.setText("Q0"+value);
                    }
                    else{
                        value+=1;
                        txtID.setText("Q"+value);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    private void clearTextFields(){
        txtName.clear();
        txtCapacity.clear();
        txtCity.clear();
        txtHead.clear();
        txtHeadContact.clear();
        txtTel1.clear();
        txtTel2.clear();
        cmbDistricts.getSelectionModel().clearSelection();
    }

    @SuppressWarnings("Duplicates")
    private void enableTextFields(){
        txtName.setDisable(false);
        txtID.setDisable(false);
        txtCapacity.setDisable(false);
        txtCity.setDisable(false);
        txtHead.setDisable(false);
        txtHeadContact.setDisable(false);
        txtTel1.setDisable(false);
        txtTel2.setDisable(false);
        cmbDistricts.setDisable(false);
    }

    @SuppressWarnings("Duplicates")
    private void disableTextFields(){
        txtName.setDisable(true);
        txtID.setDisable(true);
        txtCapacity.setDisable(true);
        txtCity.setDisable(true);
        txtHead.setDisable(true);
        txtHeadContact.setDisable(true);
        txtTel1.setDisable(true);
        txtTel2.setDisable(true);
        cmbDistricts.setDisable(true);
    }
}
