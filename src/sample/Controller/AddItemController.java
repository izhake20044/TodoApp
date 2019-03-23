package sample.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;

import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;


import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Database.DatabaseHandler;
import sample.animation.Fade;
import sample.animation.Shaker;


import java.io.IOException;
import java.sql.SQLException;

public class AddItemController {

    static int userId;
    static String userName;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private ImageView plusImage;
    @FXML
     private AnchorPane addItemAncorPaneRoot;

    @FXML
    private Menu menuUser;
    @FXML
    private Hyperlink tasksHyper;
    @FXML
    private Label tasksLabel;
    @FXML
    private StackPane stackPaneRoot;

    private FXMLLoader fxmlLoader = null;
    private int tasks = 0;
    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {

        try {
            databaseHandler = new DatabaseHandler();

            tasks = databaseHandler.getTasksNumber(LoginContoller.userId);


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
        if (tasks > 0) {
            tasksHyper.setVisible(true);
            tasksLabel.setText("View your last Tasks");
            tasksHyper.setText("My tasks (" + tasks + ")");
        }

        tasksHyper.setOnMouseClicked(event -> {
            goTasksWindow();
        });


        plusImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            goAddItemFormWindow();


        });

        menuUser.setOnAction(event -> {
          profileScreen();

        });

    }
    private void goTasksWindow(){
        tasksHyper.getScene().getWindow().hide();
        FXMLLoader fxmlLoader = new FXMLLoader();
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

    private void goAddItemFormWindow() {


        Shaker imageShak = new Shaker(plusImage);
        imageShak.Shake();

        FXMLLoader loader = new FXMLLoader();
        try {


            AnchorPane anchorPane = loader.load(getClass().getResource("/sample/view/addItemForm.fxml"));
            addItemAncorPaneRoot.getChildren().setAll(anchorPane);

            Fade fadeAncor = new Fade(anchorPane);
            fadeAncor.play();


        } catch (Exception e) {
            Alert a1 = new Alert(Alert.AlertType.ERROR);
            a1.setHeaderText(null);
            a1.setContentText("Error caused by "+e.getMessage());
            a1.show();
        }
    }

    private void profileScreen(){


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


    public static int getUserId() {
        return userId;
    }

    public static String getUserName() {
        return userName;
    }
    void setUserId(int userId){
        AddItemController.userId =userId;

    }
    void setUserName(String userName) {
        AddItemController.userName = userName;
        menuUser.setText(AddItemController.userName );

    }

    }
