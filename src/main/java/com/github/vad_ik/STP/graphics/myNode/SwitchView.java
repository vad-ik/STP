package com.github.vad_ik.STP.graphics.myNode;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.service.demonstration.SPTProtocolDemonstrationService;
import com.github.vad_ik.STP.utils.FxUtils;
import javafx.scene.layout.Pane;
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
    private final WindowConstantHolder constant;


    private final ObjectProvider<SwitchModel> switchModelProvider;

    @Autowired
    public SwitchView(WindowConstantHolder constant, ObjectProvider<SwitchModel> switchModelProvider) {
        this.constant = constant;
        this.switchModelProvider = switchModelProvider;
    }

    public void initialize(double x, double y, double size, int routerID) {
        switchModel = switchModelProvider.getObject();
        switchModel.initialize(routerID, x, y);

        circle = FxUtils.getCircle(size, x, y, constant.COLOR_NODE);
        getChildren().add(circle);
// TODO мб в константы
        nodeIdText = new Text(x + 1.5 * size, y - 1.5 * size, "" + routerID);
        getChildren().add(nodeIdText);
        nodeType = new Text(x, y + 2 * size, "");
        getChildren().add(nodeType);
        distanceText = new Text(x, y + 4 * size, "");
        getChildren().add(distanceText);

        switchModel.addListener(updatedModel ->
        {
            if (updatedModel.getDistanceToRoot() != null) {
                distanceText.setText(constant.LEN_TO_ROOT + updatedModel.getDistanceToRoot());
            }
            if (updatedModel.isNodeType()) {
                nodeType.setText(constant.IS_ROOT);
            } else {
                nodeType.setText(constant.IS_NOT_ROOT);
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
