package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Alert;

import sample.Database.DatabaseHandler;


import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileController {


    @FXML
    private JFXTextField userNameField;

    @FXML
    private JFXTextField firstNameField;

    @FXML
    private JFXTextField lastNameField;

    @FXML
    private JFXTextField genderField;
    @FXML
    private JFXButton closeButton;

    private String gender="";
    private String lastName="";
    private String firstName="";

    @FXML
    void exitProfile(ActionEvent event) {

        closeButton.getScene().getWindow().hide();
        AddItemFormController addItemFormController =new AddItemFormController();
        addItemFormController.setFxmlLoader();


        event.consume();
    }


    @FXML
    void initialize(){
        try {
            DatabaseHandler databaseHandler = new DatabaseHandler();
       ResultSet resultSet = databaseHandler.getUserDeatils(AddItemController.getUserId());


       while (resultSet.next()){
           firstName = resultSet.getString("firstname");
           gender=  resultSet.getString("gender");
           lastName =resultSet.getString("lastname");

       }
        } catch (ClassNotFoundException e) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText(null);
            a1.setContentText("Error caused by "+e.getMessage());
            a1.show();
        } catch (SQLException e) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText(null);
            a1.setContentText("Error caused by "+e.getMessage());
            a1.show();
        }
        userNameField.setText(AddItemController.getUserName());
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        genderField.setText(gender);


    }


    }

