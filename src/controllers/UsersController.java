package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import util.HospitalsTM;
import util.QuarantineCentersTM;
import util.UserTM;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class UsersController {
    public TextField txtSearchUser;
    public JFXButton btnAddUser;
    public JFXTextField txtName;
    public JFXTextField txtEmail;
    public JFXTextField txtTel;
    public JFXTextField txtUsername;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXTextField txtPassword;
    public JFXComboBox cmbUserRole;
    public JFXComboBox<HospitalsTM> cmbHospitals;
    public JFXComboBox<QuarantineCentersTM> cmbQuarantineCenters;
    public TableView<UserTM> tblUsers;

    ArrayList<UserTM> usersList = new ArrayList<>();

    public void initialize(){

        cmbHospitals.setVisible(false);
        cmbQuarantineCenters.setVisible(false);
        loadUsers();

        disableTextFields();

        btnSave.setDisable(true);
        btnDelete.setDisable(true);


        //mapping columns
        tblUsers.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("username"));
        tblUsers.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblUsers.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("role"));
        tblUsers.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblUsers.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("password"));
        tblUsers.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("contact"));


        ObservableList userRoles = cmbUserRole.getItems();
        userRoles.addAll("Admin","PSTF","Hospital IT","Quarantine Center IT");

        tblUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UserTM>() {
            @Override
            public void changed(ObservableValue<? extends UserTM> observable, UserTM oldValue, UserTM selectedUser) {
                if(selectedUser==null){
                    return;
                }
                enableTextFields();
                btnSave.setDisable(false);
                btnDelete.setDisable(false);
                btnSave.setText("Update");
                txtName.setText(selectedUser.getName());
                txtUsername.setText(selectedUser.getUsername());
                txtEmail.setText(selectedUser.getEmail());
                txtPassword.setText(selectedUser.getPassword());
                txtTel.setText(selectedUser.getContact());
                cmbUserRole.getSelectionModel().select(selectedUser.getRole());

                if (cmbUserRole.getSelectionModel().getSelectedItem().equals("Hospital IT")) {
                    cmbHospitals.setVisible(true);
                    cmbQuarantineCenters.setVisible(false);

                    try {
                        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("SELECT hospital_id FROM user_hospital WHERE username=?");
                        pst.setObject(1,txtUsername.getText());
                        ResultSet rst = pst.executeQuery();

                        ObservableList<HospitalsTM> hospitals = cmbHospitals.getItems();
                        loadHospitals();

                        while(rst.next()){
                            String hospitalId = rst.getString(1);

                            for (HospitalsTM hospital:hospitals) {
                                if (hospital.getId().equals(hospitalId)) {
                                    cmbHospitals.getSelectionModel().select(hospital);
                                }
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                else if(cmbUserRole.getSelectionModel().getSelectedItem().equals("Quarantine Center IT")){
                    cmbQuarantineCenters.setVisible(true);
                    cmbHospitals.setVisible(false);
                    loadQuarantineCenters();

                    try {
                        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("SELECT quarantineCenter_id FROM user_qurantineCenter WHERE username=?");
                        pst.setObject(1,txtUsername.getText());
                        ResultSet rst = pst.executeQuery();

                        ObservableList<QuarantineCentersTM> centers = cmbQuarantineCenters.getItems();

                        while(rst.next()){
                            String centerId = rst.getString(1);

                            for (QuarantineCentersTM center:centers){
                                if(center.getId().equals(centerId)){
                                    cmbQuarantineCenters.getSelectionModel().select(center);
                                }
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                else{
                    cmbHospitals.setVisible(false);
                    cmbQuarantineCenters.setVisible(false);
                }

            }
        });

        cmbUserRole.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String selectedRole) {
                if(selectedRole.equals("Hospital IT")){
                    cmbHospitals.setVisible(true);
                    cmbQuarantineCenters.setVisible(false);
                    loadHospitals();

                    try {
                        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("SELECT hospital_id FROM user_hospital WHERE username=?");
                        pst.setObject(1,txtUsername.getText());
                        ResultSet rst = pst.executeQuery();

                        ObservableList<HospitalsTM> hospitals = cmbHospitals.getItems();
                        loadHospitals();

                        while(rst.next()){
                            String hospitalId = rst.getString(1);

                            for (HospitalsTM hospital:hospitals) {
                                if (hospital.getId().equals(hospitalId)) {
                                    cmbHospitals.getSelectionModel().select(hospital);
                                }
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if(selectedRole.equals("Quarantine Center IT")){
                    cmbQuarantineCenters.setVisible(true);
                    cmbHospitals.setVisible(false);
                    loadQuarantineCenters();

                    try {
                        PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("SELECT quarantineCenter_id FROM user_qurantineCenter WHERE username=?");
                        pst.setObject(1,txtUsername.getText());
                        ResultSet rst = pst.executeQuery();

                        ObservableList<QuarantineCentersTM> centers = cmbQuarantineCenters.getItems();

                        while(rst.next()){
                            String centerId = rst.getString(1);

                            for (QuarantineCentersTM center:centers){
                                if(center.getId().equals(centerId)){
                                    cmbQuarantineCenters.getSelectionModel().select(center);
                                }
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        txtSearchUser.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                ObservableList<UserTM> users = tblUsers.getItems();
                users.clear();

                for (UserTM user: usersList){
                    if(user.getName().contains(newValue)||user.getUsername().contains(newValue)){
                        users.add(user);
                    }
                }
            }
        });
    }

    @SuppressWarnings("Duplicates")
    public void btnAddUser_OnAction(ActionEvent actionEvent) {
        btnSave.setText("Save");
        btnDelete.setDisable(true);
        btnSave.setDisable(false);
        cmbQuarantineCenters.setVisible(false);
        cmbHospitals.setVisible(false);

        clearTextFields();
        enableTextFields();
        loadUsers();

    }

    @SuppressWarnings("Duplicates")
    public void btnSave_OnAction(ActionEvent actionEvent) {
        btnSave.setText("Save");
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        clearTextFields();

        String name = txtName.getText();
        String email = txtEmail.getText();
        String username = txtUsername.getText();
        String contact = txtTel.getText();
        String password = txtPassword.getText();
        String role = cmbUserRole.getSelectionModel().getSelectedItem().toString();
        String id;

        if(name.trim().length()==0 ||
                email.trim().length()==0 ||
                username.trim().length()==0 ||
                contact.trim().length()==0 ||
                password.trim().length()==0 ||
                role.trim().length()==0
        ){
            new Alert(Alert.AlertType.ERROR,"The fields cannot be empty. Please fill all fields", ButtonType.OK).show();
        }

        if(role.equals("Hospital IT")) {
            HospitalsTM selectedHospital = cmbHospitals.getSelectionModel().getSelectedItem();
            id = selectedHospital.getId();
        }
        else {
            QuarantineCentersTM selectedCenter = cmbQuarantineCenters.getSelectionModel().getSelectedItem();
            id = selectedCenter.getId();
        }


        if(btnSave.getText().equals("Update")){
            try {
                PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("UPDATE user SET username=?, password=?, `name`=?, contact_no=? ,email=? ,role=? WHERE name='" + name + "'");
                pst.setObject(1,username);
                pst.setObject(2,password);
                pst.setObject(3,name);
                pst.setObject(4,contact);
                pst.setObject(5,email);
                pst.setObject(6,role);
                int affectedRows = pst.executeUpdate();


                if(affectedRows<0){
                    new Alert(Alert.AlertType.ERROR,"Failed to update entry", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(role.equals("Hospital IT")){
                try {
                    PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("SELECT hospitalId FROM user_hospital WHERE username=?");
                    pst.setObject(1,name);
                    ResultSet resultSet = pst.executeQuery();

                    if(!resultSet.next()){
                        PreparedStatement preparedStatement;
                        preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM user_center WHERE username=?");
                        preparedStatement.setObject(1,name);
                        preparedStatement.executeUpdate();


                        preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO user_hospital VALUES (?,?)");
                        preparedStatement.setObject(1,name);
//                        preparedStatement.setObject(2,cmbHospitals.getSelectionModel().getSelectedItem().getId());
                        preparedStatement.setObject(2,id);
                        preparedStatement.executeUpdate();


                    }
                    else{
                        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE user_hospital SET username=?,hospitalId=? WHERE username='"+txtUsername.getText()+"'");
                        preparedStatement.setObject(1,name);
                        preparedStatement.setObject(2,id);
                        preparedStatement.executeUpdate();

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if(role.equals("Quarantine Center IT")){
                try {
                    PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("SELECT centerId FROM user_center WHERE username=?");
                    pst.setObject(1,name);
                    ResultSet resultSet = pst.executeQuery();

                    if(!resultSet.next()){

                        PreparedStatement preparedStatement;
                        preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM user_hospital WHERE username=?");
                        preparedStatement.setObject(1,name);
                        preparedStatement.executeUpdate();


                        preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO user_center VALUES (?,?)");
                        preparedStatement.setObject(1,name);
                        preparedStatement.setObject(2,id);
                        preparedStatement.executeUpdate();


                    }
                    else{
                        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE user_center SET username=?,centerId=? WHERE username='"+txtUsername.getText()+"'");
                        preparedStatement.setObject(1,name);
                        preparedStatement.setObject(2,id);
                        preparedStatement.executeUpdate();
                    }



                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }


        else{
            try {
                PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO user VALUES(?,?,?,?,?,?)");
                pst.setObject(1,username);
                pst.setObject(2,password);
                pst.setObject(3,name);
                pst.setObject(4,contact);
                pst.setObject(5,email);
                pst.setObject(6,role);
                int affectedRows = pst.executeUpdate();


                if(affectedRows<0){
                    new Alert(Alert.AlertType.ERROR,"Failed to add entry", ButtonType.OK).show();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(role.equals("Hospital IT")){
                try {
                    PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO user_hospital VALUES(?,?)");
                    pst.setObject(1,username);
                    pst.setObject(2,id);
                    int affectedRows = pst.executeUpdate();

                    if(affectedRows<0){
                        new Alert(Alert.AlertType.ERROR,"Failed to add entry", ButtonType.OK).show();
                    }
                    else{
                        System.out.println("Successful");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }

            if(role.equals("Quarantine Center IT")){
                try {
                    PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO user_qurantineCenter VALUES(?,?)");
                    pst.setObject(1,username);
                    pst.setObject(2,id);
                    int affectedRows = pst.executeUpdate();


                    if(affectedRows<0){
                        new Alert(Alert.AlertType.ERROR,"Failed to add entry", ButtonType.OK).show();
                    }
                    else{
                        System.out.println("Successful");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        loadUsers();

    }

    @SuppressWarnings("Duplicates")
    public void btnDelete_OnAction(ActionEvent actionEvent) {
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
        btnSave.setText("Update");
        clearTextFields();


        String role = cmbUserRole.getSelectionModel().getSelectedItem().toString();

        if(role.equals("Hospital IT")){
            try {
                PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM user_hospital WHERE username='" + txtUsername.getText() + "'");
                int affectedRows = pst.executeUpdate();

                if(affectedRows<0){
                    new Alert(Alert.AlertType.ERROR,"Failed to add entry", ButtonType.OK).show();
                }
                else{
                    System.out.println("Delete Successful");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(role.equals("Quarantine Center IT")){
            try {
                PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM user_qurantineCenter WHERE username='" + txtUsername.getText() + "'");
                int affectedRows = pst.executeUpdate();

                if(affectedRows<0){
                    new Alert(Alert.AlertType.ERROR,"Failed to add entry", ButtonType.OK).show();
                }
                else{
                    System.out.println("Delete Successful");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        try {
            PreparedStatement pst = DBConnection.getInstance().getConnection().prepareStatement("DELETE FROM user WHERE username='" + txtUsername.getText() + "'");
            int affectedRows = pst.executeUpdate();

            if(affectedRows<0){
                new Alert(Alert.AlertType.ERROR,"Failed to add entry", ButtonType.OK).show();
            }
            else{
                System.out.println("Delete Successful");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadUsers();

    }

    @SuppressWarnings("Duplicates")
    private void loadUsers(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM user");

            ObservableList<UserTM> users = tblUsers.getItems();
            users.clear();

            while(rst.next()){
                String username = rst.getString(1);
                String password = rst.getString(2);
                String name = rst.getString(3);
                String contact = rst.getString(4);
                String email = rst.getString(5);
                String role = rst.getString(6);

                users.add(new UserTM(username,password,name,contact,email,role));
                usersList.add(new UserTM(username,password,name,contact,email,role));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Duplicates")
    private void loadHospitals(){
        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM hospital");

            ObservableList<HospitalsTM> hospitals = cmbHospitals.getItems();
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

    @SuppressWarnings("Duplicates")
    private void loadQuarantineCenters(){
        try {

            ObservableList<QuarantineCentersTM> quarantineCenters = cmbQuarantineCenters.getItems();
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

    @SuppressWarnings("Duplicates")
    private void clearTextFields(){
        txtName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtTel.clear();
        txtUsername.clear();
        cmbUserRole.getSelectionModel().clearSelection();
    }

    @SuppressWarnings("Duplicates")
    private void enableTextFields(){
        txtUsername.setDisable(false);
        txtEmail.setDisable(false);
        txtName.setDisable(false);
        txtPassword.setDisable(false);
        txtTel.setDisable(false);
        cmbUserRole.setDisable(false);
    }

    @SuppressWarnings("Duplicates")
    private void disableTextFields(){
        txtUsername.setDisable(true);
        txtEmail.setDisable(true);
        txtName.setDisable(true);
        txtPassword.setDisable(true);
        txtTel.setDisable(true);
        cmbUserRole.setDisable(true);
    }
}
