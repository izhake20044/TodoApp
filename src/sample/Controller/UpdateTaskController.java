package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import sample.Database.DatabaseHandler;
import sample.model.Task;


import java.io.IOException;
import java.sql.SQLException;

public class UpdateTaskController {


    @FXML
    private JFXTextField updateTaskLabel;

    @FXML
    private JFXButton updateButton;

    @FXML
    private JFXTextField updateDescriptionLabel;

    @FXML
    private ImageView exitButton;

    @FXML
    void initialize(){


        exitButton.setOnMouseClicked(event -> {
            exitButton.getScene().getWindow().hide();
        });



    }



    public void updateTask(Task task)
    {
        updateButton.setOnAction(event -> {
            task.setTask(updateTaskLabel.getText());
            task.setDescription(updateDescriptionLabel.getText());


            try {
                DatabaseHandler databaseHandler = new DatabaseHandler();
                databaseHandler.updateTask(task);


            } catch (ClassNotFoundException e) {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setHeaderText(null);
                a1.setContentText("Error caused by " + e.getMessage());
                a1.show();
            } catch (SQLException e) {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setHeaderText(null);
                a1.setContentText("Error caused by " + e.getMessage());
                a1.show();
            }
            exitButton.getScene().getWindow().hide();
            refreshList();
        });
    }

    public void setUpdateTaskLabel(String task){
        this.updateTaskLabel.setText(task);
    }
    public void setUpdateDescriptionLabel(String desc){
        this.updateDescriptionLabel.setText(desc);
    }
public void refreshList(){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/view/list.fxml"));
    try {
        fxmlLoader.load();
        ListController listController = fxmlLoader.getController();
        listController.refreshlist();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}
