package com.github.vad_ik.STP.graphics.myNode;

import com.github.vad_ik.STP.service.demonstration.SPTProtocolDemonstrationService;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import lombok.Getter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Getter
@Service
@Scope("prototype")
public class SwitchView extends Pane {
    private Circle circle;
    private Text nodeType;
    private Text distanceText;
    private Text nodeIdText;
    private SwitchModel switchModel;

    private final ObjectProvider<SwitchModel> switchModelProvider;

    @Autowired
    public SwitchView(ObjectProvider<SwitchModel> switchModelProvider) {
        this.switchModelProvider = switchModelProvider;
    }

    public void initialize(double x, double y, double size, int routerID) {
        switchModel = switchModelProvider.getObject();
        switchModel.initialize(routerID, x, y);

// TODO мб в утилсы
        circle = new Circle(size);
        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setFill(Color.BLUE);     // Цвет круга
        this.getChildren().add(circle);
// TODO мб в константы
        nodeIdText = new Text(x + 1.5 * size, y - 1.5 * size, "" + routerID);
        this.getChildren().add(nodeIdText);
// TODO мб в константы
        nodeType = new Text(x, y + 2 * size, "");
        this.getChildren().add(nodeType);
// TODO мб в константы
        distanceText = new Text(x, y + 4 * size, "");
        this.getChildren().add(distanceText);
        switchModel.addListener(updatedModel ->
        {
            if (updatedModel.getDistanceToRoot() != null) {
                distanceText.setText("Расстояние до корня: " + updatedModel.getDistanceToRoot());
            }
            if (updatedModel.isNodeType()) {
                nodeType.setText("корневой коммутатор");
            } else {
                nodeType.setText("не корневой коммутатор, присутствует коммутатор с меньшим ID");
            }
        });
    }

    public double getX() {
        return switchModel.getX();
    }

    public double getY() {
        return switchModel.getY();
    }

    public SPTProtocolDemonstrationService getDemonstration() {
        return switchModel.getDemonstration();
    }
}
