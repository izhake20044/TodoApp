package sample.animation;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Fade {

    private FadeTransition fadeTransition;


    public Fade(Node node,int millis) {
        fadeTransition = new FadeTransition(Duration.millis(millis),node);
        fadeTransition.setFromValue(1f);
        fadeTransition.setToValue(0f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);



    }

    public Fade(Node node) {
        fadeTransition = new FadeTransition(Duration.millis(1500),node);
        fadeTransition.setFromValue(0f);
        fadeTransition.setToValue(1f);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);



    }
    public void play(){
        fadeTransition.play();
    }


}
