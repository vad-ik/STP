package com.github.vad_ik.STP.utils;

import com.github.vad_ik.STP.graphics.myNode.SwitchView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class FxUtils {
    private FxUtils() {
    }

    public static Line getLine(SwitchView connectedNode1, SwitchView connectedNode2, int width, Color color) {

        return getLine(connectedNode1.getX(), connectedNode1.getY(), connectedNode2.getX(), connectedNode2.getY(),  width,  color);
    }
    public static Line getLine(double x1,double y1,double x2,double y2, int width, Color color) {
        Line line;
        line = new Line(x1, y1, x2, y2);
        line.setStrokeWidth(width);
        line.setStroke(color);
        return line;
    }


    public static void setDotted(Line line) {
        // Настройка пунктира (длина штриха и пробела)
        double len = 5;
        line.getStrokeDashArray().addAll(len, len * 2);

        // Устанавливаем начальное смещение
        line.setStrokeDashOffset(0);

        // Анимация движения пунктира
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(line.strokeDashOffsetProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(line.strokeDashOffsetProperty(), 3 * len))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    public static Circle getCircle(double size,double x,double y,Color color){
        Circle circle= new Circle(size);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setFill(color);
        return  circle;
    }

}
