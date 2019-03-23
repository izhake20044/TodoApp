package sample.model;


import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


//limit JFTextfield characters , by adding filterEvent
public class MaxCharLength implements EventHandler<KeyEvent> {
int limit = 0;

    public MaxCharLength(int limit){
      this.limit=limit;

    }

    @Override
    public void handle(KeyEvent event) {

        JFXTextField tx = (JFXTextField) event.getSource();
        if (tx.getText().length() >= limit) {
            event.consume();
        }
    }
}





