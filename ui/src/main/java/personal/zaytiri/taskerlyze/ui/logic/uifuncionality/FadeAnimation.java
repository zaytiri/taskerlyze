package personal.zaytiri.taskerlyze.ui.logic.uifuncionality;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class FadeAnimation {

    public void fadeIn(
            long fadeDuration,
            long fadeDelay,
            Node nodeToFade,
            EventHandler<ActionEvent> setOnFinished) {
        fade(fadeDuration, fadeDelay, 0, 1, nodeToFade, setOnFinished);
    }

    public void fadeOut(
            long fadeDuration,
            long fadeDelay,
            Node nodeToFade,
            EventHandler<ActionEvent> setOnFinished) {
        fade(fadeDuration, fadeDelay, 1, 0, nodeToFade, setOnFinished);
    }

    private void fade(
            long fadeDuration,
            long fadeDelay,
            int fadeFromValue,
            int fadeToValue,
            Node nodeToFade,
            EventHandler<ActionEvent> setOnFinished) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                FadeTransition fade = new FadeTransition(Duration.millis(fadeDuration), nodeToFade);
                fade.setFromValue(fadeFromValue);
                fade.setToValue(fadeToValue);
                fade.setAutoReverse(true);
                fade.play();
                fade.setOnFinished(event -> {
                    setOnFinished.handle(new ActionEvent());
                });
            }
        }, fadeDelay); // 10 seconds
    }
}
