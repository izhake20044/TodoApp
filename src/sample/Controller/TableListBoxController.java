package sample.Controller;

import com.jfoenix.controls.JFXListCell;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Database.DatabaseHandler;
import sample.model.Task;



import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TableListBoxController extends JFXListCell<Task> {


    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private ImageView statusImageView;


    @FXML
    private Label taskLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label descriptionLbael;
    @FXML
    private ImageView deleteButton;
    @FXML
    private ImageView updateImage;

    private FXMLLoader fxmlLoader;
    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {


        updateImage.setOnMouseClicked(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/updateTask.fxml"));
            try {
                loader.load();

                UpdateTaskController controller = loader.getController();
                controller.setUpdateTaskLabel(getItem().getTask());
                controller.setUpdateDescriptionLabel(getItem().getDescription());
                controller.updateTask(getItem());
                controller.refreshList();

            } catch (IOException e) {
                Alert a1 = new Alert(Alert.AlertType.ERROR);
                a1.setHeaderText(null);
                a1.setContentText("Error caused by " + e.getMessage());
                a1.show();
            }

            Parent root = loader.getRoot();

            Stage stage = new Stage();
          stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();



        });
    }

    @Override
    protected void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setText(null);
            setGraphic(null);
        } else {

            if (fxmlLoader == null) {

                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/tableListBox.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();


                } catch (IOException e) {

                    Alert a1 = new Alert(Alert.AlertType.ERROR);
                    a1.setHeaderText(null);
                    a1.setContentText("Error caused by " + e.getMessage());
                    a1.show();
                }
            }
            //set into the Cell the values
            dateLabel.setText(task.getDateCreated().toString());
            descriptionLbael.setText(task.getDescription());
            taskLabel.setText(task.getTask());


            int taskId = task.getTaskId();




            deleteButton.setOnMouseClicked(event -> {

                try {
                    databaseHandler = new DatabaseHandler();
                    databaseHandler.deleteTask(AddItemController.userId, taskId);


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
                getListView().getItems().remove(getItem());
            });
            setText(null);
            setGraphic(rootAnchorPane);



        }

    }
}

