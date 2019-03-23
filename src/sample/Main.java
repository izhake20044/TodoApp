package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Database.DatabaseHandler;


public class Main extends Application {

    DatabaseHandler databaseHandler;
    private double xOffset = 0;
    private double yOffset = 0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        databaseHandler = new DatabaseHandler();
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/login.fxml"));

        //hide title bar
        primaryStage.initStyle(StageStyle.UNDECORATED);
       //drag window
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
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
            }
        });

        primaryStage.setTitle("Todo Application");

     // primaryStage.setScene(new Scene(root, 640, 370));

        primaryStage.setScene(new Scene(root, 580, 300));

        primaryStage.show();



    }




    public static void main(String[] args) {
        launch(args);

    }
}
