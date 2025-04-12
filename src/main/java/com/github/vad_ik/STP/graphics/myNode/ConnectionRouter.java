package com.github.vad_ik.STP.graphics.myNode;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import lombok.Getter;

@Getter
public class ConnectionRouter extends Pane {
    private final Switch connectedNode1;
    private final Switch connectedNode2;

    private boolean active;

    public ConnectionRouter(Switch connectedNode1, Switch connectedNode2) {
        this.connectedNode1 = connectedNode1;
        this.connectedNode2 = connectedNode2;

        // Создаем пунктирную линию
        Line line = new Line(connectedNode1.getX(), connectedNode1.getY(), connectedNode2.getX(), connectedNode2.getY());
        line.setStrokeWidth(2);
        line.setStroke(Color.DARKRED);
        getChildren().add(line);
    }

    public boolean isConnected(Switch conn1, Switch conn2) {
        return conn1 == connectedNode1 && conn2 == connectedNode2 || conn2 == connectedNode1 && conn1 == connectedNode2;
    }

    public void activateAnimation(boolean direction) {
        // Создаем линию (горизонтальную или под углом)
        Line line;
        if (direction) {
            line = new Line(connectedNode1.getX(), connectedNode1.getY(), connectedNode2.getX(), connectedNode2.getY());
        } else {
            line = new Line(connectedNode2.getX(), connectedNode2.getY(), connectedNode1.getX(), connectedNode1.getY());
        }
        line.setStroke(Color.ANTIQUEWHITE);
        line.setStrokeWidth(3);

        // Настройка пунктира (длина штриха и пробела)
        double len = 5;
        line.getStrokeDashArray().addAll(len, len * 2);

        // Устанавливаем начальное смещение
        line.setStrokeDashOffset(0);

        getChildren().add(line);

        // Анимация движения пунктира
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(line.strokeDashOffsetProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(line.strokeDashOffsetProperty(), 3 * len))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void offAnimation() {
        getChildren().remove(1);
    }
}
