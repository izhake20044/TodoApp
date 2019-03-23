package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;




public class TitleBarController {


    @FXML
    private Circle hideButton;



    @FXML
    private Circle exitButton;

    @FXML
    void initialize() {


        //set on click "red" it will exit
        exitButton.setOnMouseClicked(event -> {
            Stage stage =(Stage) exitButton.getScene().getWindow();
            stage.close();
        });

        // set on click "yellow" it will minimize
        hideButton.setOnMouseClicked(event -> {
            Stage stage = (Stage) hideButton.getScene().getWindow();
            stage.setIconified(true);

        });





    }
}
