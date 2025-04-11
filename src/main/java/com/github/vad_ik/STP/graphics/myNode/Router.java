package com.github.vad_ik.STP.graphics.myNode;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Router extends Pane {
    private final int routerID;
    private final double x;
    private final double y;
    private final Circle circle;
    private final Text nodeIdText;
    private final ArrayList<ConnectionRouter> connection = new ArrayList<>();


    public Router(double v, int routerID, double x, double y) {
        this.routerID = routerID;
        this.x = x;
        this.y = y;

        circle = new Circle(v);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setFill(Color.BLUE);     // Цвет круга
        this.getChildren().add(circle);

        nodeIdText = new Text(x + 1.5 * v, y - 1.5 * v, "" + routerID);
        this.getChildren().add(nodeIdText);
    }
}
