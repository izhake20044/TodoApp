package sample.Controller;



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.image.ImageView;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import sample.Database.DatabaseHandler;

import sample.animation.Fade;
import sample.model.Task;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ListController {

    private DatabaseHandler databaseHandler;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private JFXListView<Task> listView;
    private ObservableList<Task> tasks;
    private ObservableList<Task> reFreshtasks;

    @FXML
    private ImageView returnImage;
    @FXML
    public AnchorPane ancorPaneRoot;
    @FXML
    private AnchorPane updateTaskAncorpane;



    @FXML
    private JFXTextField updateTaskLabel;
    @FXML
    private JFXTextField updateDescriptionLabel;


    @FXML
    private JFXButton updateButton;
    @FXML
    private ImageView refreshButton;




    private FXMLLoader fxmlLoader = null;

    @FXML
    private Menu menuUser;

    @FXML
    void initialize() {

        refreshButton.setOnMouseClicked(event -> {
            refreshlist();
        });


        returnImage.setOnMouseClicked(event -> {
            goBack();
        });
        menuUser.setText(AddItemController.getUserName());

        //display the profile window on click
        menuUser.setOnAction(event -> {
            profileScreen();
        });

        tasks = FXCollections.observableArrayList();

        //getting the user tasks from dataBase
        try {
            databaseHandler = new DatabaseHandler();


            // resultSet contains the rows from database
            ResultSet resultSet = databaseHandler.getUserTasks(AddItemController.userId);

            while (resultSet.next()) {
                Task task = new Task(); //Creating Task object every run

                //adding into the task the values from dataBase
                task.setTaskId(resultSet.getInt("taskid"));
                task.setTask(resultSet.getString("task"));
                task.setDescription(resultSet.getString("description"));
                task.setDateCreated(resultSet.getTimestamp("datecreated"));

                //add the task into the observable List
                tasks.add(task);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // set the observableList into the ListView
        listView.setItems(tasks);

        //setting the Cell Factory by creating tableLIstBoxController
        listView.setCellFactory(tableListBoxController
                -> new TableListBoxController());


    }

    private void profileScreen() {


        if (fxmlLoader == null) {
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


            root.setOnMousePressed(event -> {


                xOffset = event.getSceneX();
                yOffset = event.getSceneY();

            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);

            });
        }


    }

    private void goBack() {
        returnImage.getScene().getWindow().hide();

        fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/addItemForm.fxml"));

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


        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);

        });
    }
    public void refreshlist(){


        reFreshtasks = FXCollections.observableArrayList();

        //getting the user tasks from dataBase
        try {
            databaseHandler = new DatabaseHandler();


            // resultSet contains the rows from database
            ResultSet resultSet = databaseHandler.getUserTasks(AddItemController.userId);

            while (resultSet.next()) {
                Task task = new Task(); //Creating Task object every run

                //adding into the task the values from dataBase
                task.setTaskId(resultSet.getInt("taskid"));
                task.setTask(resultSet.getString("task"));
                task.setDescription(resultSet.getString("description"));
                task.setDateCreated(resultSet.getTimestamp("datecreated"));

                //add the task into the observable List
                reFreshtasks.add(task);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // set the observableList into the ListView
        listView.setItems(reFreshtasks);

        //setting the Cell Factory by creating tableLIstBoxController
        listView.setCellFactory(tableListBoxController
                -> new TableListBoxController());

    }





    }











