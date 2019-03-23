package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import sample.Database.DatabaseHandler;
import sample.animation.Fade;
import sample.animation.Shaker;
import sample.model.MaxCharLength;
import sample.model.Task;


import java.io.IOException;
import java.sql.SQLException;

import java.util.Calendar;

public class AddItemFormController {
private FXMLLoader fxmlLoader=null;
    @FXML
    private JFXButton saveTaskButton;

    @FXML
    private JFXTextField taskInput;
    @FXML
    private JFXButton myTasksButton;

    @FXML
    private JFXTextField descriptionInput;
    @FXML
    private Label taskAddedLabel;

    private DatabaseHandler databaseHandler;

    @FXML
    private MenuBar menuar;

    @FXML
    private Menu menuUser;

    private int taskNumber=0;

    private double xOffset = 0;
    private double yOffset = 0;



    @FXML
    void initialize(){
menuUser.setText(AddItemController.getUserName());

menuUser.setOnAction(event -> {
    profileScreen();


});
        taskInput.addEventFilter(KeyEvent.KEY_TYPED,new MaxCharLength(45));
        descriptionInput.addEventFilter(KeyEvent.KEY_TYPED,new MaxCharLength(45));

        checkTaskNum();



        // key type listener
        taskInput.setOnKeyTyped(event ->{
            //Disable taskLabel
            taskAddedLabel.setVisible(false);



                //when the label is empty -> Disable the Button
             if (taskInput.getText().trim().isEmpty() ||descriptionInput.getText().trim().isEmpty()){
                     saveTaskButton.setDisable(true);

                 }

                     else {
                     saveTaskButton.setDisable(false);

            }

        });

        descriptionInput.setOnKeyTyped(event ->{
            //Disable taskLabel
            taskAddedLabel.setVisible(false);




                //when the label is empty -> Disable the Button
             if (descriptionInput.getText().trim().isEmpty() || taskInput.getText().trim().isEmpty()){
                saveTaskButton.setDisable(true);

                 }

                     else {
                     saveTaskButton.setDisable(false);

                 }


        });

        // OnClick "Save Task" Button.
        saveTaskButton.setOnAction(event -> {


            //run the method saveTask
            saveTask();
        });

        myTasksButton.setOnAction(event -> {

            goTasksWindow();

        });





    }

    private void saveTask(){

        //get Calendar date local zone
        Calendar calendar = Calendar.getInstance();
        //get the time zone in milliSeconds
        java.sql.Timestamp timestamp = new java.sql.Timestamp(calendar.getTimeInMillis());

        //Create new Task object
        Task task = new Task();


        // set into task the values
        task.setUserID(AddItemController.userId);
        task.setDateCreated(timestamp);
        task.setDescription(descriptionInput.getText());
        task.setTask(taskInput.getText());



        try {
            //add into the Database the task
             databaseHandler = new DatabaseHandler();
            databaseHandler.insertTask(task);

            //clear the input
            taskInput.clear();
            descriptionInput.clear();



        } catch (ClassNotFoundException e) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText(null);
            a1.setContentText("Error caused by "+e.getMessage());
        } catch (SQLException e) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText(null);
            a1.setContentText("Error caused by "+e.getMessage());
        }
        //disable the button
        saveTaskButton.setDisable(true);



        //reChecking the taskNumber -> after adding new task
        checkTaskNum();


        //show Success label
        taskAddedLabel.setVisible(true);

        //animation
        Fade fade = new Fade(taskAddedLabel);
        fade.play();

        Shaker shaker = new Shaker(taskAddedLabel);
        shaker.Shake();



    }

    private void goTasksWindow(){
        myTasksButton.getScene().getWindow().hide();
        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/list.fxml"));

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


    private void profileScreen( ){


        if (fxmlLoader ==null) {
            fxmlLoader = new FXMLLoader();

            fxmlLoader.setLocation(getClass().getResource("/sample/view/profile.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setHeaderText(null);
                a1.setContentText("Error caused by " + e.getMessage());
                a1.show();
            }

            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root));
            stage.show();
            stage.requestFocus();





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

    private void checkTaskNum(){
        //get the taskNumber from DatabaseHandler
        try {
            databaseHandler = new DatabaseHandler();
            taskNumber = databaseHandler.getTasksNumber(AddItemController.userId);

            // if the taskNumber is 0 then disable the button , else enable it
            if (taskNumber==0) {
                myTasksButton.setDisable(true);
            }else {
                myTasksButton.setDisable(false);
            }
        } catch (SQLException e) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText(null);
            a1.setContentText("Error caused by "+e.getMessage());
            a1.show();

        } catch (ClassNotFoundException e) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText(null);
            a1.setContentText("Error caused by "+e.getMessage());
            a1.show();
        }
        myTasksButton.setText("My Tasks ("+taskNumber+")");
    }


    public void setFxmlLoader() {
        this.fxmlLoader = null;

    }
}
