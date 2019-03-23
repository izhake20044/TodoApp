package sample.Controller;

import com.jfoenix.controls.*;

import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import sample.Database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrationController {

    private double xOffset = 0;
    private double yOffset = 0;


    @FXML
    private JFXCheckBox maleCheckBox;

    @FXML
    private JFXCheckBox femaleCheckBox;

    @FXML
    private JFXTextField userNameTextF;

    @FXML
    private JFXTextField firstNameTextF;

    @FXML
    private JFXTextField lastNameTextF;

    @FXML
    private JFXPasswordField passwordTextF;

    @FXML
    private JFXPasswordField REpasswordTextF;

    @FXML
    private JFXButton signUpButton;


    @FXML
    private Label passwordError;

    @FXML
    private Label rePasswordError;

    @FXML
    private Label firstNameError;

    @FXML
    private Label lastNameError;

    @FXML
    private Label userNameError;

    @FXML
    private Hyperlink signInHyper;

    private boolean userExists =false;

        public void initialize() {

            signInHyper.setOnAction(event -> {

               loginScreen();

            });


            // on click "male" check box . female will disSelected
        maleCheckBox.setOnAction(event -> {

            femaleCheckBox.setSelected(false);
            if (!femaleCheckBox.isSelected()) maleCheckBox.setSelected(true);
        });

    // on click "female" check box . male will disSelected
        femaleCheckBox.setOnAction(event -> {
        maleCheckBox.setSelected(false);
        if (!maleCheckBox.isSelected()) femaleCheckBox.setSelected(true);
    });



        // on click signUp Button ----->
        signUpButton.setOnAction(event -> {


            //get the values from text fields
            String userNameText =  userNameTextF.getText().trim();
            String firstNameText = firstNameTextF.getText().trim();
            String lastNameText  = lastNameTextF.getText().trim();
            String passwordText = passwordTextF.getText().trim();
            String rePasswordText = REpasswordTextF.getText().trim();

            try {
                DatabaseHandler databaseHandler = new DatabaseHandler();

                userExists = databaseHandler.userExists(userNameText);






            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            // if all the user input tables are empty
                if (userNameText.equals("")
                        && firstNameText.equals("")
                        && lastNameText.equals("")
                        && passwordText.equals("")
                        && rePasswordText.equals("")) {

                    rePasswordError.setText("Password not matching!");
                    userNameError.setText("Please enter Username");
                    lastNameError.setText("Please enter Last name");
                    firstNameError.setText("Please enter First name");


                    //Disable sign up button
                    signUpButton.setDisable(true);
                }
                else if (userExists){
                    userNameError.setText("This user already exists");
                }



                // checks if the passwords are not matching
             else   if (!passwordText.equals(rePasswordText)) {

                    // passwords NOT matching
                    rePasswordError.setText("Password not matching!");

                }


                // if  userNameTextF is  empty
                else if (userNameText.equals("")) {

                    userNameError.setText("Please enter Username");
                    userNameTextF.requestFocus();


                }


               else if (passwordText.equals("")) {

                    // set passwordError text
                    passwordError.setText("Please enter Password");

                    // get focus on user input line
                    passwordTextF.requestFocus();


                }


               else if (firstNameText.equals("")) {

                    // set firstNameError text
                    firstNameError.setText("Please enter First name");

                    // get focus on user input line
                    firstNameTextF.requestFocus();


                }


                else if (lastNameText.equals("")) {
                    // set lastNameError text
                    lastNameError.setText("Please enter Last name");

                    // get focus on user input line
                    lastNameTextF.requestFocus();


                }

                // if all the texts field are filled.
                // then it will Sign up the user
            else {
                    //create the user
                    createUser();

                    //setup alert
                Alert a1 = new Alert(Alert.AlertType.INFORMATION);
                a1.setTitle("Registration");
                a1.setContentText("You Have Been Successfully Registered!");
                a1.setHeaderText(null);
                a1.show();


                //close the current window and go into login window
                a1.setOnCloseRequest(event1 -> {
                   loginScreen();
                });
            }



            // set key listener for Enable the button
            // if the user type into the Text field
            firstNameTextF.setOnKeyTyped((KeyEvent event2)->{
                signUpButton.setDisable(false);

                firstNameError.setText("");
            });

            userNameTextF.setOnKeyTyped((KeyEvent event1)->{
                signUpButton.setDisable(false);
            userNameError.setText("");
            });

            lastNameTextF.setOnKeyTyped((KeyEvent event2) -> {
                    signUpButton.setDisable(false);
            lastNameError.setText("");

            });
            passwordTextF.setOnKeyTyped((KeyEvent event2)->{
                passwordError.setText("");

            });
            REpasswordTextF.setOnKeyTyped((KeyEvent event2)->{
                rePasswordError.setText("");
                });





        });

    }



    private void createUser(){
        String userName =  userNameTextF.getText().trim();
        String firstName = firstNameTextF.getText().trim();
        String lastName  = lastNameTextF.getText().trim();
        String password = passwordTextF.getText().trim();

        String gender;
        if(femaleCheckBox.isSelected()){
            gender = "female";
        }else{
            gender="male";
        }


        try {
            DatabaseHandler databaseHandler = new DatabaseHandler();

            User user = new User(userName,password,firstName,lastName,gender);

            databaseHandler.signUpUser(user);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void loginScreen(){
        //hide the current window
        signUpButton.getScene().getWindow().hide();

        //load the login window
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/login.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = fxmlLoader.getRoot();
        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

        // set the window Draggable
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
