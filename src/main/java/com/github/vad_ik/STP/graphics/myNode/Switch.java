package com.github.vad_ik.STP.graphics.myNode;

import com.github.vad_ik.STP.service.demonstration.SPTProtocolDemonstrationService;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;

@Getter
public class Switch extends Pane {
    private final int routerID;
    private final double x;
    private final double y;
    private final Circle circle;
    private final Text nodeIdText;
    private final ArrayList<ConnectionRouter> connection = new ArrayList<>();

    private final SPTProtocolDemonstrationService demonstration;
    private final ApplicationContext context;
    private final Text nodeType;

    public Switch(double v, int routerID, double x, double y,ApplicationContext context) {
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


        nodeType = new Text(x , y + 2* v, "");
        this.getChildren().add(nodeType);
        this.context=context;
        demonstration = context.getBean(SPTProtocolDemonstrationService.class);

    }
    public void startSTP(int rootID){
       if (demonstration.getRootID()>rootID) {
           demonstration.startSTP(this);
       }
    }

    public void distanceToRoot() {
        if (demonstration.isRoot()) {
            demonstration.distanceToRoot(this);
        }
    }
}
