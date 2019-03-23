package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Database.DatabaseHandler;
import sample.animation.Shaker;
import sample.model.User;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LoginContoller {

    static int userId;
    static String userName;

    @FXML
    private JFXTextField textFiledUsername;

    @FXML
    private JFXPasswordField textFieldPassword;

    @FXML
    private JFXButton loginButton;

    @FXML
    private Hyperlink signupHyperlink;

    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    void initialize() {

loginButton.setOnAction(event -> {

    // get the username and password from JtextField.
    String username = textFiledUsername.getText().trim();
    String userpassword = textFieldPassword.getText().trim();

//if the values are not empty
    if(!username.equals("") && !userpassword.equals("")) {
        try {

            DatabaseHandler databaseHandler = new DatabaseHandler();

            //create user object
            User user = new User();

            // set into User object the password and username
            user.setUserName(username);
            user.setPassword(userpassword);

            //set the User object into DatabaseHandler
           ResultSet resultSet = databaseHandler.loginUser(user);



            //if the username and password are correct log him in
            if (resultSet.next()){   //---> its mean that the username & password are correct

                //get the user id and username from dataBase.
                //and set it into the variables .
                userId = resultSet.getInt("userid");
                userName = resultSet.getString("username");

               // load-open the addItem Screen.
                addItemScreen();


            }else{ //--> if the resultSet is empty it means that username or password are not Correct
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setHeaderText(null);
                a1.setTitle("Login failed");
                a1.setContentText("Username Or Password is incorrect");
                a1.show();

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }else{

        //if labels input is empty shake the object
        Shaker loginShaker =new Shaker(textFiledUsername);
        Shaker pwdShaker = new Shaker(textFieldPassword);
        loginShaker.Shake();
        pwdShaker.Shake();
    }
});

        // on click the hyper link Sign up
        signupHyperlink.setOnAction(event1 -> {
            // take the user into sing up view.
            signUpScreen();

        });

    }
    private void addItemScreen(){ //--> this Screen will load and open only if login Successful


        //hide the current window
        loginButton.getScene().getWindow().hide();

        //load the addItem fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/addItem.fxml"));


        try {

            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parent , root , stage
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        // pass the userName and userId into AddItemController
        AddItemController addItemController = loader.getController();



        addItemController.setUserId(userId);
        addItemController.setUserName(userName);


    }

    public void signUpScreen(){

        signupHyperlink.getScene().getWindow().hide();

        //load the sign up fxml
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/view/registration.fxml"));


        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Parent , root , stage
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));

        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }


}





