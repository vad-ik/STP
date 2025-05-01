package com.github.vad_ik.STP.service;

import com.github.vad_ik.STP.config.constants.WindowConstantHolder;
import com.github.vad_ik.STP.graphics.NodeTableUI;
import com.github.vad_ik.STP.graphics.myNode.ConnectionRouter;
import com.github.vad_ik.STP.graphics.myNode.SwitchView;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Service;

@Service
public class ClearService {

    private final NodeTableUI nodeTableUI;
    private final WindowConstantHolder constant;

    public ClearService(NodeTableUI nodeTableUI, WindowConstantHolder constant) {

        this.nodeTableUI = nodeTableUI;
        this.constant = constant;
    }

    public void clear(Pane activeRegion) {
        nodeTableUI.clear();
        for (Node child : activeRegion.getChildren()) {
            if (child instanceof SwitchView switchView) {
                if (!switchView.isDisable()) {
                    nodeTableUI.addNode(switchView.getSwitchModel().getRouterID());
                    switchView.getSwitchModel().reset();
                }
            } else if (child instanceof ConnectionRouter connectionRouter) {
                if (!connectionRouter.isDis()) {
                    connectionRouter.setActive(true);
                    connectionRouter.getMeinLine().setStroke(constant.COLOR_LINE);
                    connectionRouter.getMeinLine().setStrokeWidth(constant.WIDTH_LINE);
                }
            }
        }
    }
}
