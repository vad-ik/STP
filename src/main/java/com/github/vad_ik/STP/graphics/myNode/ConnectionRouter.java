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
import lombok.Setter;

@Getter
@Setter
public class ConnectionRouter extends Pane {
    private final SwitchView connectedNode1;
    private final SwitchView connectedNode2;
    Line line;
    private boolean active = true;

    public ConnectionRouter(SwitchView connectedNode1, SwitchView connectedNode2) {
        this.connectedNode1 = connectedNode1;
        this.connectedNode2 = connectedNode2;

        // Создаем пунктирную линию
        line = new Line(connectedNode1.getX(), connectedNode1.getY(), connectedNode2.getX(), connectedNode2.getY());
        line.setStrokeWidth(2);
        line.setStroke(Color.DARKRED);
        getChildren().add(line);
    }

    public boolean isConnected(SwitchView conn1, SwitchView conn2) {
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

    public void offLine() {
        line.setStroke(Color.BLACK);
    }

    public void onLine() {
        line.setStroke(Color.DARKRED);
    }

    @Override
    public String toString() {
        return "ConnectionRouter{" +
                "connectedNode2=" + connectedNode2.getSwitchModel().getRouterID() +
                ", connectedNode1=" + connectedNode1.getSwitchModel().getRouterID() +
                '}';
    }
}
